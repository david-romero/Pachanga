/**
 * 
 */
package com.p.service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.p.model.User;

@Service
/**
 * @author David
 *
 */
public class EmailManager {

	@Autowired
    private JavaMailSender mailSender;
	@Autowired
	private MetricService metricService;
	
	protected static final Logger log = Logger.getLogger(EmailManager.class);

    public void notify(User newUser) {

    	boolean enviadoCorrectamente = true;
    	
        // Do the business calculations...

        // Call the collaborators to persist the order...

        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) throws Exception {

                mimeMessage.setRecipient(Message.RecipientType.TO,
                        new InternetAddress(newUser.getEmail()));
                mimeMessage.setFrom(new InternetAddress("pachanga@pachanga.com"));
                mimeMessage.setText("Hola " + newUser.getEmail() + 
                		" <br>" +
                		"Gracias por registrarte en Pachanga.<br>" +
                		"Ya puedes acceder a la aplicaci&oacute;n");
            }
        };

        try {
            this.mailSender.send(preparator);
        }
        catch (MailException ex) {
            log.error(ex);
            enviadoCorrectamente = false;
        }
        
        metricService.saveEmail(enviadoCorrectamente);
        
    }

	public void notifyNewPassword(User user, String newPass) {
		
		boolean enviadoCorrectamente = true;
		try {
	        // Do the business calculations...
	
	        // Call the collaborators to persist the order...
	    	
	    	MimeMessage message = mailSender.createMimeMessage();
	    	
	    	MimeMessageHelper helper = new MimeMessageHelper(message, true);
	    	
	    	helper.setFrom("pachanga@pachanga.com");
			helper.setTo(user.getEmail());
			helper.setSubject("Regeneración de password en Pachanga");
			helper.setText("Hola " + user.getEmail() + 
            		" <br>" +
            		"Se ha recibido una solicitud de regeneración de clave.<br>" +
            		"Su nueva clave es " + newPass , true);


        
            this.mailSender.send(message);
        }
        catch (MailException | MessagingException ex) {
            log.error(ex);
            enviadoCorrectamente = false;
        }
		
		metricService.saveEmail(enviadoCorrectamente);
	}

}
