package ru.goryacheva.metricsconsumer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "METRIC")
public class Metric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * имя
     */
    @Column(name = "NAME")
    private String name;

    /**
     * описание
     */
    @Column(name = "VALUE")
    private String value;
}
