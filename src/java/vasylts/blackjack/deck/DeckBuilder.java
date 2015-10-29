/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.deck;

import java.util.ArrayList;
import java.util.List;
import vasylts.blackjack.deck.card.CardFactory;
import vasylts.blackjack.deck.card.EnumCardSuit;
import vasylts.blackjack.deck.card.EnumCardValue;
import vasylts.blackjack.deck.card.ICard;

/**
 *
 * @author VasylcTS
 */
public class DeckBuilder {

    /**
     * Creates a new instance of {@link StandartDeck} with all possible cards
     * defined in {@link EnumCardValue}, {@link EnumCardSuit}
     * <p>
     * @return instance of {@link StandartDeck}
     */
    static public StandartDeck buildStandartDeck() {
        List<ICard> cards = new ArrayList<>();
        for (EnumCardValue value : EnumCardValue.values()) {
            for (EnumCardSuit suit : EnumCardSuit.values()) {
                cards.add(CardFactory.getCard(suit, value));
            }
        }
        return new StandartDeck(cards);
    }

    /**
     * Creates a new instance of {@link StandartDeck} with all possible cards
     * defined in {@link EnumCardValue}, {@link EnumCardSuit} and randomly
     * shuffling it.
     * <p>
     * @return instance of {@link StandartDeck}
     */
    static public StandartDeck buildStandartShuffledDeck() {
        StandartDeck deck = buildStandartDeck();
        deck.shuffleDeck();
        return deck;
    }

}
