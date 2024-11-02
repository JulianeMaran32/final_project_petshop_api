package br.com.juhmaran.petshop_api.core.validator;

import br.com.juhmaran.petshop_api.api.common.enums.Gender;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopNotFoundException;
import br.com.juhmaran.petshop_api.core.validator.annotations.ValidGender;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

/**
 * Validador para gêneros.
 * <p>
 * Esta classe implementa a interface {@link ConstraintValidator} para validar se um gênero é válido.
 * Um gênero válido deve ser MALE, FEMALE ou OTHER.
 * </p>
 *
 * <p>Exemplo de uso:</p>
 * <pre>
 * {@code
 * ValidGenderValidator validator = new ValidGenderValidator();
 * boolean isValid = validator.isValid(Gender.MALE, null);
 * System.out.println("Gênero válido: " + isValid);
 * }
 * </pre>
 *
 * @author Juliane Maran
 * @see ValidGender
 * @see ConstraintValidator
 * @see PetShopNotFoundException
 */
@Slf4j
public class ValidGenderValidator implements ConstraintValidator<ValidGender, Gender> {

    @Override
    public boolean isValid(Gender gender, ConstraintValidatorContext context) {
        boolean isValid = (gender == Gender.MALE || gender == Gender.FEMALE || gender == Gender.OTHER);

        if (isValid) {
            log.info("Gênero válido: {}", gender);
        } else {
            log.warn("Gênero inválido: {}", gender);
            throw new PetShopNotFoundException("Genêro não encontrada: " + gender);
        }

        return true;
    }

}