/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.player;

import vasylts.blackjack.player.hand.IHand;
import vasylts.blackjack.player.wallet.IWallet;

/**
 *
 * @author VasylcTS
 */
public interface IPlayer {
    public Long getId();
    public Long getUserId();
    public IWallet getWallet();
    public IHand getHand();
    public void resetGame();
    public double getBet();
    public void setBet(double bet);
    public boolean isReadyToStart();
    public void setReadyToStart();
    public boolean isReadyToFinish();
    public void setReadyToFinish();
    public String getName();
}
