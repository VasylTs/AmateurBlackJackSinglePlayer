/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.game;

/**
 *
 * @author VasylcTS
 */
public class Bet {
    private double bet;
    
    public Bet(double bet) {
        this.bet = bet;
    }

    /**
     * @return the bet
     */
    public double getBet() {
        return bet;
    }

    /**
     * @param bet the bet to set
     */
    public void setBet(double bet) {
        this.bet = bet;
    }
    
    public double addToBet(double addBet) {
        bet += addBet;
        return bet;
    }
}
