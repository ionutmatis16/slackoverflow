package ro.utcn.sd.mid.assign1.slackoverflow.repository.memory;

import org.springframework.stereotype.Component;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.SOUser;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.SOUserRepository;

import java.util.Map;
import java.util.Optional;

@Component
public class InMemorySOUserRepository extends InMemoryAbstractRepository<SOUser>
        implements SOUserRepository {
    private Map<Integer, SOUser> data = super.getData();

    @Override
    public Optional<SOUser> findBySOUsername(String name) {
        for (SOUser user : data.values()) {
            if (user.getSOUsername().equalsIgnoreCase(name))
                return Optional.of(user);
        }
        return Optional.empty();
    }
}
