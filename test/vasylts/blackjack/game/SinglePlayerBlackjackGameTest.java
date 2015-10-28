/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import vasylts.blackjack.deck.DeckBuilder;
import vasylts.blackjack.deck.NoCardInDeckException;
import vasylts.blackjack.deck.StandartDeck;
import vasylts.blackjack.deck.card.CardFactory;
import vasylts.blackjack.deck.card.EnumCardSuit;
import vasylts.blackjack.deck.card.EnumCardValue;
import vasylts.blackjack.deck.card.ICard;
import vasylts.blackjack.jaxb.EnumGameState;
import vasylts.blackjack.player.NoSuchPlayerException;
import vasylts.blackjack.player.wallet.FakeWallet;
import vasylts.blackjack.user.IUser;
import vasylts.blackjack.user.SimpleUser;

/**
 *
 * @author VasylcTS
 */
public class SinglePlayerBlackjackGameTest {
    
    public SinglePlayerBlackjackGameTest() {
    }
    
    private SinglePlayerBlackjackGame standartGame;
    private IUser userTaras;
    private long playerTarasId;
    

    
    @Before
    public void setUp() {
        standartGame = new SinglePlayerBlackjackGame(DeckBuilder.buildStandartDeck(), true);
        userTaras = new SimpleUser(1l, "taras", "1", new FakeWallet());
        playerTarasId = standartGame.addUserToGame(userTaras);    
    }
    
    
   
    
    
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //************************** METHODS TESTS ********************************
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    
    /**
     * Test of getState method, of class SinglePlayerBlackjackGame.
     */
    @Test
    public void testGetState() {
        SinglePlayerBlackjackGame instance = standartGame;
        // First step
        EnumGameState expResult = EnumGameState.WAITING_BETS;
        EnumGameState result = instance.getState();
        assertEquals(expResult, result);
        
        // Second step
        standartGame.placeBet(userTaras.getId(), playerTarasId, 50d);
        standartGame.readyPlayerToStart(playerTarasId);
        expResult = EnumGameState.GAME_IN_PROCESS;
        result = instance.getState();
        assertEquals(expResult, result);
        
        // Third step
        standartGame.standPlayer(userTaras.getId(), playerTarasId);
        expResult = EnumGameState.GAME_FINISHED;
        result = instance.getState();
        assertEquals(expResult, result);
        
        // Fourth step
        standartGame.readyPlayerToEnd(playerTarasId);
        expResult = EnumGameState.WAITING_BETS;
        result = instance.getState();
        assertEquals(expResult, result);
    }

    /**
     * Test of placeBet method, of class SinglePlayerBlackjackGame.
     */
    @Test
    public void testPlaceBet() {
        double bet = 100d;
        double impossibleBet = 50000000000000d;
        //invailid user
        try {
            standartGame.placeBet(2132131, 51345345, bet);
            fail("You can`t place bet to not existing player!");
        } catch (NoSuchPlayerException e) {
            // it`s ok
        }        
        
        standartGame.placeBet(userTaras.getId(), playerTarasId, impossibleBet);
        assertEquals(0, standartGame.getPlayerBet(playerTarasId), 0.001);
        
        standartGame.placeBet(userTaras.getId(), playerTarasId, bet);
        standartGame.readyPlayerToStart(playerTarasId);
        
        try {
            standartGame.placeBet(userTaras.getId(), playerTarasId, bet);
            fail("You can`t place bet after game started!");
        } catch (IllegalStateException e) {
            // it`s ok
        }
        
        standartGame.standPlayer(userTaras.getId(), playerTarasId);
        try {
            standartGame.placeBet(userTaras.getId(), playerTarasId, bet);
            fail("You can`t place bet after game started!");
        } catch (IllegalStateException e) {
            // it`s ok
        }
        standartGame.readyPlayerToEnd(playerTarasId);
        standartGame.placeBet(userTaras.getId(), playerTarasId, bet);
    }

