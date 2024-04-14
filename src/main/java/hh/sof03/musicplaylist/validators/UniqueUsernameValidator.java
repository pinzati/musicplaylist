package hh.sof03.musicplaylist.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UniqueUsernameValidation.class })
public @interface UniqueUsernameValidator {

    public String message() default "Invalid username";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};

}
