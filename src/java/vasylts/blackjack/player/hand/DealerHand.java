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
public class DealerHand implements IHand {

    public final static int dealerPlayScore = 17;
    private final List<ICard> notHiddenCards;
    private ICard hiddenCard;
    private boolean stand;

    public DealerHand() {
        notHiddenCards = new ArrayList<>();
        stand = false;
    }

    public void setHiddenCard(ICard card) {
        if (hiddenCard == null && notHiddenCards.isEmpty()) {
            hiddenCard = card;
        } else {
            throw new IllegalStateException("You can`t set hidden card when dealer already have not hidden cards or another hidden card.");
        }
    }

    public void openHiddenCard() {
        addCard(hiddenCard);
        hiddenCard = null;
    }

    @Override
    public void addCard(ICard card) {
        if (card != null) {
            notHiddenCards.add(card);
        }
    }

    @Override
    public List getCardList() {
        sortByValue(notHiddenCards);
        return Collections.unmodifiableList(notHiddenCards);
    }

    @Override
    public int getScore() {
        final int MIN_SCORE_TO_CALCULATE_NORMAL_ACE = 10;
        int score = 0;
        int acesInHand = 0;

        // calc score without ACEs 
        for (ICard card : notHiddenCards) {
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
        for (ICard card : notHiddenCards) {
            sb.append(card.toString());
        }
        sb.append("]");
        return sb.toString();
    }
}
