package vasylts.blackjack.deck;

import java.util.List;
import java.util.Random;
import vasylts.blackjack.deck.card.ICard;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author VasylcTS
 */
public interface IDeck {
    public ICard getNextCard();
    public List getCards();
    public void resetDeck();
    public void shuffleDeck();
    public void shuffleDeck(Random rand);
}
