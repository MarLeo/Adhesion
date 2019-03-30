package com.tchokonthe.adhesion.security;

import com.tchokonthe.adhesion.config.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;


@Configuration
@EnableResourceServer
@EnableConfigurationProperties(JwtProperties.class)
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {


    private static final String ROOT_PATTERN = "/**";

//    private final JwtProperties jwtProperties;
    private final TokenStore tokenStore;
    private final DefaultTokenServices tokenServices;
//    private final JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    public OAuth2ResourceServerConfig(/*@Qualifier("customJwtProperties") JwtProperties jwtProperties,*/
                                      final TokenStore tokenStore,
                                      DefaultTokenServices tokenServices
                                      /*final JwtAccessTokenConverter jwtAccessTokenConverter*/) {

//        this.jwtProperties = jwtProperties;
        this.tokenStore = tokenStore;
        this.tokenServices = tokenServices;
//        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
    }


    @Override
    public void configure(final ResourceServerSecurityConfigurer resources) {
        resources.tokenStore(tokenStore);
    }

    /*@Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        *//*resources
                .resourceId("resource_id")
                .stateless(false);*//*
        super.configure(resources);
        resources.tokenServices(tokenServices);
//        resources.tokenStore(tokenStore);
    }*/

//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//
//
//        http
//                .cors()
//                .and()
//                .csrf()
//                .disable()
//                .authorizeRequests()
//                .antMatchers("/",
//                        "/favicon.ico",
//                        "/**/*.png",
//                        "/**/*.gif",
//                        "/**/*.svg",
//                        "/**/*.jpg",
//                        "/**/*.html",
//                        "/**/*.css",
//                        "/**/*.js").permitAll()
//                .antMatchers("/**/api/auth/**")
//                .access("hasRole('ADMIN')")
//                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
//
//        /*http.authorizeRequests()
//                .antMatchers(HttpMethod.GET, ROOT_PATTERN).access("#oauth2.hasScope('read')")
//                .antMatchers(HttpMethod.POST, ROOT_PATTERN).access("#oauth2.hasScope('write')")
//                .antMatchers(HttpMethod.PATCH, ROOT_PATTERN).access("#oauth2.hasScope('write')")
//                .antMatchers(HttpMethod.PUT, ROOT_PATTERN).access("#oauth2.hasScope('write')")
//                .antMatchers(HttpMethod.DELETE, ROOT_PATTERN).access("#oauth2.hasScope('write')");
//*/
//        /*http
//                .cors()
//                .and()
//                .csrf()
//                .disable()*/
////        http.anonymous().disable()
////                .authorizeRequests()
////                .antMatchers("/",
////                        "/favicon.ico",
////                        "/**/*.png",
////                        "/**/*.gif",
////                        "/**/*.svg",
////                        "/**/*.jpg",
////                        "/**/*.html",
////                        "/**/*.css",
////                        "/**/*.js").permitAll()
////                .antMatchers("/**/api/auth/**")
////                .access("hasRole('ADMIN')")
////                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
//    }


   /* @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(jwtProperties.getSigning_key());
//        converter.setVerifierKey(jwtProperties.getVerify_key());
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
//    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }*/


}