    /**
     * Test of hitPlayer method, of class SinglePlayerBlackjackGame.
     */
    @Test
    public void testHitPlayer() {
        try {
            standartGame.hitPlayer(userTaras.getId(), playerTarasId);
            fail("You can`t hit card before game starts!");
        } catch (IllegalStateException e) {
            // it`s ok
        }          
        
        standartGame.placeBet(userTaras.getId(), playerTarasId, 1d);
        standartGame.readyPlayerToStart(playerTarasId);
        
        //invailid user
        try {
            standartGame.hitPlayer(2132131, 51345345);
            fail("You can`t hit card to not existing player!");
        } catch (NoSuchPlayerException e) {
            // it`s ok
        }
        
        standartGame.hitPlayer(userTaras.getId(), playerTarasId); 
        standartGame.standPlayer(userTaras.getId(), playerTarasId);
        
        try {
            standartGame.hitPlayer(userTaras.getId(), playerTarasId);
            fail("You can`t hit card after game finished!");
        } catch (IllegalStateException e) {
            // it`s ok
        }
        standartGame.readyPlayerToEnd(playerTarasId);
        
        try {
            standartGame.hitPlayer(userTaras.getId(), playerTarasId);
            fail("You can`t hit card before game starts!");
        } catch (IllegalStateException e) {
            // it`s ok
        }
    }

    
    /**
     * Test of addUserToGame method, of class SinglePlayerBlackjackGame.
     */
    @Test
    public void testAddUserToGame() {
        try {
            standartGame.addUserToGame(null);
            fail("Game must not receive null user!");
        } catch (NullPointerException ex) {
            // it`s ok
        }
        try {
            standartGame.addUserToGame(userTaras);
            fail("Game must not receive more than one user!");
        } catch (IllegalStateException e) {
        }
    }

    /**
     * Test of getDealerCards method, of class SinglePlayerBlackjackGame.
     */
    @Test
    public void testGetDealerCards() {
        List<ICard> result;
        try {
            result = standartGame.getDealerCards();
        } catch (IllegalStateException e) {
            // it`s ok
        }
        
        standartGame.placeBet(userTaras.getId(), playerTarasId, 50d);
        standartGame.readyPlayerToStart(playerTarasId);
        result = standartGame.getDealerCards();
        assertEquals("Only one not hidden card must show dealer before game ends!", 1, result.size());
        
        standartGame.standPlayer(userTaras.getId(), playerTarasId);
        result = standartGame.getDealerCards();
        assertTrue("Game must show all dealer cards after game ends!", result.size() > 1);
    }

    /**
     * Test of getDealerScore method, of class SinglePlayerBlackjackGame.
     */
    @Test
    public void testGetDealerScore() {
        int result;
        try {
            standartGame.getDealerScore();
        } catch (IllegalStateException e) {
            // it`s ok
        }
        
        standartGame.placeBet(userTaras.getId(), playerTarasId, 50d);
        standartGame.readyPlayerToStart(playerTarasId);
        result = standartGame.getDealerScore();
        assertTrue("Only one not hidden dealer`s card must be calculated before game ends!", result <= 11);
        
        standartGame.standPlayer(userTaras.getId(), playerTarasId);
        result = standartGame.getDealerScore();
        assertTrue("Dealer must gain 17 points or more after game ends!", result >= 17);
    }

