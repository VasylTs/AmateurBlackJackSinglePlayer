/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.game.end;

import java.util.Collection;
import java.util.Map;
import vasylts.blackjack.logger.EnumLogAction;
import vasylts.blackjack.logger.IActionLogger;
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

    private IActionLogger logger;
    
    @Override
    public void givePrizesToWinners(DealerHand dealerHand, Collection<IPlayer> players, IActionLogger logger) {
        if (!dealerHand.isStand()) {
            throw new IllegalStateException("Can not give prize to winner before dealer called action \"STAND\"");
        }
        this.logger = logger;
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
        logger.logPlayerAction(true, player.getId(), EnumLogAction.USER_FUNDS, "Giving prize: " + prize + " to player: " + player.getId() + ". User id: " + player.getUserId());
    }

    private void givePrizePlayerDraw(IPlayer player) {
        player.getWallet().addFunds(player.getBet());
        logger.logPlayerAction(true, player.getId(), EnumLogAction.USER_FUNDS, "Returning bet: " + player.getBet() + " to player: " + player.getId() + ". User id: " + player.getUserId());
    }

    private void givePrizePlayerLose(IPlayer player) {
        // we already received our money he he he
        logger.logPlayerAction(true, player.getId(), EnumLogAction.USER_FUNDS, "Taking bet from table(no changing to player`s balance): " + player.getBet() + " to player: " + player.getId() + ". User id: " + player.getUserId());
    }
}
