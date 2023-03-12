package io.ylab.university.ex1;

public class SequencesTest {
    public static void main(String[] args) {
        //переполнения не проверяем и не ограничиваем n сверху
        final int count = 6;
        SequenceGenerator sequence = new SequenceGeneratorImpl();

        System.out.print("A. ");
        sequence.a(count);

        System.out.print("B. ");
        sequence.b(count);

        System.out.print("C. ");
        sequence.c(count);

        System.out.print("D. ");
        sequence.d(count);

        System.out.print("E. ");
        sequence.e(count);

        System.out.print("F. ");
        sequence.f(count);

        System.out.print("G. ");
        sequence.g(count);

        System.out.print("H. ");
        sequence.h(count);

        System.out.print("I. ");
        sequence.i(count);

        System.out.print("J. ");
        sequence.j(count);
    }
}
