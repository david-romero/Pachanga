package com.p.controller;

/**
 * imports
 */
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.p.model.User;
import com.p.service.UsersService;


@Controller
/**
 * Abstract!
 * @author David Romero Alcaide
 *
 */
public abstract class AbstractController {

	/**
	 * Log
	 */
	private static final Logger LOGGER = Logger
			.getLogger(AbstractController.class);
	
	@Autowired
	protected UsersService userService;

	// Panic handler ----------------------------------------------------------

	@ExceptionHandler(Throwable.class)
	/**
	 * panic
	 * @author David Romero Alcaide
	 * @param oops
	 * @return
	 */
	public ModelAndView panic(Throwable oops) {
		oops.printStackTrace();
		LOGGER.error(oops);
		ModelAndView result;

		result = new ModelAndView();
		result.addObject("name", ClassUtils.getShortName(oops.getClass()));
		result.addObject("exceptionMessage", oops.getMessage());
		result.addObject("stackTrace", ExceptionUtils.getStackTrace(oops));

		return result;
	}

	// Transaction management -------------------------------------------------

	@Autowired
	/**
	 * Manager
	 */
	private PlatformTransactionManager transactionManager;
	/**
	 * State of transaction
	 */
	protected TransactionStatus txStatus;

	/**
	 * Begin a transaction with the database
	 * 
	 * @author David Romero Alcaide
	 */
	protected void beginTransaction() {
		assert txStatus == null;
		beginTransaction(false);
	}

	/**
	 * Begin a transaction with the database
	 * 
	 * @author David Romero Alcaide
	 * @param readOnly
	 */
	protected void beginTransaction(boolean readOnly) {
		assert txStatus == null;
		
		if ( txStatus != null ){
			commitTransaction();
		}

		DefaultTransactionDefinition definition;

		definition = new DefaultTransactionDefinition();
		definition
				.setIsolationLevel(DefaultTransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		definition
				.setPropagationBehavior(DefaultTransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		definition.setReadOnly(readOnly);
		txStatus = transactionManager.getTransaction(definition);
	}

	/**
	 * Commit a transaction
	 * 
	 * @author David Romero Alcaide
	 * @throws Throwable
	 */
	protected void commitTransaction() throws TransactionException {
		assert txStatus != null;
		if (txStatus!=null){
			try {
				transactionManager.commit(txStatus);
				txStatus = null;
			} catch (TransactionException oops) {
				throw oops;
			}
		}
	}

	/**
	 * Rollback a transaction
	 * 
	 * @author David Romero Alcaide
	 * @throws Throwable
	 */
	protected void rollbackTransaction() throws TransactionException {
		assert txStatus != null;
		try {
			if (!txStatus.isCompleted()) {
				transactionManager.rollback(txStatus);
			}
			txStatus = null;
		} catch (TransactionException oops) {
			throw oops;
		}
	}
	
	@ExceptionHandler(NullPointerException.class)
    public ModelAndView handleNullPointerException(HttpServletRequest request, Exception ex){
		LOGGER.error("Requested URL="+request.getRequestURL());
		LOGGER.error("Exception Raised="+ex);
         
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", ex);
        modelAndView.addObject("url", request.getRequestURL());
         
        modelAndView.setViewName("error");
        return modelAndView;
    } 
	
	protected User findUserSigned() {
		User usr = null;
		org.springframework.security.core.userdetails.User userSigned = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		try {
			beginTransaction(true);
			usr = userService.getByEmail(userSigned.getUsername());
			commitTransaction();
		} catch (Exception e) {
			LOGGER.error(e);
			rollbackTransaction();
		}
		return usr;
	}
	
	/**
	 * Devuelve el email del usuario logueado en la aplicacion
	 * 
	 * @return
	 */
	protected String findUsernameUserSigned() {
		Authentication auth =
		SecurityContextHolder
		.getContext().getAuthentication();
		if ( (auth instanceof AnonymousAuthenticationToken) ){
			return ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();
		}else{
			return "Anonymous";
		}
	}

}
