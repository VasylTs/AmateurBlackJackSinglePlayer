package vasylts.blackjack.deck;

import java.util.List;
import java.util.Random;
import vasylts.blackjack.deck.card.ICard;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
/**
 *
 * @author VasylcTS
 */
public interface IDeck {

    /**
     * Get next card from deck
     * <p>
     * @return instance of {@link ICard}
     */
    public ICard getNextCard();

    /**
     * Get all cards in deck(even that, that was taken from it)
     * <p>
     * @return all cards that was in deck when it was created
     */
    public List getCards();

    /**
     * Reset iterator in this deck, so next call of getNextCard() will return
     * first card in deck
     */
    public void resetDeck();

    /**
     * Randomly sorts cards in deck
     */
    public void shuffleDeck();

    public void shuffleDeck(Random rand);
}
