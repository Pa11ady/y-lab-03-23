package ex3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OrgStructureParserImpl implements OrgStructureParser {
    @Override
    public Employee parseStructure(File csvFile) throws IOException {
        Map<Long, Employee> employees = readEmployees(csvFile);
        //можно было бы найти Босса подумал, что нарушение SRP, но сделал другую версию
        linkBuilding(employees);
        return findBoss(employees);
    }

    private Employee findBoss(Map<Long, Employee> employees) {
        var result = employees.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getBossId() == null)
                .findAny().orElseThrow();
        return result.getValue();
    }

    private Map<Long, Employee> readEmployees(File csvFile) throws FileNotFoundException {
        Map<Long, Employee> employees = new HashMap<>();
        try (Scanner scanner = new Scanner(new FileInputStream(csvFile))) {
            scanner.nextLine(); //Пропуск заголовка
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                Employee employee = parseLine(line);
                employees.put(employee.getId(), employee);
            }
        }
        return employees;
    }

    private Employee parseLine(String line) {
        String[] splits = line.split(";");
        String id = splits[0];
        String bossId = splits[1];
        String name = splits[2];
        String position = splits[3];

        Employee employee = new Employee();
        employee.setId(Long.parseLong(id));
        if (!bossId.isEmpty()) {
            employee.setBossId(Long.parseLong(bossId));
        }
        employee.setName(name);
        employee.setPosition(position);
        return employee;
    }

    private void linkBuilding(Map<Long, Employee> employees) {
        for (Employee employee : employees.values()) {
            if (employee.getBossId() != null) {
                Employee boss = employees.get(employee.getBossId());
                if (boss == null) {
                    throw new RuntimeException("Потеряна ссылка на босса!");
                }
                boss.getSubordinate().add(employee);
            }
        }
    }
}
