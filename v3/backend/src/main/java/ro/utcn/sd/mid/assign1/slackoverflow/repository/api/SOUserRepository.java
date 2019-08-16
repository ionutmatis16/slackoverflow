package ro.utcn.sd.mid.assign1.slackoverflow.repository.api;

import ro.utcn.sd.mid.assign1.slackoverflow.entity.SOUser;

import java.util.Optional;


public interface SOUserRepository extends AbstractRepository<SOUser> {
    Optional<SOUser> findBySOUsername(String name);
}
