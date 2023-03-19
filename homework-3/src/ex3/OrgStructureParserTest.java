package ex3;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class OrgStructureParserTest {
    public static void main(String[] args) throws IOException {
        File file = new File("data.csv");
        OrgStructureParser orgStructureParser = new OrgStructureParserImpl();
        Employee boss = orgStructureParser.parseStructure(file);
        printBoss(boss);
        System.out.println("\n======Тестовая распечатка всех сотрудников======\n");
        printAll(boss); //Тестовая печать. Такая рекурсия очень непроизводительная
    }

    private static void printBoss(Employee boss) {
        System.out.print("Босс: ");
        System.out.println(boss);
        System.out.print("\nПрямые подчинённые: ");
        List<Employee> subordinate = boss.getSubordinate();
        for (Employee employee: subordinate) {
            System.out.println(employee);
        }
    }

    private static void printAll(Employee employee) {
        List<Employee> subordinate = employee.getSubordinate();
        System.out.println(employee);
        if (subordinate.isEmpty()) {
            return;
        }
        for (var element: subordinate) {
            printAll(element);
        }
    }
}
