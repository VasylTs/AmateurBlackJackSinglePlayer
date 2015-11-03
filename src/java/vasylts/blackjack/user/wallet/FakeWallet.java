/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.user.wallet;

import vasylts.blackjack.logger.IWalletLogger;
import vasylts.blackjack.logger.NullLogger;

/**
 * This is fake realization of user`s wallet. It does not making any connections
 * to DB. It created just for tests
 *
 * @author VasylcTS
 */
public class FakeWallet implements IWallet {

    private IWalletLogger logger;
    
    private double balance = 200.00d;

    public FakeWallet() {
        logger = new NullLogger();
    }
    
    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public double withdrawMoney(double amount) {
        double change = Math.abs(amount);
        balance -= change;
        getWalletLogger().logChangingBalance(0, -change, "Taking money from wallet.");
        return balance;
    }

    @Override
    public double addFunds(double amount) {
        double change = Math.abs(amount);
        balance += change;
        getWalletLogger().logChangingBalance(0, change, "Adding money to wallet.");
        return balance;
    }

    /**
     * @return the logger
     */
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
