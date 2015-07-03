package com.p.config.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.p.service.MetricService;

public class MetricFilter implements Filter {
	 
    private MetricService metricService;
    
    protected static final Logger log = Logger.getLogger(MetricFilter.class);
    
	
 
    @Override
    public void init(FilterConfig config) throws ServletException {
        metricService = (MetricService) WebApplicationContextUtils
         .getRequiredWebApplicationContext(config.getServletContext())
         .getBean("metricService");
    }
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
      throws java.io.IOException, ServletException {
        
        try{
        	chain.doFilter(request, response);
        	metricService.saveMetrics( (HttpServletRequest) request, (HttpServletResponse) response);
        	//Si hay algun fallo no paramos la aplicacion. Las metricas son secundarias.
        }catch(Exception e ){
        	log.error(e);
        }
    }

	@Override
	public void destroy() {
		metricService = null;
	}

}
