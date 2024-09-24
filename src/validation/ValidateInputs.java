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
            try {
                value = Boolean.parseBoolean(scanner.nextLine());
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("please enter true or false");
            }
        }
        return value;
    }







}
