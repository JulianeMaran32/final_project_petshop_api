package br.com.juhmaran.petshop_api.core.validator.annotations;

import br.com.juhmaran.petshop_api.core.validator.ValidSpeciesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para validar espécies.
 * <p>
 * Esta anotação é usada para validar se uma espécie é válida. Uma espécie válida deve ser DOG, CAT, BIRD, FISH, REPTILE ou OTHER.
 * </p>
 *
 * <p>Exemplo de uso:</p>
 * <pre>
 * {@code
 * public class Animal {
 *
 *     @ValidSpecies
 *     private Species species;
 *
 *     // getters e setters
 * }
 * }
 * </pre>
 *
 * @author Juliane Maran
 * @see ValidSpeciesValidator
 */
@Constraint(validatedBy = ValidSpeciesValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSpecies {

    String message() default "Espécie inválida";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
