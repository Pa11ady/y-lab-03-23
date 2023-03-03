package ex1;

import java.util.Arrays;
import java.util.Scanner;

public class Stars {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        String template = scanner.next();
        String[] lines = new String[m];
        Arrays.fill(lines, template);
        String line = String.join(" ", lines);

        //StringBuilder было бы использовать более производительно
        for (int i = 0; i < n; i++) {
            System.out.println(line);
        }
    }
}
