package io.ylab.university.ex2;

import java.util.Objects;

public class ComplexNumber {
    private final double realPart;
    private final double imaginaryPart;

    public ComplexNumber(double realPart, double imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    public ComplexNumber(double realPart) {
        this(realPart, 0);
    }

    public ComplexNumber add(ComplexNumber number) {
        double newReal = realPart + number.realPart;
        double newImaginary = imaginaryPart + number.imaginaryPart;
        return new ComplexNumber(newReal, newImaginary);
    }

    public ComplexNumber subtract(ComplexNumber number) {
        double newReal = realPart - number.realPart;
        double newImaginary = imaginaryPart - number.imaginaryPart;
        return new ComplexNumber(newReal, newImaginary);
    }

    public ComplexNumber multiply(ComplexNumber number) {
        double newReal = realPart * number.realPart - imaginaryPart * number.imaginaryPart;
        //double newImaginary = realPart * imaginaryPart + imaginaryPart * number.realPart;
        double newImaginary = realPart * number.imaginaryPart + imaginaryPart * number.realPart;
        return new ComplexNumber(newReal, newImaginary);
    }

    public double getModule() {
        return Math.sqrt(realPart * realPart + imaginaryPart * imaginaryPart);
    }

    @Override
    public String toString() {
        if (imaginaryPart >= 0) {
            return String.format("%.2f + %.2fi", realPart, imaginaryPart);
        }
        return String.format("%.2f - %.2fi", realPart, Math.abs(imaginaryPart));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexNumber that = (ComplexNumber) o;
        return Double.compare(that.realPart, realPart) == 0 && Double.compare(that.imaginaryPart, imaginaryPart) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(realPart, imaginaryPart);
    }
}
