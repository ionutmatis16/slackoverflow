package ro.utcn.sd.mid.assign1.slackoverflow.repository.jpa;

import org.springframework.stereotype.Component;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.SOUser;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.api.SOUserRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Component
public class HibernateSOUserRepository extends HibernateAbstractRepository<SOUser>
        implements SOUserRepository {
    private final EntityManager entityManager;

    public HibernateSOUserRepository(EntityManager entityManager) {
        super(entityManager, SOUser.class);
        this.entityManager = entityManager;
    }

    @Override
    public Optional<SOUser> findBySOUsername(String name) {
        CriteriaQuery<SOUser> cq = entityManager.getCriteriaBuilder().createQuery(SOUser.class);
        Root<SOUser> from = cq.from(SOUser.class);

        cq.select(from);
        cq.where(entityManager.getCriteriaBuilder().equal(from.get("sOUsername"), name)); // <- this will add the restriction.

        List<SOUser> soUsers = entityManager.createQuery(cq).getResultList();
        return soUsers.isEmpty() ? Optional.empty() : Optional.of(soUsers.get(0));
    }
}
