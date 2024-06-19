package ru.goryacheva.metricsproducer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Metric {

    /**
     * имя метрики
     */
    private String name;

    /**
     * параметры
     */
    private String value;
}
