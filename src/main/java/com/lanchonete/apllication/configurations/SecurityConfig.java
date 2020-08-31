package com.lanchonete.apllication.configurations;

import com.lanchonete.domain.services.usuario.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.lanchonete.apllication.configurations.SecurityConstants.*;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, 
securedEnabled = true, 
jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .anyRequest().authenticated()
                .and()
        // .antMatchers(HttpMethod.GET, SIGN_UP_URL).permitAll()
        //.antMatchers("/*/api/*").hasRole("USER")
        //.and()
        .addFilter(new JwtAuthenticationFilter(authenticationManager()))
        .addFilter(new JwtAutorizationFilter(authenticationManager(), customUserDetailsService))
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // http
        // .authorizeRequests()
        // //.anyRequest().authenticated()
        // .antMatchers("/*/api/*").hasRole("USER")
        // .and()
        // .httpBasic()
        // .and()
        // .csrf().disable();
    }

    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        // auth.inMemoryAuthentication()
        // .withUser("user").password("{noop}teste").roles("USER")
        // .and()
        // .withUser("admin").password("{noop}password").roles("ADMIN", "USER");
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }
}