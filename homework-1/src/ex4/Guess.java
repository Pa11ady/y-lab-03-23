package ex4;

import java.util.Random;
import java.util.Scanner;

public class Guess {
    static String getWord(int i) {
        if (i == 1) return "попытка";
        if (i < 5) return "попытки";
        else return "попыток";
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int quantity = 10;
        int num = new Random().nextInt(99) + 1;
        System.out.printf("Я загадал число от 1 до 99. У тебя %d %s угадать%n", quantity, getWord(quantity));
        for (int i = 1; i <= quantity; i++) {
            int inputNum = scanner.nextInt();
            if (inputNum != num && i == quantity) {
                System.out.println("Ты не угадал");
            } else if (inputNum > num) {
                System.out.printf("Моё число меньше! Осталось %d %s\n", quantity - i, getWord(quantity - i));
            } else if (inputNum < num) {
                System.out.printf("Моё число больше! Осталось %d %s\n", quantity - i, getWord(quantity - i));

            } else {
                System.out.printf("Ты угадал с % d попытки\n", i);
                break;
            }
        }
    }
}
