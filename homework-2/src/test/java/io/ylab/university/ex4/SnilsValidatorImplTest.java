package io.ylab.university.ex4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SnilsValidatorImplTest {
    private final SnilsValidatorImpl validator = new SnilsValidatorImpl();

    @Test
    void testValidNumber() {
        String number = "90114404441";
        boolean validate = validator.validate(number);
        assertTrue(validate, "Expected " + number + " to be valid");
    }

    @Test
    void testInvalidNumber() {
        String number = "01468870570";
        boolean validate = validator.validate(number);
        assertFalse(validate, "Expected " + number + " to be invalid");
    }

    @Test
    void testNullInput() {
        boolean validate = validator.validate(null);
        assertFalse(validate, "Expected null input to be invalid");
    }

    @Test
    void testShortInput() {
        String number = "123456789";
        boolean validate = validator.validate(number);
        assertFalse(validate, "Expected " + number + " to be invalid");
    }

    @Test
    void testNonDigitInput() {
        String number = "A0114404441";
        boolean validate = validator.validate(number);
        assertFalse(validate, "Expected " + number + " to be invalid");
    }
}