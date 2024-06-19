package ru.goryacheva.metricsconsumer.repository;

import ru.goryacheva.metricsconsumer.model.Metric;

import java.util.List;

public interface ConsumerRepository {

    void save(Metric metric);

    Metric findById(Integer id);

    List<Metric> find();
}
