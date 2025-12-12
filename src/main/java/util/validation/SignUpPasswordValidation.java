package util.validation;

public class SignUpPasswordValidation {
    public static String validate(String password) {
        if (passwordTooShort(password)) {
//            return "Password must be at least 6 characters"; not necessary because of hibernate's validation
            return null;
        } else if (passwordHaveNoUpperCaseCharacter(password)) {
            return "Password must contain at least one uppercase character";
        }
        return null;
    }

    private static boolean passwordTooShort(String password) {
        return password.length() < 6;
    }

    private static boolean passwordHaveNoUpperCaseCharacter(String password) {
        for (int i = 0; i < password.length(); i++) {
            Character character = password.toCharArray()[i];
            if (Character.isUpperCase(character)) {
                return false;
            }
        }
        return true;
    }

}
