package ex1;

import java.util.Scanner;

public class Stars1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        String template = scanner.next();
        for (int i = 0; i < n; i++) {
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < m; j++) {
                line.append(template).append(" ");
            }
            System.out.println(line.toString().trim());
        }
    }
}
