package ru.goryacheva.metricsconsumer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import ru.goryacheva.metricsconsumer.model.Metric;
import ru.goryacheva.metricsconsumer.repository.ConsumerRepository;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ConsumerService {

    private final ConsumerRepository repository;

    @Autowired
    public ConsumerService(ConsumerRepository repository) {
        this.repository = repository;
    }

    /**
     * получаем метрику и сохраняем в бд
     */
    @KafkaListener(id = "metricGroup", topics = "metricsTopic")
    public void listenAndSave(Metric obj) {
        Metric metric = new Metric();
        if(Objects.nonNull(obj)){
            metric.setName(obj.getName());
            metric.setValue(obj.getValue());
        } else
            throw new RuntimeException("metric is null");

        log.info("Полученная метрика: {}", metric);
        repository.save(metric);
    }

    /**
     * получаем битые сообщения
     */
    @KafkaListener(id = "dltGroup", topics = "metricGroup.DLT")
    public void dltListen(byte[] in, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset) {
        log.info("полученный DLT: {}, from {} @ {}", in, topic, offset);
    }

    /**
     * получение конкретной метрики по id
     * @param id метрики
     * @return метрику
     */
    public Metric get(Integer id){
        return repository.findById(id);
    }

    /**
     * получение списка метрик для анализа
     * @return список полученный из БД
     */
    public List<Metric> getAll(){
        return repository.find();
    }
}
