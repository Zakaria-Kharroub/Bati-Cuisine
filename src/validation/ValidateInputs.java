package validation;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

public static LocalDate validateDateEmission(String message) {
    LocalDate dateEmission = null;
    boolean isValid = false;
    while (!isValid) {
        System.out.println(message);
        try {
            dateEmission = LocalDate.parse(scanner.nextLine());
            if (!dateEmission.isBefore(LocalDate.now())) {
                isValid = true;
            } else {
                System.out.println("la date obligatoire de superieur ou egal date now.");
            }
        } catch (DateTimeParseException e) {
            System.out.println("please cette date au format YYYY-MM-DD.");
        }
    }
    return dateEmission;
}

    public static LocalDate validateDateValidite(String message, LocalDate dateEmission) {
        LocalDate dateValidite = null;
        boolean isValid = false;
        while (!isValid) {
            System.out.println(message);
            try {
                dateValidite = LocalDate.parse(scanner.nextLine());
                if (dateValidite.isAfter(dateEmission)) {
                    isValid = true;
                } else {
                    System.out.println("la date validite doit etre superieur a la date emission.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("please cette date au format YYYY-MM-DD.");
            }
        }
        return dateValidite;
    }






}
