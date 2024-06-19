package ru.goryacheva.metricsproducer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper;
import org.springframework.util.backoff.FixedBackOff;
import ru.goryacheva.metricsproducer.model.Metric;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    /**
     * создание топика
     */
    @Bean
    public NewTopic metricTopic() {
        return new NewTopic("metricTopic", 1, (short) 1);
    }

    /**
     * создание топика для битых сообщений
     */
    @Bean
    public NewTopic dlt(){
        return new NewTopic("metricTopic.DLT", 1, (short) 1);
    }

    /**
     * Обработчик ошибок
     */
    @Bean
    public CommonErrorHandler errorHandler(KafkaOperations<Object, Object> template) {
        return new DefaultErrorHandler(new DeadLetterPublishingRecoverer(template), new FixedBackOff(1000L, 2));
    }

    /**
     * конвертер для сообщений
     */
    @Bean
    public RecordMessageConverter converter() {
        JsonMessageConverter converter = new JsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
        typeMapper.addTrustedPackages("ru.goryacheva.metricsproducer");
        Map<String, Class<?>> mapping = new HashMap<>();
        mapping.put("metric", Metric.class);
        typeMapper.setIdClassMapping(mapping);
        converter.setTypeMapper(typeMapper);
        return converter;
    }
}
