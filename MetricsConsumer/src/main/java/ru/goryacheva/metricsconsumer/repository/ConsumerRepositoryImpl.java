package ru.goryacheva.metricsconsumer.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.goryacheva.metricsconsumer.model.Metric;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class ConsumerRepositoryImpl implements ConsumerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Metric metric) {
        entityManager.persist(metric);
    }

    @Override
    public Metric findById(Integer id) {
        return entityManager
                .createQuery("select m from Metric m where id = :id", Metric.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<Metric> find() {
        return entityManager
                .createQuery("select m from Metric m", Metric.class)
                .getResultList();
    }
}
