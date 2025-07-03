package com.example.notificationservice;

import com.example.notificationservice.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendSimpleEmail_shouldSendEmailWithCorrectData() {
        String to = "recipient@example.com";
        String subject = "Test Subject";
        String text = "This is the email body.";

        emailService.sendSimpleEmail(to, subject, text);

        // Capture the SimpleMailMessage passed to mailSender.send()
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();

        // Vérifications
        assert sentMessage.getTo() != null;
        assert sentMessage.getTo().length == 1;
        assert sentMessage.getTo()[0].equals(to);
        assert sentMessage.getSubject().equals(subject);
        assert sentMessage.getText().equals(text);
    }
}
