package jp.co.company.space.api.features.passenger.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jp.co.company.space.api.features.passenger.validation.validator.PassportNumberFormatValidator;

import java.lang.annotation.*;

/**
 * The validation annotation for the format of a passport number.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PassportNumberFormatValidator.class)
@Documented
public @interface ValidPassportNumberFormat {
    String message() default "";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
