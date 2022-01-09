package com.github.lukaslt1993.songs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.github.lukaslt1993.songs.controller.EndpointNames;
import com.github.lukaslt1993.songs.model.User;
import com.github.lukaslt1993.songs.repository.UserRepository;
import com.github.lukaslt1993.songs.security.SecurityConstants.Role;

import static org.springframework.security.core.userdetails.User.builder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private String tokenSecret;

    private UserRepository repo;

    public WebSecurityConfig(UserRepository repo) {
        this.repo = repo;
    }

    @Autowired
    public void setTokenSecret(@Value("${tokenSecret}") String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    private class UserDetailsServiceImpl implements UserDetailsService {
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = repo.findFirstByUserName(username);

            if (user != null) {
                String password = user.getPassword();
                return builder().username(username).password(password).roles(user.getRole().getValue()).build();
            }

            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, EndpointNames.LOGIN).permitAll()
                .antMatchers(HttpMethod.POST, EndpointNames.USER).permitAll()
                .antMatchers("/v2/api-docs", "/swagger*/**").permitAll()
                .antMatchers(HttpMethod.POST, EndpointNames.SONG).hasRole(Role.ADMIN.getValue())
                .antMatchers(HttpMethod.PUT, EndpointNames.SONG).hasRole(Role.ADMIN.getValue())
                .antMatchers(HttpMethod.DELETE, EndpointNames.SONG).hasRole(Role.ADMIN.getValue())
                .antMatchers(HttpMethod.POST, EndpointNames.ARTIST).hasRole(Role.ADMIN.getValue())
                .antMatchers(HttpMethod.PUT, EndpointNames.ARTIST).hasRole(Role.ADMIN.getValue())
                .antMatchers(HttpMethod.DELETE, EndpointNames.ARTIST).hasRole(Role.ADMIN.getValue())
                .anyRequest().authenticated().and().csrf().disable()
                .addFilter(new AuthenticationFilter(authenticationManager(), tokenSecret))
                .addFilter(new AuthorizationFilter(authenticationManager(), tokenSecret, new UserDetailsServiceImpl()))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

}
