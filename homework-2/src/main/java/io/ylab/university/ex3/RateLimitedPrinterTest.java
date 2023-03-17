package io.ylab.university.ex3;

public class RateLimitedPrinterTest {
    public static void main(String[] args) throws InterruptedException {
        int interval = 1000;
        RateLimitedPrinter rateLimitedPrinter = new RateLimitedPrinter(interval);
        for (int i = 0; i < 1_000_000_000; i++) {
            rateLimitedPrinter.print(String.valueOf(i));
        }

        //дополнительная проверка
        RateLimitedPrinter rateLimitedPrinter1 = new RateLimitedPrinter(interval);
        for (int i = 0; i <= 5; i++) {
            rateLimitedPrinter1.print(String.valueOf(i));
            Thread.sleep(interval);
        }

    }
}
