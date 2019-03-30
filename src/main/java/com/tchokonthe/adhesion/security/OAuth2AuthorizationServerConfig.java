package com.tchokonthe.adhesion.security;

import com.tchokonthe.adhesion.config.JwtProperties;
import com.tchokonthe.adhesion.config.JwtProperties.Credentials;
import com.tchokonthe.adhesion.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Optional;


@Configuration
@EnableAuthorizationServer
@EnableConfigurationProperties(JwtProperties.class)
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(OAuth2AuthorizationServerConfig.class);

    private final AuthenticationManager authenticationManager;
    private final JwtProperties jwtProperties;
    private final CustomUserDetailsService customUserDetailsService;
    private final TokenStore tokenStore;
    private final JwtAccessTokenConverter jwtAccessTokenConverter;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public OAuth2AuthorizationServerConfig(final AuthenticationManager authenticationManager,
                                           @Qualifier("customJwtProperties") JwtProperties jwtProperties,
                                           final CustomUserDetailsService customUserDetailsService,
                                           final TokenStore tokenStore,
                                           final JwtAccessTokenConverter jwtAccessTokenConverter,
                                           final PasswordEncoder passwordEncoder) {

        this.authenticationManager = authenticationManager;
        this.jwtProperties = jwtProperties;
        this.customUserDetailsService = customUserDetailsService;
        this.tokenStore = tokenStore;
        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                .passwordEncoder(this.passwordEncoder)
                .tokenKeyAccess("permitAll()")
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
                                .authorities(jwtProperties.getAuthorities().toArray(new String[0]))
                                .accessTokenValiditySeconds(Integer.parseInt(jwtProperties.getAccess_token_validity()))
                                .refreshTokenValiditySeconds(Integer.parseInt(jwtProperties.getRefresh_token_validity()));
                    } catch (Exception e) {
                        logger.error("Impossible to get clients detail", e);
                    }
                },
                () -> new RuntimeException("Credentials are empty"));
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(this.authenticationManager)
                .accessTokenConverter(this.jwtAccessTokenConverter)
                .tokenStore(this.tokenStore);
        /*.userDetailsService(userDetailsService)*/
    }
}
