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
public class CardFactory {

    static public ICard getCard(EnumCardSuit suit, EnumCardValue value) {
        ICard card = new SimpleCard(suit, value);
        return card;
    }
}
