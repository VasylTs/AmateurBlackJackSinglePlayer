/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.game.end;

import java.util.Collection;
import vasylts.blackjack.logger.IActionLogger;
import vasylts.blackjack.player.hand.DealerHand;
import vasylts.blackjack.player.IPlayer;

/**
 * Realization of this interface should check dealer and players hands and find
 * a winners.
 * <p>
 * @author VasylcTS
 * @see GameFinishWorker
 * @see GameFinishDealerBlackjack
 * @see GameFinishDealerBusted
 */
public interface IGameFinishWorker {

    /**
     * Take bet of player if he loosed, return his bet if he`s score equals
     * dealer`s score, and give a prize if he won
     * <p>
     * @param dealerHand 
     * @param players
     */
    public void givePrizesToWinners(DealerHand dealerHand, Collection<IPlayer> players, IActionLogger logger);
}
