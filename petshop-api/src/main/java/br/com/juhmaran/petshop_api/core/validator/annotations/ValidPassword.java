package br.com.juhmaran.petshop_api.core.validator.annotations;

import br.com.juhmaran.petshop_api.core.validator.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para validar senhas.
 * <p>
 * Esta anotação é usada para garantir que uma senha atenda aos critérios de segurança especificados.
 * Uma senha válida deve ter pelo menos 8 caracteres, incluindo pelo menos uma letra minúscula, uma letra maiúscula,
 * um dígito e um caractere especial.
 * </p>
 *
 * <p>Exemplo de uso:</p>
 * <pre>
 * {@code
 * public class User {
 *
 *     @ValidPassword
 *     private String password;
 *
 *     // getters e setters
 * }
 * }
 * </pre>
 *
 * @author Juliane Maran
 * @see PasswordValidator
 */
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    String message() default "A senha deve conter no mínimo 8 caracteres, sendo pelo menos uma letra minúscula, " +
            "uma letra maiúscula, um número e um caracter especial.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}