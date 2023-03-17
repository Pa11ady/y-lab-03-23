package io.ylab.university.ex4;

public class SnilsValidatorImpl implements SnilsValidator {

    @Override
    public boolean validate(String snils) {
        if (!isElevenNumeric(snils)) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            int digit = Character.getNumericValue(snils.charAt(i));
            sum += digit * (9 - i);
        }
        int controlNumber = 0;
        if (sum < 100) {
            controlNumber = sum;
        } else if (sum > 101) {
            int tmp = sum % 101;
            if (tmp != 100) {
                controlNumber = tmp;
            }
        }
        int lastDigits = Integer.parseInt(snils.substring(9));
        return controlNumber == lastDigits;
    }

    private static boolean isElevenNumeric(String line) {
        if (line == null || line.length() != 11) {
            return false;
        }
        return line.chars().allMatch(Character::isDigit);
    }
}