    /**
     * Test of isPlayerCanTakeCard method, of class SinglePlayerBlackjackGame.
     */
    @Test
    public void testIsPlayerCanTakeCard() {
        
        boolean isCanTakeCard = standartGame.isPlayerCanTakeCard(playerTarasId);
        assertFalse(isCanTakeCard);
        
        standartGame.placeBet(userTaras.getId(), playerTarasId, 1d);
        isCanTakeCard = standartGame.isPlayerCanTakeCard(playerTarasId);
        assertFalse(isCanTakeCard);
        
        standartGame.readyPlayerToStart(playerTarasId);
        try {
            standartGame.isPlayerCanTakeCard(playerTarasId + 412);
            fail("Player can`t take card if he is not added to game");
        } catch (NoSuchPlayerException e) {
            // it`s ok
        }  
        isCanTakeCard = standartGame.isPlayerCanTakeCard(playerTarasId);
        assertTrue(isCanTakeCard);        

        
        standartGame.hitPlayer(userTaras.getId(), playerTarasId); 
        standartGame.standPlayer(userTaras.getId(), playerTarasId);
        isCanTakeCard = standartGame.isPlayerCanTakeCard(playerTarasId);
        assertFalse(isCanTakeCard);

        standartGame.readyPlayerToEnd(playerTarasId);
        
        isCanTakeCard = standartGame.isPlayerCanTakeCard(playerTarasId);
        assertFalse(isCanTakeCard);
    }
    
    
    
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //**************** TEST DIFFERENT POSSIBLE SITUATIONS *********************
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    
    @Test
    public void testPlayerGoingBust() {
        List<ICard> cards = new ArrayList<>();
        cards.add(CardFactory.getCard(EnumCardSuit.CLUBS, EnumCardValue.TEN));
        cards.add(CardFactory.getCard(EnumCardSuit.CLUBS, EnumCardValue.TEN));
        cards.add(CardFactory.getCard(EnumCardSuit.CLUBS, EnumCardValue.TEN));
        cards.add(CardFactory.getCard(EnumCardSuit.CLUBS, EnumCardValue.TEN));
        cards.add(CardFactory.getCard(EnumCardSuit.CLUBS, EnumCardValue.TEN));
        cards.add(CardFactory.getCard(EnumCardSuit.CLUBS, EnumCardValue.TEN));
        cards.add(CardFactory.getCard(EnumCardSuit.CLUBS, EnumCardValue.TEN));
        cards.add(CardFactory.getCard(EnumCardSuit.CLUBS, EnumCardValue.TEN));
        StandartDeck deck = new StandartDeck(cards);
        
        SinglePlayerBlackjackGame game = new SinglePlayerBlackjackGame(deck, true);
        
        int CARD_TEN = 10;
        IUser userLena = new SimpleUser(1l, "lena", "1", new FakeWallet());
        long playerLenaId = game.addUserToGame(userLena);
        
        game.placeBet(userLena.getId(), playerLenaId, 50.25);
        game.readyPlayerToStart(playerLenaId);
        
        int lenaScore = game.getPlayerScore(playerLenaId);
        assertFalse(game.isPlayerBusted(playerLenaId));
        assertEquals(CARD_TEN * 2, lenaScore);
        
        game.hitPlayer(userLena.getId(), playerLenaId);
        lenaScore = game.getPlayerScore(playerLenaId);
        assertTrue(game.isPlayerBusted(playerLenaId));
        assertEquals(CARD_TEN * 3, lenaScore);
        
       
        try {
            // let`s try to take 10 more cards
            // game shouldn`t give us any card after we gone busted
            for (int i = 0; i < 10; i++) {
                game.hitPlayer(userLena.getId(), playerLenaId);
            } 
        } catch (IllegalStateException e) {
            // Game can be finished after our player goes bust 
        }

        assertEquals(CARD_TEN * 3, lenaScore);
    }
    
