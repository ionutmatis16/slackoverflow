package ro.utcn.sd.mid.assign1.slackoverflow.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ro.utcn.sd.mid.assign1.slackoverflow.service.SOUsersUserDetailsService;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final SOUsersUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // all requests (except for those to /users) should be authenticated
        http.authorizeRequests().antMatchers("/users").permitAll().and()
                .authorizeRequests().antMatchers("/register").permitAll().and()
                .authorizeRequests().antMatchers("/login").permitAll().and()
                .authorizeRequests().anyRequest().authenticated().and()
                .httpBasic().and()
                // next line disables session creation (forces true HTTP Basic behavior)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .cors().and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // service which knows to load users
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    // Bean can be used in the configuration classes where you create
    // the instance yourself (return new BCryptPasswordEncoder();)
    // but passing it to Spring so that it can inject whenever it needs it
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
