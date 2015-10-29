/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.main;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;
import vasylts.blackjack.deck.DeckBuilder;
import vasylts.blackjack.deck.IDeck;
import vasylts.blackjack.game.IBlackjackGame;
import vasylts.blackjack.game.SinglePlayerBlackjackGame;

/**
 *
 * @author VasylcTS
 */
public class GameFactory {

    private static final Map<Long, IBlackjackGame> games;
    private static final Timer cleanerTimerDaemon;
    private static Long id;

    static {
        games = new HashMap<>();
        id = 0l;
        /**
         * Initializing timer for deleting empty games each five minutes
         */
        cleanerTimerDaemon = new Timer("Game cleaner", true);
        cleanerTimerDaemon.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (Iterator<Entry<Long, IBlackjackGame>> it = games.entrySet().iterator(); it.hasNext();) {
                    Map.Entry<Long, IBlackjackGame> entry = it.next();
                    if (entry.getValue().getPlayersInGame().isEmpty()) {
                        it.remove();
                    }
                }
            }
        }, 5 * 60 * 1000, 5 * 60 * 1000);
    }

    /**
     * Get already created game by it`s id
     * <p>
     * @param id id of game
     * <p>
     * @return instance of SinglePlayerBlackjackGame
     */
    public static IBlackjackGame getGame(Long id) {
        IBlackjackGame game = games.get(id);
        if (game == null) {
            throw new NoSuchElementException("There is no game with such id! ");
        } else {
           return game;
        }
        
    }

    /**
     * Creates new instance of {@code SinglePlayerBlackjackGame} game with
     * standard deck with 52 cards and function auto-resetting deck
     * <p>
     * @return id of created game
     */
    public static Long createGame() {
        IDeck deck = DeckBuilder.buildStandartDeck();
        deck.shuffleDeck();
        return createGame(deck, true);
    }

    /**
     * Creates new instance of {@code SinglePlayerBlackjackGame} game with
     * specific deck and chosen type of auto-resetting deck
     * <p>
     * @param deck          deck for game
     * @param autoResetDeck is deck will be reseted and shuffled after each game
     * <p>
     * @return id of created game
     */
    public static Long createGame(IDeck deck, boolean autoResetDeck) {
        SinglePlayerBlackjackGame game = new SinglePlayerBlackjackGame(deck, autoResetDeck);
        return setGame(game);
    }

    /**
     * Add new game to this game factory
     * <p>
     * @param game instance of SinglePlayerBlackjackGame to be added
     * <p>
     * @return id of this game in this game factory
     */
    public static Long setGame(IBlackjackGame game) {
        games.put(id++, game);
        return id - 1;
    }
}
