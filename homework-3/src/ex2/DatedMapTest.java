package ex2;

public class DatedMapTest {
    private static final DatedMap datedMap = new DatedMapImpl();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("проверка пустых значений");
        System.out.println("ключ 123 Значение NULL " + datedMap.get("123"));
        System.out.println("Значения " + datedMap.keySet());
        datedMap.remove("124"); //нет исключения
        System.out.println("ключ 123 дата последнего добавления " + datedMap.getKeyLastInsertionDate("123"));

        System.out.println("\nпроверка добавления");
        addEntry("key1", "val1");
        addEntry("key2", "val2");
        Thread.sleep(100);  //чтобы точно было видно изменение времени
        addEntry("key3", "val3");
        System.out.println("Значения " + datedMap.keySet());

        System.out.println("\nпроверка удаления");
        datedMap.remove("key2");
        datedMap.remove("key3");
        System.out.println("key3 " + datedMap.get("key3"));
        System.out.println("Значения " + datedMap.keySet());
        System.out.println("Дата key1 " + datedMap.getKeyLastInsertionDate("key1").toInstant());
        System.out.println("Дата key3 " + datedMap.getKeyLastInsertionDate("key3"));

        System.out.println("\nпроверка наличия ключа");
        System.out.println("Содержит ключ key1 " + datedMap.containsKey("key1"));
        System.out.println("Содержит ключ  key2 " + datedMap.containsKey("key2"));
        System.out.println("Содержит ключ  key3 " + datedMap.containsKey("key3"));
    }

    private static void addEntry(String key, String value) {
        datedMap.put(key, value);
        System.out.println("Ключ " + key);
        System.out.println("Значение " + datedMap.get(key));
        System.out.println("Timestamp" + datedMap.getKeyLastInsertionDate(key).toInstant()); // чтобы видно было
    }
}
