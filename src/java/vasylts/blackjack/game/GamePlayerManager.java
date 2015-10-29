/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.game;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import vasylts.blackjack.deck.card.ICard;
import vasylts.blackjack.player.IPlayer;
import vasylts.blackjack.player.NoSuchPlayerException;
import vasylts.blackjack.player.SimplePlayer;
import vasylts.blackjack.user.wallet.IWallet;
import vasylts.blackjack.user.IUser;

/**
 * This class I were creating for supporting a multiplayer game, but then I
 * decided not to bother and made a single player game, so part of methods is
 * useless in this realization of blackjack
 * <p>
 * @author VasylcTS
 */
public class GamePlayerManager {

    // our players
    private final Map<Long, IPlayer> players;

    /**
     * Creates a new instance of {@code GamePlayerManager}
     */
    public GamePlayerManager() {
        players = new HashMap<>();
    }

    /**
     * Create a new player and add him to this manager. Players is created with
     * using info from {@code IUser} instance
     * <p>
     * @param user instance of {@code IUser} who wants to create player
     * <p>
     * @return Player`s id in this manager
     * <p>
     * @see vasylts.blackjack.user.IUser
     * @see vasylts.blackjack.player.IPlayer
     */
    public long addUserToGame(IUser user) {
        long id = getNewId();
        IPlayer player = new SimplePlayer(id, user);
        players.put(id, player);
        return id;
    }

    /**
     * This method checks if all players is set flag "readyToStart". This answer
     * can be ignored to force start game
     * <p>
     * @return {@code true} if all players are ready to start game,
     * {@code false} otherwise
     */
    public boolean isAllPlayerReadyToStart() {
        Predicate<IPlayer> allPlayersAreReady = (IPlayer player) -> {
            return player.getBet() > 0 && player.isReadyToStart();
        };
        return players.values().stream().anyMatch(allPlayersAreReady);
    }

    /**
     * This method checks if all players is set flag "readyToEnd". This answer
     * can be ignored to force end game
     * <p>
     * @return {@code true} if all players are ready to end game, {@code false}
     * otherwise
     */
    public boolean isAllPlayerReadyToEnd() {
        Predicate<IPlayer> allPlayersAreReady = (IPlayer player) -> {
            return player.getBet() > 0 && player.isReadyToFinish();
        };
        return players.values().stream().anyMatch(allPlayersAreReady);
    }

