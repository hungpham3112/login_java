import java.util.Random;
import java.util.ResourceBundle;
import java.util.HashMap;
import java.util.Map;

public class Ebank {
    public enum Locale {
        EN,
        VI,
    }

    static ResourceBundle message;
    private static final Map<String, String> accounts = new HashMap<>();

    static {
        accounts.put("1234567890", "Password123");
        accounts.put("0987654321", "Admin12345");
        accounts.put("1111111111", "Hello1234");
    }

    public static boolean isConrrectFormat(String password) {
        return password.matches("(?=.*[0-9])(?=.*[A-Za-z]).*");
    }

    public static boolean isValidPassword(String accountNumber, String password) {
        return password.equals(accounts.get(accountNumber));
    }

    public static boolean isInDatabase(String accountNumber) {
        return accounts.containsKey(accountNumber);
    }

    public static boolean checkAccountNumber(String accountNumber) {
        return accountNumber.matches("\\d{10}");
    }

    public static String generateCaptcha() {
        String source = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        char[] chars = source.toCharArray();
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 6; ++i) {
            int num = random.nextInt(chars.length);
            sb.append(chars[num]);
        }
        return sb.toString();
    }

    public static String checkCaptcha(String captchaInput, String captchaGenerate) {
        if (captchaInput.length() == 0) {
            return message.getString("errCheckInputIntLimit");
        }
        return captchaGenerate.equals(captchaInput) ? "Valid" : message.getString("errorCheckInputIntLimit");
    }

    public static void setLocale(Locale locale) {
        if (locale == Locale.VI) {
            message = ResourceBundle.getBundle("Language.vi");
        } else if (locale == Locale.EN) {
            message = ResourceBundle.getBundle("Language.en");
        }
    }
}
