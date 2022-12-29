package com.mingati.kikunditestrepo.configuration;


import com.mingati.kikunditestrepo.security.CustomerAuthenticationFilter;
import com.mingati.kikunditestrepo.security.UserAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserAuthenticationProvider userAuthenticationProvider;

    private static final String[] SWAGGER_WHITELIST = {
            // -- swagger ui
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/create",
            "/verifyRegistration",
            "/reset-password-request/{email}"
    };

    private static final String[] NOT_SECURED = {
            "/actuator/health",
            "/login",
            "/reset-password-request",
            "/greetings"

    };

    private static final String[] SECURED = {
            "/stats"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers(SWAGGER_WHITELIST).permitAll()
                .antMatchers(NOT_SECURED).permitAll()
                .anyRequest().authenticated()
                .and()
                .authenticationProvider(userAuthenticationProvider)
                .addFilterBefore(authenticationFilter(), AnonymousAuthenticationFilter.class)
                .exceptionHandling().and()
                .headers().frameOptions().disable()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private CustomerAuthenticationFilter authenticationFilter() throws Exception {
        final CustomerAuthenticationFilter filter = new CustomerAuthenticationFilter(new OrRequestMatcher(Arrays.stream(SECURED)
                .map(AntPathRequestMatcher::new)
                .collect(Collectors.toList())));
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(userAuthenticationProvider);
    }


}
