/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.user.wallet;

/**
 * This interface describes standard methods wich should have instance of user
 * wallet
 *
 * @author VasylcTS
 */
public interface IWallet {

    /**
     * Returns current balance in this wallet
     *
     * @return balance at this time
     */
    public double getBalance();

    /**
     * Take money from wallet. Implementations should ignore sign of parameter
     * and in both situations(-amount/+amount) should decrease balance at amount
     *
     * @param amount amout to take from wallet
     * @return current amount after taking money
     */
    public double withdrawMoney(double amount);

    /**
     * Put money to wallet. mplementations should ignore sign of parameter and
     * in both situations(-amount/+amount) should increase balance at amount
     *
     * @param amount amount to put to wallet
     * @return current amount after put
     */
    public double addFunds(double amount);
}
