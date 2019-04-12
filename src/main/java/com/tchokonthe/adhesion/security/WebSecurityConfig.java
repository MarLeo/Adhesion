package com.tchokonthe.adhesion.security;


import com.tchokonthe.adhesion.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity(debug = true)
@EnableJpaRepositories(basePackages = "com.tchokonthe.adhesion")
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfig(final CustomUserDetailsService customUserDetailsService,
                             final PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers(
                        "/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/**",
                        "/swagger-ui.html",
                        "/oauth/**",
                        "/webjars/**",
                        "/h2-console/**");
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        /*http.authorizeRequests().anyRequest().authenticated().and().sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.NEVER);*/
//        http.csrf()
//                .disable()
//                .sessionManagement().sessionCreationPolicy(STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/",
//                        "/favicon.ico",
//                        "/**/*.png",
//                        "/**/*.gif",
//                        "/**/*.svg",
//                        "/**/*.jpg",
//                        "/**/*.html",
//                        "/**/*.css",
//                        "/**/*.js"/*,
//                        "/oauth/token"*/).permitAll()
//                .antMatchers("/**/api/auth/**").permitAll()
//                .anyRequest().authenticated();
//       /* http
//                .csrf().disable()
//                .anonymous().disable()
//                .httpBasic().disable()
//                .cors().disable()
//                .authorizeRequests()
//                .anyRequest().authenticated();*/
////        http.httpBasic().disable().csrf().disable().antMatcher("/oauth/token").authorizeRequests().anyRequest().permitAll();
//
//       /* http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/oauth/token").permitAll()
//                .antMatchers("/api-docs/**").permitAll()
//                .anyRequest().authenticated()
//                .and().anonymous().disable();*/
//    }

}
