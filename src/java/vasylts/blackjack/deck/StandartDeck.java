/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.deck;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import vasylts.blackjack.deck.card.ICard;

/**
 *
 * @author VasylcTS
 */
public class StandartDeck implements IDeck {
    final private List<ICard> cards;
    private Iterator<ICard> cardIterator;
    
    public StandartDeck()
    {
        cards = DeckBuilder.buildStandartDeck().getCards();
        cardIterator = cards.iterator();
    }
    
    
    public StandartDeck(List<ICard> cardDeck) {
        if (cardDeck == null) {
            throw new NullPointerException("Card deck can not be null");
        }
        cards = cardDeck;
        cardIterator = cards.iterator();
    }
    
    /**
     * Get next card from deck
     * @return instance of {@link ICard}
     * @throws NoCardInDeckException if there is no more cards in deck
     */
    @Override
    public ICard getNextCard() {
        if (cardIterator.hasNext()) {
            return cardIterator.next();
        } else {
            throw new NoCardInDeckException();
        }            
    }

    @Override
    public void shuffleDeck() {
        Collections.shuffle(cards);
    }
    
    @Override
    public void shuffleDeck(Random rand) {
        Collections.shuffle(cards, rand);
    }

    @Override
    public List getCards() {
        return cards;
    }

    @Override
    public void resetDeck() {
        cardIterator = cards.stream().iterator();
    }

}
