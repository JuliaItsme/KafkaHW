package ru.goryacheva.metricsproducer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.goryacheva.metricsproducer.model.Metric;

import java.util.Objects;

@Service
public class ProducerService {
    public static final String NAME_METRIC = "system.cpu.usage";

    private final KafkaTemplate<String, Metric> template;

    private final MetricsEndpoint metricsEndpoint;

    @Autowired
    public ProducerService(KafkaTemplate<String, Metric> template, MetricsEndpoint metricsEndpoint) {
        this.template = template;
        this.metricsEndpoint = metricsEndpoint;
    }

    /**
     * собираем метрику и отправляем
     */
    public void sendMetric() {
        Metric metric = new Metric();
        metric.setName(NAME_METRIC);
        metric.setValue(String.valueOf(metricsEndpoint.metric(NAME_METRIC, null)
                .getMeasurements()
                .stream()
                .filter(Objects::nonNull)
                .findFirst()
                .map(MetricsEndpoint.Sample::getValue)
                .filter(Double::isFinite)
                .orElse(0.0D)));
        template.send("metricsTopic", metric);
    }
}
