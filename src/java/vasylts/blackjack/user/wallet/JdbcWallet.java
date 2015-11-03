/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.user.wallet;

import java.sql.SQLException;
import vasylts.blackjack.logger.IWalletLogger;
import vasylts.blackjack.user.databaseworker.jdbcworker.DatabaseWorkerWalletJdbc;

/**
 *
 * @author VasylcTS
 */
public class JdbcWallet implements IWallet {

    private IWalletLogger logger;
    private DatabaseWorkerWalletJdbc dbWorker;
    private final long walletId;

    /**
     * This constructor you need to use if you have already created wallet in
     * Database and you have it`s id. For creating fully new wallet, use static
     * method createNewWallet()
     * <p>
     * @param walletId id of wallet in database
     */
    public JdbcWallet(long walletId) {
        this.walletId = walletId;
        dbWorker = new DatabaseWorkerWalletJdbc();
    }

    /**
     * Creates fully new wallet and register it in database
     * <p>
     * @return instance of wallet
     * <p>
     * @throws SQLException
     */
    static public JdbcWallet createNewWallet() throws SQLException {
        DatabaseWorkerWalletJdbc dbWorker = new DatabaseWorkerWalletJdbc();
        Long walletId = dbWorker.createNewWallet();
        JdbcWallet wallet = new JdbcWallet(walletId);
        return wallet;
    }

    /**
     * Returns current balance in this wallet
     * <p>
     * @return balance at this time
     */
    @Override
    public double getBalance() {
        try {
            return dbWorker.getBalance(walletId);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Take money from wallet. It ignores a sign of parameter and in both
     * situations(-amount/+amount) will decrease balance at amount
     * <p>
     * @param amount amout to take from wallet
     * <p>
     * @return current amount after taking money
     */
    @Override
    public double withdrawMoney(double amount) {
        try {
            double change = Math.abs(amount);
            dbWorker.changeBalance(walletId, -amount);
            getWalletLogger().logChangingBalance(walletId, change, "Taking money from wallet.");
            return getBalance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Put money to wallet. It ignores sign of parameter and in both
     * situations(-amount/+amount) will increase balance at amount
     * <p>
     * @param amount amount to put to wallet
     * <p>
     * @return current amount after put
     */
    @Override
    public double addFunds(double amount) {
        try {
            double change = Math.abs(amount);
            dbWorker.changeBalance(walletId, amount);
            getWalletLogger().logChangingBalance(walletId, change, "Adding money to wallet.");
            return getBalance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get id of this wallet in database
     * <p>
     * @return id of this wallet
     */
    public long getWalletId() {
        return walletId;
    }

    @Override
    public IWalletLogger getWalletLogger() {
        if (logger == null) {
            throw new NullPointerException("Wallet logger is not initialized!");
        }
        return logger;
    }

    @Override
    public void setWalletLogger(IWalletLogger logger) {
        this.logger = logger;
    }

}
