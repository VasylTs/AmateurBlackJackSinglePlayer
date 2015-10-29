/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.game.end;

import java.util.Collection;
import java.util.Map;
import vasylts.blackjack.player.hand.DealerHand;
import vasylts.blackjack.player.IPlayer;

/**
 * This class process game end, when dealer has score less or equals then 21(but
 * not blackjack). Other players can get status "win" if they have blackjack in
 * their hands or "draw" or "lose" if they busted or their score is less than
 * the dealer score. 
 * <p>
 * @author VasylcTS
 */
public class GameFinishWorker implements IGameFinishWorker {

    @Override
    public void givePrizesToWinners(DealerHand dealerHand, Collection<IPlayer> players) {
        if (!dealerHand.isStand()) {
            throw new IllegalStateException("Can not give prize to winner before dealer called action \"STAND\"");
        }
        int dealerScore = dealerHand.getScore();
        players.stream().forEach((pl) -> {
            if (pl.getHand().getScore() > dealerScore || pl.getHand().isBlackjack()) {
                givePrizePlayerWin(pl);
            } else if (pl.getHand().getScore() == dealerScore) {
                givePrizePlayerDraw(pl);
            } else {
                givePrizePlayerLose(pl);
            }
        });
    }

    private void givePrizePlayerWin(IPlayer player) {
        double bet = player.getBet();
        double prize = (player.getHand().isBlackjack() ? bet * 1.5 : bet) + bet;
        player.getWallet().addFunds(prize);
    }

    private void givePrizePlayerDraw(IPlayer player) {
        player.getWallet().addFunds(player.getBet());
    }

    private void givePrizePlayerLose(IPlayer player) {
        // we already received our money he he he
    }
}
