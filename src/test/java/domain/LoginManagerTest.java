package domain;

import database.DatabaseManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class LoginManagerTest {
    private static LoginManager loginManager;
    private static DatabaseManager dbManager;
    private static ATM atm;

    @BeforeAll
    static void setUp() {
        dbManager = new DatabaseManager();
        atm = new ATM(dbManager);
        loginManager = new LoginManager(atm, dbManager);
    }

    @Test
    void loginThrowsIllegalArgumentExceptionWithInvalidCardNumber() {
        assertThrows(IllegalArgumentException.class, () -> loginManager.login(1234567890123456L, 123));
    }

    @Test
    void loginThrowsInvalidPinExceptionWithIncorrectPin() {
        assertThrows(InvalidPINException.class, () -> loginManager.login(1367845270394549L, 123));
    }

    @Test
    void atmAccountUpdatedOnLoginSuccess() throws SQLException, InvalidPINException {
        Account expected = new Account(dbManager.getAccountDetails(3997395871580161L));
        loginManager.login(3997395871580161L, 854);
        assertEquals(expected, atm.getCurrentAccount());
    }
}