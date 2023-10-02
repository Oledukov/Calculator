import java.util.Scanner;
class Main {
    private enum RomanNumeral {
        I(1), II(2), III(3), IV(4), V(5),
        VI(6), VII(7), VIII(8), IX(9), X(10), L(50), C(100);
        private final int value;

        RomanNumeral(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Input: ");
            String input = scanner.nextLine();

            try {
                String result = calc(input);
                System.out.println("Output: " + result);
            } catch (Exception e) {
                System.out.println("Output: ");
                System.out.println("throws Exception " + e.getMessage());
            }
            break;
        }
    }

    public static String calc(String input) throws Exception {
        String[] tokens = input.split(" ");
        if (tokens.length == 1) {
            throw new Exception("//строка не является математической операцией");
        }
        if (tokens.length != 3) {
            throw new Exception("//формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }

        String operand1 = tokens[0];
        String operator = tokens[1];
        String operand2 = tokens[2];

        boolean isRoman1 = isRomanNumeral(operand1);
        boolean isRoman2 = isRomanNumeral(operand2);

        if ((isRoman1 && !isRoman2) || (!isRoman1 && isRoman2)) {
            throw new Exception("//используются одновременно разные системы счисления");
        }

        int num1 = isRoman1 ? romanToArabian(operand1) : Integer.parseInt(operand1);
        int num2 = isRoman2 ? romanToArabian(operand2) : Integer.parseInt(operand2);
        if ((num1 < 1 || num1 > 10) || (num2 < 1 || num2 > 10)) {
            throw new Exception("//операнды должны быть от 1 до 10 включительно");
        }

        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                result = num1 / num2;
                break;
            default:
                throw new Exception("//неверный оператор: " + operator);
        }

        if (isRoman1 && isRoman2) {
            if (result <= 0) {
                throw new Exception("//в римской системе нет отрицательных чисел");
            }
            return arabianToRoman(result);
        } else {
            return String.valueOf(result);
        }
    }

    static boolean isRomanNumeral(String str) {
        for (RomanNumeral numeral : RomanNumeral.values()) {
            if (str.equalsIgnoreCase(numeral.name())) {
                return true;
            }
        }
        return false;
    }

    static int romanToArabian(String roman) {
        for (RomanNumeral numeral : RomanNumeral.values()) {
            if (roman.equalsIgnoreCase(numeral.name())) {
                return numeral.getValue();
            }
        }
        return 0;
    }

    static String arabianToRoman(int arabian) {
        if (arabian <= 0) {
            throw new IllegalArgumentException("//в римской системе нет отрицательных чисел");
        }

        StringBuilder result = new StringBuilder();
        RomanNumeral[] values = RomanNumeral.values();
        int i = values.length - 1;

        while (arabian > 0) {
            int value = values[i].getValue();
            if (arabian >= value) {
                result.append(values[i].name());
                arabian -= value;
            } else {
                i--;
            }
        }

        return result.toString();
    }
}