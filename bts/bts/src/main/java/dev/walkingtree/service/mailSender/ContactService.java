package dev.walkingtree.service.mailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private final JavaMailSender javaMailSender;

    public ContactService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void contactus(String queryMessage){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("amanrajchaurasia073@gmail.com");
        message.setTo("amanrajchaurasia073@gmail.com");
        message.setText(queryMessage);
        message.setSubject("Contact From College Bus Tracking System");

        javaMailSender.send(message);


    }
}
