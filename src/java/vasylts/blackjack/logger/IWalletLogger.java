/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.logger;

/**
 *
 * @author VasylcTS
 */
public interface IWalletLogger {
    public void logChangingBalance(long walletId, double change, String shortDescription);
}
