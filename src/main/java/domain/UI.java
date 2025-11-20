package domain;
import com.zaxxer.hikari.HikariDataSource;
import database.DatabaseManager;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;



public class UI{
    private static final String JDBC_URL =
            "jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:5432/postgres?user=postgres.kjcrwhinbakxmxnzjswk&password=380ATMProject";
    public void guestui(long cardnumber, LocalDateTime timestamp, int pin, String name, boolean ourBranch, double balance, boolean isAdmin){
        Scanner sc = new Scanner(System.in);
        Account ac = new Account(cardnumber, pin, name, ourBranch, balance, isAdmin);
        HikariDataSource ds = new HikariDataSource();
        DatabaseManager dm = new DatabaseManager(ds);
        Transaction Transaction;
        ATM atm = new ATM(dm);
        System.out.println("Welcome, Enter a number to continue");
        System.out.print("1) Deposit");
        System.out.print("2) Withdraw");
        System.out.print("3) View Balance");
        System.out.print("4) Transaction History");
        System.out.print("5) Exit");
        int command = sc.nextInt();
        switch(command){
            case 1:
                System.out.println("Enter Desired amount to deposit (Enter 0 to return to main menu):");
                int damount= sc.nextInt();

                if(damount != 0) {
                    Transaction = new Transaction(domain.Transaction.TransactionType.DEPOSIT, damount, cardnumber, timestamp);
                } else{
                    guestui(cardnumber, timestamp, pin, name, ourBranch, balance, isAdmin);
                }
            case 2:
                System.out.println("Enter Desired amount to withdraw (Enter 0 to return to main menu):");
                int wamount= sc.nextInt();
                if(wamount != 0){
                Transaction t = new Transaction(domain.Transaction.TransactionType.WITHDRAWAL, wamount, cardnumber, timestamp);}
                else { guestui(cardnumber,  timestamp, pin, name, ourBranch, balance, isAdmin);
                }
            case 3:
                double b = ac.getBalance();
                System.out.println("Current Balance:" + b);
                guestui(cardnumber, timestamp, pin, name, ourBranch, balance, isAdmin);
            case 4:
                System.out.println("Transaction History: " );
                ac.printTransactions();
                guestui(cardnumber, timestamp, pin, name, ourBranch, balance, isAdmin);
            case 5:
                guestui(cardnumber, timestamp, pin, name, ourBranch, balance, isAdmin);
        }
    }
    public void adminui(){
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
        switch(command){
            case 1:

            case 2:
            case 3:

        }

    }

    }

public static HikariDataSource getDataSource() {
    HikariDataSource ds = new HikariDataSource();
    ds.setJdbcUrl(JDBC_URL);
    ds.setAutoCommit(false);
    return ds;



}
