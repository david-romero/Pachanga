package com.p.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.p.config.DbConfigMetricas;
import com.p.model.Partido;
import com.p.model.User;
import com.p.model.metricas.MetricaHttpLogin;
import com.p.model.metricas.MetricaHttpRequest;
import com.p.model.metricas.MetricaHttpStatus;
import com.p.model.metricas.MetricaPartidoUsuario;
import com.p.model.modelAux.Pair;

@Service
public class MetricStadisticsService {

	private static final String OS_IPHONE = "IPhone";
	private static final String OS_ANDROID = "Android";
	private static final String OS_UNIX = "Unix";
	private static final String OS_MAC = "Mac";
	private static final String OS_WINDOWS = "Windows";

	// For Annotation
	ApplicationContext ctx = new AnnotationConfigApplicationContext(
			DbConfigMetricas.class);
	MongoOperations mongoOperation = (MongoOperations) ctx
			.getBean("mongoTemplate");
	
	protected static final Logger log = Logger.getLogger(MetricStadisticsService.class);

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
		String json = "{" + "\"aggregate\": \"ip\" ," + "\"pipeline\": ["
				+ "{ $group: { _id: \"$ip\"}  },"
				+ "{ $group: { _id: 1, count: { $sum: 1 } } }" + "]" + "}";

