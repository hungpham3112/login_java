import java.util.Scanner;

public class Main {
    public static void printMenu() {
        System.out.printf("""
1. Vietnamese
2. English
3. Exit
""");
    }

    public static void main(String[] args) {
        boolean loginSuccess = false;
        Scanner scanner = new Scanner(System.in);

        printMenu();
        String lang_choice = scanner.nextLine();
        switch (lang_choice) {
            case "1":
                Ebank.setLocale(Ebank.Locale.VI);
                break;
            case "2":
                Ebank.setLocale(Ebank.Locale.EN);
                break;
            default:
                scanner.close();
                return;
        }

        while (true) {
            System.out.print(Ebank.message.getString("enterAccountNumber"));
            String accountNumber = scanner.nextLine();
            if (!Ebank.checkAccountNumber(accountNumber)) {
                System.err.println(Ebank.message.getString("errCheckInputAccount"));
                continue;
            }
            break;
        }

        boolean passwordValid = false;
        for (int i = 0; i < 3; ++i) {
            System.out.print(Ebank.message.getString("enterPassword"));
            String password = scanner.nextLine();
            int length = password.length();
            if (length < 8 || length > 31) {
                System.err.println(Ebank.message.getString("errCheckLengthPassword"));
                continue;
            }
            if (!Ebank.checkPassword(password)) {
                System.err.println(Ebank.message.getString("errCheckAlphanumericPassword"));
                continue;
            }
            passwordValid = true;
            break;
        }

        if (passwordValid) {
            String captcha = Ebank.generateCaptcha();
            System.out.println(captcha);
            for (int i = 0; i < 3; ++i) {
                System.out.print(Ebank.message.getString("enterCaptcha"));
                String captchaInput = scanner.nextLine();
                String result = Ebank.checkCaptcha(captchaInput, captcha);
                if (!result.equals("Valid")) {
                    System.err.println(result);
                    continue;
                }
                loginSuccess = true;
                break;
            }
        }

        if (loginSuccess) {
            System.out.println(Ebank.message.getString("loginSuccess"));
        } else {
            System.err.println("Login failed");
        }

        scanner.close();
    }
}
