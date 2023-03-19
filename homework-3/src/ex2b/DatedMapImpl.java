package ex2b;

import java.util.*;

/*Вариант 2. Со статическим вложенным классов элемент вместо двух мап.
Статические вложенный чтобы не светить детали реализации*/

public class DatedMapImpl implements DatedMap {
    private final HashMap<String, Element> map = new HashMap<>();

    @Override
    public void put(String key, String value) {
        Element element = new Element(value, new Date());
        map.put(key, element);
    }

    @Override
    public String get(String key) {
        Element element = map.get(key);
        if (element == null) {
            return null;
        }
        return element.getValue();
    }

    @Override
    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    @Override
    public void remove(String key) {
        map.remove(key);
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Date getKeyLastInsertionDate(String key) {
        Element element = map.get(key);
        if (element == null) {
            return null;
        }
        return element.getDate();
    }

    private static class Element {
        private final String value;
        private final Date date;

        public Element(String value, Date date) {
            this.value = value;
            this.date = date;
        }

        public String getValue() {
            return value;
        }

        public Date getDate() {
            return date;
        }
    }
}
