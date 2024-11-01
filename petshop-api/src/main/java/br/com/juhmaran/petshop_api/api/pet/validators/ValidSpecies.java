package br.com.juhmaran.petshop_api.api.pet.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidSpeciesValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSpecies {

    String message() default "Invalid species";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
