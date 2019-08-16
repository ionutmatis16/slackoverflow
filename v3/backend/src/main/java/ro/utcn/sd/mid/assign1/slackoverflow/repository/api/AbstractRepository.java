package ro.utcn.sd.mid.assign1.slackoverflow.repository.api;

import org.springframework.stereotype.Component;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.IDEntity;

import java.util.List;
import java.util.Optional;

@Component
public interface AbstractRepository<T extends IDEntity> {
    T save(T t);

    Optional<T> findById(Integer id);

    void delete(T t);

    List<T> findAll();
}
