package domain;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private long cardNumber;
    private int pin;
    private String name;
    private boolean ourBranch;
    private double balance;
    private boolean isAdmin;
    private List<Transaction> transactionList;

    public Account(long cardNumber, int pin, String name, boolean ourBranch, double balance, boolean isAdmin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.name = name;
        this.ourBranch = ourBranch;
        this.balance = balance;
        this.isAdmin = isAdmin;
    }

    /*  Use getAccountDetails() in DatabaseManager to make an
        Account with this constructor.
     */
    public Account(String params) {
        String[] parts = params.split(" ");
        this(Long.parseLong(parts[0]),              // cardNumber
                Integer.parseInt(parts[1]),         // pin
                parts[2] + " " + parts[3],          // name
                Boolean.parseBoolean(parts[4]),     // ourBranch
                Double.parseDouble(parts[5]),       // balance
                Boolean.parseBoolean(parts[6]));    // isAdmin
    }

    public void createTransactionList(ArrayList<String> params) {
        transactionList = new ArrayList<>();
        transactionList = params.stream().map(Transaction::new).toList();
    }

    public String transactionsAsString() {
        StringBuilder sb = new StringBuilder();
        transactionList.forEach(txn -> sb.append(txn).append("\n"));
        return sb.toString();
    }

    public void printTransactions() {
        transactionList.forEach(System.out::println);
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public int getPin() {
        return pin;
    }

    public String getName() {
        return name;
    }

    public boolean isOurBranch() {
        return ourBranch;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public String toString() {
        return name + ": "
                + (ourBranch ? "Member of our branch. " : "Not a member of our branch. ")
                + (isAdmin ? "Admin privileges. " : "Not an admin. ")
                + "Balance: " + String.format("%.2f", balance);

    }
}
