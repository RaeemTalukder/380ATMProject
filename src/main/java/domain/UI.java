package domain;
import java.util.Scanner;
import com.zaxxer.hikari.HikariDataSource;
import database.DatabaseManager;

public class UI {
    Enum <> bills;
    EnumMap <> coins;
    private static final String JDBC_URL =
            "jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:5432/postgres?user=postgres.kjcrwhinbakxmxnzjswk&password=380ATMProject";
    public void guestui(long cardNumber, int pin, String name, boolean ourBranch, double balance, boolean isAdmin){
        Scanner sc = new Scanner(System.in);
        HikariDataSource ds = new HikariDataSource();
        DatabaseManager dm = new DatabaseManager(ds);
        Account ac = new Account(cardNumber, pin, name, ourBranch, balance, isAdmin);
        Transaction Transaction;
        ATM atm = new ATM();
        System.out.println("Welcome, Enter a number to continue");
        System.out.print("1) Deposit");
        System.out.print("2) Withdraw");
        System.out.print("3) View Balance");
        System.out.print("4) Transaction History");
        System.out.print("5) Logout");
        int command = sc.nextInt();
        switch(command){
            case 1:
                System.out.println("Amount of ones to deposit?");
                int dones = sc.nextInt();
                System.out.println("Amount of Fives to deposit?");
                int dfives = sc.nextInt();
                System.out.println("Amount of Tens to deposit?");
                int dtens = sc.nextInt();
                System.out.println("Amount of twenties to deposit?");
                int dtwenties = sc.nextInt();
                System.out.println("Amount of fifties to deposit?");
                int dfifties = sc.nextInt();
                System.out.println("Amount of hundred to deposit?");
                int dhundred = sc.nextInt();

                atm.deposit();



            case 2:
                System.out.println("Amount of ones to withdraw?");
                int wones = sc.nextInt();
                System.out.println("Amount of Fives? to withdraw");
                int wfives = sc.nextInt();
                System.out.println("Amount of Tens? to withdraw");
                int wtens = sc.nextInt();
                System.out.println("Amount of twenties? to withdraw");
                int wtwenties = sc.nextInt();
                System.out.println("Amount of fifties? to withdraw");
                int wfifties = sc.nextInt();
                System.out.println("Amount of hundred ? to withdraw");
                int whundred = sc.nextInt();

            case 3:
                double b = ac.getBalance();
                System.out.println("Current Balance:" + b);

            case 4:
                System.out.println("Transaction History: " );
                ac.printTransactions();
                guestui(cardNumber, pin, name, ourBranch, balance, isAdmin);
            case 5:
                LoginManager LM = new LoginManager(atm, dm);

        }
    }
    public void adminui() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter command");
        System.out.println("1)Open ATM");
        System.out.println("2)Open ATM History");
        System.out.println("3) Check ATM ");
        HikariDataSource ds = new HikariDataSource();
        DatabaseManager dm = new DatabaseManager(ds);
        Transaction Transaction;
        ATM atm = new ATM(dm);
        Account ac;
        int command = sc.nextInt();
        switch (command) {
            case 1:

            case 2:
            case 3:

        }
    }
    public static HikariDataSource getDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(JDBC_URL);
        ds.setAutoCommit(false);
        return ds;


    }
}

