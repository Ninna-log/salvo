package com.codeoftheweb.salvo.config;

import com.codeoftheweb.salvo.model.Player;
import com.codeoftheweb.salvo.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class WebUserConfig extends GlobalAuthenticationConfigurerAdapter {
    //AUTHENTICATION
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userName -> {
            Player player = playerRepository.findByUserName(userName);
            if (player != null) {
                return new User(player.getUserName(), player.getPassword(),
                        AuthorityUtils.createAuthorityList("USER")); // <-- User role
            } else {
                throw new UsernameNotFoundException("Unknown user: " + userName);
            }
        });
    }
}