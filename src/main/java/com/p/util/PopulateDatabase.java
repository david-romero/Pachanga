/* PopulateDatabase.java
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package com.p.util;

import java.util.Map.Entry;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PopulateDatabase {

	private static final Logger LOGGER = Logger.getLogger(PopulateDatabase.class);

	public static void main(String[] args) {

		ApplicationContext ctx;
		EntityManagerFactory emf;
		EntityManager em;
		EntityTransaction et;

		ctx = new ClassPathXmlApplicationContext(
				"com/p/util/PopulateDatabase.xml");

		emf = Persistence.createEntityManagerFactory("p");
		em = emf.createEntityManager();
		et = em.getTransaction();

		et.begin();
		try {
			for (Entry<String, Object> entry : ctx.getBeansWithAnnotation(
					Entity.class).entrySet()) {
				em.persist(entry.getValue());
				LOGGER.debug(String.format("Persisting (%s,%s, %s@%d)",
						entry.getKey(), entry.getValue(), entry.getValue()
								.getClass().getName(), entry.getValue()
								.hashCode()));
			}
			et.commit();
		} catch (Exception oops) {
			LOGGER.error(oops.getMessage(),oops);
			et.rollback();
		} finally {
			if (em.isOpen()) {
				em.close();
			}
			if (emf.isOpen()) {
				emf.close();
			}
		}
	}
}
