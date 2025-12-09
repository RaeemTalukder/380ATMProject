package domain;

import database.DatabaseManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.*;

class ATMTest {
    private static ATM atm;
    private static EnumMap<Cash, Integer> cash;
    private static EnumMap<Coin, Integer> coins;

    @BeforeAll
    static void setUp() {
        atm = new ATM(new DatabaseManager());
        cash = new EnumMap<>(Cash.class);
        coins = new EnumMap<>(Coin.class);
    }

    @Test
    void atmHasNoMoneyBeforeInitialized() {
        assertEquals(0, atm.totalMoneyInATM());
    }

    @Test
    void initializeMoneyFillsAtmWithCorrectAmount() {
        atm.initializeMoney();
        assertEquals(152910, atm.totalMoneyInATM());
    }

    @Test
    void withdrawDoesNotAllowEmptyWithdrawal() {
        atm.initializeMoney();
        assertThrows(InsufficientCashException.class, () -> atm.withdraw(cash, coins));
    }

    @Test
    void withdrawThrowsInsufficientCashExceptionWhenWithdrawingTooMuchCash() {
        atm.initializeMoney();
        cash.put(Cash.HUNDRED, 10000);
        assertThrows(InsufficientCashException.class, () -> atm.withdraw(cash, coins));
    }

    @Test
    void withdrawThrowsInsufficientCashExceptionWhenWithdrawingTooManyCoins() {
        atm.initializeMoney();
        coins.put(Coin.QUARTER, 10000);
        assertThrows(InsufficientCashException.class, () -> atm.withdraw(cash, coins));
    }

    @Test
    void depositDoesNotAllowEmptyDeposit() {
        atm.initializeMoney();
        assertThrows(InsufficientCashException.class, () -> atm.deposit(cash, coins));
    }

}