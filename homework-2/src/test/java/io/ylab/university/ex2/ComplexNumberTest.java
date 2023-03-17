package io.ylab.university.ex2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ComplexNumberTest {
    private ComplexNumber num1;
    private ComplexNumber num2;

    @BeforeEach
    void setUp() {
        num1 = new ComplexNumber(2, 3);
        num2 = new ComplexNumber(4, 5);
    }

    @Test
    public void testAdd() {
        ComplexNumber result = num1.add(num2);
        ComplexNumber expected = new ComplexNumber(6, 8);
        assertEquals(expected, result);
    }

    @Test
    public void testSubtract() {
        ComplexNumber result = num1.subtract(num2);
        ComplexNumber expected = new ComplexNumber(-2, -2);
        assertEquals(expected, result);
    }

    @Test
    public void testMultiply() {
        ComplexNumber result = num1.multiply(num2);
        ComplexNumber expected = new ComplexNumber(-7, 22);
        assertEquals(expected, result);
    }

    @Test
    public void testGetModule() {
        ComplexNumber num = new ComplexNumber(3, 4);
        double result = num.getModule();
        double expected = 5;
        assertEquals(expected, result, 0.0001);
    }

    @Test
    public void testToString() {
        ComplexNumber num = new ComplexNumber(2, -3.5);
        String result = num.toString();
        //проблемы с региональными настройками могут быть
        String expected = "2,00 - 3,50i";
        assertEquals(expected, result);
    }

}