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
    public EnumCardSuit getCardSuit();
    public EnumCardValue getCardValue();
    public boolean equalsRank(ICard anotherCard);
}
