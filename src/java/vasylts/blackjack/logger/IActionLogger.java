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
public interface IActionLogger {
    public void setGameId(long id);
    public void logGameAction(boolean isOk, EnumLogAction action, String desc);
    public void logPlayerAction(boolean isOk, Long playerId, EnumLogAction action, String desc);
}
