package domain;

import com.zaxxer.hikari.HikariDataSource;
import database.DatabaseManager;

import java.sql.SQLException;

public class LoginManager {
    private ATM atm;
    private DatabaseManager manager;

    public LoginManager(ATM atm, DatabaseManager manager) {
        this.atm = atm;
        this.manager = manager;
    }

    public void login(long cardNumber, int pin) throws IllegalArgumentException, InvalidPINException, SQLException {
        if (manager.verifyPIN(cardNumber, pin)) {
            atm.setCurrentAccount(cardNumber);
        }
        else {
            throw new InvalidPINException("PIN does not match card number");
        }
    }

}
