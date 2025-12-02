package ui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Model {

    private final StringProperty cardNumberInput = new SimpleStringProperty("");
    private final StringProperty pinInput = new SimpleStringProperty("");
    private final StringProperty atmCash = new SimpleStringProperty("");
    private final StringProperty loginErrorMessage = new SimpleStringProperty("");
    private final StringProperty adminErrorMessage = new SimpleStringProperty("");
    private final StringProperty withdrawalErrorMessage = new SimpleStringProperty("");
    private final StringProperty depositErrorMessage = new SimpleStringProperty("");
    private final StringProperty txnSuccessMessage = new SimpleStringProperty("");
    private final StringProperty greeting = new SimpleStringProperty("");
    private final StringProperty cardNumber = new SimpleStringProperty("");
    private final StringProperty balance = new SimpleStringProperty("");
    private final StringProperty transactions = new SimpleStringProperty("");
    private final IntegerProperty ones = new SimpleIntegerProperty(0);
    private final IntegerProperty fives = new SimpleIntegerProperty(0);
    private final IntegerProperty tens = new SimpleIntegerProperty(0);
    private final IntegerProperty twenties = new SimpleIntegerProperty(0);
    private final IntegerProperty fifties = new SimpleIntegerProperty(0);
    private final IntegerProperty hundreds = new SimpleIntegerProperty(0);
    private final IntegerProperty pennies = new SimpleIntegerProperty(0);
    private final IntegerProperty nickels = new SimpleIntegerProperty(0);
    private final IntegerProperty dimes = new SimpleIntegerProperty(0);
    private final IntegerProperty quarters = new SimpleIntegerProperty(0);


    protected String getCardNumberInput() {
        return cardNumberInput.get();
    }

    protected void setCardNumberInput(String value) {
        cardNumberInput.set(value);
    }

    protected StringProperty cardNumberInputProperty() {
        return cardNumberInput;
    }

    protected String getPinInput() {
        return pinInput.get();
    }

    protected void setPinInput(String value) {
        pinInput.set(value);
    }

    protected StringProperty pinInputProperty() {
        return pinInput;
    }

    protected String getAtmCash() {
        return atmCash.get();
    }

    protected StringProperty atmCashProperty() {
        return atmCash;
    }

    protected void setAtmCash(String value) {
        atmCash.set(value);
    }

    public String getLoginErrorMessage() {
        return loginErrorMessage.get();
    }

    public void setLoginErrorMessage(String value) {
        loginErrorMessage.set(value);
    }

    public StringProperty loginErrorMessageProperty() {
        return loginErrorMessage;
    }

    public String getAdminErrorMessage() {
        return adminErrorMessage.get();
    }

    public void setAdminErrorMessage(String value) {
        adminErrorMessage.set(value);
    }

    public StringProperty adminErrorMessageProperty() {
        return adminErrorMessage;
    }

    public String getGreeting() {
        return greeting.get();
    }

    public void setGreeting(String value) {
        greeting.set(value);
    }

    public StringProperty greetingProperty() {
        return greeting;
    }

    public String getCardNumber() {
        return cardNumber.get();
    }

    public void setCardNumber(String value) {
        cardNumber.set(value);
    }

    public StringProperty cardNumberProperty() {
        return cardNumber;
    }

    public String getBalance() {
        return balance.get();
    }

    public void setBalance(String value) {
        balance.set(value);
    }

    public StringProperty balanceProperty() {
        return balance;
    }

    public String getTransactions() {
        return transactions.get();
    }

    public void setTransactions(String value) {
        transactions.set(value);
    }

    public StringProperty transactionsProperty() {
        return transactions;
    }

    public int getOnes() {
        return ones.get();
    }

    public IntegerProperty onesProperty() {
        return ones;
    }

    public void setOnes(int value) { ones.setValue(value); }

    public int getFives() {
        return fives.get();
    }

    public IntegerProperty fivesProperty() {
        return fives;
    }

    public void setFives(int value) { fives.setValue(value); }

    public int getTens() {
        return tens.get();
    }

    public IntegerProperty tensProperty() {
        return tens;
    }

    public void setTens(int value) { tens.setValue(value); }

    public int getTwenties() {
        return twenties.get();
    }

    public IntegerProperty twentiesProperty() {
        return twenties;
    }

    public void setTwenties(int value) { twenties.setValue(value); }

    public int getFifties() {
        return fifties.get();
    }

    public IntegerProperty fiftiesProperty() {
        return fifties;
    }

    public void setFifties(int value) { fifties.setValue(value); }

    public int getHundreds() {
        return hundreds.get();
    }

    public IntegerProperty hundredsProperty() {
        return hundreds;
    }

    public void setHundreds(int value) { hundreds.setValue(value); }

    public int getPennies() {
        return pennies.get();
    }

    public IntegerProperty penniesProperty() {
        return pennies;
    }

    public void setPennies(int value) { pennies.setValue(value); }

    public int getNickels() {
        return nickels.get();
    }

    public IntegerProperty nickelsProperty() {
        return nickels;
    }

    public void setNickels(int value) { nickels.setValue(value); }

    public int getDimes() {
        return dimes.get();
    }

    public IntegerProperty dimesProperty() {
        return dimes;
    }

    public void setDimes(int value) { dimes.setValue(value); }

    public int getQuarters() {
        return quarters.get();
    }

    public IntegerProperty quartersProperty() {
        return quarters;
    }

    public void setQuarters(int value) { quarters.setValue(value); }

    public String getWithdrawalErrorMessage() {
        return withdrawalErrorMessage.get();
    }

    public StringProperty withdrawalErrorMessageProperty() {
        return withdrawalErrorMessage;
    }

    public void setWithdrawalErrorMessage(String value) {
        withdrawalErrorMessage.set(value);
    }

    public String getTxnSuccessMessage() {
        return txnSuccessMessage.get();
    }

    public StringProperty txnSuccessMessageProperty() {
        return txnSuccessMessage;
    }

    public void setTxnSuccessMessage(String value) {
        txnSuccessMessage.set(value);
    }

    public String getDepositErrorMessage() {
        return depositErrorMessage.get();
    }

    public StringProperty depositErrorMessageProperty() {
        return depositErrorMessage;
    }

    public void setDepositErrorMessage(String value) {
        depositErrorMessage.set(value);
    }
}
