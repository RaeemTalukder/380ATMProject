package domain;

import database.DatabaseManager;

import java.sql.SQLException;
import java.util.EnumMap;

import static domain.Cash.*;
import static domain.Coin.*;

public class ATM {
    private Account currentAccount;
    private final DatabaseManager manager;
    private EnumMap<Cash, Integer> ATMCash;
    private EnumMap<Coin, Integer> ATMCoins;

    public ATM(DatabaseManager manager) {
        this.manager = manager;
        ATMCash = new EnumMap<>(Cash.class);
        ATMCoins = new EnumMap<>(Coin.class);
    }

    /**
     * Initializes ATMCash and ATMCoins with the necessary
     * amount of bills and coins. For now, it is called in the
     * constructor, but should be called by the first admin
     * login to the ATM when that is implemented.
     */
    public void initializeMoney() {
        ATMCash.put(ONE, 5000);
        ATMCash.put(FIVE, 1000);
        ATMCash.put(TEN, 1000);
        ATMCash.put(TWENTY, 1000);
        ATMCash.put(FIFTY, 750);
        ATMCash.put(HUNDRED, 750);

        ATMCoins.put(PENNY, 1000);
        ATMCoins.put(NICKEL, 1000);
        ATMCoins.put(DIME, 1000);
        ATMCoins.put(QUARTER, 1000);
    }

    public void setCurrentAccount(long cardNumber) throws SQLException {
        String accountParams = manager.getAccountDetails(cardNumber);
        currentAccount = new Account(accountParams);
        currentAccount.createTransactionList(manager.getTransactions(cardNumber));
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public void printCurrentAccount() {
        System.out.println(currentAccount);
    }

    public void printTransactions() {
        currentAccount.printTransactions();
    }

    /**
     * Use after calling deposit() or withdraw() to keep the
     * Account object up to date with the database.
     */
    public void updateAccount() throws SQLException {
        setCurrentAccount(currentAccount.getCardNumber());
    }

    /**
     * Makes a deposit on the account represented by
     * currentAccount, using the DatabaseManager deposit method.
     */
    public void deposit(EnumMap<Cash, Integer> bills, EnumMap<Coin, Integer> coins) throws SQLException, InsufficientCashException {
        double amount = calculateTransactionAmount(bills, coins);

        if (amount == 0) {
            throw new InsufficientCashException("All cash and coins fields cannot be 0.");
        }

        manager.deposit(currentAccount.getCardNumber(), amount);
        updateAccount();

        for (Cash bill : bills.keySet()) {
            int billAmount = bills.getOrDefault(bill, 0);
            ATMCash.replace(bill, ATMCash.get(bill) + billAmount);
        }
        for (Coin coin : coins.keySet()) {
            int coinAmount = coins.getOrDefault(coin, 0);
            ATMCoins.replace(coin, ATMCoins.get(coin) + coinAmount);
        }
    }

    public void withdraw(EnumMap<Cash, Integer> bills, EnumMap<Coin, Integer> coins) throws InsufficientCashException, SQLException {
        if (!atmHasSufficientCash(bills, coins)) {
            throw new InsufficientCashException("ATM does not have enough cash to process this withdrawal, " +
                    "please contact an administrator.");
        }

        double amount = calculateTransactionAmount(bills, coins);

        if (amount == 0) {
            throw new InsufficientCashException("All cash and coins fields cannot be 0.");
        }

        if (amount > currentAccount.getBalance()) {
            throw new InsufficientCashException("Your account does not have enough cash to process this withdrawal.");
        }

        manager.withdraw(currentAccount.getCardNumber(), amount);
        updateAccount();


        for (Cash bill : bills.keySet()) {
            int billAmount = bills.getOrDefault(bill, 0);
            ATMCash.replace(bill, ATMCash.get(bill) - billAmount);
        }
        for (Coin coin : coins.keySet()) {
            int coinAmount = coins.getOrDefault(coin, 0);
            ATMCoins.replace(coin, ATMCoins.get(coin) - coinAmount);
        }
    }

    /**
     * Calculates the total dollar value of the bills
     * and coins contained in the EnumMap parameters.
     */
    public double calculateTransactionAmount(EnumMap<Cash, Integer> bills, EnumMap<Coin, Integer> coins) {
        double result = 0;

        for (Cash bill : bills.keySet()) {
            result += bill.value * bills.getOrDefault(bill, 0);
        }
        for (Coin coin : coins.keySet()) {
            result += coin.value * coins.getOrDefault(coin, 0);
        }

        return result;
    }

    /**
     * For use in withdraw() to make sure that the ATM contains
     * enough cash to process the withdrawal.
     */
    public boolean atmHasSufficientCash(EnumMap<Cash, Integer> bills, EnumMap<Coin, Integer> coins) {
        for (Cash bill : ATMCash.keySet()) {
            if (bills.getOrDefault(bill, 0) > ATMCash.get(bill)) {
                return false;
            }
        }

        for (Coin coin : ATMCoins.keySet()) {
            if (coins.getOrDefault(coin, 0) > ATMCoins.get(coin)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the amount of each type of bill and coin
     * in the ATM as a String, or if the ATM's cash has
     * not been initialized yet, a message stating so is returned.
     */
    @Override
    public String toString() {
        if (ATMCash.isEmpty()) {
            return "ATM currently has no cash, please initialize cash before starting operations.";
        }

        StringBuilder result = new StringBuilder();

        for (Cash bill : ATMCash.keySet()) {
            result.append(bill.name()).append(": ").append(ATMCash.getOrDefault(bill, 0)).append("\n");
        }
        for (Coin coin : ATMCoins.keySet()) {
            result.append(coin.name()).append(": ").append(ATMCoins.getOrDefault(coin, 0)).append("\n");
        }

        return result.toString();
    }

    public void printATMCash() {
        System.out.println(this.toString());
    }

    /**
     * Returns a double, for printing purposes make sure to
     * use String.format to truncate the result to two decimal places.
     */
    public double totalMoneyInATM() {
        return calculateTransactionAmount(ATMCash, ATMCoins);
    }

}
