package io.ylab.university.ex2;

public class ComplexNumberTest {
    public static void main(String[] args) {
        ComplexNumber num1 = new ComplexNumber(3, 7);
        ComplexNumber num2 = new ComplexNumber(2, -1);
        ComplexNumber num3 = new ComplexNumber(-112);
        ComplexNumber num4 = new ComplexNumber(10, -20);

        System.out.println("num1 = " + num1);
        System.out.println("num2 = " + num2);
        System.out.println("num3 = " + num3);
        System.out.println("num4 = " + num4);

        System.out.println("num1 + num2 = " + num1.add(num2));
        System.out.println("num1 - num2 = " + num1.subtract(num2));
        System.out.println("num1 * num2 = " + num1.multiply(num2));

        System.out.println("num2 + num1 = " + num2.add(num1));
        System.out.println("num2 * num1 = " + num2.multiply(num1));
        System.out.printf("|num1| = %.2f\n", num1.getModule());
        System.out.printf("|num3| = %.2f\n", num3.getModule());
    }
}
