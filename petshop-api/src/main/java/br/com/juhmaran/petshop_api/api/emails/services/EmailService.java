package br.com.juhmaran.petshop_api.api.emails.services;

/**
 * Interface para o serviço de envio de e-mails.
 * <p>
 * Esta interface define o contrato para o envio de e-mails de redefinição de senha.
 * </p>
 *
 * @author Juliane Maran
 */
public interface EmailService {

    /**
     * Envia um e-mail de redefinição de senha para o endereço de e-mail especificado.
     *
     * @param email o endereço de e-mail para o qual o e-mail de redefinição de senha será enviado
     * @param token o token de redefinição de senha a ser incluído no e-mail
     */
    void sendPasswordResetToken(String email, String token);

}