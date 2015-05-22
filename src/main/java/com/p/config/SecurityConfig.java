/**
 * 
 */
package com.p.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
@EnableWebSecurity
/**
 * @author David
 *
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth
	      .inMemoryAuthentication()
	        .withUser("bent@test.com").password("SBP2014").roles("USER");
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
        .authorizeRequests()
             .antMatchers("/register","/app","/secure/**","/signin/**","/rest/**").permitAll()
			.antMatchers("/admin","/admin/**").hasRole("ADMIN")
			.anyRequest().authenticated()
            .and()
            	.csrf()
            		.csrfTokenRepository(csrfTokenRepository())
            .and()
            	.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
            .exceptionHandling()
            		.accessDeniedPage("/denied")
            .and()
            	.formLogin()
            		.failureUrl("/secure?error=true")
            		.loginPage("/secure")
            		.usernameParameter("username")
            		.passwordParameter("password")
            		.defaultSuccessUrl("/app")
            		.permitAll()
            .and()
				.rememberMe()
            .and()
            	.logout()
            		.deleteCookies("JSESSIONID")
            		.logoutSuccessUrl("/app")
            		//Adds the SocialAuthenticationFilter to Spring Security's filter chain.
            .and()
                .apply(new SpringSocialConfigurer());
	}
	
	private CsrfTokenRepository csrfTokenRepository() {
		  HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		  repository.setHeaderName("X-XSRF-TOKEN");
		  return repository;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
