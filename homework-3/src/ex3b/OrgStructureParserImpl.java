package ex3b;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrgStructureParserImpl implements OrgStructureParser {
    @Override
    public Employee parseStructure(File csvFile) {
        Map<Long, Employee> employees = readEmployees(csvFile);
        //Другой вариант. Сразу возвращаем босса плюс читаем файл через стримы
        return linkBuilding(employees);
    }

    //Переписал иначе метод через NIO
    private Map<Long, Employee> readEmployees(File csvFile) {
        List<String> lines;
        Path path = csvFile.toPath();
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Невозможно прочитать файл " + path);
        }

        return lines.stream()
                .skip(1)
                .map(this::parseLine)
                .collect(Collectors.toMap(Employee::getId, employee -> employee));
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

    //Другой вариант теперь возвращаем босса. Возможно нарушение SRP
    private Employee linkBuilding(Map<Long, Employee> employees) {
        Employee superBos = null;
        for (Employee employee : employees.values()) {
            if (employee.getBossId() != null) {
                Employee boss = employees.get(employee.getBossId());
                if (boss == null) {
                    throw new RuntimeException("Потеряна ссылка на босса!");
                }
                boss.getSubordinate().add(employee);
            } else {
                superBos = employee;
            }
        }
        return superBos;
    }
}
