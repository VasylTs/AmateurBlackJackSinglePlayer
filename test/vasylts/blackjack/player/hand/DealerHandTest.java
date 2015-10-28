/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.player.hand;

import java.util.Collections;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import vasylts.blackjack.deck.DeckBuilder;
import vasylts.blackjack.deck.card.CardFactory;
import vasylts.blackjack.deck.card.EnumCardSuit;
import vasylts.blackjack.deck.card.EnumCardValue;
import vasylts.blackjack.deck.card.ICard;

/**
 *
 * @author VasylcTS
 */
public class DealerHandTest {
    
    private ICard cardTwo;
    private ICard cardTen;
    private ICard cardAce;
    
    public DealerHandTest() {
    }

    @Before
    public void setUp() {
        cardTwo = CardFactory.getCard(EnumCardSuit.CLUBS, EnumCardValue.TWO);
        cardTen = CardFactory.getCard(EnumCardSuit.CLUBS, EnumCardValue.TEN);
        cardAce = CardFactory.getCard(EnumCardSuit.CLUBS, EnumCardValue.ACE);
    }

    @Test
    public void testDealerBlackjack() {
        
        DealerHand hand = new DealerHand();
        hand.setHiddenCard(cardAce);
        hand.addCard(cardTen);
        assertFalse(hand.isBlackjack());
        
        hand.openHiddenCard();
        assertTrue(hand.isBlackjack());
    }
    
    @Test
    public void testNullCards() {
        DealerHand hand = new DealerHand();
        hand.addCard(null);
        hand.addCard(null);
        hand.addCard(null);
        hand.addCard(null);
        
        assertEquals(false, hand.isBlackjack());
        assertEquals(0, hand.getScore());
        hand.openHiddenCard();
        assertEquals(false, hand.isBlackjack());
        assertEquals(0, hand.getScore());
    }
    
    
    @Test(expected = UnsupportedOperationException.class)
    public void testChangingCardList() {
        DealerHand hand = new DealerHand();
        hand.addCard(cardTen);
        hand.addCard(cardTen);
        hand.addCard(cardTen);
        
        assertEquals(cardTen.getCardValue().getScoreValue() * 3, hand.getScore());
        // let`s cheat and make our score = 20. Should throw UnsupportedOperationException
        hand.getCardList().remove(cardTen);
    }
    
    
    /**
     * Test of setHiddenCard method, of class DealerHand.
     */
    @Test(expected = IllegalStateException.class)
    public void testSetHiddenCard() {
        DealerHand instance = new DealerHand();
        instance.setHiddenCard(cardTwo);
        
        instance.setHiddenCard(cardTen); //throw exception
        fail("Second hidden card must not be setted");
    }

    /**
     * Test of openHiddenCard method, of class DealerHand.
     */
    @Test
    public void testOpenHiddenCard() {
        DealerHand instance = new DealerHand();
        
        instance.setHiddenCard(cardTwo);
        assertEquals(0, instance.getScore());
        
        instance.openHiddenCard();
        assertEquals(cardTwo.getCardValue().getScoreValue() , instance.getScore());
    }

    /**
     * Test of addCard method, of class DealerHand.
     */
    @Test
    public void testAddCard() {
        DealerHand instance = new DealerHand();
        instance.addCard(null);
        assertEquals(0, instance.getScore());
        
        instance.addCard(cardAce);
        assertEquals(cardAce.getCardValue().getScoreValue(), instance.getScore());
    }

    /**
     * Test of getCardList method, of class DealerHand.
     */
    @Test
    public void testGetCardList() {
        DealerHand instance = new DealerHand();
        List expResult = Collections.EMPTY_LIST;
        List result = instance.getCardList();
        assertEquals(expResult, result);
    }

    /**
     * Test of getScore method, of class DealerHand.
     */
    @Test
    public void testGetScore() {
        DealerHand instance = new DealerHand();
        instance.setHiddenCard(cardAce);
        instance.addCard(cardTen);
        int expResult = cardTen.getCardValue().getScoreValue();
        int result = instance.getScore();
        assertEquals(expResult, result);

        instance.openHiddenCard();
        expResult = cardAce.getCardValue().getScoreValue() + cardTen.getCardValue().getScoreValue();
        result = instance.getScore();
        assertEquals(expResult, result);
    }

    /**
     * Test of isStand method, of class DealerHand.
     */
    @Test
    public void testIsStand() {
        DealerHand instance = new DealerHand();
        boolean expResult = false;
        boolean result = instance.isStand();
        assertEquals(expResult, result);
        
        instance.setStand();
        expResult = true;
        result = instance.isStand();
        assertEquals(expResult, result);
    }
    
}
