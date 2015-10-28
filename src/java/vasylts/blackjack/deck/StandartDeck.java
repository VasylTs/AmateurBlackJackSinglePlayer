/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.deck;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import vasylts.blackjack.deck.card.ICard;

/**
 *
 * @author VasylcTS
 */
public class StandartDeck implements IDeck {
    final private List<ICard> cards;
    private int index = 0;
    
    public StandartDeck()
    {
        cards = DeckBuilder.buildStandartDeck().getCards();
    }
    
    public StandartDeck(List<ICard> cardDeck) {
        if (cardDeck == null) {
            throw new NullPointerException("Card deck can not be null");
        }
        cards = cardDeck;
    }
    
    @Override
    public ICard getNextCard() {
        if (index > cards.size() - 1)
            throw new NoCardInDeckException();
        else 
            return cards.get(index++);
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
        index = 0;
    }
    
}