		com.mongodb.CommandResult result = mongoOperation.executeCommand(json);
		BasicDBList results = (BasicDBList) result.get("result");
		Integer ipsDistintas = 0;
		for (Iterator<Object> it = results.iterator(); it.hasNext();) {
			BasicDBObject objeto = (BasicDBObject) it.next();
			ipsDistintas = (Integer) objeto.get("count");
		}
		return ipsDistintas;
	}

	@Transactional(readOnly = true)
	public List<Integer> listSiteVisitors() {
		Aggregation agg = newAggregation(
				project("ip").andExpression("dayOfMonth(fecha)").as("day")
						.andExpression("month(fecha)").as("month")
						.andExpression("year(fecha)").as("year"),
				group("day", "month", "year").count().as("count"),
				sort(Sort.Direction.DESC, "year", "month", "day"), limit(30));
		AggregationResults<DBObject> results = mongoOperation.aggregate(agg,
				"ip", DBObject.class);
		List<DBObject> objetos = Lists.newArrayList(results.getMappedResults());
		// Le damos la vuelta porque la consulta me viene con el primer indice
		// el dia mas cercado a hoy
		Collections.reverse(objetos);
		LocalDate fecha = LocalDate.now();
		fecha = fecha.minusDays(30);
		List<Integer> resultados = Lists.newArrayList();
		for (DBObject object : objetos) {
			Integer day = Integer.parseInt(object.get("day").toString());
			Integer month = Integer.parseInt(object.get("month").toString());
			Integer year = Integer.parseInt(object.get("year").toString());
			LocalDate fechaDeLaVisita = LocalDate.of(year, month, day);
			long distancia = Duration.between(fechaDeLaVisita.atTime(0, 0),
					fecha.atTime(0, 0)).toDays();
			distancia = Math.abs(distancia) - 1;
			for (int i = 0; i < distancia; i++) {
				resultados.add(0);
			}
			fecha = LocalDate.from(fechaDeLaVisita);
			Integer numeroVisitas = Integer.parseInt(object.get("count")
					.toString());
			resultados.add(numeroVisitas);
		}
		return resultados;
	}

	@Transactional(readOnly = true)
	public List<Integer> listRequest() {
		Aggregation agg = newAggregation(
				project("request", "count").andExpression("dayOfMonth(fecha)")
						.as("day").andExpression("month(fecha)").as("month")
						.andExpression("year(fecha)").as("year"),
				group("day", "month", "year").sum("count").as("total"),
				sort(Sort.Direction.DESC, "year", "month", "day"), limit(16));
		AggregationResults<DBObject> results = mongoOperation.aggregate(agg,
				"request", DBObject.class);
		List<DBObject> objetos = Lists.newArrayList(results.getMappedResults());
		// Le damos la vuelta porque la consulta me viene con el primer indice
		// el dia mas cercado a hoy
		Collections.reverse(objetos);
		LocalDate fecha = LocalDate.now();
		fecha = fecha.minusDays(16);
		List<Integer> resultados = Lists.newArrayList();
		for (DBObject object : objetos) {
			Integer day = Integer.parseInt(object.get("day").toString());
			Integer month = Integer.parseInt(object.get("month").toString());
			Integer year = Integer.parseInt(object.get("year").toString());
			LocalDate fechaDeLaVisita = LocalDate.of(year, month, day);
			long distancia = Duration.between(fechaDeLaVisita.atTime(0, 0),
					fecha.atTime(0, 0)).toDays();
			distancia = Math.abs(distancia) - 1;
			for (int i = 0; i < distancia; i++) {
				resultados.add(0);
			}
			fecha = LocalDate.from(fechaDeLaVisita);
			Integer numeroVisitas = Integer.parseInt(object.get("total")
					.toString());
			resultados.add(numeroVisitas);
		}
		return resultados;
	}

	@Transactional(readOnly = true)
	public List<Integer> listLogin() {
		Criteria criteria = Criteria.where("username").not().regex("admin");
		Aggregation agg = newAggregation(match(criteria),
				project("username", "count").andExpression("dayOfMonth(fecha)")
						.as("day").andExpression("month(fecha)").as("month")
						.andExpression("year(fecha)").as("year"),
				group("day", "month", "year").count().as("total"),
				sort(Sort.Direction.DESC, "year", "month", "day"), limit(16));
		AggregationResults<DBObject> results = mongoOperation.aggregate(agg,
				"login", DBObject.class);
		List<DBObject> objetos = Lists.newArrayList(results.getMappedResults());
		// Le damos la vuelta porque la consulta me viene con el primer indice
		// el dia mas cercado a hoy
		Collections.reverse(objetos);
		LocalDate fecha = LocalDate.now();
		fecha = fecha.minusDays(16);
		List<Integer> resultados = Lists.newArrayList();
		for (DBObject object : objetos) {
			Integer day = Integer.parseInt(object.get("day").toString());
			Integer month = Integer.parseInt(object.get("month").toString());
			Integer year = Integer.parseInt(object.get("year").toString());
			LocalDate fechaDeLaVisita = LocalDate.of(year, month, day);
			long distancia = Duration.between(fechaDeLaVisita.atTime(0, 0),
					fecha.atTime(0, 0)).toDays();
			distancia = Math.abs(distancia) - 1;
			for (int i = 0; i < distancia; i++) {
				resultados.add(0);
			}
			fecha = LocalDate.from(fechaDeLaVisita);
			Integer numeroVisitas = Integer.parseInt(object.get("total")
					.toString());
			resultados.add(numeroVisitas);
		}
		return resultados;
	}

	@Transactional(readOnly = true)
	public List<Integer> listErrores() {
		Criteria criteria = Criteria
				.where("status")
				.exists(true)
				.orOperator(Criteria.where("status").is(404),
						Criteria.where("status").is(500));
		Aggregation agg = newAggregation(match(criteria),
				project("status", "count").andExpression("dayOfMonth(fecha)")
						.as("day").andExpression("month(fecha)").as("month")
						.andExpression("year(fecha)").as("year"),
				group("day", "month", "year").sum("count").as("total"),
				sort(Sort.Direction.DESC, "year", "month", "day"), limit(30));
		AggregationResults<DBObject> results = mongoOperation.aggregate(agg,
				"metricas", DBObject.class);
		List<DBObject> objetos = Lists.newArrayList(results.getMappedResults());
		// Le damos la vuelta porque la consulta me viene con el primer indice
		// el dia mas cercado a hoy
		Collections.reverse(objetos);
		LocalDate fecha = LocalDate.now();
		fecha = fecha.minusDays(30);
		List<Integer> resultados = Lists.newArrayList();
		for (DBObject object : objetos) {
			Integer day = Integer.parseInt(object.get("day").toString());
			Integer month = Integer.parseInt(object.get("month").toString());
			Integer year = Integer.parseInt(object.get("year").toString());
			LocalDate fechaDeLaVisita = LocalDate.of(year, month, day);
			long distancia = Duration.between(fechaDeLaVisita.atTime(0, 0),
					fecha.atTime(0, 0)).toDays();
			distancia = Math.abs(distancia) - 1;
			for (int i = 0; i < distancia; i++) {
				resultados.add(0);
			}
			fecha = LocalDate.from(fechaDeLaVisita);
			Integer numeroVisitas = Integer.parseInt(object.get("total")
					.toString());
			resultados.add(numeroVisitas);
		}
		return resultados;
	}

	@Transactional(readOnly = true)
	public Integer siteTraffict() {
		Aggregation agg = newAggregation(group("id").sum("count").as("total"));
		AggregationResults<DBObject> results = mongoOperation.aggregate(agg,
				"request", DBObject.class);
		String result = ((DBObject) ((BasicDBList) ((DBObject) results
				.getRawResults().get("result"))).get(0)).get("total")
				.toString();
		return Integer.parseInt(result);
	}

	@Transactional(readOnly = true)
	public Integer usuariosActivos() {
		LocalDateTime fecha = LocalDateTime.now();
		fecha = fecha.minusDays(30);
		Criteria criteria = Criteria.where("fecha")
				.gt(Date.from(fecha.toInstant(ZoneOffset.UTC))).and("username")
				.not().regex("admin");
		GroupByResults<MetricaHttpLogin> results = mongoOperation.group(
				criteria,
				"login",
				GroupBy.key("username")
						.initialDocument("{ count: 0 }")
						.reduceFunction(
								"function(doc, prev) { prev.count += 1 }"),
				MetricaHttpLogin.class);
		return results.getKeys();
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

	@Transactional(readOnly = true)
	public Integer errores() {
		Criteria criteria = Criteria
				.where("status")
				.exists(true)
				.orOperator(Criteria.where("status").is(404),
						Criteria.where("status").is(500));
		GroupByResults<MetricaHttpStatus> results = mongoOperation
				.group(criteria,
						"metricas",
						GroupBy.key("status")
								.initialDocument("{ count: 0 }")
								.reduceFunction(
										"function(doc, prev) { prev.count += doc.count }"),
						MetricaHttpStatus.class);
		String result = results.getRawResults().get("count").toString();
		return Integer.parseInt(result);
	}

	@Transactional(readOnly = true)
	public Integer paginas() {
		Criteria criteria = Criteria.where("request").regex("resources").not();
		GroupByResults<MetricaHttpRequest> results = mongoOperation.group(
				criteria,
				"request",
				GroupBy.key("request")
						.initialDocument("{ count: 0 }")
						.reduceFunction(
								"function(doc, prev) { prev.count += 1 }"),
				MetricaHttpRequest.class);
		String result = results.getRawResults().get("count").toString();
		return Integer.parseInt(result);
	}

	@Transactional(readOnly = true)
	public Integer partidos() {
		Aggregation agg = newAggregation(group("id").sum("count").as("total"));
		AggregationResults<DBObject> results = mongoOperation.aggregate(agg,
				"partido", DBObject.class);
		String result = "0";
		if (((BasicDBList) ((DBObject) results.getRawResults().get("result")))
				.size() > 0) {
			result = ((DBObject) ((BasicDBList) ((DBObject) results
					.getRawResults().get("result"))).get(0)).get("total")
					.toString();
		}
		return Integer.parseInt(result);
	}

	@Transactional(readOnly = true)
	public List<Integer> listPartidos() {
		Aggregation agg = newAggregation(
				project("count").andExpression("dayOfMonth(fecha)").as("day")
						.andExpression("month(fecha)").as("month")
						.andExpression("year(fecha)").as("year"),
				group("day", "month", "year").sum("count").as("total"),
				sort(Sort.Direction.DESC, "year", "month", "day"), limit(100));
		AggregationResults<DBObject> results = null;
		try {
			results = mongoOperation.aggregate(agg,
					"partido", DBObject.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<DBObject> objetos = Lists.newArrayList(results.getMappedResults());
		// Le damos la vuelta porque la consulta me viene con el primer indice
		// el dia mas cercado a hoy
		Collections.reverse(objetos);
		LocalDate fecha = LocalDate.now();
		fecha = fecha.minusDays(100);
		List<Integer> resultados = Lists.newArrayList();
		for (DBObject object : objetos) {
			Integer day = Integer.parseInt(object.get("day").toString());
			Integer month = Integer.parseInt(object.get("month").toString());
			Integer year = Integer.parseInt(object.get("year").toString());
			LocalDate fechaDeLaVisita = LocalDate.of(year, month, day);
			long distancia = Duration.between(fechaDeLaVisita.atTime(0, 0),
					fecha.atTime(0, 0)).toDays();
			distancia = Math.abs(distancia) - 1;
			for (int i = 0; i < distancia; i++) {
				resultados.add(0);
			}
			fecha = LocalDate.from(fechaDeLaVisita);
			Integer numeroVisitas = Integer.parseInt(object.get("total")
					.toString());
			resultados.add(numeroVisitas);
		}
		if ( resultados.size() == 0 ){
			for (int i = 1; i <= 30; i++){
				resultados.add(0);
			}
		}
		return resultados;
	}
	
	@Transactional(readOnly = true)
	public Integer mensajes() {
		Aggregation agg = newAggregation(group("id").sum("count").as("total"));
		AggregationResults<DBObject> results = mongoOperation.aggregate(agg,
				"mensaje", DBObject.class);
		String result = "0";
		if (((BasicDBList) ((DBObject) results.getRawResults().get("result")))
				.size() > 0) {
			result = ((DBObject) ((BasicDBList) ((DBObject) results
					.getRawResults().get("result"))).get(0)).get("total")
					.toString();
		}
		return Integer.parseInt(result);
	}

	@Transactional(readOnly = true)
	public List<Integer> listMensajes() {
		Aggregation agg = newAggregation(
				project("count").andExpression("dayOfMonth(fecha)").as("day")
						.andExpression("month(fecha)").as("month")
						.andExpression("year(fecha)").as("year"),
				group("day", "month", "year").sum("count").as("total"),
				sort(Sort.Direction.DESC, "year", "month", "day"), limit(30));
		AggregationResults<DBObject> results = null;
		try {
			results = mongoOperation.aggregate(agg,
					"mensaje", DBObject.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<DBObject> objetos = Lists.newArrayList(results.getMappedResults());
		// Le damos la vuelta porque la consulta me viene con el primer indice
		// el dia mas cercado a hoy
		Collections.reverse(objetos);
		LocalDate fecha = LocalDate.now();
		fecha = fecha.minusDays(30);
		List<Integer> resultados = Lists.newArrayList();
		for (DBObject object : objetos) {
			Integer day = Integer.parseInt(object.get("day").toString());
			Integer month = Integer.parseInt(object.get("month").toString());
			Integer year = Integer.parseInt(object.get("year").toString());
			LocalDate fechaDeLaVisita = LocalDate.of(year, month, day);
			long distancia = Duration.between(fechaDeLaVisita.atTime(0, 0),
					fecha.atTime(0, 0)).toDays();
			distancia = Math.abs(distancia) - 1;
			for (int i = 0; i < distancia; i++) {
				resultados.add(0);
			}
			fecha = LocalDate.from(fechaDeLaVisita);
			Integer numeroVisitas = Integer.parseInt(object.get("total")
					.toString());
			resultados.add(numeroVisitas);
		}
		if ( resultados.size() == 0 ){
			for (int i = 1; i <= 30; i++){
				resultados.add(0);
			}
		}
		return resultados;
	}
	
	@Transactional(readOnly = true)
	public List<MetricaPartidoUsuario> rankingUsuariosPartidos() {
		Aggregation agg = newAggregation(group("username","fullName").sum("count").as("total"),
				sort(Sort.Direction.DESC, "total"), limit(5));
		AggregationResults<MetricaPartidoUsuario> results = mongoOperation.aggregate(agg,
				"partidoUsuario", MetricaPartidoUsuario.class);
		List<MetricaPartidoUsuario> list = Lists.newArrayList();
		if (((BasicDBList) ((DBObject) results.getRawResults().get("result")))
				.size() > 0) {
			try {
				list = (((BasicDBList) ((DBObject) results.getRawResults().get("result"))).parallelStream().map( (dbObject) ->{
					MetricaPartidoUsuario metric = new MetricaPartidoUsuario();
					DBObject databaseObject = (DBObject) dbObject;
					metric.setCount(Integer.parseInt(databaseObject.get("total").toString()));
					metric.setFullName(((DBObject) databaseObject.get("_id")).get("fullName").toString());
					metric.setUsername(((DBObject) databaseObject.get("_id")).get("username").toString());
					return metric;
				})).collect(Collectors.toList());
			} catch (Exception e) {
				log.error(e);
			}
		}
		return list;
	}
	
	@Transactional(readOnly = true)
	public Integer getMailStadistics() {
		Integer porcentaje = 0;
		AggregationOperation group = Aggregation.group("id").sum("count")
				.as("count");
		Aggregation aggregation = Aggregation.newAggregation(group);
		AggregationResults<DBObject> result = mongoOperation.aggregate(
				aggregation, "email", DBObject.class);
		if (result.getUniqueMappedResult() != null) {
			int allRequest = Integer.parseInt(result.getUniqueMappedResult()
					.get("count").toString());

			AggregationOperation match = Aggregation.match(Criteria.where("enviado")
					.is(true));
			group = Aggregation.group("id").sum("count").as("count");
			aggregation = Aggregation.newAggregation(match, group);
			result = mongoOperation.aggregate(aggregation, "email",
					DBObject.class);
			int enviados = 0;
			if (result.getUniqueMappedResult() != null) {
				enviados = Integer.parseInt(result
						.getUniqueMappedResult().get("count").toString());
			}

			porcentaje = (enviados * 100) / allRequest;

		}
		return porcentaje;
	}

	@Transactional(readOnly = true)
	public Integer getProfileVisitsStadistics(User usr) {
		Criteria criteria = Criteria.where("request").regex("/P/usuarios/profile/"+usr.getId());
		AggregationOperation group = Aggregation.group("id").sum("count")
				.as("total");
		Aggregation aggregation = newAggregation(MetricaHttpRequest.class,
				match(criteria),
				group);
		AggregationResults<DBObject> result = mongoOperation.aggregate(
				aggregation, "request", DBObject.class);
		Integer visitas = 0;
		if (result.getUniqueMappedResult() != null) {
			visitas = Integer.parseInt(result.getUniqueMappedResult()
					.get("total").toString());
		}
		return visitas;
	}
	@Transactional(readOnly = true)
	public Integer getPartidoVisitsStadistics(Partido p) {
		Criteria criteria = Criteria.where("request").regex("/P/partido/show/"+p.getId());
		AggregationOperation group = Aggregation.group("id").sum("count")
				.as("total");
		Aggregation aggregation = newAggregation(MetricaHttpRequest.class,
				match(criteria),
				group);
		AggregationResults<DBObject> result = mongoOperation.aggregate(
				aggregation, "request", DBObject.class);
		Integer visitas = 0;
		if (result.getUniqueMappedResult() != null) {
			visitas = Integer.parseInt(result.getUniqueMappedResult()
					.get("total").toString());
		}
		return visitas;
	}

}
