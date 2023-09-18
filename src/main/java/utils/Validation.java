package utils;

import java.util.List;
import java.util.regex.Pattern;

public class Validation {
    enum Validators{
        EMAIL,
        IS_EMPTY,
        IS_MIN
    }

    public static boolean isEmail(String email ){
        String pattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(pattern).matcher(email).matches();
    }

    public static boolean isEmpty(String input){
        return input.trim().equals("");
    }

    public static boolean isMinChar(int value, String input){
        return input.trim().length() >= value;
    }

    public static boolean isMaxChar(int value, String input){
        return input.trim().length() <= value;
    }

    public static boolean validateEmail(String input, List<Validators> validators){
        boolean isValid = true;
        for (Validators v: validators){
            if (v == Validators.EMAIL){
                isValid = isValid && isEmail(input);
            }else if (v == Validators.IS_EMPTY){
                isValid = isValid && isEmpty(input);
            }
        }
        return isValid;
    }
}
