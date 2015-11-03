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
public class FakeActionLogger implements IActionLogger {
    private static Logger log = Logger.getLogger(FakeActionLogger.class.getSimpleName());
    private long gameId;
    
    
    @Override
    public void logGameAction(boolean isOk, EnumLogAction action, String desc) {
        String msgStr = "Fake log. Log action: {0}. Log description: {1}";
        if (isOk) {
            log.log(Level.INFO, msgStr, new Object[]{action.toString(), desc});
        }
        else {
            log.log(Level.WARNING, msgStr, new Object[]{action, desc});
        }
    }
// id, game_id, work_state, action, description, player_id
    @Override
    public void logPlayerAction(boolean isOk, Long playerId, EnumLogAction action, String desc) {
        String msgStr = "Fake log. Player id: {0}. Log action: {1}. Log description: {2}";
        if (isOk) {
            log.log(Level.INFO, msgStr, new Object[]{playerId, action, desc});
        }
        else {
            log.log(Level.WARNING, msgStr, new Object[]{playerId, action, desc});
        }
    }

    @Override
    public void setGameId(long id) {
        gameId = id;
    }
}
