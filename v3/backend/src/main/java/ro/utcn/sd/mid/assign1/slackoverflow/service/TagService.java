package ro.utcn.sd.mid.assign1.slackoverflow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Tag;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.api.RepositoryFactory;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TagService {
    private final RepositoryFactory rf;

    @Transactional
    public List<Tag> listTags() {
        return rf.createTagRepository().findAll();
    }
}
