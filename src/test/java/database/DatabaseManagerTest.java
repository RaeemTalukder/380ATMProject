package database;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseManagerTest {
    private static DatabaseManager manager;

    @BeforeAll
    static void setup() {
        manager = new DatabaseManager();
    }

    @Test
    void verifyPinThrowsIllegalArgumentExceptionWithInvalidCardNumber() {
        assertThrows(IllegalArgumentException.class, () -> manager.verifyPIN(1234567890123456L, 123));
    }

    @Test
    void verifyPinReturnsTrueWithCorrectPin() throws SQLException {
        assertTrue(manager.verifyPIN(3176430787586701L, 311));
    }

    @Test
    void verifyPinReturnsFalseWithIncorrectPin() throws SQLException {
        assertFalse(manager.verifyPIN(3176430787586701L, 123));
    }

    @Test
    void isAdminThrowsIllegalArgumentExceptionWithInvalidCardNumber() {
        assertThrows(IllegalArgumentException.class, () -> manager.isAdmin(1234567890123456L));
    }

    @Test
    void isAdminReturnsTrueWithAdminCardNumber() throws SQLException {
        assertTrue(manager.isAdmin(3997395871580161L));
    }

    @Test
    void isAdminReturnsFalseWithNonAdminCardNumber() throws SQLException {
        assertFalse(manager.isAdmin(7634151210750907L));
    }
}