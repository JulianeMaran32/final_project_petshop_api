package br.com.juhmaran.petshop_api.api.contact.services;

import br.com.juhmaran.petshop_api.api.contact.dtos.ContactFormRequest;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopInternalServerErrorException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

/**
 * Serviço responsável pelo envio de e-mails de contato.
 *
 * @author Juliane Maran
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ContactService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String adminEmail;

    /**
     * Envia um e-mail de contato com as informações fornecidas no formulário.
     *
     * @param contactForm Objeto contendo as informações do formulário de contato.
     * @throws PetShopInternalServerErrorException Se ocorrer um erro ao enviar o e-mail.
     */
    public void sendContactEmail(ContactFormRequest contactForm) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            var helper = new MimeMessageHelper(message, true);
            helper.setTo(adminEmail);
            helper.setSubject("Contato do Usuário: " + contactForm.getName());
            helper.setText(buildEmailContent(contactForm), true);
            mailSender.send(message);
            log.info("Email de contato enviado com sucesso para: {}", adminEmail);
        } catch (MessagingException e) {
            log.error("Erro ao enviar email de contato para: {}", adminEmail, e);
            throw new PetShopInternalServerErrorException("Falha ao enviar email de contato", e);
        }
    }

    /**
     * Constrói o conteúdo do e-mail de contato usando o template Thymeleaf.
     *
     * @param contactForm Objeto contendo as informações do formulário de contato.
     * @return O conteúdo do e-mail processado.
     */
    private String buildEmailContent(ContactFormRequest contactForm) {
        var context = new Context();
        context.setVariable("contactForm", contactForm);
        return templateEngine.process("contact-email", context);
    }

}
