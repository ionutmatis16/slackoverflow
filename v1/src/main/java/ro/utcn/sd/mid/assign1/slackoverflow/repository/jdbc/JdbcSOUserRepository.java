package ro.utcn.sd.mid.assign1.slackoverflow.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.SOUser;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.SOUserRepository;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.jdbc.mapper.SOUserMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JdbcSOUserRepository implements SOUserRepository {
    private final JdbcTemplate template;

    @Override
    public Optional<SOUser> findBySOUsername(String name) {
        List<SOUser> sousers = template.query("SELECT * FROM sousers WHERE sousername = ?",
                new SOUserMapper(), name);
        return sousers.isEmpty() ? Optional.empty() : Optional.of(sousers.get(0));
    }

    @Override
    public SOUser save(SOUser soUser) {
        if (soUser.getId() == null) {
            soUser.setId(insert(soUser));
        } else {
            update(soUser);
        }
        return soUser;
    }

    @Override
    public Optional<SOUser> findById(Integer id) {
        List<SOUser> sousers = template.query("SELECT * FROM sousers WHERE id = ?",
                new SOUserMapper(), id);
        return sousers.isEmpty() ? Optional.empty() : Optional.of(sousers.get(0));

    }

    @Override
    public void delete(SOUser soUser) {
        template.update("DELETE FROM sousers WEHRE id = ?", soUser.getId());

    }

    @Override
    public List<SOUser> findAll() {
        return template.query("SELECT * FROM sousers", new SOUserMapper());
    }

    private int insert(SOUser sOUser) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("sousers");
        insert.usingGeneratedKeyColumns("id");
        Map<String, Object> map = new HashMap<>();
        map.put("sousername", sOUser.getSOUsername());
        map.put("sopassword", sOUser.getSOPassword());
        return insert.executeAndReturnKey(map).intValue();
    }

    private void update(SOUser sOUser) {
        template.update("UPDATE sousers SET sousername = ?, sopassword = ? WHERE id = ?",
                sOUser.getSOUsername(), sOUser.getSOPassword(), sOUser.getId());
    }
}
