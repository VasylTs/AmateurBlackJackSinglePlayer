/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.deck.card;

import java.util.Objects;

/**
 *
 * @author VasylcTS
 */
public class SimpleCard implements ICard {
    private final EnumCardSuit cardSuit;
    private final EnumCardValue cardValue;


    /*default*/SimpleCard(EnumCardSuit suit, EnumCardValue value)
    {
        this.cardSuit = suit;
        this.cardValue = value;
    }
    
    @Override
    public boolean equalsRank(ICard anotherCard) {
        if (anotherCard != null && anotherCard.getCardValue() == this.getCardValue()) {
            return true;
        } else { 
            return false;
        }
    }
    
    @Override 
    public boolean equals(Object anotherCard) {
        if (anotherCard == null) { return false; }
        if (anotherCard instanceof ICard) {
            ICard otherCard = (ICard)anotherCard;
            if (this.getCardValue() == otherCard.getCardValue()
                    && this.getCardSuit() == otherCard.getCardSuit()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.cardSuit);
        hash = 29 * hash + Objects.hashCode(this.cardValue);
        return hash;
    }
    
    
    @Override
    public EnumCardSuit getCardSuit() {
        return cardSuit;
    }

    @Override
    public EnumCardValue getCardValue() {
        return cardValue;
    }



}
