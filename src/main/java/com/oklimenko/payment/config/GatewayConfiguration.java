package com.oklimenko.payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

//@Configuration
//@EnableResourceServer
public class GatewayConfiguration extends ResourceServerConfigurerAdapter {
    /*@Override
    public void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests().
            antMatchers("/oauth/**", "/user/register").//, "/payment/**").
            permitAll().
            antMatchers("/**").
            authenticated();
    }
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");

//         final Resource resource = new ClassPathResource("public.txt");
//         String publicKey = null;
//         try {
//         publicKey = IOUtils.toString(resource.getInputStream());
//         } catch (final IOException e) {
//         throw new RuntimeException(e);
//         }
//         converter.setVerifierKey(publicKey);
//        return converter;
        converter.setVerifierKey("123");
        // final KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"), "mypass".toCharArray());
        // converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));
        return converter;
    }*/
}
