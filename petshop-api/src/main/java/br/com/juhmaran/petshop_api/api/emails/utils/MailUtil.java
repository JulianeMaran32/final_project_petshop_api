package br.com.juhmaran.petshop_api.api.emails.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;

@Slf4j
@UtilityClass
public class MailUtil {

    JavaMailSender javaMailSender;

}
