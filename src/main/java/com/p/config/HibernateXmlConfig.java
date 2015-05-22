package com.p.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ImportResource({ "/WEB-INF/hibernate-context.xml" })
public class HibernateXmlConfig{
   //
}
