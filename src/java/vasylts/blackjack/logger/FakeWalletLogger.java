/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.logger;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author VasylcTS
 */
public class FakeWalletLogger implements IWalletLogger {

    private static Logger log = Logger.getLogger(FakeActionLogger.class.getSimpleName());

    @Override
    public void logChangingBalance(long walletId, double change, String shortDescription) {
        log.log(Level.INFO, "Fake log. WalletId: {0}. Log change: {1}. Description: {2}", new Object[]{walletId, change, shortDescription});
    }

}
