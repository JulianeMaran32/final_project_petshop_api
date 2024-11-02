package br.com.juhmaran.petshop_api.core.validator;

import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopBadRequestException;
import br.com.juhmaran.petshop_api.core.validator.annotations.ValidPhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

/**
 * Validador para números de telefone.
 * <p>
 * Esta classe implementa a interface {@link ConstraintValidator} para validar se um número de telefone é válido.
 * Um número de telefone válido deve ter entre 9 e 14 dígitos e pode opcionalmente começar com um sinal de mais (+).
 * </p>
 *
 * <p>Exemplo de uso:</p>
 * <pre>
 * {@code
 * PhoneNumberValidator validator = new PhoneNumberValidator();
 * boolean isValid = validator.isValid("+1234567890", null);
 * System.out.println("Número de telefone válido: " + isValid);
 * }
 * </pre>
 *
 * @author Juliane Maran
 * @see ValidPhoneNumber
 * @see ConstraintValidator
 * @see PetShopBadRequestException
 */
@Slf4j
public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    private static final String PHONE_PATTERN = "^\\+?\\d{9,14}$";

    /**
     * Inicializa o validador de número de telefone.
     * <p>
     * Este metodo é chamado antes de qualquer validação ser realizada. Atualmente, não há inicialização necessária,
     * então o metodo está vazio.
     * </p>
     *
     * @param constraintAnnotation a anotação que está sendo usada para a validação
     */
    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
        // document why this method is empty
    }

    /**
     * Verifica se o número de telefone fornecido é válido.
     *
     * @param phone   o número de telefone a ser validado
     * @param context o contexto do validador
     * @return true se o número de telefone for válido, caso contrário false
     * @throws PetShopBadRequestException se o número de telefone for inválido
     */
    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if (phone == null || !phone.matches(PHONE_PATTERN)) {
            log.warn("Número de telefone é nulo.");
            throw new PetShopBadRequestException("Número de telefone inválido.");
        }
        log.info("Número de telefone válido: {}", phone);
        return true;
    }

}
