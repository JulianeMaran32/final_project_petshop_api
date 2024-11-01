package br.com.juhmaran.petshop_api.core.validator;

import br.com.juhmaran.petshop_api.core.validator.annotations.ValidPhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    private static final String PHONE_PATTERN = "^\\+?\\d{9,14}$";

    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
        // document why this method is empty
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        return phone != null && phone.matches(PHONE_PATTERN);
    }

}
