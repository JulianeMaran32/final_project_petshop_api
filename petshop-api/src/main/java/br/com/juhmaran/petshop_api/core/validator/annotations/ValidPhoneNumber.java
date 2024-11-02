package br.com.juhmaran.petshop_api.core.validator.annotations;

import br.com.juhmaran.petshop_api.core.validator.PhoneNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para validar números de telefone.
 * <p>
 * Esta anotação pode ser usada para validar se um número de telefone é válido.
 * Um número de telefone válido deve ter entre 9 e 14 dígitos e pode opcionalmente começar com um sinal de mais (+).
 * </p>
 *
 * <p>Exemplo de uso:</p>
 * <pre>
 * {@code
 * public class User {
 *
 *     @ValidPhoneNumber
 *     private String phoneNumber;
 *
 *     // getters e setters
 * }
 * }
 * </pre>
 *
 * @author Juliane Maran
 * @see PhoneNumberValidator
 */
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhoneNumber {

    String message() default "O número de telefone é inválido.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}