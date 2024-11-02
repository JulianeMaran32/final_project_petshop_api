package br.com.juhmaran.petshop_api.core.validator;

import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopBadRequestException;
import br.com.juhmaran.petshop_api.core.validator.annotations.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

/**
 * Validador para senhas.
 * <p>
 * Esta classe implementa a interface {@link ConstraintValidator} para validar se uma senha é válida.
 * Uma senha válida deve ter pelo menos 8 caracteres, incluindo pelo menos uma letra minúscula, uma letra maiúscula,
 * um dígito e um caractere especial.
 * </p>
 *
 * <p>Exemplo de uso:</p>
 * <pre>
 * {@code
 * PasswordValidator validator = new PasswordValidator();
 * boolean isValid = validator.isValid("Senha@123", null);
 * System.out.println("Senha válida: " + isValid);
 * }
 * </pre>
 *
 * @author Juliane Maran
 * @see ValidPassword
 * @see ConstraintValidator
 * @see PetShopBadRequestException
 */
@Slf4j
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        // Este metodo é chamado antes de qualquer validação ser realizada. Atualmente, não há inicialização necessária,
        // então o metodo está vazio.
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || !password.matches(PASSWORD_PATTERN)) {
            log.info("Senha inválida!");
            throw new PetShopBadRequestException("Senha inválida.");
        }
        log.info("Senha válida!");
        return true;
    }

}