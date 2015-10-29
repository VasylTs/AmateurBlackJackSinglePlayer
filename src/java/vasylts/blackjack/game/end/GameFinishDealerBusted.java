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
 * This class process game end, when dealer busted his hand.
 * Other players can get status "win" if they have not been busted or "lose"
 * otherwise. Players can not get status "draw".
 * @author VasylcTS
 */
public class GameFinishDealerBusted implements IGameFinishWorker {

    @Override
    public void givePrizesToWinners(DealerHand dealerHand, Collection<IPlayer> players) {
        if (!dealerHand.isStand()) {
            throw new IllegalStateException("Can not give prize to winner before dealer called action \"STAND\"");
        }
        players.stream().forEach((pl) -> {
                if (pl.getHand().isBusted()) {
                    givePrizePlayerLose(pl);
                } else {
                    givePrizePlayerWin(pl);
                }
            });
    }
    
    private void givePrizePlayerWin(IPlayer player) {
        double bet = player.getBet();
        double prize = (player.getHand().isBlackjack() ? bet * 1.5 : bet) + bet;
        player.getWallet().addFunds(prize);
    }

    private void givePrizePlayerLose(IPlayer player) {
        // we already received our money he he he
    }
    
}
