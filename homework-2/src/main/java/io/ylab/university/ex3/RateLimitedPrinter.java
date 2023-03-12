package io.ylab.university.ex3;

public class RateLimitedPrinter {
    private final int interval;
    long  messageTime = 0;

    public RateLimitedPrinter(int interval) {
        this.interval = interval;
    }

    public void print(String message) {
        //в первые миллисекунды начала времён от сотворения Юникса это пропустил бы первый вывод:)
        //а наше время первое сообщение должно выводить на экран
        long difference =  System.currentTimeMillis() - messageTime;
        if (difference  >= interval) {
            messageTime =  System.currentTimeMillis();
            System.out.println(message);
        }
    }
}