    @Test
    public void testSinglePlayerGame() {
        List<ICard> cards = new ArrayList<>();
        cards.add(CardFactory.getCard(EnumCardSuit.CLUBS, EnumCardValue.SIX));
        cards.add(CardFactory.getCard(EnumCardSuit.CLUBS, EnumCardValue.NINE));
        cards.add(CardFactory.getCard(EnumCardSuit.CLUBS, EnumCardValue.EIGHT));
        cards.add(CardFactory.getCard(EnumCardSuit.CLUBS, EnumCardValue.SEVEN));
        cards.add(CardFactory.getCard(EnumCardSuit.CLUBS, EnumCardValue.FOUR));
                
        SinglePlayerBlackjackGame game = new SinglePlayerBlackjackGame(new StandartDeck(cards), true);
        
        IUser userLena = new SimpleUser(1l, "lena", "1", new FakeWallet());
        double oldMoneyLena = userLena.getWallet().getBalance();
        double bet = 45;
        long playerLenaId = game.addUserToGame(userLena);
        
        game.placeBet(userLena.getId(), playerLenaId, bet);
        game.readyPlayerToStart(playerLenaId);
        // game.isStarted();
        int lenaScore = game.getPlayerScore(playerLenaId);
        assertFalse(game.isPlayerBusted(playerLenaId));
        assertEquals(EnumCardValue.EIGHT.getScoreValue() + EnumCardValue.SEVEN.getScoreValue(), lenaScore);
        
        assertEquals(EnumCardValue.NINE.getScoreValue(), game.getDealerScore());
        game.standPlayer(userLena.getId(), playerLenaId);
        assertEquals(EnumCardValue.SIX.getScoreValue() 
                   + EnumCardValue.NINE.getScoreValue() 
                   + EnumCardValue.FOUR.getScoreValue(), game.getDealerScore());
        
        // User Lena must lose
        assertEquals(oldMoneyLena - bet, userLena.getWallet().getBalance(), 0.001);
    }
    
    @Test(expected = NoCardInDeckException.class)
    public void testNotEnoughCards() {
        List<ICard> cards = new ArrayList<>();
               
        SinglePlayerBlackjackGame game = new SinglePlayerBlackjackGame(new StandartDeck(cards), true);
        IUser userLena = new SimpleUser(1l, "lena", "1", new FakeWallet());
        long playerLenaId = game.addUserToGame(userLena);
        
        game.placeBet(userLena.getId(), playerLenaId, 1d);
        game.readyPlayerToStart(playerLenaId); //should give cards to lena player
    }
    
    @Test
    public void testNoBetWantPlay() {
        List<ICard> cards = new ArrayList<>();
               
        SinglePlayerBlackjackGame game = new SinglePlayerBlackjackGame(new StandartDeck(cards), true);
        IUser userLena = new SimpleUser(1l, "lena", "1", new FakeWallet());
        long playerLenaId = game.addUserToGame(userLena);
        
        assertFalse(game.isPlayerCanTakeCard(playerLenaId));
        game.readyPlayerToStart(playerLenaId); //should not give cards to lena player
        int a = game.getPlayerCards(playerLenaId).size();
        assertEquals(0, a);
    }
    
    @Test
    public void testTwoGamesInARow() {      
        double lenaBet = 100.00;
        SinglePlayerBlackjackGame game = new SinglePlayerBlackjackGame(DeckBuilder.buildStandartDeck(), true);
        IUser userLena = new SimpleUser(1l, "lena", "1", new FakeWallet());
        long playerLenaId = game.addUserToGame(userLena);
        
        // First game
        game.placeBet(userLena.getId(), playerLenaId, lenaBet);
        
        game.readyPlayerToStart(playerLenaId);
        
        game.hitPlayer(userLena.getId(), playerLenaId);
        
        // Check amount of cards and bets
        assertEquals(3, game.getPlayerCards(playerLenaId).size());
        assertEquals(lenaBet, game.getPlayerBet(playerLenaId), 0.001);
        
        // End of first game
        // All cards and bets should reset
        game.standPlayer(userLena.getId(), playerLenaId); 
        game.readyPlayerToEnd(playerLenaId);

        
        // Second game
        game.placeBet(userLena.getId(), playerLenaId, lenaBet);
               
        game.readyPlayerToStart(playerLenaId);
        
        game.hitPlayer(userLena.getId(), playerLenaId);

        
        // Check amount of cards and bets
        assertEquals(3, game.getPlayerCards(playerLenaId).size());
        assertEquals(lenaBet, game.getPlayerBet(playerLenaId), 0.001);
        
        game.standPlayer(userLena.getId(), playerLenaId); 
    }
    
    
    
    /**
     * Try to make operation to wrong player
     */
    @Test(expected = NoSuchPlayerException.class)
    public void testChaoticMethodCallsInGame1() {
        standartGame.placeBet(17, 222, 1232141.57);
    }
    
