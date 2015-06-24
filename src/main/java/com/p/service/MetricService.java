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
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.DBObject;
import com.p.config.DbConfigMetricas;
import com.p.model.metricas.MetricaHttpIP;
import com.p.model.metricas.MetricaHttpRequest;
import com.p.model.metricas.MetricaHttpStatus;
import com.p.model.metricas.MetricaHttpUserAgent;
import com.p.model.modelAux.Pair;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;


@Service
public class MetricService {

	private static final String OS_IPHONE = "IPhone";
	private static final String OS_ANDROID = "Android";
	private static final String OS_UNIX = "Unix";
	private static final String OS_MAC = "Mac";
	private static final String OS_WINDOWS = "Windows";
	private static final String IPHONE = "iphone";
	private static final String ANDROID = "android";
	private static final String UNIX = "x11";
	private static final String MAC = "mac";
	private static final String WINDOWS = "windows";
	// For Annotation
	ApplicationContext ctx = new AnnotationConfigApplicationContext(
			DbConfigMetricas.class);
	MongoOperations mongoOperation = (MongoOperations) ctx
			.getBean("mongoTemplate");

	protected static final Logger log = Logger.getLogger(MetricService.class);

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Transactional()
	public void saveMetrics(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpServletRequest httpRequest = ((HttpServletRequest) request);
			String req = httpRequest.getMethod() + " "
					+ httpRequest.getRequestURI();

			String browserDetails = httpRequest.getHeader("User-Agent");
			String userAgent = browserDetails;
			String user = userAgent.toLowerCase();

			String os = getOs(userAgent);
			String browser = getBrowser(userAgent, user);

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
	 * @param userAgent
	 * @param user
	 * @param browser
	 * @return
	 */
	protected String getBrowser(String userAgent, String user) {
		String browser = "";
		// ===============Browser===========================
		if (user.contains("msie")) {
			String substring = userAgent.substring(userAgent.indexOf("MSIE"))
					.split(";")[0];
			browser = substring.split(" ")[0].replace("MSIE", "IE") + "-"
					+ substring.split(" ")[1];
		} else if (user.contains("safari") && user.contains("version")) {
			browser = (userAgent.substring(userAgent.indexOf("Safari")).split(
					" ")[0]).split("/")[0]
					+ "-"
					+ (userAgent.substring(userAgent.indexOf("Version")).split(
							" ")[0]).split("/")[1];
		} else if (user.contains("opr") || user.contains("opera")) {
			if (user.contains("opera"))
				browser = (userAgent.substring(userAgent.indexOf("Opera"))
						.split(" ")[0]).split("/")[0]
						+ "-"
						+ (userAgent.substring(userAgent.indexOf("Version"))
								.split(" ")[0]).split("/")[1];
			else if (user.contains("opr"))
				browser = ((userAgent.substring(userAgent.indexOf("OPR"))
						.split(" ")[0]).replace("/", "-")).replace("OPR",
						"Opera");
		} else if (user.contains("chrome")) {
			browser = (userAgent.substring(userAgent.indexOf("Chrome")).split(
					" ")[0]).replace("/", "-");
		} else if ((user.indexOf("mozilla/7.0") > -1)
				|| (user.indexOf("netscape6") != -1)
				|| (user.indexOf("mozilla/4.7") != -1)
				|| (user.indexOf("mozilla/4.78") != -1)
				|| (user.indexOf("mozilla/4.08") != -1)
				|| (user.indexOf("mozilla/3") != -1)) {
			browser = "Netscape-?";

		} else if (user.contains("firefox")) {
			browser = (userAgent.substring(userAgent.indexOf("Firefox")).split(
					" ")[0]).replace("/", "-");
		} else if (user.contains("rv")) {
			browser = "IE";
		} else {
			browser = "UnKnown, More-Info: " + userAgent;
		}
		return browser;
	}

	/**
	 * @param userAgent
	 * @return
	 */
	protected String getOs(String userAgent) {
		String os;
		// =================OS=======================
		if (userAgent.toLowerCase().indexOf(WINDOWS) >= 0) {
			os = OS_WINDOWS;
		} else if (userAgent.toLowerCase().indexOf(MAC) >= 0) {
			os = OS_MAC;
		} else if (userAgent.toLowerCase().indexOf(UNIX) >= 0) {
			os = OS_UNIX;
		} else if (userAgent.toLowerCase().indexOf(ANDROID) >= 0) {
			os = OS_ANDROID;
		} else if (userAgent.toLowerCase().indexOf(IPHONE) >= 0) {
			os = OS_IPHONE;
		} else {
			os = "UnKnown, More-Info: " + userAgent;
		}
		return os;
	}

	/**
	 * @param status
	 * @throws ParseException
	 */
	protected void manageHttpStatus(int status) throws ParseException {
		// find
		Query query = new Query();
		Date current = new Date(System.currentTimeMillis());
		String inicioDia = sdf.format(current);
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime inicioDiaDate = LocalDateTime.parse(inicioDia + " 00:00",
				formatter);
		LocalDateTime finDiaDate = inicioDiaDate.plusHours(23).plusMinutes(59)
				.plusSeconds(59);
		Date fin = Date.from(finDiaDate.toInstant(ZoneOffset.UTC));
		Date inicio = Date.from(inicioDiaDate.toInstant(ZoneOffset.UTC));
		query.addCriteria(Criteria.where("status").is(status).and("fecha")
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
					Update.update("count", metrica.getCount() + 1),
					MetricaHttpStatus.class);
		}
	}

