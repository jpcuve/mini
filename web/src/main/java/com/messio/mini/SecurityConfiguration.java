package com.messio.mini;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfiguration {
    @Bean
    public BasicAuthenticationFilter basicAuthenticationFilter(){
        final BasicAuthenticationFilter basicAuthenticationFilter = new BasicAuthenticationFilter(authenticationManager(), authenticationEntryPoint());
        return basicAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        final AuthenticationManager authenticationManager = new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return null;
            }
        };
        return authenticationManager;
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        final BasicAuthenticationEntryPoint authenticationEntryPoint = new BasicAuthenticationEntryPoint();
        authenticationEntryPoint.setRealmName("mini");
        return authenticationEntryPoint;
    }
}
