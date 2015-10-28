/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.player.wallet;

/**
 *
 * @author VasylcTS
 */
public interface IWallet {
    public double getBalance();
    public double withdrawMoney(double amount);
    public double addFunds(double amount);
}
