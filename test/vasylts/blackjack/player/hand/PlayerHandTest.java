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
import vasylts.blackjack.deck.card.CardFactory;
import vasylts.blackjack.deck.card.EnumCardSuit;
import vasylts.blackjack.deck.card.EnumCardValue;
import vasylts.blackjack.deck.card.ICard;

/**
 *
 * @author VasylcTS
 */
public class PlayerHandTest {
    
    private ICard cardTwo;
    private ICard cardTen;
    private ICard cardAce;
    

    @Before
    public void setUp() {
        cardTwo = CardFactory.getCard(EnumCardSuit.CLUBS, EnumCardValue.TWO);
        cardTen = CardFactory.getCard(EnumCardSuit.CLUBS, EnumCardValue.TEN);
        cardAce = CardFactory.getCard(EnumCardSuit.CLUBS, EnumCardValue.ACE);
    }
    public PlayerHandTest() {
    }

    
    @Test
    public void testPlayerBlackjack() {
        
        PlayerHand hand = new PlayerHand();
        hand.addCard(cardAce);
        assertFalse(hand.isBlackjack());
        
        hand.addCard(cardTen);
        assertTrue(hand.isBlackjack());
    }
    
    @Test
    public void testNullCards() {
        PlayerHand hand = new PlayerHand();
        hand.addCard(null);
        hand.addCard(null);
        hand.addCard(null);
        hand.addCard(null);
        
        assertEquals(false, hand.isBlackjack());
        assertEquals(0, hand.getScore());
    }
    
    
    @Test(expected = UnsupportedOperationException.class)
    public void testChangingCardList() {
        PlayerHand hand = new PlayerHand();
        hand.addCard(cardTen);
        hand.addCard(cardTen);
        hand.addCard(cardTen);
        
        assertEquals(cardTen.getCardValue().getScoreValue() * 3, hand.getScore());
        // let`s cheat and make our score = 20. Should throw UnsupportedOperationException
        hand.getCardList().remove(cardTen);
    }
    
    /**
     * Test of addCard method, of class PlayerHand.
     */
    @Test
    public void testAddCard() {
        PlayerHand instance = new PlayerHand();
        instance.addCard(null);
        assertEquals(0, instance.getScore());
        
        instance.addCard(cardAce);
        assertEquals(cardAce.getCardValue().getScoreValue(), instance.getScore());
    }

    /**
     * Test of getCardList method, of class PlayerHand.
     */
    @Test
    public void testGetCardList() {
        PlayerHand instance = new PlayerHand();
        List expResult = Collections.EMPTY_LIST;
        List result = instance.getCardList();
        assertEquals(expResult, result);
    }

    /**
     * Test of getScore method, of class PlayerHand.
     */
    @Test
    public void testGetScore() {
        PlayerHand instance = new PlayerHand();
        instance.addCard(cardTen);
        instance.addCard(cardAce);
        instance.addCard(cardAce);
        instance.addCard(cardTwo);
        int expResult = cardTen.getCardValue().getScoreValue() + cardTwo.getCardValue().getScoreValue() + 1 + 1;
        int result = instance.getScore();
        assertEquals(expResult, result);
    }

    /**
     * Test of isStand method, of class PlayerHand.
     */
    @Test
    public void testIsStand() {
        PlayerHand instance = new PlayerHand();
        boolean expResult = false;
        boolean result = instance.isStand();
        assertEquals(expResult, result);
        
        instance.setStand();
        expResult = true;
        result = instance.isStand();
        assertEquals(expResult, result);
    }

    
}
