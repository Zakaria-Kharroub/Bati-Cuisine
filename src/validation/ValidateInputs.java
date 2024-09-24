package validation;

public class ValidateInputs {

    public static boolean validateDate(String date) {
        return date.matches("^\\d{4}-\\d{2}-\\d{2}$");
    }
    public boolean validateDouble(String number) {
        return number.matches("^\\d+(\\.\\d+)?$");
    }

    public boolean validateBoolean(String bool) {
        return bool.matches("true|false");
    }
}
