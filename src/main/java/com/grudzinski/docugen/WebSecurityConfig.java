package com.grudzinski.docugen;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/h2-console/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                    .and().formLogin()//enable form login instead of basic login
                    .and().csrf().ignoringAntMatchers("/h2-console/**")//don't apply CSRF protection to /h2-console
                    .and().headers().frameOptions().sameOrigin()
                    .and()
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/wedding/**").hasRole("USER")
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
//                    .loginPage("/login")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        auth.inMemoryAuthentication()
                .passwordEncoder(encoder)
                .withUser("admin")
                .password(encoder.encode("26iKyb0Ufa5w"))
                .roles("USER", "ADMIN");
    }
}
