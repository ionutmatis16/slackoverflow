package ro.utcn.sd.mid.assign1.slackoverflow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.SOUser;
import ro.utcn.sd.mid.assign1.slackoverflow.repository.api.RepositoryFactory;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class SOUsersUserDetailsService implements UserDetailsService {
    private final RepositoryFactory repositoryFactory;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SOUser soUser = repositoryFactory.createSOUserRepository().findBySOUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException("Unknown user"));
        return new User(soUser.getSOUsername(), soUser.getSOPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }


    // cu principal ca sa transmiti cine face requestu
    // security context holder. getContext.getAuthenticated
}
