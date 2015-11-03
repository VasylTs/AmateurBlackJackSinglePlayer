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
public class NullLogger implements IActionLogger, IWalletLogger {

    @Override
    public void setGameId(long id) {
        
    }

    @Override
    public void logGameAction(boolean isOk, EnumLogAction action, String desc) {
        
    }

    @Override
    public void logPlayerAction(boolean isOk, Long playerId, EnumLogAction action, String desc) {
        
    }

    @Override
    public void logChangingBalance(long walletId, double change, String shortDescription) {
        
    }
    
}
