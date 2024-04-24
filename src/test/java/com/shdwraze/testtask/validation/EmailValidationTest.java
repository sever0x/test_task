package com.shdwraze.testtask.validation;

import com.shdwraze.testtask.model.request.UserRequest;
import jakarta.validation.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailValidationTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void shouldValidateValidEmail() {
        UserRequest userRequest = new UserRequest(
                "test@example.com",
                "John",
                "Doe",
                LocalDate.now(),
                "123 Main St",
                "1234567890"
        );

        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldThrowExceptionForInvalidEmail() {
        UserRequest userRequest = new UserRequest(
                "invalid-email",
                "John",
                "Doe",
                LocalDate.now(),
                "123 Main St",
                "1234567890"
        );

        assertThrows(ValidationException.class, () -> {
            validator.validate(userRequest);
        });
    }
}
