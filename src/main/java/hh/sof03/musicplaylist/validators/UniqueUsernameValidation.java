package hh.sof03.musicplaylist.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hh.sof03.musicplaylist.domain.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UniqueUsernameValidation implements ConstraintValidator<UniqueUsernameValidator, String> {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Check if a user with the given username already exists
        return userRepository.findByUsername(value) == null;
    }

}
