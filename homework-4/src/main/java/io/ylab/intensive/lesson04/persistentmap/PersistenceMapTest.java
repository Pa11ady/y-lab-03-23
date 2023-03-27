package io.ylab.intensive.lesson04.persistentmap;

import io.ylab.intensive.lesson04.DbUtil;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Реализовал базой тестовый сценарий где сначала проверяю, что мапы
 * пустые и не летят исключения, и что можно несколько мап использовать.
 * Отдельно проверил отчистку мап, чтобы отчищалась только одна мапа,
 * а другая оставалась целой.
 * Отдельно проверка дубликатов.
 */

public class PersistenceMapTest {
    public static void main(String[] args) throws SQLException {
        DataSource dataSource = initDb();
        PersistentMap persistentMap = new PersistentMapImpl(dataSource);

        basicTest1(persistentMap, "map1");
        basicTest1(persistentMap, "map2");
        testClear(persistentMap);
        testDuplicate(persistentMap);
    }

    public static DataSource initDb() throws SQLException {
        String createMapTable = ""
                + "drop table if exists persistent_map; "
                + "CREATE TABLE if not exists persistent_map (\n"
                + "   map_name varchar,\n"
                + "   KEY varchar,\n"
                + "   value varchar\n"
                + ");";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(createMapTable, dataSource);
        return dataSource;
    }

    private static void basicTest1(PersistentMap map, String mapName) throws SQLException {
        map.init(mapName);
        System.out.printf("\n=========%s=========\n", mapName);
        System.out.println("До вставки должно быть пусто:");
        System.out.println("Ключи: " + map.getKeys());
        System.out.println("Содержит key1: " + map.containsKey("key1"));
        System.out.println("Значение по key1: " + map.get("key1"));
        System.out.println("Не должно быть исключений при удалении");
        map.remove("key1");

        System.out.println("\nДобавление значений:");
        map.put("key1", "value1");
        map.put("key2", "value2");
        System.out.println("Содержит key1: " + map.containsKey("key1"));
        System.out.println("Содержит key2: " + map.containsKey("key2"));
        System.out.println("Ключи: " + map.getKeys());
        System.out.println("Значения key1: " + map.get("key1"));
        System.out.println("Значения key2: " + map.get("key2"));

        System.out.println("\nУдаляем key1:");
        map.remove("key1");
        System.out.println("Содержит key1: " + map.containsKey("key1"));
        System.out.println("Значение по key1: " + map.get("key1"));
        System.out.println("Ключи: " + map.getKeys());
        System.out.println("Значения key2: " + map.get("key2"));
    }

    private static void testClear(PersistentMap map) throws SQLException {
        System.out.println("\n=========Тест отчистки=========");
        String mapName1 = "MapClear1";
        map.init(mapName1);
        map.put("ckey1", "cvalue1");
        System.out.println("Мапа " + mapName1 + " ключи :" + map.getKeys());

        String mapName2 = "MapClear2";
        map.init(mapName2);
        map.put("ckey2", "cvalue2");
        System.out.println("Мапа " + mapName2 + " ключи :" + map.getKeys());
        System.out.println("Отчистка " + mapName2);
        map.clear();
        System.out.println("Мапа " + mapName2 + " ключи :" + map.getKeys());
        map.init(mapName1);

        System.out.println("Мапа " + mapName1 + " не пострадала");
        System.out.println("Мапа " + mapName1 + " ключи :" + map.getKeys());
    }

    private static void testDuplicate(PersistentMap map) throws SQLException {
        System.out.println("\n=========Тест дублей =========");
        map.init("DuplicateMap");
        System.out.println("Ключи До: " + map.getKeys());
        map.put("key1", "value1");
        System.out.println("Добавили значения key1: " + map.get("key1"));
        map.put("key1", "value2");
        map.put("key1", "value3");
        System.out.println("Ключи после: " + map.getKeys());
        System.out.println("Значения key1 после: " + map.get("key1"));

    }
}
