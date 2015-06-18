package com.p.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.p.config.DbConfigMetricas;
import com.p.model.metricas.MetricaHttpRequest;
import com.p.model.metricas.MetricaHttpStatus;

@Service
public class MetricService {

	// For Annotation
	ApplicationContext ctx = new AnnotationConfigApplicationContext(
			DbConfigMetricas.class);
	MongoOperations mongoOperation = (MongoOperations) ctx
			.getBean("mongoTemplate");

	@Transactional()
	public void increaseCount(String request, int status) {

		manageHttpStatus(status);

		manageHttpRequest(request);
	}

	/**
	 * @param status
	 */
	protected void manageHttpStatus(int status) {
		// find
		Query query = new Query();
		query.addCriteria(Criteria.where("status").is(status));
		MetricaHttpStatus metrica = mongoOperation.findOne(query, MetricaHttpStatus.class,
				"metricas");

		if (metrica == null || metrica.getId() == null
				|| metrica.getId().isEmpty()) {
			metrica = new MetricaHttpStatus();
			metrica.setCount(1);
			metrica.setStatus(status);
			mongoOperation.save(metrica,"metricas");
		} else {
			mongoOperation.updateFirst(query, 
                    Update.update("count", metrica.getCount() + 1),MetricaHttpStatus.class);
		}
	}
	
	/**
	 * @param status
	 */
	protected void manageHttpRequest(String request) {
		// find
		Query query = new Query();
		query.addCriteria(Criteria.where("request").is(request));
		MetricaHttpRequest metrica = mongoOperation.findOne(query, MetricaHttpRequest.class,
				"request");

		if (metrica == null || metrica.getId() == null
				|| metrica.getId().isEmpty()) {
			metrica = new MetricaHttpRequest();
			metrica.setCount(1);
			metrica.setRequest(request);
			mongoOperation.save(metrica,"request");
		} else {
			mongoOperation.updateFirst(query, 
                    Update.update("count", metrica.getCount() + 1),MetricaHttpRequest.class);
		}
	}

}
