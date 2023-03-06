package ex2;

import java.util.Scanner;

public class Pell {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        System.out.println(getPell(n));
    }

    static long getPell(int n) {
        if (0 <= n && n <= 1) return n;
        long num1 = 0;
        long num2 = 1;
        for (int i = 2; i <= n; i++) {
            long tmp = num2;
            num2 = 2 * num2 + num1;
            num1 = tmp;
        }
        return num2;
    }
}
