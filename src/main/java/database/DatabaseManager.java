package database;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class DatabaseManager {

    private static final String JDBC_URL =
            "jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:5432/postgres?user=postgres.kjcrwhinbakxmxnzjswk&password=380ATMProject";
    private final HikariDataSource ds;

    public DatabaseManager() {
        this.ds = getDataSource();
    }

    public static HikariDataSource getDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(JDBC_URL);
        ds.setAutoCommit(false);
        return ds;
    }

    /**
     * Returns true if the provided card number and PIN match, false otherwise.
     * Throws an IllegalArgumentException if the card number does not exist,
     * be sure to use inside a try-catch so it doesn't crash the ATM.
     */
    public boolean verifyPIN(long cardNumber, int pin) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from accounts where card_num = ?");
            stmt.setLong(1, cardNumber);
            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                throw new IllegalArgumentException("Card number " + cardNumber + " is not valid.");
            }
            int actualPin = result.getInt("pin");
            return pin == actualPin;
        }
    }

    private int getPrimaryKey(long cardNumber) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from accounts where card_num = ?");
            stmt.setLong(1, cardNumber);
            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                throw new IllegalArgumentException("Card number " + cardNumber + " is not valid.");
            }
            return result.getInt("id");
        }
    }

    public double getBalance(long cardNumber) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from accounts where card_num = ?");
            stmt.setLong(1, cardNumber);
            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                throw new IllegalArgumentException("Card number " + cardNumber + " is not valid.");
            }
            return result.getDouble("balance");
        }
    }

    /**
     * Takes a card number, returns an ArrayList of Strings
     * that are formatted to contain the information necessary
     * for the Account constructor.
     */
    public String getAccountDetails(long cardNumber) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from accounts where card_num = ?");
            stmt.setLong(1, cardNumber);
            ResultSet result = stmt.executeQuery();

            if (!result.next()) {
                throw new IllegalArgumentException("Card number " + cardNumber + " is not valid.");
            }

            StringBuilder sb = new StringBuilder();
            sb.append(cardNumber).append(" ")
                    .append(result.getInt("pin")).append(" ")
                    .append(result.getString("first_name")).append(" ")
                    .append(result.getString("last_name")).append(" ")
                    .append(result.getBoolean("our_branch")).append(" ")
                    .append(result.getDouble("balance")).append(" ")
                    .append(result.getBoolean("is_admin"));

            return sb.toString();
        }
    }

    public void withdraw(long cardNumber, double amount) throws SQLException {
        Connection conn = ds.getConnection();

        try (conn) {
            PreparedStatement updateStmt = conn.prepareStatement
                    ("update accounts set balance = (balance - ?) where card_num = ?");
            updateStmt.setDouble(1, amount);
            updateStmt.setLong(2, cardNumber);
            updateStmt.executeUpdate();

            int primaryKey = getPrimaryKey(cardNumber);

            PreparedStatement insertStmt = conn.prepareStatement
                    ("insert into transactions (amount, fk_account_id, transaction_type, timestamp) values (?, ?, ?, ?)");
            insertStmt.setDouble(1, amount);
            insertStmt.setInt(2, primaryKey);
            insertStmt.setString(3, "WITHDRAWAL");
            insertStmt.setObject(4, LocalDateTime.now());
            insertStmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
        }
    }

    public void deposit(long cardNumber, double amount) throws SQLException {
        Connection conn = ds.getConnection();

        try (conn) {
            PreparedStatement updateStmt = conn.prepareStatement
                    ("update accounts set balance = (balance + ?) where card_num = ?");
            updateStmt.setDouble(1, amount);
            updateStmt.setLong(2, cardNumber);
            updateStmt.executeUpdate();

            int primaryKey = getPrimaryKey(cardNumber);

            PreparedStatement insertStmt = conn.prepareStatement
                    ("insert into transactions (amount, fk_account_id, transaction_type, timestamp) values (?, ?, ?, ?)");
            insertStmt.setDouble(1, amount);
            insertStmt.setInt(2, primaryKey);
            insertStmt.setString(3, "DEPOSIT");
            insertStmt.setObject(4, LocalDateTime.now());
            insertStmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
        }
    }

    /**
     * For testing purposes. Returns a random card number so that
     * you don't have to copy a card number from the table each time.
     */
    public long getRandomCardNumber() throws SQLException {
        try (Connection conn = ds.getConnection()) {

            // Get the total number of accounts in the table
            PreparedStatement stmt = conn.prepareStatement("select count(*) from accounts");
            ResultSet result = stmt.executeQuery();
            result.next();
            int numAccounts = result.getInt(1);

            // Choose a random account
            Random random = new Random();
            int randomPrimaryKey = random.nextInt(numAccounts) + 1;

            // Return that random account's card number
            stmt = conn.prepareStatement("select * from accounts where id = ?");
            stmt.setInt(1, randomPrimaryKey);
            result = stmt.executeQuery();
            result.next();
            return result.getLong("card_num");
        }
    }

    /**
     * Returns an ArrayList of Strings containing the parameters
     * for the Transaction constructor in the format
     * "transaction_type amount card_number timestamp"
     * for which there is a constructor Transaction(String params)
     */
    public ArrayList<String> getTransactions(long cardNumber) throws SQLException {
        ArrayList<String> result = new ArrayList<>();

        try (Connection conn = ds.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from transactions where fk_account_id = ?");
            stmt.setLong(1, getPrimaryKey(cardNumber));
            ResultSet results = stmt.executeQuery();

            if (!results.next()) {
                // If account has no transactions, return an empty ArrayList
                return result;
            }

            do {
                StringBuilder sb = new StringBuilder();

                sb.append(results.getString("transaction_type")).append(" ");

                sb.append(results.getDouble("amount")).append(" ");

                sb.append(cardNumber).append(" ");

                LocalDateTime timestamp = results.getObject("timestamp", LocalDateTime.class);
                sb.append(timestamp);

                result.add(sb.toString());
            } while (results.next());
        }

        return result;
    }

    public boolean isAdmin(long cardNumber) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from accounts where card_num = ?");
            stmt.setLong(1, cardNumber);
            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                throw new IllegalArgumentException("Card number " + cardNumber + " is not valid.");
            }
            return result.getBoolean("is_admin");
        }
    }

}
