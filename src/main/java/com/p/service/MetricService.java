package com.p.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.p.config.DbConfigMetricas;
import com.p.model.User;
import com.p.model.metricas.MetricaHttpIP;
import com.p.model.metricas.MetricaHttpLogin;
import com.p.model.metricas.MetricaHttpRequest;
import com.p.model.metricas.MetricaHttpStatus;
import com.p.model.metricas.MetricaHttpUserAgent;
import com.p.model.metricas.MetricaMensaje;
import com.p.model.metricas.MetricaPartido;
import com.p.model.metricas.MetricaPartidoUsuario;
import com.p.util.SystemUtil;

@Service
/**
 * Servicio encargado de persistir metricas sobre los usos de la aplicación en una base de datos
 * MongoDB
 * Esos datos persistidos se utilizarán para estadisticas mayormente o para venta de información.
 * @author David Romero Alcaide
 *
 */
public class MetricService {

	private static final String METRIC_HOUR_PATTERN = " 00:00";
	private static final String METRIC_DATE_PATTERN = "dd/MM/yyyy HH:mm";
	private static final String METRIC_ATTRIBUTE_FECHA = "fecha";
	private static final String METRIC_ATTRIBUTE_COUNT = "count";
	// For Annotation
	ApplicationContext ctx = new AnnotationConfigApplicationContext(
			DbConfigMetricas.class);
	MongoOperations mongoOperation = (MongoOperations) ctx
			.getBean("mongoTemplate");

