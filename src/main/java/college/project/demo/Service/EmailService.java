package college.project.demo.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {


    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendRegistrationEmail(String email, String name) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Registration Successful - Lost & Found");

        message.setText(
                "Hello " + name + ",\n\n" +
                        "Your registration was successful!\n\n" +
                        "Welcome to the Lost & Found application.\n\n" +
                        "You can now log in and start using the application.\n\n" +
                        "Thank you."
        );

        mailSender.send(message);
    }
}
