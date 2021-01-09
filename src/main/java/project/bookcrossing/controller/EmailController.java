package project.bookcrossing.controller;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import project.bookcrossing.configuration.EmailConfiguration;
import project.bookcrossing.entity.Contact;

import java.util.Properties;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/contact")
public class EmailController {

	private final EmailConfiguration emailCfg;

	public EmailController(EmailConfiguration emailCfg) {
		this.emailCfg = emailCfg;
	}

	@PostMapping
	public void sendEmail(@RequestBody Contact details, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			System.out.println("Details are incorrect!");
		} else {
			// Create a mail sender
			JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
			mailSender.setHost(this.emailCfg.getHost());
			mailSender.setPort(this.emailCfg.getPort());
			mailSender.setUsername(this.emailCfg.getUsername());
			mailSender.setPassword(this.emailCfg.getPassword());
			Properties props = new Properties();
			props.put("mail.smtp.starttls.enable", "true");
			mailSender.setJavaMailProperties(props);

			// Create an email instance
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(details.getEmail());
			mailMessage.setTo(emailCfg.getUsername());
			mailMessage.setSubject(details.getTitle() + " from " + details.getName());
			mailMessage.setText(details.getContent() + "\n\nAnswer to " + details.getEmail());

			// Send mail
			mailSender.send(mailMessage);
		}
	}
}