	protected static final Logger log = Logger.getLogger(MetricService.class);

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Transactional()
	/**
	 * Persiste las métricas vinculadas a una petición Http
	 * <li>
	 * 		<ol>URL de la petición</ol>
	 * 		<ol>Código Http resultante de la petición</ol>
	 * 		<ol>Navegador Utilizado</ol>
	 * 		<ol>IP de la petición</ol>
	 * </li>
	 * @param request
	 * @param response
	 */
	public void saveMetrics(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpServletRequest httpRequest = ((HttpServletRequest) request);
			String req = httpRequest.getMethod() + " "
					+ httpRequest.getRequestURI();

			String browserDetails = httpRequest.getHeader("User-Agent");
			String userAgent = browserDetails;
			String user = userAgent.toLowerCase();

			String os = SystemUtil.getOs(userAgent);
			String browser = SystemUtil.getBrowser(userAgent, user);

			int status = response.getStatus();

			// is client behind something?
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
			}
			manageHttpRequest(req);
			manageHttpStatus(status);
			manageHttpUserAgent(os, browser);
			manageHttpIP(ipAddress);
		} catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * @param status
	 * @throws ParseException
	 */
	protected void manageHttpStatus(int status) throws ParseException {
		try {
			// find
			Query query = new Query();
			Date current = new Date(System.currentTimeMillis());
			String inicioDia = sdf.format(current);
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern(METRIC_DATE_PATTERN);
			LocalDateTime inicioDiaDate = LocalDateTime.parse(inicioDia
					+ METRIC_HOUR_PATTERN, formatter);
			LocalDateTime finDiaDate = inicioDiaDate.plusHours(23)
					.plusMinutes(59).plusSeconds(59);
			Date fin = Date.from(finDiaDate.toInstant(ZoneOffset.UTC));
			Date inicio = Date.from(inicioDiaDate.toInstant(ZoneOffset.UTC));
			query.addCriteria(Criteria.where("status").is(status).and(METRIC_ATTRIBUTE_FECHA)
					.gte(inicio).lte(fin));
			MetricaHttpStatus metrica = mongoOperation.findOne(query,
					MetricaHttpStatus.class, "metricas");

			if (metrica == null || metrica.getId() == null
					|| metrica.getId().isEmpty()) {
				metrica = new MetricaHttpStatus();
				metrica.setCount(1);
				metrica.setStatus(status);
				metrica.setFecha(current);
				mongoOperation.save(metrica, "metricas");
			} else {
				mongoOperation.updateFirst(query,
						Update.update(METRIC_ATTRIBUTE_COUNT, metrica.getCount() + 1),
						MetricaHttpStatus.class);
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * @param status
	 */
	protected void manageHttpRequest(String request) {
		try {
			// find
			Query query = new Query();
			Date current = new Date(System.currentTimeMillis());
			String inicioDia = sdf.format(current);
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern(METRIC_DATE_PATTERN);
			LocalDateTime inicioDiaDate = LocalDateTime.parse(inicioDia
					+ METRIC_HOUR_PATTERN, formatter);
			LocalDateTime finDiaDate = inicioDiaDate.plusHours(23)
					.plusMinutes(59).plusSeconds(59);
			Date fin = Date.from(finDiaDate.toInstant(ZoneOffset.UTC));
			Date inicio = Date.from(inicioDiaDate.toInstant(ZoneOffset.UTC));
			query.addCriteria(Criteria.where("request").is(request)
					.and(METRIC_ATTRIBUTE_FECHA).gte(inicio).lte(fin));
			MetricaHttpRequest metrica = mongoOperation.findOne(query,
					MetricaHttpRequest.class, "request");

			if (metrica == null || metrica.getId() == null
					|| metrica.getId().isEmpty()) {
				metrica = new MetricaHttpRequest();
				metrica.setCount(1);
				metrica.setRequest(request);
				metrica.setFecha(current);
				mongoOperation.save(metrica, "request");
			} else {
				mongoOperation.updateFirst(query,
						Update.update(METRIC_ATTRIBUTE_COUNT, metrica.getCount() + 1),
						MetricaHttpRequest.class);
			}

		} catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * @param status
	 */
	protected void manageHttpUserAgent(String os, String browser) {
		try {
			// find
			Query query = new Query();
			Date current = new Date(System.currentTimeMillis());
			String inicioDia = sdf.format(current);
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern(METRIC_DATE_PATTERN);
			LocalDateTime inicioDiaDate = LocalDateTime.parse(inicioDia
					+ METRIC_HOUR_PATTERN, formatter);
			LocalDateTime finDiaDate = inicioDiaDate.plusHours(23)
					.plusMinutes(59).plusSeconds(59);
			Date fin = Date.from(finDiaDate.toInstant(ZoneOffset.UTC));
			Date inicio = Date.from(inicioDiaDate.toInstant(ZoneOffset.UTC));
			query.addCriteria(Criteria
					.where("os")
					.is(os)
					.andOperator(
							Criteria.where("browser").is(browser).and(METRIC_ATTRIBUTE_FECHA)
									.gte(inicio).lte(fin)));
			MetricaHttpUserAgent metrica = mongoOperation.findOne(query,
					MetricaHttpUserAgent.class, "userAgent");

			if (metrica == null || metrica.getId() == null
					|| metrica.getId().isEmpty()) {
				metrica = new MetricaHttpUserAgent();
				metrica.setCount(1);
				metrica.setBrowser(browser);
				metrica.setOs(os);
				metrica.setFecha(current);
				mongoOperation.save(metrica, "userAgent");
			} else {
				mongoOperation.updateFirst(query,
						Update.update(METRIC_ATTRIBUTE_COUNT, metrica.getCount() + 1),
						MetricaHttpUserAgent.class);
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	private void manageHttpIP(String ip) {
		try {
			// find
			Query query = new Query();
			Date current = new Date(System.currentTimeMillis());
			String inicioDia = sdf.format(current);
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern(METRIC_DATE_PATTERN);
			LocalDateTime inicioDiaDate = LocalDateTime.parse(inicioDia
					+ METRIC_HOUR_PATTERN, formatter);
			LocalDateTime finDiaDate = inicioDiaDate.plusHours(23)
					.plusMinutes(59).plusSeconds(59);
			Date fin = Date.from(finDiaDate.toInstant(ZoneOffset.UTC));
			Date inicio = Date.from(inicioDiaDate.toInstant(ZoneOffset.UTC));
			query.addCriteria(Criteria.where("ip").is(ip).and(METRIC_ATTRIBUTE_FECHA)
					.gte(inicio).lte(fin));
			MetricaHttpIP metrica = mongoOperation.findOne(query,
					MetricaHttpIP.class, "ip");

			if (metrica == null || metrica.getId() == null
					|| metrica.getId().isEmpty()) {
				metrica = new MetricaHttpIP();
				metrica.setCount(1);
				metrica.setIp(ip);
				metrica.setFecha(current);
				mongoOperation.save(metrica, "ip");
			} else {
				mongoOperation.updateFirst(query,
						Update.update(METRIC_ATTRIBUTE_COUNT, metrica.getCount() + 1),
						MetricaHttpIP.class);
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	@Transactional()
	/**
	 * 
	 * @param domainUser
	 */
	public void saveLogin(User domainUser) {
		try {
			// find
			Query query = new Query();
			Date current = new Date(System.currentTimeMillis());
			String inicioDia = sdf.format(current);
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern(METRIC_DATE_PATTERN);
			LocalDateTime inicioDiaDate = LocalDateTime.parse(inicioDia
					+ METRIC_HOUR_PATTERN, formatter);
			LocalDateTime finDiaDate = inicioDiaDate.plusHours(23)
					.plusMinutes(59).plusSeconds(59);
			Date fin = Date.from(finDiaDate.toInstant(ZoneOffset.UTC));
			Date inicio = Date.from(inicioDiaDate.toInstant(ZoneOffset.UTC));
			query.addCriteria(Criteria.where("username")
					.is(domainUser.getEmail()).and(METRIC_ATTRIBUTE_FECHA).gte(inicio)
					.lte(fin));
			MetricaHttpLogin metrica = mongoOperation.findOne(query,
					MetricaHttpLogin.class, "login");

			if (metrica == null || metrica.getId() == null
					|| metrica.getId().isEmpty()) {
				metrica = new MetricaHttpLogin();
				metrica.setCount(1);
				metrica.setUsername(domainUser.getEmail());
				metrica.setFullName(domainUser.getFirstName() + " "
						+ domainUser.getLastName());
				metrica.setFecha(current);
				mongoOperation.save(metrica, "login");
			} else {
				mongoOperation.updateFirst(query,
						Update.update(METRIC_ATTRIBUTE_COUNT, metrica.getCount() + 1),
						MetricaHttpLogin.class);
			}
		} catch (Exception e) {
			log.error(e);
		}
	}
	@Transactional()
	public void savePartido() {
		try {
			// find
			Query query = new Query();
			Date current = new Date(System.currentTimeMillis());
			String inicioDia = sdf.format(current);
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern(METRIC_DATE_PATTERN);
			LocalDateTime inicioDiaDate = LocalDateTime.parse(inicioDia
					+ METRIC_HOUR_PATTERN, formatter);
			LocalDateTime finDiaDate = inicioDiaDate.plusHours(23)
					.plusMinutes(59).plusSeconds(59);
			Date fin = Date.from(finDiaDate.toInstant(ZoneOffset.UTC));
			Date inicio = Date.from(inicioDiaDate.toInstant(ZoneOffset.UTC));
			query.addCriteria(Criteria.where(METRIC_ATTRIBUTE_FECHA).gte(inicio)
					.lte(fin));
			MetricaPartido metrica = mongoOperation.findOne(query,
					MetricaPartido.class, "partido");

			if (metrica == null || metrica.getId() == null
					|| metrica.getId().isEmpty()) {
				metrica = new MetricaPartido();
				metrica.setCount(1);
				metrica.setFecha(current);
				mongoOperation.save(metrica, "partido");
			} else {
				mongoOperation.updateFirst(query,
						Update.update(METRIC_ATTRIBUTE_COUNT, metrica.getCount() + 1),
						MetricaPartido.class);
			}
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	@Transactional()
	public void saveMensaje() {
		try {
			// find
			Query query = new Query();
			Date current = new Date(System.currentTimeMillis());
			String inicioDia = sdf.format(current);
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern(METRIC_DATE_PATTERN);
			LocalDateTime inicioDiaDate = LocalDateTime.parse(inicioDia
					+ METRIC_HOUR_PATTERN, formatter);
			LocalDateTime finDiaDate = inicioDiaDate.plusHours(23)
					.plusMinutes(59).plusSeconds(59);
			Date fin = Date.from(finDiaDate.toInstant(ZoneOffset.UTC));
			Date inicio = Date.from(inicioDiaDate.toInstant(ZoneOffset.UTC));
			query.addCriteria(Criteria.where(METRIC_ATTRIBUTE_FECHA).gte(inicio)
					.lte(fin));
			MetricaMensaje metrica = mongoOperation.findOne(query,
					MetricaMensaje.class, "mensaje");

			if (metrica == null || metrica.getId() == null
					|| metrica.getId().isEmpty()) {
				metrica = new MetricaMensaje();
				metrica.setCount(1);
				metrica.setFecha(current);
				mongoOperation.save(metrica, "mensaje");
			} else {
				mongoOperation.updateFirst(query,
						Update.update(METRIC_ATTRIBUTE_COUNT, metrica.getCount() + 1),
						MetricaPartido.class);
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	public void savePartidoUsuario(User user) {
		try {
			// find
			Query query = new Query();
			Date current = new Date(System.currentTimeMillis());
			String inicioDia = sdf.format(current);
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern(METRIC_DATE_PATTERN);
			LocalDateTime inicioDiaDate = LocalDateTime.parse(inicioDia
					+ METRIC_HOUR_PATTERN, formatter);
			LocalDateTime finDiaDate = inicioDiaDate.plusHours(23)
					.plusMinutes(59).plusSeconds(59);
			Date fin = Date.from(finDiaDate.toInstant(ZoneOffset.UTC));
			Date inicio = Date.from(inicioDiaDate.toInstant(ZoneOffset.UTC));
			query.addCriteria(Criteria.where("username").is(user.getEmail()).and(METRIC_ATTRIBUTE_FECHA).gte(inicio)
					.lte(fin));
			MetricaPartidoUsuario metrica = mongoOperation.findOne(query,
					MetricaPartidoUsuario.class, "partidoUsuario");

			if (metrica == null || metrica.getId() == null
					|| metrica.getId().isEmpty()) {
				metrica = new MetricaPartidoUsuario();
				metrica.setUsername(user.getEmail());
				metrica.setFullName(user.getFirstName() + " "
						+ user.getLastName());
				metrica.setCount(1);
				metrica.setFecha(current);
				mongoOperation.save(metrica, "partidoUsuario");
			} else {
				mongoOperation.updateFirst(query,
						Update.update(METRIC_ATTRIBUTE_COUNT, metrica.getCount() + 1),
						MetricaPartidoUsuario.class);
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

}
