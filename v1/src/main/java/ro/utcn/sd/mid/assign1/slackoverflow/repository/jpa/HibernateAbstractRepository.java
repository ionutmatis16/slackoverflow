package ro.utcn.sd.mid.assign1.slackoverflow.repository.jpa;

import lombok.RequiredArgsConstructor;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.IDEntity;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.AbstractRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class HibernateAbstractRepository<T extends IDEntity> implements AbstractRepository<T> {
    private final EntityManager entityManager;
    private final Class<T> classType;

    @Override
    public T save(T t) {
        if (t.getId() == null) {
            entityManager.persist(t);
            return t;
        } else {
            return entityManager.merge(t);
        }
    }

    @Override
    public Optional<T> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(classType, id));
    }

    @Override
    public void delete(T t) {
        entityManager.remove(t);
    }

    @Override
    public List<T> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(classType);
        query.select(query.from(classType));
        return entityManager.createQuery(query).getResultList();
    }
}
