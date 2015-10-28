/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.game;

import java.util.List;
import java.util.Set;
import vasylts.blackjack.deck.card.ICard;
import vasylts.blackjack.user.IUser;
import vasylts.blackjack.jaxb.EnumGameState;

/**
 *
 * @author VasylcTS
 */
public interface IBlackjackGame {

    /**
     * Game will create new player and will add him to game.
     * <p>
     * @param user User who wants to play this game.
     * <p>
     * @return player`s id in game.
     */
    public long addUserToGame(IUser user);

    /**
     * This method will delete user`s player from this instance of IBlackjackGame
     * <p>
     * @param userId User`s id who is a parent of player
     * <p>
     * @param playerId Player`s id in this game
     * @return {@code true} if player deleted from game
     */
    public boolean deletePlayerByUser(long userId, long playerId);

    /**
     * This method returns all players in this instance of IBlackjackGame
     * <p>
     * @return players that added to this game
     */
    public Set<Long> getPlayersInGame();

    /**
     * Sends message to game that this player is place his bet and ready to
     * start game. Game will start automaticly after all players will send this
     * message or 30 seconds after first bet
     * <p>
     * @param playerId Player id in this game
     */
    public void readyPlayerToStart(long playerId);

    /**
     * Send message to game that this player received all needed info about last
     * game and ready to finish this game and start another one. Game will
     * finish after all active players will send this message or 10 seconds
     * after first player send this message
     * <p>
     * @param playerId
     */
    public void readyPlayerToEnd(long playerId);

    /**
     * Returns players bet
     * <p>
     * @param playerId Player id in this game
     * <p>
     * @return Bet of selected player
     */
    public double getPlayerBet(long playerId);

    /**
     * This method returns {@code List} of player cards
     * <p>
     * @param playerId Player id in this game
     * <p>
     * @return {@code List} of player cards
     */
    public List<ICard> getPlayerCards(long playerId);

    /**
     * Check if player is busted(score is more than 21) and can not take cards
     * any more
     * <p>
     * @param playerId Player id in this game
     * <p>
     * @return {@code true} if player busted
     */
    public boolean isPlayerBusted(long playerId);

    /**
     * This method checks if game started, player placed a bet, he is not busted
     * and some other conditions
     * <p>
     * @param playerId Player id in this game
     * <p>
     * @return {@code true} if player can take more cards
     */
    public boolean isPlayerCanTakeCard(long playerId);


    /**
     * This method return player score.
     * <p>
     * @param playerId Player id
     * <p>
     * @return Player score
     */
    public int getPlayerScore(long playerId);

    
    /**
     * Place a bet for a player.
     * <p>
     * @param userId User`s id who is parent of player
     * @param playerId long playerId
     * @param bet Value of a bet.
     * <p>
     * @return false if it is impossible to place a bet for this player.
     */
    public boolean placeBet(long userId, long playerId, Double bet);
    
    /**
     * Blackjack action: "Hit: Take another card from the dealer." Gives new
     * card to player from deck.
     * <p>
     * @param userId User`s id who is parent of player
     * @param playerId Player id who will receive new card
     * <p>
     * @return Card from deck.
     */
    public ICard hitPlayer(long userId, long playerId);
    
    /**
     * Blackjack action: "Stand: Take no more cards, also known as "stand pat",
     * "stick", or "stay"." Stops game for this player.
     * <p>
     * @param userId User`s id who is parent of player
     * @param playerId Player id who wants to stop the game
     */
    public void standPlayer(long userId, long playerId);

    /**
     * This action returns dealer cards if all players used action: "Stand"
     * otherwise it will return only opened(not hidden) dealer cards.
     * <p>
     * @return dealer cards if all players used action: "Stand" otherwise it
     * will return only opened(not hidden) dealer cards.
     */
    public List<ICard> getDealerCards();

    /**
     * This action returns dealer score only if all players used action: "Stand"
     * otherwise it return {@code null}
     * <p>
     * @return dealer score only if all players used action: "Stand" otherwise
     * it return {@code null}
     */
    public int getDealerScore();
    
    /**
     * Returns game status
     * @return status
     * @see vasylts.blackjack.game.EnumGameState
     */
    public EnumGameState getState();
}
