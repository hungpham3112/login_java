import java.util.Scanner;

public class Login {
    private final Scanner scanner;

    public Login(Scanner scanner) {
        this.scanner = scanner;
    }

    public void printMenu() {
        System.out.println("""
                1. Vietnamese
                2. English
                3. Exit
                """);
    }

    public boolean setLocale() {
        String langChoice = scanner.nextLine();

        switch (langChoice) {
            case "1":
                Ebank.setLocale(Ebank.Locale.VI);
                return true;

            case "2":
                Ebank.setLocale(Ebank.Locale.EN);
                return true;

            default:
                return false;
        }
    }

    public String checkAccountNumber() {
        while (true) {
            System.out.print(Ebank.message.getString("enterAccountNumber"));

            String accountNumber = scanner.nextLine();

            if (!Ebank.checkAccountNumber(accountNumber)) {
                System.err.println(Ebank.message.getString("errCheckInputAccount"));
                continue;
            }

            if (!Ebank.isInDatabase(accountNumber)) {
                System.err.println(Ebank.message.getString("errCheckInputAccountNotInDatabase"));
                continue;
            }

            return accountNumber;
        }
    }

    public boolean checkPassword(String accountNumber) {
        for (int i = 0; i < 3; ++i) {
            System.out.print(Ebank.message.getString("enterPassword"));

            String password = scanner.nextLine();
            int length = password.length();

            if (length < 8 || length > 31) {
                System.err.println(
                        Ebank.message.getString("errCheckLengthPassword"));
                continue;
            }

            if (!Ebank.isConrrectFormat(password) || !Ebank.isValidPassword(accountNumber, password)) {
                System.err.println(Ebank.message.getString("errCheckAlphanumericPassword"));
                continue;
            }

            return true;
        }

        return false;
    }

    public boolean checkCaptcha() {
        String captcha = Ebank.generateCaptcha();
        System.out.println(captcha);

        for (int i = 0; i < 3; ++i) {
            System.out.print(
                    Ebank.message.getString("enterCaptcha"));

            String captchaInput = scanner.nextLine();
            String result = Ebank.checkCaptcha(captchaInput, captcha);

            if (!result.equals("Valid")) {
                System.err.println(result);
                continue;
            }

            return true;
        }

        return false;
    }

    public void run() {
        printMenu();

        if (!setLocale()) {
            return;
        }

        String accountNumber = checkAccountNumber();

        if (!checkPassword(accountNumber) || !checkCaptcha()) {
            System.err.println("Login failed");
            return;
        }

        System.out.println(Ebank.message.getString("loginSuccess"));
    }

    public static void init() {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login(scanner);
        login.run();
    }
}
