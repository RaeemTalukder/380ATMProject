package ui;

import com.zaxxer.hikari.HikariDataSource;
import database.DatabaseManager;
import domain.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class TestApp {

    public static void main(String[] args) throws SQLException {
        DatabaseManager manager = new DatabaseManager();

//        boolean pinVerification = manager.verifyPIN(3997395871580161L, 854);
//        System.out.println("pinVerification = " + pinVerification);

        ATM atm = new ATM(manager);
        LoginManager loginManager = new LoginManager(atm, manager);
        try {
            loginManager.login(3997395871580161L, 854);
        } catch (InvalidPINException e) {
            System.out.println(e.getMessage());
        }
        atm.initializeMoney();


        EnumMap<Cash, Integer> bills = new EnumMap<>(Cash.class);
        bills.put(Cash.HUNDRED, 500);
        EnumMap<Coin, Integer> coins = new EnumMap<>(Coin.class);

        System.out.println(atm.calculateTransactionAmount(bills, coins));
        System.out.println(atm.getCurrentAccount().getBalance());
        System.out.println(atm.calculateTransactionAmount(bills, coins) > atm.getCurrentAccount().getBalance());
//        try {
//            atm.withdraw(bills, coins);
//        } catch (InsufficientCashException e) {
//            System.out.println(e.getMessage());
//        }

    }
}
