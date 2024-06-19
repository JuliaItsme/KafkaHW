package ru.goryacheva.metricsproducer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.goryacheva.metricsproducer.service.ProducerService;

@RestController
@RequestMapping(value = "/send")
public class ProducerController {
    private final ProducerService producerService;

    @Autowired
    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/metrics")
    public void sendMetrics() {
        producerService.sendMetric();
    }
}
