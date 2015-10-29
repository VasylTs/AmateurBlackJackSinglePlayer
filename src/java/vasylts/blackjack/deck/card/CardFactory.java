/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.deck.card;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

/**
 *
 * @author VasylcTS
 */
public class CardFactory {

    private static final Set<ICard> cards;
    
    static {
        int maxPossibleCards = EnumCardSuit.values().length * EnumCardValue.values().length;
        cards = new HashSet<>(maxPossibleCards);
    }
    
    /**
     * Return an instance of {@code SimpleCard} 
     * @param suit Suit of card
     * @param value card rank   
     * @return Instance of {@code SimpleCard} 
     * @see vasylts.blackjack.deck.card.SimpleCard
     */
    static public ICard getCard(EnumCardSuit suit, EnumCardValue value) {
        Predicate<ICard> sameCard = (ICard card) -> {
            return card.getCardSuit() == suit && card.getCardValue() == value;
        };
        Optional<ICard> optCard = cards.stream().filter(sameCard).findFirst();
        
        ICard card;
        if (optCard.isPresent()) {
            card = optCard.get();
        } else {
            card = new SimpleCard(suit, value);
            cards.add(card);
        }
        return card;
    }
}