    /**
     * Try to get new card before making bet
     */
    @Test(expected = IllegalStateException.class)
    public void testChaoticMethodCallsInGame2() {       
        standartGame.hitPlayer(userTaras.getId(), playerTarasId);
    }
    
    /**
     * Try to use stand when taking bets
     */
    @Test
    public void testChaoticMethodCallsInGame3() {
        standartGame.readyPlayerToStart(playerTarasId);
        // there are no players that placed bets in game, so it must not start
        assertEquals(EnumGameState.WAITING_BETS, standartGame.getState());
    }
    
    /**
     * Ok, let`s now start normally game
     * and try to add another user
     */
    @Test(expected = IllegalStateException.class)
    public void testChaoticMethodCallsInGame4() {
        standartGame.placeBet(userTaras.getId(), playerTarasId, 100d);
        standartGame.readyPlayerToStart(playerTarasId);
        // Everithing ok, game shuld be started
        assertEquals(EnumGameState.GAME_IN_PROCESS, standartGame.getState());
        boolean canTakeCard = standartGame.isPlayerCanTakeCard(playerTarasId);
        assertTrue(canTakeCard);
        // Let`s try add one more user to our game;
        standartGame.addUserToGame(userTaras);
    }
    
    /*
     * We are bad guys and want to take all cards in deck and more
     */
    @Test
    public void testChaoticMethodCallsInGame5() {
        standartGame.placeBet(userTaras.getId(), playerTarasId, 100d);
        standartGame.readyPlayerToStart(playerTarasId);
        try {
            for (int i = 0; i < 60; i++) {
                standartGame.hitPlayer(userTaras.getId(), playerTarasId);
            }
        } catch (IllegalStateException e) {
             // Game can be finished after our player goes bust 
        }
        // but game must not give us so much cards
        int MAX_CARDS_IN_HAND = 12; //A+A+A+A = 4  +  2+2+2+2 = 12    + 3+3+3+3 = 24 
        assertTrue(standartGame.getPlayerCards(playerTarasId).size() <= MAX_CARDS_IN_HAND);
        
    }
    
    /**
     * Let`s try make a bet after game finished but not started new one
     */
    @Test(expected = IllegalStateException.class)
    public void testChaoticMethodCallsInGame6() {
        standartGame.placeBet(userTaras.getId(), playerTarasId, 100d);
        standartGame.readyPlayerToStart(playerTarasId);
        standartGame.standPlayer(userTaras.getId(), playerTarasId);
        // game must end in that case
        assertEquals(EnumGameState.GAME_FINISHED, standartGame.getState());
        
        standartGame.placeBet(userTaras.getId(), playerTarasId, 1273d);
    }
    
    @Test
    public void testForNullAnswers() {
        standartGame.placeBet(userTaras.getId(), playerTarasId, 100d);
        standartGame.readyPlayerToStart(playerTarasId);
        standartGame.standPlayer(userTaras.getId(), playerTarasId);
        // game must end in that case
        assertEquals(EnumGameState.GAME_FINISHED, standartGame.getState());
        
        assertNotNull(standartGame.getDealerCards());
        assertTrue(standartGame.getDealerScore() > 0);
        assertTrue(standartGame.getPlayerBet(playerTarasId) > 0);
        assertNotNull(standartGame.getPlayerCards(playerTarasId));
        assertTrue(standartGame.getPlayerScore(playerTarasId) > 0);
        assertTrue(standartGame.getPlayersInGame().size() > 0);
        
        standartGame.readyPlayerToEnd(playerTarasId);
        assertFalse(standartGame.getPlayerBet(playerTarasId) > 0);
        assertTrue(standartGame.getPlayerCards(playerTarasId).isEmpty());
        assertFalse(standartGame.getPlayerScore(playerTarasId) > 0);
        assertTrue(standartGame.getPlayersInGame().size() > 0);
    }
    
}
