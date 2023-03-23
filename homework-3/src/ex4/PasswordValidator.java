package ex4;

import ex4.exception.WrongLoginException;
import ex4.exception.WrongPasswordException;

public class PasswordValidator {
    public static boolean validate(String login, String password, String confirmPassword) {
        try {
            if (!login.matches("\\w+")) {
                throw new WrongLoginException("Логин содержит недопустимые символы");
            }
            if (login.length() >= 20) {
                throw new WrongLoginException("Логин слишком длинный");
            }

            if (!password.matches("\\w+")) {
                throw new WrongPasswordException("Пароль содержит недопустимые символы");
            }

            if (password.length() >= 20) {
                throw new WrongPasswordException("Пароль слишком длинный");
            }
            if (!password.equals(confirmPassword)) {
                throw new WrongPasswordException("Пароль и подтверждение не совпадают");
            }
        } catch (WrongLoginException | WrongPasswordException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }
}

