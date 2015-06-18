package com.p.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories
public class DbConfigMetricas extends AbstractMongoConfiguration {





	@Override
	protected String getDatabaseName() {
		return "metricas";
	}

	@Override
	public Mongo mongo() throws Exception {
		MongoClient client = new MongoClient("localhost");
		return client;
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongo(), "metricas");
	}

	@Override
	protected String getMappingBasePackage() {
		return "com.p.model.metricas";
	}
	


}