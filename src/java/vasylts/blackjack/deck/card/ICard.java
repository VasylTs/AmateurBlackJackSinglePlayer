/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.deck.card;

/**
 *
 * @author VasylcTS
 */
public interface ICard {
    /**
     * Returns {@link EnumCardSuit} suit type of this card
     * @return suit type of this card
     */
    public EnumCardSuit getCardSuit();
    
    /**
     * Returns {@link EnumCardValue} rank type of this card
     * @return rank type of this card
     */
    public EnumCardValue getCardValue();
    
    /**
     * Checks if card rank is equal to this card
     * @param anotherCard Other card object to be compared with
     * @return {@code true} if card ranks is equal, {@code false} otherwise
     */
    public boolean equalsRank(ICard anotherCard);
}
