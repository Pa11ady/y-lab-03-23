package io.ylab.university.ex1;

import java.util.Arrays;
import java.util.stream.Collectors;

//Решил использовать массив, чтобы отделить обработку от вывода на экран.
//Можно сказать, что это SRP в какой-то степени, но скорее мне так удобнее.
//Ещё такое решение позволило в несколько этапов обрабатывать числа
//и уменьшить повторения кода.
//Формат вывода, как в примере чтобы удобнее проверять.

public class SequenceGeneratorImpl implements SequenceGenerator {
    @Override
    public void a(int n) {
        check(n);
        print(arithmeticProgression(2, 2, n));
    }

    @Override
    public void b(int n) {
        check(n);
        print(arithmeticProgression(1, 2, n));
    }

    @Override
    public void c(int n) {
        check(n);
        print(power(2, n));
    }

    @Override
    public void d(int n) {
        check(n);
        print(power(3, n));
    }

    @Override
    public void e(int n) {
        check(n);
        int[] numbers = arithmeticProgression(1, 0, n);
        print(multiplyOddIndex(numbers, -1));
    }

    @Override
    public void f(int n) {
        check(n);
        int[] numbers = arithmeticProgression(1, 1, n);
        print(multiplyOddIndex(numbers, -1));
    }

    @Override
    public void g(int n) {
        check(n);
        int[] numbers = power(2, n);
        print(multiplyOddIndex(numbers, -1));
    }

    @Override
    public void h(int n) {
        check(n);
        int[] numbers = new int[n];
        int number = 1;
        //По умолчанию массивы инициализируются нулями, но решил явно написать.
        for (int i = 0; i < numbers.length; i++) {
            if ((i % 2 != 0)) {
                numbers[i] = 0;
            } else {
                numbers[i] = number++;
            }
        }
        print(numbers);
    }

    @Override
    public void i(int n) {
        check(n);
        long[] numbers = new long[n];
        numbers[0] = 1;
        for (int i = 1; i < numbers.length; i++) {
            numbers[i] = numbers[i - 1] * (i + 1);
        }
        print(numbers);
    }

    @Override
    public void j(int n) {
        check(n);
        long[] numbers = new long[n];
        print(fiboFromSecond(numbers));
    }

    private void print(int[] data) {
        System.out.println(Arrays.stream(data).mapToObj(String::valueOf).collect(Collectors.joining(", ")));
    }

    //ещё один способ вывести массив плюс закрепить в д/з перегрузку методов
    private void print(long[] data) {
        System.out.println(Arrays.toString((data)).replaceAll("[\\[\\]]", ""));
    }

    private int[] arithmeticProgression(int num0, int step, int n) {
        int[] numbers = new int[n];
        numbers[0] = num0;
        for (int i = 1; i < numbers.length; i++) {
            numbers[i] = numbers[i - 1] + step;
        }
        return numbers;
    }

    private int[] power(int exponent, int n) {
        int[] numbers = new int[n];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = (int) Math.pow(i + 1, exponent);
        }
        return numbers;
    }

    private int[] multiplyOddIndex(int[] numbers, int multiplier) {
        for (int i = 0; i < numbers.length; i++) {
            if (i % 2 != 0) {
                numbers[i] = numbers[i] * multiplier;
            }
        }
        return numbers;
    }

    private long[] fiboFromSecond(long[] numbers) {
        numbers[0] = 1;
        if (numbers.length > 1) {
            numbers[1] = 1;
            for (int i = 2; i < numbers.length; i++) {
                numbers[i] = numbers[i - 1] + numbers[i - 2];
            }
        }
        return numbers;
    }

    private void check(int n) {
        if (n <= 0) {
            throw new RuntimeException("Некорректное кол-во элементов!");
        }
    }
}
