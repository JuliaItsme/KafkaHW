package ru.goryacheva.metricsconsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.goryacheva.metricsconsumer.model.Metric;
import ru.goryacheva.metricsconsumer.service.ConsumerService;

import java.util.List;

@RestController("/metrics")
public class ConsumerController {

    private final ConsumerService service;

    @Autowired
    public ConsumerController(ConsumerService service) {
        this.service = service;
    }

    /**
     * Получение списка всех метрик
     */
    @GetMapping
    public List<Metric> getMetrics() {
        return service.getAll();
    }

    /**
     * Получение конкретной метрики по ее идентификатору
     */
    @GetMapping("/{id}")
    public Metric getMetric(@PathVariable Integer id) {
        return service.get(id);
    }
}
