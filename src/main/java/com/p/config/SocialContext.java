package com.p.config;


import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.support.OAuth1ConnectionFactory;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SocialAuthenticationServiceLocator;
import org.springframework.social.security.SocialAuthenticationServiceRegistry;
import org.springframework.social.security.provider.OAuth1AuthenticationService;
import org.springframework.social.security.provider.OAuth2AuthenticationService;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import com.p.service.SpringSecuritySignInAdapter;

@Configuration
@EnableSocial
public class SocialContext implements SocialConfigurer {
 
    @Autowired
    private DataSource dataSource;
    
    Logger log = Logger.getLogger(SocialContext.class);
 
    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
        cfConfig.addConnectionFactory(new TwitterConnectionFactory(
                "abcdef",
                "secreet"
        ));
        cfConfig.addConnectionFactory(new FacebookConnectionFactory(
                "asdasd-asdasdasd-asdasdasd-asdasdasd",
                "secret"
        ));
        cfConfig.addConnectionFactory(new GoogleConnectionFactory(
                "asdasd-asdasdasd-asdasdasd-asdasdasd",
                "secret"
        ));
    }
    
    @Bean
    public ConnectionFactoryLocator connectionFactoryLocator() {
        ConnectionFactoryRegistry cfConfig = new ConnectionFactoryRegistry();            
        return cfConfig;
    }
 
    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }
 
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return new JdbcUsersConnectionRepository(
                dataSource,
                connectionFactoryLocator,
                Encryptors.noOpText()
        );
    }

    
    @Bean
    public ProviderSignInController providerSignInController() {
        ProviderSignInController controller = new ProviderSignInController(connectionFactoryLocator(), 
        		getUsersConnectionRepository(connectionFactoryLocator()), new SpringSecuritySignInAdapter());
        //controller.setApplicationUrl("/P/app");
        return controller;
    }
    
    @Bean
    @Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)
    public ConnectionRepository connectionRepository(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
        }
        return getUsersConnectionRepository(connectionFactoryLocator()).createConnectionRepository(authentication.getName());
    }

	
	@Bean
    public SocialAuthenticationServiceLocator socialAuthenticationServiceLocator() {
        SocialAuthenticationServiceRegistry registry = new SocialAuthenticationServiceRegistry();
        
        //add google
        OAuth2ConnectionFactory<Google> googleConnectionFactory = new GoogleConnectionFactory(
        		"Cliente.id",
                "Secret");
        OAuth2AuthenticationService<Google> googleAuthenticationService = 
        		new OAuth2AuthenticationService<Google>(googleConnectionFactory);
        googleAuthenticationService.setDefaultScope("https://www.googleapis.com/auth/userinfo.profile");
        registry.addAuthenticationService(googleAuthenticationService);

        //add twitter
        OAuth1ConnectionFactory<Twitter> twitterConnectionFactory = new TwitterConnectionFactory(
        		"twitter.consumerKey",
                "twitter.consumerSecret");
        OAuth1AuthenticationService<Twitter> twitterAuthenticationService = new OAuth1AuthenticationService<Twitter>(twitterConnectionFactory);
        registry.addAuthenticationService(twitterAuthenticationService);

        //add facebook
        OAuth2ConnectionFactory<Facebook> facebookConnectionFactory = new FacebookConnectionFactory(
        		"facebook.clientId",
                "facebook.clientSecret");
        OAuth2AuthenticationService<Facebook> facebookAuthenticationService = new OAuth2AuthenticationService<Facebook>(facebookConnectionFactory);
        facebookAuthenticationService.setDefaultScope("");
        registry.addAuthenticationService(facebookAuthenticationService);

        return registry;
    }    

}
