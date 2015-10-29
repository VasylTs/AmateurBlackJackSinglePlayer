/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.user.wallet;

/**
 * This is fake realization of user`s wallet. It does not making any connections
 * to DB. It created just for tests
 *
 * @author VasylcTS
 */
public class FakeWallet implements IWallet {

    private double balance = 200.00d;

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public double withdrawMoney(double amount) {
        balance -= Math.abs(amount);
        return balance;
    }

    @Override
    public double addFunds(double amount) {
        balance += Math.abs(amount);
        return balance;
    }

}
