import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final String OPERATOR_PATTERN = "[\\/*\\-+]";

    public static int[] calc(String inputString) throws Exception {
       return analyzeString(inputString);

    }
    public static int[] analyzeString(String inputString) throws Exception {
        String operandsPattern = "\\d+|\\w+";

        Pattern operands = Pattern.compile(operandsPattern);
        Pattern operators = Pattern.compile(OPERATOR_PATTERN);
        final Matcher operandsMatcher = operands.matcher(inputString);
        final Matcher operatorMatcher = operators.matcher(inputString);

        int operandsCount = countMatches(operandsMatcher);
        int operatorCount = countMatches(operatorMatcher);
        if (operandsCount != 2 && operatorCount != 1) {
            throw new Exception("Неверный формат выражения!");
        }
        String[] strArr = inputString.split(OPERATOR_PATTERN);
        if (inputString.contains(".") || inputString.contains(",")) {
            throw new Exception("Выражение содержит десятичные дроби!");
        }
        return convertString(strArr);
    }
    public static int[] convertString(String[] strArr) throws Exception {
        boolean isArabic = true;
        boolean isRoman = false;
        int[] numArr = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            try {
                if (isArabic) {
                    numArr[i] = Integer.parseInt(strArr[i]);
                    if (numArr[i] > 10 || numArr[i] < 1) {
                        throw new Exception("Введённое число не находится в промежутке от 1 до 10!");
                    }
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                isArabic = false;
                numArr[i] = romanToArabic(strArr[i].toUpperCase().trim());
            }
        }
        try {
            if (isArabic == isRoman) {
                String s = Arrays.toString(strArr);
                StringBuffer sb = new StringBuffer(s);
                sb.deleteCharAt(0).deleteCharAt(sb.length()-1);
                String z = sb.toString();
                String e = z.replaceAll("\\s+","");
                String[] itog = e.split("[+-/*]");
                int n = Main.romanToArabic(itog[0]);
                int m = Main.romanToArabic(itog[1]);
                isRoman = true;
                if (n > 10 || n < 1 || m > 10 || m < 1) {
                    throw new Exception("Введённое число не находится в промежутке от 1 до 10!");
                }
            } else if (isRoman && isArabic) {
                throw new NumberFormatException();
            }
        } catch (Exception e) {
            throw new Exception("некорректно введены данные!");
        }
        return numArr;
    }
    public static int countMatches(Matcher matcher) {
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }
    // Copied Right
    private static String arabToRoman(int num) {
        String m[] = {"", "M", "MM", "MMM"};
        String c[] = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String x[] = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String i[] = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        String thousands = m[num / 1000];
        String hundreds = c[(num % 1000) / 100];
        String tens = x[(num % 100) / 10];
        String ones = i[num % 10];
        return thousands + hundreds + tens + ones;
    }
    private static int value(char r) {
        if (r == 'I')
            return 1;
        if (r == 'V')
            return 5;
        if (r == 'X')
            return 10;
        if (r == 'L')
            return 50;
        if (r == 'C')
            return 100;
        throw new NumberFormatException("Введённый символ '" + r + "' не является римским числом!");
    }
    private static int romanToArabic(String str) {
        int res = 0;
        for (int i = 0; i < str.length(); i++) {
            int s1 = value(str.charAt(i));
            if (i + 1 < str.length()) {
                int s2 = value(str.charAt(i + 1));
                if (s1 >= s2) {
                    res = res + s1;
                } else {
                    res = res + s2 - s1;
                    i++;
                }
            } else {
                res = res + s1;
                i++;
            }
        }
        return res;
    }
    public static int calculate(int number1, int number2, char operator){
        int result = 0;
        switch (operator){
            case '+': result = number1 + number2; break;
            case '-': result = number1 - number2; break;
            case '*': result = number1 * number2; break;
            case '/': result = number1 / number2; break;
            default:throw  new IllegalArgumentException("Не верный знак операции");
        }
        return result;
    }
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Римские цифры вводятся заглавными буквами, всё выражение вводится без пробелов. Введите выражение: ");
        String input = scanner.nextLine();
        String[] operator = input.split("\\w+");
        int [] num = calc(input);
        char c = operator[1].charAt(0);
        String[] strArr = input.split(OPERATOR_PATTERN);
        String s = Arrays.toString(strArr);
        StringBuffer sb = new StringBuffer(s);
        sb.deleteCharAt(0).deleteCharAt(sb.length()-1);
        String z = sb.toString();
        String e = z.replaceAll("\\s+","");
        String[] itog = e.split("[+-/*]");
        try {
            Integer.parseInt(itog[0]);
            System.out.println(Main.calculate(num[0], num[1], c));
        } catch (NumberFormatException ex) {
            if (Main.calculate(num[0], num[1], c) <=0) {
                throw new Exception("результат математической операции с римскими цифрами может быть только положительным и больше 0");
            }
            System.out.println(Main.arabToRoman(Main.calculate(num[0], num[1], c)));
        }
    }
}