package ro.utcn.sd.mid.assign1.slackoverflow.repository;

import ro.utcn.sd.mid.assign1.slackoverflow.entity.IDEntity;

import java.util.List;
import java.util.Optional;

public interface AbstractRepository<T extends IDEntity> {
    T save(T t);

    Optional<T> findById(Integer id);

    void delete(T t);

    List<T> findAll();
}