	/**
	 * @param status
	 */
	protected void manageHttpRequest(String request) {
		// find
		Query query = new Query();
		Date current = new Date(System.currentTimeMillis());
		String inicioDia = sdf.format(current);
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime inicioDiaDate = LocalDateTime.parse(inicioDia + " 00:00",
				formatter);
		LocalDateTime finDiaDate = inicioDiaDate.plusHours(23).plusMinutes(59)
				.plusSeconds(59);
		Date fin = Date.from(finDiaDate.toInstant(ZoneOffset.UTC));
		Date inicio = Date.from(inicioDiaDate.toInstant(ZoneOffset.UTC));
		query.addCriteria(Criteria.where("request").is(request).and("fecha")
				.gte(inicio).lte(fin));
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
					Update.update("count", metrica.getCount() + 1),
					MetricaHttpRequest.class);
		}
	}

	/**
	 * @param status
	 */
	protected void manageHttpUserAgent(String os, String browser) {
		// find
		Query query = new Query();
		Date current = new Date(System.currentTimeMillis());
		String inicioDia = sdf.format(current);
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime inicioDiaDate = LocalDateTime.parse(inicioDia + " 00:00",
				formatter);
		LocalDateTime finDiaDate = inicioDiaDate.plusHours(23).plusMinutes(59)
				.plusSeconds(59);
		Date fin = Date.from(finDiaDate.toInstant(ZoneOffset.UTC));
		Date inicio = Date.from(inicioDiaDate.toInstant(ZoneOffset.UTC));
		query.addCriteria(Criteria
				.where("os")
				.is(os)
				.andOperator(
						Criteria.where("browser").is(browser).and("fecha")
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
					Update.update("count", metrica.getCount() + 1),
					MetricaHttpUserAgent.class);
		}
	}

	private void manageHttpIP(String ip) {
		// find
		Query query = new Query();
		Date current = new Date(System.currentTimeMillis());
		String inicioDia = sdf.format(current);
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime inicioDiaDate = LocalDateTime.parse(inicioDia + " 00:00",
				formatter);
		LocalDateTime finDiaDate = inicioDiaDate.plusHours(23).plusMinutes(59)
				.plusSeconds(59);
		Date fin = Date.from(finDiaDate.toInstant(ZoneOffset.UTC));
		Date inicio = Date.from(inicioDiaDate.toInstant(ZoneOffset.UTC));
		query.addCriteria(Criteria.where("ip").is(ip).and("fecha").gte(inicio)
				.lte(fin));
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
					Update.update("count", metrica.getCount() + 1),
					MetricaHttpIP.class);
		}
	}

	@Transactional(readOnly = true)
	public Pair<Integer, Integer> getSystemStadistics() {
		Pair<Integer, Integer> pair = Pair.create(0, 0);
		AggregationOperation group = Aggregation.group("id").sum("count")
				.as("count");
		Aggregation aggregation = Aggregation.newAggregation(group);
		AggregationResults<DBObject> result = mongoOperation.aggregate(
				aggregation, "userAgent", DBObject.class);
		if (result.getUniqueMappedResult() != null) {
			int allRequest = Integer.parseInt(result.getUniqueMappedResult()
					.get("count").toString());

			AggregationOperation match = Aggregation.match(Criteria.where("os")
					.regex("^" + OS_WINDOWS + "|" + OS_MAC + "|" + OS_UNIX
							+ "$"));
			group = Aggregation.group("id").sum("count").as("count");
			aggregation = Aggregation.newAggregation(match, group);
			result = mongoOperation.aggregate(aggregation, "userAgent",
					DBObject.class);
			int desktopRequest = 0;
			if (result.getUniqueMappedResult() != null) {
				desktopRequest = Integer.parseInt(result
						.getUniqueMappedResult().get("count").toString());
			}

			match = Aggregation.match(Criteria.where("os").regex(
					"^" + OS_ANDROID + "|" + OS_IPHONE + "$"));
			group = Aggregation.group("id").sum("count").as("count");
			aggregation = Aggregation.newAggregation(match, group);
			result = mongoOperation.aggregate(aggregation, "userAgent",
					DBObject.class);
			int mobileRequest = 0;
			if (result.getUniqueMappedResult() != null) {
				mobileRequest = Integer.parseInt(result.getUniqueMappedResult()
						.get("count").toString());
			}

			Integer mobilePercentaje = (mobileRequest * 100) / allRequest;
			Integer desktopercentaje = (desktopRequest * 100) / allRequest;

			pair = Pair.create(mobilePercentaje, desktopercentaje);
		}
		return pair;
	}
	@Transactional(readOnly = true)
	public Integer siteVisitors() {
		Aggregation agg = newAggregation(
			    group("ip").count().as("n"),
			    project("n").and("ip").previousOperation()
			);

		AggregationResults<DBObject> results = mongoOperation.aggregate(agg, "ip", DBObject.class);
		Integer siteVisitors = 0;
		if ( results.getUniqueMappedResult() != null  ){
			siteVisitors = Integer.parseInt(results.getUniqueMappedResult().get("n").toString());
		}
		return siteVisitors;
	}
	@Transactional(readOnly = true)
	public Integer peticiones() {
		GroupByResults<MetricaHttpRequest> results = mongoOperation.group(
				"request",
				GroupBy.key("request")
						.initialDocument("{ count: 0 }")
						.reduceFunction(
								"function(doc, prev) { prev.count += 1 }"),
								MetricaHttpRequest.class);
		String result = results.getRawResults().get("count").toString();
		return Integer.parseInt(result);
	}

}
