package br.com.juhmaran.petshop_api.api.emails.services.impl;

import br.com.juhmaran.petshop_api.api.emails.services.EmailService;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopInternalServerErrorException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void sendPasswordResetToken(String email, String token) {
        log.info("Iniciando envio de email de redefinição de senha para: {}", email);
        String subject = "Solicitação de Redefinição de Senha";
        var context = new Context();
        context.setVariable("token", token);
        String content = templateEngine.process("password-reset-email", context);
        sendEmail(email, subject, content);
        log.info("Email de redefinição de senha enviado para: {}", email);
    }

    private void sendEmail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            var helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            log.info("Enviando email para: {} com assunto: {}", to, subject);
            mailSender.send(message);
            log.info("Email enviado com sucesso para: {}", to);
        } catch (MessagingException e) {
            log.error("Erro ao enviar email para: {}", to, e);
            throw new PetShopInternalServerErrorException("Falha ao enviar email", e);
        }
    }

}
