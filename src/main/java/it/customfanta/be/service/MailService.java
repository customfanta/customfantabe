package it.customfanta.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String to, String oggetto, String testo) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(mailFrom);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(oggetto);
        simpleMailMessage.setText(testo);

        javaMailSender.send(simpleMailMessage);
    }

}
