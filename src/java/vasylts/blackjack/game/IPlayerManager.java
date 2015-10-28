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

/**
 *
 * @author VasylcTS
 */
public interface IPlayerManager {

    /**
     * Game will create new player and will add him to game.
     * <p>
     * @param user User who wants to play this game.
     * <p>
     * @return player`s id in game.
     */
    public long addUserToGame(IUser user);

    /**
     * This method will delete player from this instance of IBlackjackGame
     * <p>
     * @param playerId Player id in this game
     * <p>
     * @return {@code true} if player deleted from game
     */
    public boolean deletePlayer(long playerId);

    /**
     * This method returns all players in this instance of IBlackjackGame
     * <p>
     * @return players that added to this game
     */
    public Set<Long> getPlayersInGame();

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
     * Blackjack action: "Hit: Take another card from the dealer." Gives new
     * card to player from deck.
     * <p>
     * @param playerId Player id who will receive new card
     * <p>
     * @return Card from deck.
     */
    public ICard hitPlayer(long playerId);

    /**
     * This method return player score.
     * <p>
     * @param playerId Player id
     * <p>
     * @return Player score
     */
    public int getPlayerScore(long playerId);

    /**
     * Blackjack action: "Stand: Take no more cards, also known as "stand pat",
     * "stick", or "stay"." Stops game for this player.
     * <p>
     * @param playerId Player id who wants to stop the game
     */
    public void standPlayer(long playerId);
}
