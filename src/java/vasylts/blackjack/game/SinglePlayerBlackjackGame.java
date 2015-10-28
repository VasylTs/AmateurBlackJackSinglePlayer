/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.game;

import java.util.List;
import java.util.Set;
import vasylts.blackjack.deck.IDeck;
import vasylts.blackjack.deck.card.ICard;
import vasylts.blackjack.game.end.GameFinishDealerBlackjack;
import vasylts.blackjack.game.end.GameFinishDealerBusted;
import vasylts.blackjack.game.end.GameFinishWorker;
import vasylts.blackjack.game.end.IGameFinishWorker;
import vasylts.blackjack.jaxb.EnumGameState;
import vasylts.blackjack.player.hand.DealerHand;
import vasylts.blackjack.player.hand.IHand;
import vasylts.blackjack.player.IPlayer;
import vasylts.blackjack.user.IUser;

/**
 * This class realize logic for blackjack game with only one user
 *
 * @author VasylcTS
 */
public class SinglePlayerBlackjackGame implements IBlackjackGame {

    private final GamePlayerManager playerManager;
    private final IDeck gameDeck;
    private final boolean resetDeck;

    private EnumGameState state;
    private DealerHand dealerHand;

    /**
     * Creates a new instance of {@code SinglePlayerBlackjackGame} class with
     * specific {@code IDeck}
     * <p>
     * @param deck A card deck wich will be used for playing this game
     * @param resetDeckAfterEachGame Parameter that stands for reset and shuffle
     * deck after each game. Should be {@code true} in most cases.
     * <p>
     * @see vasylts.blackjack.deck.IDeck
     * @see vasylts.blackjack.deck.DeckBuilder
     */
    public SinglePlayerBlackjackGame(IDeck deck, boolean resetDeckAfterEachGame) {
        gameDeck = deck;
        resetDeck = resetDeckAfterEachGame;
        playerManager = new GamePlayerManager();
        state = EnumGameState.WAITING_BETS;
    }

    /**
     * We have only one player in this game :(
     * <p>
     * @return our single {@code IPlayer}
     */
    private IPlayer getMySinglePlayer() {
        if (playerManager.getPlayers().iterator().hasNext()) {
            return playerManager.getPlayers().iterator().next();
        } else {
            return null;
        }
    }

    /**
     * Starts game with one player
     */
    private void startGame() {
        checkState(EnumGameState.WAITING_BETS);
        IPlayer player = getMySinglePlayer();
        if (player != null && player.getBet() > 0) {
            // let`s give cards to dealer
            dealerHand = new DealerHand();
            dealerHand.setHiddenCard(gameDeck.getNextCard());
            dealerHand.addCard(gameDeck.getNextCard());
            // and now let`s give cards to our player

            IHand hand = player.getHand();
            hand.addCard(gameDeck.getNextCard());
            hand.addCard(gameDeck.getNextCard());

            setState(EnumGameState.GAME_IN_PROCESS);
        }
    }

    /**
     * 1. Play for a dealer
     * <p>
     * 2. Show dealer cards
     * <p>
     * 3. Select winner
     * <p>
     * 4. Give prize
     * <p>
     * p.s. this method does not reset game(so you can not start new one). For
     * reseting should call resetAll()
     * <p>
     * This method is called from: {@code  public void standPlayer();}
     */
    private void tryToEndGame() {
        checkState(EnumGameState.GAME_IN_PROCESS);
        // p.s. we have only one player so we don`t need to check if all players used action "STAND"

        // Let`s end this game
        setState(EnumGameState.GAME_FINISHED);
        playForDealer();
        IGameFinishWorker gameEndWorker;
        if (dealerHand.isBlackjack()) {
            gameEndWorker = new GameFinishDealerBlackjack();
        } else if (dealerHand.isBusted()) {
            gameEndWorker = new GameFinishDealerBusted();
        } else {
            gameEndWorker = new GameFinishWorker();
        }
        gameEndWorker.givePrizesToWinners(dealerHand, playerManager.getPlayers());
    }

