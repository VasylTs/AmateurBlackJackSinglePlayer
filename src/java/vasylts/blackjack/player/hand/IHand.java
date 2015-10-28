/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.player.hand;

import java.util.Collections;
import java.util.List;
import vasylts.blackjack.deck.card.ICard;

/**
 *
 * @author VasylcTS
 */
public interface IHand {
    public void addCard(ICard card);
    public List<ICard> getCardList();
    public int getScore();
    public boolean isStand(); 
    public void setStand(); 
    default public boolean isBlackjack() {
        // Blackjack hand should have two cards and score = 21
        return getCardList().size() == 2 && getScore() == 21;
    } 
    default public boolean isBusted() {
        int blakcjackMaxWinScore = 21;
        if (getScore() > blakcjackMaxWinScore) {
            setStand();
            return true;
        }
        return false;
    }
    default public void sortByValue(List<ICard> cards) {
        Collections.sort(cards, (card1, card2) -> {
            return ((ICard)card1).getCardValue().compareTo(((ICard)card2).getCardValue());
        });
    }
    
}
