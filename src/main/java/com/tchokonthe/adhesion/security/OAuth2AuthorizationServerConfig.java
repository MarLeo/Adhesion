package com.tchokonthe.adhesion.security;

import com.tchokonthe.adhesion.config.JwtProperties;
import com.tchokonthe.adhesion.config.JwtProperties.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Optional;


@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(OAuth2AuthorizationServerConfig.class);

    private final AuthenticationManager authenticationManager;
    private final JwtProperties jwtProperties;

    @Autowired
    public OAuth2AuthorizationServerConfig(AuthenticationManager authenticationManager,
                                           /*@Qualifier("customJwtProperties") */JwtProperties jwtProperties) {
        this.authenticationManager = authenticationManager;
        this.jwtProperties = jwtProperties;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) {
        final Optional<Credentials> credentials = Optional.of(jwtProperties.getCredentials());

        credentials.ifPresentOrElse(credential ->
                {
                    try {
                        clients
                                .inMemory()
                                .withClient(credential.getClient_id())
                                .secret(credential.getClient_secret())
                                .scopes(jwtProperties.getScopes().toArray(new String[0]))
                                .authorizedGrantTypes(jwtProperties.getGrant_types().toArray(new String[0]))
                                .accessTokenValiditySeconds(Integer.parseInt(jwtProperties.getAccess_token_validity()))
                                .refreshTokenValiditySeconds(Integer.parseInt(jwtProperties.getRefresh_token_validity()));
                    } catch (Exception e) {
                        logger.error("Impossible to get clients detail", e);
//                        e.printStackTrace();
                    }
                },
                () -> new RuntimeException("Credentials are empty"));
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore())
                .accessTokenConverter(jwtAccessTokenConverter())
                .authenticationManager(authenticationManager);
    }

    @Bean
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
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }
}
