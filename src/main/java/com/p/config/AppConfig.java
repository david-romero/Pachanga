/**
 * 
 */
package com.p.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@Configuration
@ComponentScan({ "com.p.*" })
@Import({ SecurityConfig.class , DbConfig.class, DbConfigMetricas.class })
/**
 * @author David
 *
 */
public class AppConfig
		extends
		org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter {

	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations(
				"/resources/");
	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		org.springframework.web.multipart.commons.CommonsMultipartResolver resolver = new org.springframework.web.multipart.commons.CommonsMultipartResolver();
		resolver.setMaxUploadSize(2097152);
		return resolver;
	}

}