/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.main;

import java.util.HashMap;
import java.util.Map;
import vasylts.blackjack.deck.DeckBuilder;
import vasylts.blackjack.deck.IDeck;
import vasylts.blackjack.game.SinglePlayerBlackjackGame;

/**
 *
 * @author VasylcTS
 */
public class GameFactory {
    private static final Map<Long, SinglePlayerBlackjackGame> games = new HashMap<>();
    private static Long id = 0l;
    
    
    public static SinglePlayerBlackjackGame getGame(Long id) {
        return games.get(id);
    }
    
    public static Long createGame() {
        IDeck deck = DeckBuilder.buildStandartDeck();
        deck.shuffleDeck();
        SinglePlayerBlackjackGame game = new SinglePlayerBlackjackGame(deck, true);
        ++id; 
        games.put(id, game);
        return id;
    }
    
    public static Long createGame(IDeck deck, boolean autoResetDeck) {
        SinglePlayerBlackjackGame game = new SinglePlayerBlackjackGame(deck, autoResetDeck);
        ++id; 
        games.put(id, game);
        return id;
    }
    
    public static void setGame(SinglePlayerBlackjackGame game) {
        games.put(id++, game);
    }
}