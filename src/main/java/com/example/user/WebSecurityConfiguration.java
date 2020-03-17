package com.example.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    
	@Value("${user_api_user_name}")
    private String user;

    @Value("${user_api_user_password}")
    private String password;

    @Value("${user_api_admin_name}")
    private String admin;

    @Value("${user_api_admin_password}")
    private String adminPassword;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable().authorizeRequests()
//                .antMatchers("/**").permitAll()
//                .anyRequest().authenticated();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/user").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/user/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/**").denyAll()
                .antMatchers(HttpMethod.DELETE, "/**").denyAll()
                .antMatchers(HttpMethod.PUT, "/**").denyAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .and()  //added
                .httpBasic();  //added
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(user).password(encoder().encode(password)).roles("USER")
                .and()
                .withUser(admin).password(encoder().encode(adminPassword)).roles("ADMIN");
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}