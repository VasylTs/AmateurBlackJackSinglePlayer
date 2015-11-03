/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.player.hand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import vasylts.blackjack.deck.card.EnumCardValue;
import vasylts.blackjack.deck.card.ICard;

/**
 *
 * @author VasylcTS
 */
public class PlayerHand implements IHand {

    private final List<ICard> cards;
    private boolean stand; 
    
    public PlayerHand() {
        cards = new ArrayList<>();
        stand = false;
    }

    @Override
    public void addCard(ICard card) {
        if (card != null) {
            cards.add(card); 
        }
    }

    
    @Override
    public List getCardList() {
        sortByValue(cards);
        return Collections.unmodifiableList(cards);
    }

    @Override
    public int getScore() {
        final int MIN_SCORE_TO_CALCULATE_NORMAL_ACE = 10;
        int score = 0;
        int acesInHand = 0;

        // calc score without ACEs 
        for (ICard card : cards) {
            if (card.getCardValue() == EnumCardValue.ACE) {
                ++acesInHand;
            } else {
                score += card.getCardValue().getScoreValue();
            }
        }

        // add ACEs to final score
        for (int i = 0; i < acesInHand; i++) {
            if (score <= MIN_SCORE_TO_CALCULATE_NORMAL_ACE) {
                score += EnumCardValue.ACE.getScoreValue();
            } else {
                score += EnumCardValue.getAceSecondaryScoreValue();
            }
        }

        return score;
    }

    @Override
    public boolean isStand() {
        return stand;
    }

    @Override
    public void setStand() {
        stand = true;
    }    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[busted:").append(isBusted()).append(", score:").append(getScore()).append(", ");
        for (ICard card : cards) {
            sb.append(card.toString());
        }
        sb.append("]");
        return sb.toString();
    }
}
