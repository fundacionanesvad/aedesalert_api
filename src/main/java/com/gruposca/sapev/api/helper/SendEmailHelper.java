/*******************************************************************************
 * Aedes Alert, Support to collect data to combat dengue
 * Copyright (C) 2017 Fundación Anesvad
 *   
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *   
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *   
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.gruposca.sapev.api.helper;

import javax.activation.*;
import javax.mail.*; 
import javax.mail.internet.*;

import com.gruposca.sapev.api.tools.Autentificacion;
import com.gruposca.sapev.api.tools.Template;

import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;


public class SendEmailHelper {
	//Para enviar con plantilla
			private Template template;// plantilla
			private Hashtable tabla = new Hashtable();// datos a enviar 
			private String path ="";//path de la plantilla HTML
			/////////////////////////////////////////////////////////
			private String host="";
			private String username="";
			private String password="";
			private boolean autentificarse=false;
			private String sender="";
			private String from="";
			private String fromAlias="";
			private String to="";
			private String toAlias="";
			private String bcc = "";
			private String cc = "";
			private String asunto="";
			private String atach = "";
			private String atach_name = "fichero";
			private String texto="";
			private String reply="";		
			private String pathAtach="";	
			//Para pre-prod 
			private String port="25";		
			private String ssl="false";

			public String getPathAtach() {
				return pathAtach;
			}
			public void setPathAtach(String pathAtach) {
				this.pathAtach = pathAtach;
			}
			public String getReply() {
				return reply;
			}
			public void setReply(String reply) {
				this.reply = reply;
			}
			
			public String getPort() {
				return port;
			}
			public void setPort(String port) {
				this.port = port;
			}
			public String getSsl() {
				return ssl;
			}
			public void setSsl(String ssl) {
				this.ssl = ssl;
			}
			public String getSender() {
				return sender;
			}
			public void setSender(String sender) {
				this.sender = sender;
			}		
			public String getPath() {
				return path;
			}
			public void setPath(String path) {
				this.path = path;
			}
			public String getAsunto() {
				return asunto;
			}
			public void setAsunto(String asunto) {
				this.asunto = asunto;
			}
			public String getAtach() {
				return atach;
			}
			public void setAtach(String atach) {
				this.atach = atach;
			}
			public String getAtach_name() {
				return atach_name;
			}
			public void setAtach_name(String atach_name) {
				this.atach_name = atach_name;
			}
			public boolean isAutentificarse() {
				return autentificarse;
			}
			public void setAutentificarse(boolean autentificarse) {
				this.autentificarse = autentificarse;
			}
			public String getCc() {
				return cc;
			}
			public void setCc(String cc) {
				this.cc = cc;
			}
			public String getBcc() {
				return bcc;
			}
			public void setBcc(String bcc) {
				this.bcc = bcc;
			}
			public String getFrom() {
				return from;
			}
			public void setFrom(String from) {
				this.from = from;
			}
			public String getFromAlias() {
				return fromAlias;
			}
			public void setFromAlias(String fromAlias) {
				this.fromAlias = fromAlias;
			}
			public String getHost() {
				return host;
			}
			public void setHost(String host) {
				this.host = host;
			}
			public String getPassword() {
				return password;
			}
			public void setPassword(String password) {
				this.password = password;
			}
			public Template getTemplate() {
				return template;
			}
			public void setTemplate(Template template) {
				this.template = template;
			}
			public Hashtable getTabla() {
				return tabla;
			}
			public void setTabla(Hashtable tabla) {
				this.tabla = tabla;
			}
			public String getTexto() {
				return texto;
			}
			public void setTexto(String texto) {
				this.texto = texto;
			}
			public String getTo() {
				return to;
			}
			public void setTo(String to) {
				this.to = to;
			}
			public String getToAlias() {
				return toAlias;
			}
			public void setToAlias(String toAlias) {
				this.toAlias = toAlias;
			}
			public String getUsername() {
				return username;
			}
			public void setUsername(String username) {
				this.username = username;
			}		

			public int sendWithTemplate(){ // Envio con plantilla
				try {
					String salidaHTML = "";
					if(!path.equals("")){
						this.template = new Template(this.tabla, path);
						this.template.hacerSustitucion();
						salidaHTML = this.template.getSalida();

					}else{
						salidaHTML=this.texto;
					}

					// cambiar new por System
					Properties prop = new Properties();
					prop.put("mail.smtp.host", this.host); 
					prop.put("mail.smtp.auth", String.valueOf(this.autentificarse)); 
					prop.put("mail.smtp.ssl.enable",this.ssl);
					prop.put("mail.smtp.port", this.port);
					prop.put("mail.transport.protocol","smtp");				
				    prop.setProperty("mail.smtp.quitwait", "false");				

					String debugActivo = "false";
					if(debugActivo.equals("true"))
						prop.put("mail.debug", "true"); 
					else 
						prop.put("mail.debug", "false");
					Session session = null;
					
					if(this.autentificarse){
						Autentificacion auth = new Autentificacion();
						auth.setUsername(this.username);
						auth.setPassword(this.password);
						session = Session.getInstance(prop , auth ); 
					}else{
						session = Session.getInstance(prop , null );
					}					
					
					// Cambio de message a mimemessage
					MimeMessage msg = new MimeMessage(session); 
					//ADD sender
					if(this.sender.equals(""))	this.sender=this.from;
					// Si el sender viene vacÃ­o
					msg.setSender(new InternetAddress(this.sender));
					msg.setFrom(new InternetAddress(this.from,this.fromAlias));
					msg.setReplyTo(InternetAddress.parse(this.from));            
					msg.setSentDate(new Date());
					
					msg.addRecipient(Message.RecipientType.TO, new InternetAddress(this.to,this.toAlias));
					msg.setSubject(this.asunto);
					//msg.setText(this.texto);
					if (!this.cc.equals("")) {
						String[] dirs = this.cc.split(";");
						InternetAddress[] addresses = new InternetAddress[dirs.length];
						for (int i = 0; i < dirs.length; i++) {
							addresses[i] = new InternetAddress(dirs[i]);
						}
						msg.setRecipients(Message.RecipientType.CC, addresses);
					}
					if (!this.bcc.equals("")) {
						String[] dirs = this.bcc.split(";");
						InternetAddress[] address3 = new InternetAddress[dirs.length];
						for (int i = 0; i < dirs.length; i++) {
							address3[i] = new InternetAddress(dirs[i]);
						}
						msg.setRecipients(Message.RecipientType.BCC, address3);
					}
					
					// Construir el cuerpo del email
					MimeBodyPart mbp = new MimeBodyPart();// AÃ±ado texto html
					mbp.setContent(salidaHTML, "text/html; charset=utf-8");
					Multipart mp = new MimeMultipart();
					mp.addBodyPart(mbp);
					
					//si hay adjunto
					if (!this.atach.equals("")) {
						MimeBodyPart messageBodyPart = new MimeBodyPart();
						DataSource source = new FileDataSource(this.atach);
						messageBodyPart.setDataHandler(new DataHandler(source));
						messageBodyPart.setFileName( this.atach_name );
						mp.addBodyPart(messageBodyPart);
					}
					
					msg.setContent(mp);
					System.out.println("Enviando email..." );
					Transport.send(msg); 
					System.out.println("Mensaje enviado al USUARIO:"+this.to);		

				}catch (java.io.UnsupportedEncodingException ex){
					System.out.println(ex.toString());
					return 0;
				}catch (MessagingException ex){
					System.out.println(ex.toString());
					return 0;
				}catch (Exception e){ 
					System.out.println(e.toString());
					return 0;
				}
				return 1;
			}
}
