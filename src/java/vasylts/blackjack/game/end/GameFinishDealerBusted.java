/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.game.end;

import java.util.Collection;
import vasylts.blackjack.logger.EnumLogAction;
import vasylts.blackjack.logger.IActionLogger;
import vasylts.blackjack.player.hand.DealerHand;
import vasylts.blackjack.player.IPlayer;

/**
 * This class process game end, when dealer busted his hand. Other players can
 * get status "win" if they have not been busted or "lose" otherwise. Players
 * can not get status "draw".
 * <p>
 * @author VasylcTS
 */
public class GameFinishDealerBusted implements IGameFinishWorker {

    private IActionLogger logger;
    
    @Override
    public void givePrizesToWinners(DealerHand dealerHand, Collection<IPlayer> players, IActionLogger logger) {
        if (!dealerHand.isStand()) {
            throw new IllegalStateException("Can not give prize to winner before dealer called action \"STAND\"");
        }
        this.logger = logger;
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
        logger.logPlayerAction(true, player.getId(), EnumLogAction.USER_FUNDS, "Giving prize: " + prize + " to player: " + player.getId() + ". User id: " + player.getUserId());
    }

    private void givePrizePlayerLose(IPlayer player) {
        // we already received our money he he he
        logger.logPlayerAction(true, player.getId(), EnumLogAction.USER_FUNDS, "Taking bet from table(no changing to player`s balance): " + player.getBet() + " to player: " + player.getId() + ". User id: " + player.getUserId());
    }

}
