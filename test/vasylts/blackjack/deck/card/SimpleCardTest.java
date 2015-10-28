/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.deck.card;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author VasylcTS
 */
public class SimpleCardTest {
    
    public SimpleCardTest() {
    }

    @Test
    public void testCardEquals() {
        ICard c1 = CardFactory.getCard(EnumCardSuit.CLUBS, EnumCardValue.TWO);
        ICard c2 = CardFactory.getCard(EnumCardSuit.CLUBS, EnumCardValue.TWO);
        assertEquals(c1, c2);
    }
    
    @Test
    public void testCardEqualsRanks() {
        ICard c1 = CardFactory.getCard(EnumCardSuit.CLUBS, EnumCardValue.TWO);
        ICard c2 = CardFactory.getCard(EnumCardSuit.DIAMONDS, EnumCardValue.TWO);
        assertTrue(c1.equalsRank(c2));
    }
    
    @Test
    public void testCardNotEquals() {
        ICard c1 = CardFactory.getCard(EnumCardSuit.SPADES, EnumCardValue.TWO);
        ICard c2 = CardFactory.getCard(EnumCardSuit.CLUBS, EnumCardValue.TWO);
        assertNotSame(c1, c2);
    }
    
    @Test
    public void testCardNotEqualsRanks() {
        ICard c1 = CardFactory.getCard(EnumCardSuit.CLUBS, EnumCardValue.TWO);
        ICard c2 = CardFactory.getCard(EnumCardSuit.CLUBS, EnumCardValue.TEN);
        assertFalse(c1.equalsRank(c2));
    }
    
}
