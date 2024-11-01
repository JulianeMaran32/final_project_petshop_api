package br.com.juhmaran.petshop_api.api.pet.validators;

import br.com.juhmaran.petshop_api.api.common.enums.Gender;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

/**
 * Validador para o gênero de um animal de estimação.
 * <p>
 * Esta classe implementa a interface {@link ConstraintValidator} para validar se o gênero de um animal
 * é válido, ou seja, se é MALE, FEMALE ou OTHER.
 * </p>
 *
 * @author Juliane Maran
 */
@Slf4j
public class ValidGenderValidator implements ConstraintValidator<ValidGender, Gender> {

    /**
     * Verifica se o gênero fornecido é válido.
     *
     * @param gender  o gênero a ser validado
     * @param context o contexto do validador
     * @return true se o gênero for MALE, FEMALE ou OTHER, caso contrário false
     */
    @Override
    public boolean isValid(Gender gender, ConstraintValidatorContext context) {
        boolean isValid = (gender == Gender.MALE || gender == Gender.FEMALE || gender == Gender.OTHER);
        if (isValid) {
            log.info("Gênero válido: {}", gender);
        } else {
            log.warn("Gênero inválido: {}", gender);
        }
        return isValid;
    }

}