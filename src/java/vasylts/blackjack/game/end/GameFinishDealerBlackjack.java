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
 * This class process game end, when dealer has a blackjack in his hand.
 * Other players can get status "draw" if they also have blackjack or "lose"
 * otherwise. Players can not win in this situation.
 * <p>
 * @author VasylcTS
 */
public class GameFinishDealerBlackjack implements IGameFinishWorker {

    @Override
    public void givePrizesToWinners(DealerHand dealerHand, Collection<IPlayer> players) {
        if (!dealerHand.isStand()) {
            throw new IllegalStateException("Can not give prize to winner before dealer called action \"STAND\"");
        }
        players.stream().forEach((pl) -> {
            if (pl.getHand().isBlackjack()) {
                givePrizePlayerDraw(pl);
            } else {
                givePrizePlayerLose(pl);
            }
        });
    }

    private void givePrizePlayerDraw(IPlayer player) {
        player.getWallet().addFunds(player.getBet());
    }

    private void givePrizePlayerLose(IPlayer player) {
        // we already received our money he he he
    }

}
