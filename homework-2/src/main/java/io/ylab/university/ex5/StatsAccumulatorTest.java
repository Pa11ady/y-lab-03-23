package io.ylab.university.ex5;

public class StatsAccumulatorTest {
    public static void main(String[] args) {
        StatsAccumulator s = new StatsAccumulatorImpl();
        System.out.println("Пустые значения");
        print(s);
        System.out.println("\nЗаполняем");
        s.add(1);
        print(s);
        System.out.println("=============");
        s.add(2);
        System.out.println(s.getAvg()); // 1.5 - среднее арифметическое
        s.add(0);
        System.out.println(s.getMin()); // 0 - минимальное
        s.add(3);
        s.add(8);
        System.out.println(s.getMax()); // 8 - максимальный
        System.out.println(s.getCount()); // 5 - количество переданных элементов
    }

    private static void print(StatsAccumulator s) {
        System.out.println("Среднее = " + s.getAvg());
        System.out.println("Мин = " + s.getMin());
        System.out.println("Макс = " + s.getMax());
        System.out.println("Кол-во = " + s.getCount());
    }
}
