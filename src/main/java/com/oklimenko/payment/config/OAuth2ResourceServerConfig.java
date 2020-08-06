package com.oklimenko.payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.DelegatingJwtClaimsSetVerifier;
import org.springframework.security.oauth2.provider.token.store.IssuerClaimVerifier;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtClaimsSetVerifier;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests().
                antMatchers("/oauth/**", "/user/register").//, "/payment/**").
                permitAll().
                antMatchers("/**").
                authenticated();


        /*http.sessionManagement().
                sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).
                and().
                authorizeRequests().
                antMatchers("/swagger*", "/v2/**").
                access("#oauth2.hasScope('read')").
                anyRequest().permitAll();*/
    }

    // JWT token store
    @Override
    public void configure(final ResourceServerSecurityConfigurer config) {
        config.tokenServices(tokenServices());
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");
        converter.setJwtClaimsSetVerifier(jwtClaimsSetVerifier());

        // final Resource resource = new ClassPathResource("public.txt");
        // String publicKey = null;
        // try {
        // publicKey = IOUtils.toString(resource.getInputStream(), Charset.defaultCharset());
        // } catch (final IOException e) {
        // throw new RuntimeException(e);
        // }
        // converter.setVerifierKey(publicKey);
        return converter;
    }

    @Bean
    public JwtClaimsSetVerifier jwtClaimsSetVerifier() {
        return new DelegatingJwtClaimsSetVerifier(Arrays.asList(issuerClaimVerifier(), customJwtClaimVerifier()));
    }

    public static class CustomClaimVerifier implements JwtClaimsSetVerifier {

        @Override
        public void verify(Map<String, Object> map) throws InvalidTokenException {
            System.out.println("CustomClaimVerifier - verify()");
            //no-ops
        }
    }

    @Bean
    public JwtClaimsSetVerifier issuerClaimVerifier() {
        return new CustomClaimVerifier();
    }


    @Bean
    public JwtClaimsSetVerifier customJwtClaimVerifier() {
        return new CustomClaimVerifier();
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }
}