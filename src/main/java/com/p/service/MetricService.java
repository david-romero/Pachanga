package com.p.service;

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
import com.p.config.filter.MetricFilter;
import com.p.model.metricas.MetricaHttpRequest;
import com.p.model.metricas.MetricaHttpStatus;
import com.p.model.metricas.MetricaHttpUserAgent;

@Service
public class MetricService {

	// For Annotation
	ApplicationContext ctx = new AnnotationConfigApplicationContext(
			DbConfigMetricas.class);
	MongoOperations mongoOperation = (MongoOperations) ctx
			.getBean("mongoTemplate");
	
	protected static final Logger log = Logger.getLogger(MetricService.class);


	@Transactional()
	public void saveMetrics(HttpServletRequest request,HttpServletResponse response){
		try{
			HttpServletRequest httpRequest = ((HttpServletRequest) request);
	        String req = httpRequest.getMethod() + " " + httpRequest.getRequestURI();        
	        
	        String  browserDetails  =   httpRequest.getHeader("User-Agent");
	        String  userAgent       =   browserDetails;
	        String  user            =   userAgent.toLowerCase();
	
	        String os = getOs(userAgent);
	        String browser = getBrowser(userAgent, user);
	        
	        int status = ((HttpServletResponse) response).getStatus();
	        
	        
	        manageHttpRequest(req);
	        manageHttpStatus(status);
	        manageHttpUserAgent(os, browser);
		}catch(Exception e){
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
		//===============Browser===========================
        if (user.contains("msie"))
        {
            String substring=userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
            browser=substring.split(" ")[0].replace("MSIE", "IE")+"-"+substring.split(" ")[1];
        } else if (user.contains("safari") && user.contains("version"))
        {
            browser=(userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
        } else if ( user.contains("opr") || user.contains("opera"))
        {
            if(user.contains("opera"))
                browser=(userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
            else if(user.contains("opr"))
                browser=((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-")).replace("OPR", "Opera");
        } else if (user.contains("chrome"))
        {
            browser=(userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
        } else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1)  || (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1) || (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1) )
        {
            browser = "Netscape-?";

        } else if (user.contains("firefox"))
        {
            browser=(userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
        } else if(user.contains("rv"))
        {
            browser="IE";
        } else
        {
            browser = "UnKnown, More-Info: "+userAgent;
        }
		return browser;
	}
	/**
	 * @param userAgent
	 * @return
	 */
	protected String getOs(String userAgent) {
		String os;
		//=================OS=======================
         if (userAgent.toLowerCase().indexOf("windows") >= 0 )
         {
             os = "Windows";
         } else if(userAgent.toLowerCase().indexOf("mac") >= 0)
         {
             os = "Mac";
         } else if(userAgent.toLowerCase().indexOf("x11") >= 0)
         {
             os = "Unix";
         } else if(userAgent.toLowerCase().indexOf("android") >= 0)
         {
             os = "Android";
         } else if(userAgent.toLowerCase().indexOf("iphone") >= 0)
         {
             os = "IPhone";
         }else{
             os = "UnKnown, More-Info: "+userAgent;
         }
		return os;
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
	
	/**
	 * @param status
	 */
	protected void manageHttpUserAgent(String os,String browser) {
		// find
		Query query = new Query();
		query.addCriteria(Criteria.where("os").is(os).andOperator(Criteria.where("browser").is(browser)));
		MetricaHttpUserAgent metrica = mongoOperation.findOne(query, MetricaHttpUserAgent.class,
				"userAgent");

		if (metrica == null || metrica.getId() == null
				|| metrica.getId().isEmpty()) {
			metrica = new MetricaHttpUserAgent();
			metrica.setCount(1);
			metrica.setBrowser(browser);
			metrica.setOs(os);
			mongoOperation.save(metrica,"userAgent");
		} else {
			mongoOperation.updateFirst(query, 
                    Update.update("count", metrica.getCount() + 1),MetricaHttpUserAgent.class);
		}
	}

}
