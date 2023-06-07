package com.starfy.laAgencia.services.implementations;

import com.starfy.laAgencia.controllers.MailController;
import com.starfy.laAgencia.dtos.requests.RequestMailContacto;
import com.starfy.laAgencia.exceptions.CustomException;
import com.starfy.laAgencia.services.MailService;
import com.starfy.laAgencia.services.ParametroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

import static com.starfy.laAgencia.constants.Parametros.*;

@Service
public class MailServiceImpl implements MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Autowired
    ParametroService parametroService;

    @Override
    public void enviarMailContacto(RequestMailContacto request) {

        String subject = parametroService.getParametro(EMAIL_SUBJECT);
        String toEmail = parametroService.getParametro(EMAIL_TO);

        String body = parametroService.getParametro(EMAIL_BODY);
        body = body.replace("[NOMBRE]", request.getNombre());
        body = body.replace("[MENSAJE]", request.getMensaje());
        body = body.replace("[MAIL]", request.getMail());
        body = body.replace("[NUMERO]", request.getTelefono());

        // Credenciales del correo electrónico que envía el mensaje
        String emailAdress = parametroService.getParametro(EMAIL_USERNAME);
        String emailPassword = parametroService.getParametro(EMAIL_PASSWORD);

        // Configuración de las propiedades del servidor SMTP de Gmail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Autenticación del correo electrónico
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAdress, emailPassword);
            }
        });

        try {
            // Creación del mensaje
            Message emailMessage = new MimeMessage(session);
            emailMessage.setFrom(new InternetAddress(emailAdress));
            emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            emailMessage.setSubject(subject);

            MimeMultipart multipart = new MimeMultipart();
            MimeBodyPart htmlBody = new MimeBodyPart();
            htmlBody.setContent(body, "text/html");
            multipart.addBodyPart(htmlBody);
            emailMessage.setContent(multipart);

            // Envío del mensaje
            Transport.send(emailMessage);

            logger.info("Correo electrónico enviado exitosamente.");

        } catch (MessagingException e) {
            logger.error("Fallo en el proceso de envio del mail: " + e.getMessage());
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Fallo en el proceso de envio del mail");
        }
    }


}
