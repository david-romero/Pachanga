package com.p.config.core;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.p.config.AppConfig;
import com.p.config.filter.MetricFilter;

public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { AppConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		servletContext
        .addFilter("metricFilter", 
                   new MetricFilter())
        .addMappingForUrlPatterns(null, false, "/*");
		RequestContextListener listener = new RequestContextListener();
		servletContext.addListener(listener);
		super.onStartup(servletContext);
	}
	
	
	
	
	
}