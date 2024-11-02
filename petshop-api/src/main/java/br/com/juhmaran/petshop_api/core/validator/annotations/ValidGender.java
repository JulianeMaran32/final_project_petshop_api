package br.com.juhmaran.petshop_api.core.validator.annotations;

import br.com.juhmaran.petshop_api.core.validator.ValidGenderValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para validar gêneros.
 * <p>
 * Esta anotação é usada para validar se um gênero é válido. Um gênero válido deve ser MALE, FEMALE ou OTHER.
 * </p>
 *
 * <p>Exemplo de uso:</p>
 * <pre>
 * {@code
 * public class Person {
 *
 *     @ValidGender
 *     private Gender gender;
 *
 *     // getters e setters
 * }
 * }
 * </pre>
 *
 * @author Juliane Maran
 * @see ValidGenderValidator
 */
@Constraint(validatedBy = ValidGenderValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidGender {

    String message() default "Genêro inválido.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}