package ui;

import database.DatabaseManager;
import domain.*;

import java.sql.SQLException;
import java.util.EnumMap;

import static domain.Cash.*;
import static domain.Coin.*;

public class Interactor {

    private final Model model;
    private final Controller controller;
    private final ATM atm;
    private final LoginManager loginManager;

    public Interactor(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;
        DatabaseManager dbManager = new DatabaseManager();
        atm = new ATM(new DatabaseManager());
        loginManager = new LoginManager(atm, dbManager);
    }

    public void processInitialLoginRequest() {
        if (blankLoginField()) {
            return;
        }

        long cardNumber = Long.parseLong(model.getCardNumberInput());
        int pin = Integer.parseInt(model.getPinInput());

        clearLoginFields();

        try {
            loginManager.login(cardNumber, pin);

            if (!atm.getCurrentAccount().isAdmin()) {
                model.setLoginErrorMessage("Admin login is required to initialize the ATM. Please contact a bank administrator.");
                return;
            }

            model.setAtmCash(atm.toString());
            controller.setInitialAdminScreen();
            model.setLoginErrorMessage("");
        } catch (IllegalArgumentException | InvalidPINException e) {
            model.setLoginErrorMessage(e.getMessage());
        } catch (SQLException e) {
            model.setLoginErrorMessage("Database error occurred during login: " + e.getMessage());
        }
    }

    public void setAtmCash() {
        atm.initializeMoney();
        model.setAtmCash(atm.toString());
    }

    public void processAdminOpen() {
        if (atm.totalMoneyInATM() == 0) {
            model.setAdminErrorMessage("ATM must be filled with cash before opening.");
            return;
        }

        model.setAdminErrorMessage("");
        controller.setMainLoginScreen();
    }

    public void processLoginRequest() {
        model.setLoginErrorMessage("");

        if (blankLoginField()) {
            return;
        }

        long cardNumber = Long.parseLong(model.getCardNumberInput());
        int pin = Integer.parseInt(model.getPinInput());

        clearLoginFields();

        try {
            loginManager.login(cardNumber, pin);

            model.setGreeting("Welcome to Dominion Bank,\n" + atm.getCurrentAccount().getName());
            model.setCardNumber(String.valueOf(atm.getCurrentAccount().getCardNumber()));
            model.setBalance("$ " + atm.getCurrentAccount().getBalance());
            model.setTransactions(atm.getCurrentAccount().transactionsAsString());

            if (atm.getCurrentAccount().isAdmin()) {
                controller.setAdminAccountScreen();
            } else {
                controller.setAccountScreen();
            }

        } catch (IllegalArgumentException | InvalidPINException e) {
            model.setLoginErrorMessage(e.getMessage());
        } catch (SQLException e) {
            model.setLoginErrorMessage("Database error occurred during login: " + e.getMessage());
        }
    }

    public void processAdminOptionsButton() {
        model.setLoginErrorMessage("");
        model.setTxnSuccessMessage("");
        controller.setAdminScreen();
    }

    public void processWithdrawButton() {
        model.setTxnSuccessMessage("");
        model.setWithdrawalErrorMessage("");
        controller.setWithdrawScreen();
    }

    public void processDepositButton() {
        model.setTxnSuccessMessage("");
        model.setDepositErrorMessage("");
        controller.setDepositScreen();
    }

    public void processLogoutButton() {
        controller.setMainLoginScreen();
    }

    public void processCancelButton() {
        clearDepositWithdrawFields();
        returnToAccountScreen();
    }

    public void processWithdrawal() {
        EnumMap<Cash, Integer> cash = new EnumMap<>(Cash.class);
        EnumMap<Coin, Integer> coins = new EnumMap<>(Coin.class);

        cash.put(ONE, model.getOnes());
        cash.put(FIVE, model.getFives());
        cash.put(TEN, model.getTens());
        cash.put(TWENTY, model.getTwenties());
        cash.put(FIFTY, model.getFifties());
        cash.put(HUNDRED, model.getHundreds());

        coins.put(PENNY, model.getPennies());
        coins.put(NICKEL, model.getNickels());
        coins.put(DIME, model.getDimes());
        coins.put(QUARTER, model.getQuarters());

        try {
            atm.withdraw(cash, coins);
        } catch (InsufficientCashException e) {
            model.setWithdrawalErrorMessage(e.getMessage());
            clearDepositWithdrawFields();
            return;
        } catch (SQLException e) {
            model.setWithdrawalErrorMessage("Database error when withdrawing: " + e.getMessage());
            clearDepositWithdrawFields();
            return;
        }

        clearDepositWithdrawFields();

        model.setTxnSuccessMessage("Withdrawal Was Successful.");
        model.setWithdrawalErrorMessage("");
        model.setBalance("$ " + atm.getCurrentAccount().getBalance());
        model.setTransactions(atm.getCurrentAccount().transactionsAsString());
        model.setAtmCash(atm.toString());

        returnToAccountScreen();
    }

    public void processDeposit() {
        EnumMap<Cash, Integer> cash = new EnumMap<>(Cash.class);
        EnumMap<Coin, Integer> coins = new EnumMap<>(Coin.class);

        cash.put(ONE, model.getOnes());
        cash.put(FIVE, model.getFives());
        cash.put(TEN, model.getTens());
        cash.put(TWENTY, model.getTwenties());
        cash.put(FIFTY, model.getFifties());
        cash.put(HUNDRED, model.getHundreds());

        coins.put(PENNY, model.getPennies());
        coins.put(NICKEL, model.getNickels());
        coins.put(DIME, model.getDimes());
        coins.put(QUARTER, model.getQuarters());

        try {
            atm.deposit(cash, coins);
        } catch (InsufficientCashException e) {
            model.setDepositErrorMessage(e.getMessage());
            clearDepositWithdrawFields();
            return;
        }
        catch (SQLException e) {
            model.setDepositErrorMessage("Database error when withdrawing: " + e.getMessage());
            clearDepositWithdrawFields();
            return;
        }

        clearDepositWithdrawFields();

        model.setTxnSuccessMessage("Deposit Was Successful.");
        model.setDepositErrorMessage("");
        model.setBalance("$ " + atm.getCurrentAccount().getBalance());
        model.setTransactions(atm.getCurrentAccount().transactionsAsString());
        model.setAtmCash(atm.toString());

        returnToAccountScreen();
    }

    public void closeAtm() {
        System.exit(0);
    }

    private boolean blankLoginField() {
        return model.getCardNumberInput().isBlank() || model.getPinInput().isBlank();
    }

    private void clearLoginFields() {
        model.setCardNumberInput("");
        model.setPinInput("");
    }

    private void returnToAccountScreen() {
        if (atm.getCurrentAccount().isAdmin()) {
            controller.setAdminAccountScreen();
        } else {
            controller.setAccountScreen();
        }
    }

    private void clearDepositWithdrawFields() {
        model.setOnes(0);
        model.setFives(0);
        model.setTens(0);
        model.setTwenties(0);
        model.setFifties(0);
        model.setHundreds(0);
        model.setPennies(0);
        model.setNickels(0);
        model.setDimes(0);
        model.setQuarters(0);
    }

}