    /**
     * Dealer will take cards until his score is less than 17
     * <p>
     * This method is called from {@code tryToEndGame()}
     */
    private void playForDealer() {
        checkState(EnumGameState.GAME_FINISHED);
        dealerHand.openHiddenCard();
        while (dealerHand.getScore() < DealerHand.dealerPlayScore) {
            dealerHand.addCard(gameDeck.getNextCard());
        }
        dealerHand.setStand();
    }

    /**
     * Reset: 1. Dealer hand 2. Our single player hand(card|bet|flags) 3. Reset
     * Deck if such flag is true
     */
    private void resetAll() {
        checkState(EnumGameState.GAME_FINISHED);
        if (getMySinglePlayer() != null) {
            // reset our single player hand(card|bet|flags)
            getMySinglePlayer().resetGame();
            // reset dealer hand
            dealerHand = null;
            // reset deck
            if (resetDeck) {
                gameDeck.resetDeck();
                gameDeck.shuffleDeck();
            }
            setState(EnumGameState.WAITING_BETS);
        }

    }

    private void checkState(EnumGameState needToBeState) throws IllegalStateException {
        if (this.getState() != needToBeState) {
            throw new IllegalStateException("You can not perform this action right now. Game must be in state: " + needToBeState.toString() + ". Now game in state: " + getState().toString());
        }
    }

    private void checkStateUpperEqualsTo(EnumGameState needToBeStateOrUpper) throws IllegalStateException {
        if (this.getState().ordinal() < needToBeStateOrUpper.ordinal()) {
            throw new IllegalStateException("You can not perform this action right now. Game state must be >= " + needToBeStateOrUpper.toString() + ". Now game in state: " + getState().toString());
        }
    }

    /**
     * @return the state
     */
    @Override
    public EnumGameState getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    private void setState(EnumGameState state) {
        this.state = state;
    }

    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Access to IPlayerManager methods with checking and changing state of game
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    /**
     * Place a bet for a player.
     * <p>
     * @param userId in single player game does not using
     * @param playerId long playerId
     * @param bet Value of a bet.
     * <p>
     * @return false if it is impossible to place a bet for this player.
     */
    @Override
    public boolean placeBet(long userId, long playerId, Double bet) {
        checkState(EnumGameState.WAITING_BETS);
        return playerManager.placeBetUnauthorized(playerId, bet);
    }

    /**
     * Blackjack action: "Hit: Take another card from the dealer." Gives new
     * card to player from deck.
     * <p>
     * @param userId in single player game does not using
     * @param playerId Player id who will receive new card
     * <p>
     * @return Card from deck.
     */
    @Override
    public ICard hitPlayer(long userId, long playerId) {
        checkState(EnumGameState.GAME_IN_PROCESS);
        if (isPlayerCanTakeCard(playerId)) {
            ICard card = gameDeck.getNextCard();
            playerManager.giveCardUnauthorized(playerId, card);
            if (playerManager.isPlayerBusted(playerId)) {
                standPlayer(userId, playerId);
            }
            // card already added to players hand
            // returning this card just for info
            return card;
        } else {
            return null;
        }
    }

    /**
     * Blackjack action: "Stand: Take no more cards, also known as "stand pat",
     * "stick", or "stay"." Stops game for this player.
     * <p>
     * @param userId in single player game does not using
     * @param playerId Player id who wants to stop the game
     */
    @Override
    public void standPlayer(long userId, long playerId) {
        checkState(EnumGameState.GAME_IN_PROCESS);
        playerManager.standPlayerUnauthorized(playerId);
        tryToEndGame();
    }

    /**
     * Sends message to game that this player is place his bet and ready to
     * start game.
     * <p>
     * @param playerId Player id in this game
     */
    @Override
    public void readyPlayerToStart(long playerId) {
        checkState(EnumGameState.WAITING_BETS);
        playerManager.getPlayer(playerId).setReadyToStart();
        if (playerManager.isAllPlayerReadyToStart()) {
            startGame();
        }
    }

