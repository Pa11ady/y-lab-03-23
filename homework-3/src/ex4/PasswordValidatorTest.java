package ex4;

public class PasswordValidatorTest {
    public static void main(String[] args) {
        //успешные
        System.out.println(PasswordValidator.validate("q", "q", "q"));
        PasswordValidator.validate("Pav", "123", "123");
        PasswordValidator.validate("Pav1", "123", "123");
        PasswordValidator.validate("Pav_", "123_", "123_");
        PasswordValidator.validate("user1_", "u123_", "u123_");

        //неуспешные
        PasswordValidator.validate("павел", "q", "q");
        PasswordValidator.validate("Pav", "?12", "?12");
        PasswordValidator.validate("Pav1", "123", "12");
        PasswordValidator.validate("Pavvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv1", "123", "12");
        PasswordValidator.validate("Pav1", "1233333333333333333333333333333333333333",
                "1233333333333333333333333333333333333333");
    }
}
