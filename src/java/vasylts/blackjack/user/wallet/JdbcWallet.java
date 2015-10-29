/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.user.wallet;

import java.sql.SQLException;
import vasylts.blackjack.user.databaseworker.jdbcworker.DatabaseWorkerWalletJdbc;

/**
 *
 * @author VasylcTS
 */
public class JdbcWallet implements IWallet {

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
     * @return instance of wallet
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
     *
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
     * Take money from wallet. It ignores a sign of parameter
     * and in both situations(-amount/+amount) will decrease balance at amount
     *
     * @param amount amout to take from wallet
     * @return current amount after taking money
     */
    @Override
    public double withdrawMoney(double amount) {
        try {
            if (amount < 0) {
                dbWorker.changeBalance(walletId, amount);
            } else if (amount > 0) {
                dbWorker.changeBalance(walletId, amount * -1);
            }
            return getBalance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Put money to wallet. It ignores sign of parameter
     * and in both situations(-amount/+amount) will increase balance at amount
     * @param amount amount to put to wallet
     * @return current amount after put
     */
    @Override
    public double addFunds(double amount) {
        try {
            if (amount > 0) {
                dbWorker.changeBalance(walletId, amount);
            } else if (amount < 0) {
                dbWorker.changeBalance(walletId, amount * -1);
            }
            return getBalance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Get id of this wallet in database
     * @return id of this wallet
     */
    public long getWalletId() {
        return walletId;
    }

}