    /**
     * Deleting player(s) from this manager by user_id wich created that players
     * <p>
     * @param userId user_id who is parent of players.
     * @param playerId player`s id to delete from game
     * <p>
     * @return {@code true} if player deleted, {@code false} otherwise
     */
    public boolean deletePlayerByUser(long userId, long playerId) {
        Iterator<IPlayer> playerIter = players.values().iterator();
        while (playerIter.hasNext()) {
            IPlayer player = playerIter.next();
            if (player.getId().equals(userId) && player.getId().equals(playerId)) {
                playerIter.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Deleting player(s) from this manager without cheking accessory to user
     * <p>
     * @param playerId player`s id to delete from game
     * <p>
     * @return {@code true} if player deleted, {@code false} otherwise
     */
    public boolean deletePlayerUnauthorized(long playerId) {
        return players.remove(playerId) != null;
    }

    /**
     * Return all players` id in this manager. It need if client wants to show
     * other player cards/score/bets
     * <p>
     * @return {@code Set} of players` id
     */
    public Set<Long> getPlayersInGame() {
        return players.keySet();
    }

    /**
     * Return player bet
     * <p>
     * @param playerId Player`s id in this game
     * <p>
     * @return Player`s bet
     */
    public double getPlayerBet(long playerId) {
        return getPlayer(playerId).getBet();
    }

    /**
     * Return {@code List} of player`s cards
     * <p>
     * @param playerId Player`s id in this game
     * <p>
     * @return {@code List} of player`s cards
     */
    public List<ICard> getPlayerCards(long playerId) {
        return getPlayer(playerId).getHand().getCardList();
    }

    /**
     * Checks if player is busted. Blackjack wiki: "Scoring higher than 21
     * (called "busting" or "going bust") results in a loss."
     * <p>
     * @param playerId Player`s id in this game
     * <p>
     * @return {@code true} if player is busted, {@code false} otherwise
     */
    public boolean isPlayerBusted(long playerId) {
        return getPlayer(playerId).getHand().isBusted();
    }

    /**
     * Return player`s score
     * <p>
     * @param playerId Player`s id in game
     * <p>
     * @return player`s score
     */
    public int getPlayerScore(long playerId) {
        return getPlayer(playerId).getHand().getScore();
    }

    /**
     * Checks if player can take more cards
     * <p>
     * @param playerId Player`s id in game
     * <p>
     * @return
     */
    public boolean isPlayerCanTakeCard(long playerId) {
        return getPlayerBet(playerId) > 0 && !isPlayerStand(playerId) && !isPlayerBusted(playerId);
    }

    /**
     * This method is trying to place a bet for specific player. It will
     * withdraw money from player`s wallet and set bet to game
     * <p>
     * @param userId User`s id who wants to place bet for his player
     * @param playerId Player`s id in game
     * @param bet amount of money
     * <p>
     * @return {@code true} if placing a bet is successful, {@code false}
     * otherwise
     */
    public boolean placeBet(long userId, long playerId, Double bet) {
        IPlayer player = getPlayer(playerId);
        boolean isOk = false;
        if (player.getUserId().equals(userId)) {
            if (bet > 0 && isEnoughMoneyInWallet(player.getWallet(), bet)) {
                if (player.getBet() > 0) {
                    returnBetToPlayer(player);
                }
                player.getWallet().withdrawMoney(bet);
                player.setBet(bet);
                isOk = true;
            }
        }
        return isOk;
    }

    /**
     * This method is trying to place a bet for specific player without checking
     * accessory to user. It will withdraw money from player`s wallet and set
     * bet to game
     * <p>
     * @param playerId Player`s id in game
     * @param bet amount of money
     * <p>
     * @return {@code true} if placing a bet is successful, {@code false}
     * otherwise
     */
    public boolean placeBetUnauthorized(long playerId, Double bet) {
        IPlayer player = getPlayer(playerId);
        if (player.getBet() > 0) {
            returnBetToPlayer(player);
        }
        if (bet > 0 && isEnoughMoneyInWallet(player.getWallet(), bet)) {
            player.getWallet().withdrawMoney(bet);
            player.setBet(bet);
            return true;
        }
        return false;
    }

    /**
     * Use action "HIT" to specific player. Blackjack wiki: Hit: Take another
     * card from the dealer.
     * <p>
     * @param userId User`s id who wants to use action "STAND" to his player
     * @param playerId Player`s id in game
     * @param card
     */
    public void giveCard(long userId, long playerId, ICard card) {
        IPlayer player = getPlayer(playerId);
        if (player.getUserId().equals(userId)) {
            player.getHand().addCard(card);
        }
    }

    /**
     * Use action "HIT" to specific player without checking accessory to user.
     * Blackjack wiki: Hit: Take another card from the dealer.
     * <p>
     * @param playerId Player`s id in game
     * @param card
     */
    public void giveCardUnauthorized(long playerId, ICard card) {
        getPlayer(playerId).getHand().addCard(card);
    }

    /**
     * Use action "STAND" to specific player. Blackjack wiki: Stand: Take no
     * more cards, also known as "stand pat", "stick", or "stay".
     * <p>
     * @param userId User`s id who wants to use action "STAND" to his player
     * @param playerId Player`s id in game
     */
    public void standPlayer(long userId, long playerId) {
        IPlayer player = getPlayer(playerId);
        if (player.getUserId().equals(userId)) {
            player.getHand().setStand();
        }
    }

    /**
     * Use action "STAND" to specific player without checking accessory to user.
     * Blackjack wiki: Stand: Take no more cards, also known as "stand pat",
     * "stick", or "stay".
     * <p>
     * @param playerId Player`s id in game
     */
    public void standPlayerUnauthorized(long playerId) {
        getPlayer(playerId).getHand().setStand();
    }

    /**
     * Check if user is used action "STAND" Blackjack wiki: Stand: Take no more
     * cards, also known as "stand pat", "stick", or "stay".
     * <p>
     * @param playerId Player`s id in game
     * <p>
     * @return {@code true} if user used action "STAND", {@code false} otherwise
     */
    public boolean isPlayerStand(long playerId) {
        return getPlayer(playerId).getHand().isStand();
    }

    /**
     * Get player
     * <p>
     * @param playerId Player`s id
     * <p>
     * @return player
     */
    /*
     * default-package
     */ IPlayer getPlayer(Long playerId) {
        IPlayer player = players.get(playerId);
        if (player == null) {
            throw new NoSuchPlayerException("There is no player with ID: " + playerId + " in this game! ");
        } else {
            return player;
        }
    }

    /**
     * Get collection of players.
     * <p>
     * @return collection of players
     */
    /*
     * default-package
     */ Collection<IPlayer> getPlayers() {
        return players.values();
    }

    /**
     * Generate unique id for player
     * <p>
     * @return id
     */
    private Long getNewId() {
        Random rand = new Random();
        Long randId;
        do {
            randId = rand.nextLong();
        } while (players.containsKey(randId));
        return randId;
    }

    /**
     *
     * @param wallet player`s wallet
     * @param money amount of money we want to withdraw
     * <p>
     * @return {@code true} if there are enough money in wallet, {@code false}
     * otherwise
     */
    private boolean isEnoughMoneyInWallet(IWallet wallet, double money) {
        return wallet.getBalance() >= money;
    }

    private void returnBetToPlayer(IPlayer player) {
        double bet = player.getBet();
        if (bet > 0) {
            player.getWallet().addFunds(bet);
            player.setBet(0);
        }
    }
}
