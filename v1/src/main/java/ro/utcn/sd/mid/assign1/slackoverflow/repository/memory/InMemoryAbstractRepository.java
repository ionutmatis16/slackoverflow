package ro.utcn.sd.mid.assign1.slackoverflow.repository.memory;

//import org.springframework.core.GenericTypeResolver;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.IDEntity;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.AbstractRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryAbstractRepository<T extends IDEntity> implements AbstractRepository<T> {
    //private final Class<T> genericType; //type will contain the class of the generic type T
    private Map<Integer, T> data = new ConcurrentHashMap<>();
    private AtomicInteger currentID = new AtomicInteger(0);


    public InMemoryAbstractRepository() {
        //this.genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(),
        //        InMemoryAbstractRepository.class);
        //genericType = clazz;
    }

    @Override
    public T save(T t) {
        if (t.getId() == null) {
            t.setId(currentID.incrementAndGet());
        }
        data.put(t.getId(), t);
        return t;
    }

    @Override
    public Optional<T> findById(Integer id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public void delete(T t) {
        data.remove(t.getId());
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(data.values());
    }

    protected Map<Integer, T> getData() {
        return data;
    }
}
