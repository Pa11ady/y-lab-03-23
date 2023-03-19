package ex1;

public class TransliteratorTest {
    public static void main(String[] args) {
        Transliterator transliterator = new TransliteratorImpl();
        String res1 = transliterator.transliterate("HELLO! ПРИВЕТ! Go, boy!");
        System.out.println(res1);

        System.out.println(transliterator.transliterate(""));

        System.out.println(transliterator.transliterate(null));

        String res2 = transliterator.transliterate("Я ЛЮБЛЮ ПРОГРАММИРОВАНТЬ НА JAVA!");
        System.out.println(res2);


        String res3 = transliterator.transliterate("АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ");
        System.out.println(res3);
    }
}
