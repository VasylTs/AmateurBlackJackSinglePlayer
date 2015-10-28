/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.player;

import vasylts.blackjack.player.hand.IHand;
import vasylts.blackjack.player.hand.PlayerHand;
import vasylts.blackjack.player.wallet.IWallet;
import vasylts.blackjack.user.IUser;

/**
 *
 * @author VasylcTS
 */
public class SimplePlayer implements IPlayer {
    
    final private IUser user;
    final private Long id;
    private IHand playerHand;
    private double bet;
    private String playerName;
    private boolean readyToStart;
    private boolean readyToFinish;
    
    public SimplePlayer(Long id, IUser user, String name) {
        if (user != null) {
            this.user = user;
            this.playerHand = new PlayerHand();
            this.playerName = name;
            this.id = id;
        } else {
            throw new NullPointerException("IUser can not be null");
        }

    }
    
    public SimplePlayer(Long id, IUser user) {
        this.user = user;
        this.playerHand = new PlayerHand();
        this.playerName = user.getLogin();
        this.id = id;
    }
    
    @Override
    public IWallet getWallet() {
        return user.getWallet();
    }

    @Override
    public IHand getHand() {
        return playerHand;
    }

    @Override
    public String getName() {
        return playerName;
    }
    
    public void setName(String name) {
        this.playerName = name;
    }
    
    @Override 
    public String toString() {
        return this.playerName;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public double getBet() {
        return bet;
    }

    @Override
    public void setBet(double bet) {
        this.bet = bet;
    }

    @Override
    public void resetGame() {
        playerHand = new PlayerHand();
        bet = 0;
        readyToFinish = false;
        readyToStart = false;
    }

    @Override
    public boolean isReadyToStart() {
        return readyToStart;
    }

    @Override
    public void setReadyToStart() {
        this.readyToStart = true;
    }

    @Override
    public boolean isReadyToFinish() {
        return readyToFinish;
    }

    @Override
    public void setReadyToFinish() {
        readyToFinish = true;
    }

    @Override
    public Long getUserId() {
        return user.getId();
    }
}
