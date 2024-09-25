package validation;

import java.util.Scanner;

public class ValidateInputs {

    public static Scanner scanner = new Scanner(System.in);

    public static double validateDouble(String message) {
        double number = 0;
        boolean isValid = false;
        while (!isValid) {
            System.out.println(message);
            try {
                number = Double.parseDouble(scanner.nextLine());
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("please enter nombre valid");
            }
        }
        return number;
    }

    public static boolean validateBoolean(String message) {
        boolean value = false;
        boolean isValid = false;
        System.out.println(message);
        while (!isValid) {
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("true") || input.equals("false")) {
                value = Boolean.parseBoolean(input);
                isValid = true;
            } else {
                System.out.println("please enter true ou false");
            }
        }
        return value;
    }







}
