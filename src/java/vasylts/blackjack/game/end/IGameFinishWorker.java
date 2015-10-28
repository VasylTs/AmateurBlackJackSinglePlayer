/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.game.end;

import java.util.Collection;
import vasylts.blackjack.player.hand.DealerHand;
import vasylts.blackjack.player.IPlayer;

/**
 *
 * @author VasylcTS
 */
public interface IGameFinishWorker {
    public void givePrizesToWinners(DealerHand dealerHand, Collection<IPlayer> players);
}