    /**
     * Send message to game that this player received all needed info about last
     * game and ready to finish this game and start another one.
     * <p>
     * @param playerId
     */
    @Override
    public void readyPlayerToEnd(long playerId) {
        checkState(EnumGameState.GAME_FINISHED);
        playerManager.getPlayer(playerId).setReadyToFinish();

        if (playerManager.isAllPlayerReadyToEnd()) {
            resetAll();
        }
    }

    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Access to IPlayerManager methods
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    /**
     * Game will create new player and will add him to game.
     * <p>
     * @param user User who wants to play this game.
     * <p>
     * @return player`s id in game.
     * @throws IllegalStateException when game already have player
     */
    @Override
    public long addUserToGame(IUser user) {
        if (user == null) {
            throw new NullPointerException("User can not be null");
        }
        if (getMySinglePlayer() == null) {
            return playerManager.addUserToGame(user);
        } else {
            throw new IllegalStateException("This game already have player");
        }

    }

    /**
     * This method will delete user`s player from this instance of
     * IBlackjackGame
     * <p>
     * @param userId in single player game does not using
     * <p>
     * @param playerId Player`s id in this game
     * <p>
     * @return {@code true} if player deleted from game
     */
    @Override
    public boolean deletePlayerByUser(long userId, long playerId) {
        return playerManager.deletePlayerUnauthorized(playerId);
    }

    /**
     * This method returns all players in this instance of IBlackjackGame
     * <p>
     * @return players that added to this game
     */
    @Override
    public Set<Long> getPlayersInGame() {
        return playerManager.getPlayersInGame();
    }

    /**
     * Returns players bet
     * <p>
     * @param playerId Player id in this game
     * <p>
     * @return Bet of selected player
     */
    @Override
    public double getPlayerBet(long playerId) {
        return playerManager.getPlayerBet(playerId);
    }

    /**
     * This method returns {@code List} of player cards
     * <p>
     * @param playerId Player id in this game
     * <p>
     * @return {@code List} of player cards
     */
    @Override
    public List<ICard> getPlayerCards(long playerId) {
        return playerManager.getPlayerCards(playerId);
    }

    /**
     * Check if player is busted(score is more than 21) and can not take cards
     * any more
     * <p>
     * @param playerId Player id in this game
     * <p>
     * @return {@code true} if player busted
     */
    @Override
    public boolean isPlayerBusted(long playerId) {
        return playerManager.isPlayerBusted(playerId);
    }

    /**
     * This action returns dealer cards if all players used action: "Stand"
     * otherwise it will return only opened(not hidden) dealer cards.
     * <p>
     * @return dealer cards if all players used action: "Stand" otherwise it
     * will return only opened(not hidden) dealer cards.
     */
    @Override
    public List<ICard> getDealerCards() {
        checkStateUpperEqualsTo(EnumGameState.GAME_IN_PROCESS);
        return dealerHand.getCardList();
    }

    /**
     * This action returns dealer score only if all players used action: "Stand"
     * otherwise it return {@code null}
     * <p>
     * @return dealer score only if all players used action: "Stand" otherwise
     * it return {@code null}
     */
    @Override
    public int getDealerScore() {
        checkStateUpperEqualsTo(EnumGameState.GAME_IN_PROCESS);
        return dealerHand.getScore();
    }

    /**
     * This method checks if game started, player placed a bet, he is not busted
     * and some other conditions
     * <p>
     * @param playerId Player id in this game
     * <p>
     * @return {@code true} if player can take more cards
     */
    @Override
    public boolean isPlayerCanTakeCard(long playerId) {
        if (state == EnumGameState.GAME_IN_PROCESS) {
            return playerManager.isPlayerCanTakeCard(playerId);
        } else {
            return false;
        }
    }

    /**
     * This method return player score.
     * <p>
     * @param playerId Player id
     * <p>
     * @return Player score
     */
    @Override
    public int getPlayerScore(long playerId) {
        return playerManager.getPlayerScore(playerId);
    }

}
