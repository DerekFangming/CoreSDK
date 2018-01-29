package com.fmning.service.manager.impl;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;
import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fmning.service.exceptions.SessionExpiredException;
import com.fmning.service.manager.HelperManager;
import com.fmning.util.ErrorMessage;

@Component
public class HelperManagerImpl implements HelperManager{

	public static final String SECRET = "PJNing";
	
	@Override
	public void sendEmail(String from, String to, String subject, String content){

	      String host = "localhost";
	      Properties properties = System.getProperties();
	      properties.setProperty("mail.smtp.host", host);

	      Session session = Session.getDefaultInstance(properties);
	      try{
	         MimeMessage message = new MimeMessage(session);
	         message.setFrom(new InternetAddress(from));
	         if (to.indexOf(',') > 0){
	        	 message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));   
	         } else {
	        	 message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
	         }
	         message.setSubject(subject);
	         message.setText(content);
	         Transport.send(message);
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	}

	@Override
	public String getEmailConfirmCode(String username) {
		String exp = Instant.now().plus(Duration.ofDays(1)).toString();
		try {
			Algorithm algorithm = Algorithm.HMAC256(SECRET);
		    String token = JWT.create()
		    		.withClaim("username", username)
		    		.withClaim("action", "emailVeri")
		    		.withClaim("expire", exp)
		    		.sign(algorithm);
		    return token;
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			// TODO Do something else?
			return null;
		}
	}
	
	public String getChangePasswordCode(String username) {
		String exp = Instant.now().plus(Duration.ofDays(1)).toString();
		try {
			Algorithm algorithm = Algorithm.HMAC256(SECRET);
		    String token = JWT.create()
		    		.withClaim("username", username)
		    		.withClaim("action", "pwdVeri")
		    		.withClaim("expire", exp)
		    		.sign(algorithm);
		    return token;
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			return null;
		}
	}

	@Override
	public Map<String, Object> decodeJWT(String JWTStr) throws IllegalStateException{
		
		try {
		    Algorithm algorithm = Algorithm.HMAC256(SECRET);
		    JWTVerifier verifier = JWT.require(algorithm).build();
		    DecodedJWT jwt = verifier.verify(JWTStr);
		    Map<String, Claim> claims = jwt.getClaims();
		    Map<String, Object> result = new HashMap<String, Object>();
	        for (String key : claims.keySet()) {
	            result.put(key, claims.get(key).asString());
	        }
	        
	        return result;
		} catch (UnsupportedEncodingException | JWTVerificationException e){
			throw new IllegalStateException(ErrorMessage.INVALID_ACCESS_TOKEN.getMsg());
		}
	}

	@Override
	public String getEmailConfirmedPage(String msg) {
		String temp = "<!DOCTYPE html> <html lang='en'> <head> <title>Email confirmation</title> <link href='http://fmning.com/css/bootstrap.min.css' rel='stylesheet'> <link href='http://fmning.com/css/agency.css' rel='stylesheet'> <link href='http://fmning.com/font-awesome/css/font-awesome.min.css' rel='stylesheet' type='text/css'> <link href='https://fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'> <link href='https://fonts.googleapis.com/css?family=Kaushan+Script' rel='stylesheet' type='text/css'> <link href='https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic' rel='stylesheet' type='text/css'> <link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700' rel='stylesheet' type='text/css'> <!--[if lt IE 9]> <script src='https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js'></script> <script src='https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js'></script> <![endif]--> <script type='text/javascript'> function close_window() { close(); } </script> </head> <body id='page-top' class='index'> <nav class='navbar navbar-default navbar-fixed-top'> <div class='container'> <div class='navbar-header page-scroll'> <a class='navbar-brand page-scroll' href='#page-top'>Fmning.com</a> </div> <div class='collapse navbar-collapse' id='bs-example-navbar-collapse-1'> <ul class='nav navbar-nav navbar-right'> </ul> </div> </div> </nav> <header> <div class='container'> <div class='intro-text'> <div class='intro-lead-in'>Thank you for registering at fmning.com domain</div> <div class='intro-lead-in";
		if(msg.equals("success")){
			temp+= "'>Your email address has been confirmed</div>";
		}else if(msg.equals("resend")){
			temp+= " text-danger'>Your confirmation code has expired</div>";
			temp+= "<div class='intro-lead-in'>A new confirmation email has been sent to your inbox</div>";
		}else{
			temp+= (" text-danger'>" + msg +"</div>");
			temp+= "<div class='intro-lead-in'>Please email <a href='mailto:admin@fmning.com?Subject=";
			temp+= msg.replace(" ", "%20");
			temp+= "' target='_top'>admin@fmning.com</a> for support</div>";
		}
		temp += "<a href='#' onclick='close_window();return false;' class='page-scroll btn btn-xl'>close</a> </div> </div> </header> <footer> <div class='container'> <div class='row'> <div class='col-md-4'> <span class='copyright'>Copyright &copy; fmning.com 2017</span> </div> <div class='col-md-4'> <ul class='list-inline social-buttons'> <li><a href='#'><i class='fa fa-twitter'></i></a> </li> <li><a href='#'><i class='fa fa-facebook'></i></a> </li> <li><a href='#'><i class='fa fa-linkedin'></i></a> </li> </ul> </div> <div class='col-md-4'> <ul class='list-inline quicklinks'> <li><a href='http://fmning.com/privacy_policy.html' target='_blank'>Privacy Policy</a> </li> <li><a href='http://fmning.com/term_of_use.html' target='_blank'>Terms of Use</a> </li> </ul> </div> </div> </div> </footer> <script src='http://fmning.com/js/jquery.js'></script> <script src='ttp://fmning.com/js/bootstrap.min.js'></script> <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js'></script> <script src='http://fmning.com/js/classie.js'></script> <script src='http://fmning.com/js/cbpAnimatedHeader.js'></script> <script src='http://fmning.com/js/agency.js'></script> </body> </html>";
		return temp;
	}

	@Override
	public String createAccessToken(String username, Instant expDate) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(SECRET);
		    String token = JWT.create()
		    		.withClaim("username", username)
		    		.withClaim("expire", expDate.toString())
		    		.sign(algorithm);
		    return token;
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			// TODO Do something else?
			return null;
		}
	}

	@Override
	public void checkSessionTimeOut(String time) throws SessionExpiredException {
		Instant exp = Instant.parse(time);
		if(exp.compareTo(Instant.now()) < 0)
			throw new SessionExpiredException();
		
	}

}
