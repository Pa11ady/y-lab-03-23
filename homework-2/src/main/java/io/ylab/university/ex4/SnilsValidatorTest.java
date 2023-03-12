package io.ylab.university.ex4;

public class SnilsValidatorTest {
    public static void main(String[] args) {
        System.out.println(new SnilsValidatorImpl().validate("01468870570")); //false
        System.out.println(new SnilsValidatorImpl().validate("90114404441")); //true

        System.out.println(new SnilsValidatorImpl().validate("901144044411")); //false
        System.out.println(new SnilsValidatorImpl().validate("9011440444")); //false
        System.out.println(new SnilsValidatorImpl().validate(null)); //false

        System.out.println(new SnilsValidatorImpl().validate("89356422453")); //true
        System.out.println(new SnilsValidatorImpl().validate("81728361516")); //true
        System.out.println(new SnilsValidatorImpl().validate("21427586359")); //true
    }
}
