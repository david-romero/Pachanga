/**
 * 
 */
package com.p.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@Configuration
@ComponentScan({ "com.p.*" })
@Import({ SecurityConfig.class , DbConfig.class })
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
	/*
	@Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(
				dataSource());
		builder.scanPackages("com.p").addProperties(getHibernateProperties());

		return builder.buildSessionFactory();
	}

	private Properties getHibernateProperties() {
		Properties prop = new Properties();
		prop.put("hibernate.format_sql", "true");
		prop.put("hibernate.show_sql", "true");
		prop.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		return prop;
	}

	@Bean(name = "dataSource")
	public BasicDataSource dataSource() {

		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/pachanga");
		ds.setUsername("rootApp");
		ds.setPassword("root123");
		return ds;
	}

	// Create a transaction manager
	@Bean
	@Autowired
	public PlatformTransactionManager transactionManager(
			SessionFactory sessionFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactoryBean()
				.getObject());
		return transactionManager;
	}*/
	
	

	/*@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean
				.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan(

		"com.p.model", "com.p.model.modelAux"

		);

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect",
				"org.hibernate.dialect.MySQLDialect");
		jpaProperties.put("hibernate.format_sql", "true");
		jpaProperties.put("hibernate.hbm2ddl.auto", "verify");
		// jpaProperties.put("hibernate.ejb.naming_strategy",
		// env.getRequiredProperty("hibernate.ejb.naming_strategy"));
		jpaProperties.put("hibernate.show_sql", "false");

		entityManagerFactoryBean.setJpaProperties(jpaProperties);

		return entityManagerFactoryBean;
	}
	
	@Bean(name="entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		emfb.setPersistenceUnitName("p");
		emfb.setJpaDialect(jpaDialect());
		emfb.setJpaVendorAdapter(jpaVendorAdaptor());
		emfb.setDataSource(dataSource());
		emfb.setPackagesToScan("com.p");
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect",
				"org.hibernate.dialect.MySQLDialect");
		jpaProperties.put("hibernate.format_sql", "true");
		jpaProperties.put("hibernate.hbm2ddl.auto", "verify");
		// jpaProperties.put("hibernate.ejb.naming_strategy",
		// env.getRequiredProperty("hibernate.ejb.naming_strategy"));
		jpaProperties.put("hibernate.show_sql", "true");

		emfb.setJpaProperties(jpaProperties);
		return emfb;
	}
	
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager txm = new JpaTransactionManager();
		txm.setEntityManagerFactory((EntityManagerFactory) entityManagerFactoryBean());
		txm.setDataSource(dataSource());
		txm.setJpaDialect(jpaDialect());
		return txm;
	}*/

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		org.springframework.web.multipart.commons.CommonsMultipartResolver resolver = new org.springframework.web.multipart.commons.CommonsMultipartResolver();
		resolver.setMaxUploadSize(2097152);
		return resolver;
	}

}