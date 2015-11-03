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
import vasylts.blackjack.logger.EnumLogAction;
import vasylts.blackjack.logger.IActionLogger;
import vasylts.blackjack.player.hand.DealerHand;
import vasylts.blackjack.player.IPlayer;
import vasylts.blackjack.user.IUser;

/**
 * This class realize logic for blackjack game with only one user
 * <p>
 * @author VasylcTS
 */
public class SinglePlayerBlackjackGame implements IBlackjackGame {

    private final GamePlayerManager playerManager;
    private final IDeck gameDeck;
    private final boolean resetDeck;
    
    private  IActionLogger logger;
    private EnumGameState state;
    private DealerHand dealerHand;

    /**
     * Creates a new instance of {@code SinglePlayerBlackjackGame} class with
     * specific {@code IDeck}
     * <p>
     * @param deck                   A card deck wich will be used for playing
     *                               this game
     * @param resetDeckAfterEachGame Parameter that stands for reset and shuffle
     *                               deck after each game. Should be
     *                               {@code true} in most cases.
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
        EnumLogAction logAction = EnumLogAction.GAME_START;
        getLogger().logGameAction(true, logAction, "Trying to start game.");
        try {
            checkState(EnumGameState.WAITING_BETS);
            IPlayer player = getMySinglePlayer();
            if (player != null && player.getBet() > 0) {
                // let`s give cards to dealer
                dealerHand = new DealerHand();
                setDealerHiddenCard(gameDeck.getNextCard());
                getCardToDealer(gameDeck.getNextCard());
                // and now let`s give cards to our player
                setState(EnumGameState.GAME_IN_PROCESS);
                getLogger().logGameAction(true, logAction, "Game started.");
                hitPlayer(0, player.getId());
                hitPlayer(0, player.getId());
            }
        } catch (Exception e) {
            getLogger().logGameAction(false, logAction, e.toString());
            throw e;
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
        EnumLogAction logAction = EnumLogAction.GAME_END;
        getLogger().logGameAction(true, logAction, "Trying to end game.");
        try {
            checkState(EnumGameState.GAME_IN_PROCESS);
            // p.s. we have only one player so we don`t need to check if all players used action "STAND"

            // Let`s end this game
            setState(EnumGameState.GAME_FINISHED);
            playForDealer();
            IGameFinishWorker gameEndWorker;
            if (dealerHand.isBlackjack()) {
                getLogger().logGameAction(true, logAction, "Dealer has blackjack with hand: " + dealerHand.toString());
                gameEndWorker = new GameFinishDealerBlackjack();
            } else if (dealerHand.isBusted()) {
                getLogger().logGameAction(true, logAction, "Dealer busted with hand: " + dealerHand.toString());
                gameEndWorker = new GameFinishDealerBusted();
            } else {
                getLogger().logGameAction(true, logAction, "Dealer ended game with hand: " + dealerHand.toString());
                gameEndWorker = new GameFinishWorker();
            }

            gameEndWorker.givePrizesToWinners(dealerHand, playerManager.getPlayers(), getLogger());
            getLogger().logGameAction(true, logAction, "All winners received prizes.");
            getLogger().logGameAction(true, logAction, "Game successfully ended.");
        } catch (Exception e) {
            getLogger().logGameAction(false, logAction, e.toString());
            throw e;
        }
    }

    /**
     * Set dealers hidden card. This method should be called only once after new
     * game started
     * <p>
     * @param card new card
     */
    private void setDealerHiddenCard(ICard card) {
        dealerHand.setHiddenCard(card);
        getLogger().logGameAction(true, EnumLogAction.GAME_DEALER, "Received hidden card: " + card.toString());
    }

    /**
     * Gives new card to dealer hand.
     * <p>
     * @param card
     */
    private void getCardToDealer(ICard card) {
        getLogger().logGameAction(true, EnumLogAction.GAME_DEALER, "Received card: " + card.toString());
        dealerHand.addCard(card);
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
            getCardToDealer(gameDeck.getNextCard());
        }
        dealerHand.setStand();
    }

    /**
     * Reset: 1. Dealer hand 2. Our single player hand(card|bet|flags) 3. Reset
     * Deck if such flag is true
     */
    private void resetAll() {
        EnumLogAction logAction = EnumLogAction.GAME_RESET;
        getLogger().logGameAction(true, logAction, "Trying to reset game.");
        try {
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
                getLogger().logGameAction(true, logAction, "Game successfully reseted!");
            }
        } catch (Exception e) {
            getLogger().logGameAction(false, logAction, e.toString());
            throw e;
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
        if (this.state != state) {
            getLogger().logGameAction(true, EnumLogAction.GAME_STATE, "Changing game state from " + this.state.toString() + " to " + state.toString());
            this.state = state;
        }
    }
    
    /**
     * 
     * @param logger 
     */
    public void setLogger(IActionLogger logger) {
        if (logger != null) {
            this.logger = logger;
        }
    }
    
    /**
     * @return the logger
     */
    public IActionLogger getLogger() {
        if (logger == null) {
            // Please call setLogger() before calling any other methods!
            throw new NullPointerException("Logger is not initialized!");
        }
        return logger;
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
     * @param userId   in single player game does not using
     * @param playerId long playerId
     * @param bet      Value of a bet.
     * <p>
     * @return false if it is impossible to place a bet for this player.
     */
    @Override
    public boolean placeBet(long userId, long playerId, Double bet) {
        EnumLogAction action = EnumLogAction.PLAYER_BET;
        getLogger().logPlayerAction(true, playerId, action, "Trying to set a bet for player with value: " + bet);
        try {
            checkState(EnumGameState.WAITING_BETS);
            boolean isSetted = playerManager.placeBetUnauthorized(playerId, bet);
            if (isSetted) {
                getLogger().logPlayerAction(true, playerId, action, "Bet successfully setted!");
            } else {
                getLogger().logPlayerAction(false, playerId, action, "Bet did not set! Perhaps there are no money in players wallet.");
            }
            return isSetted;

        } catch (Exception e) {
            getLogger().logPlayerAction(false, playerId, action, e.toString());
            throw e;
        }
    }

    /**
     * Blackjack action: "Hit: Take another card from the dealer." Gives new
     * card to player from deck.
     * <p>
     * @param userId   in single player game does not using
     * @param playerId Player id who will receive new card
     * <p>
     * @return Card from deck.
     */
    @Override
    public ICard hitPlayer(long userId, long playerId) {
        EnumLogAction action = EnumLogAction.PLAYER_HIT;
        getLogger().logPlayerAction(true, playerId, action, "Trying to get a card for player.");
        try {
            checkState(EnumGameState.GAME_IN_PROCESS);
            if (isPlayerCanTakeCard(playerId)) {
                ICard card = gameDeck.getNextCard();
                playerManager.giveCardUnauthorized(playerId, card);
                getLogger().logPlayerAction(true, playerId, action, "Player received card: " + card.toString());
                if (playerManager.isPlayerBusted(playerId)) {
                    standPlayer(userId, playerId);
                }
                // card already added to players hand
                // returning this card just for info
               
                return card;
            } else {
                getLogger().logPlayerAction(false, playerId, action, "Player can not take card now.");
                return null;
            }
        } catch (Exception e) {
            getLogger().logPlayerAction(false, playerId, action, e.toString());
            throw e;
        }
    }

    /**
     * Blackjack action: "Stand: Take no more cards, also known as "stand pat",
     * "stick", or "stay"." Stops game for this player. Doing nothing if player
     * already used this action.
     * <p>
     * @param userId   in single player game does not using
     * @param playerId Player id who wants to stop the game
     */
    @Override
    public void standPlayer(long userId, long playerId) {
        EnumLogAction action = EnumLogAction.PLAYER_STAND;
        getLogger().logPlayerAction(true, playerId, action, "Trying to use 'STAND' to player.");
        if (state == EnumGameState.GAME_IN_PROCESS) {
            playerManager.standPlayerUnauthorized(playerId);
            getLogger().logPlayerAction(true, playerId, action, "Action 'STAND' used to player.");
            tryToEndGame();
        } else {
            getLogger().logPlayerAction(false, playerId, action, "Can not use 'STAND' to player, game in wrong state.");
        }
    }

    /**
     * Sends message to game that this player is place his bet and ready to
     * start game.
     * <p>
     * @param playerId Player id in this game
     */
    @Override
    public void readyPlayerToStart(long playerId) {
        EnumLogAction action = EnumLogAction.PLAYER_READY_START;
        try {
            checkState(EnumGameState.WAITING_BETS);
            playerManager.getPlayer(playerId).setReadyToStart();
            getLogger().logPlayerAction(true, playerId, action, "Player setted as ready to start");
        } catch (Exception e) {
            getLogger().logPlayerAction(false, playerId, action, e.toString());
            throw e;
        }

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
        EnumLogAction action = EnumLogAction.PLAYER_READY_END;
        try {
            checkState(EnumGameState.GAME_FINISHED);
            playerManager.getPlayer(playerId).setReadyToFinish();
            getLogger().logPlayerAction(true, playerId, action, "Player setted as ready to end");
        } catch (Exception e) {
            getLogger().logPlayerAction(false, playerId, action, e.toString());
            throw e;
        }

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
     * <p>
     * @throws IllegalStateException when game already have player
     */
    @Override
    public long addUserToGame(IUser user) {
        if (user == null) {
            throw new NullPointerException("User can not be null");
        }
        if (getMySinglePlayer() == null) {
            long playerId = playerManager.addUserToGame(user);
            logger.logGameAction(true, EnumLogAction.GAME_STATE, "Added player to game with id: " + playerId + ". User id: " + user.getId());
            return playerId;
        } else {
            throw new IllegalStateException("This game already have player");
        }

    }

    /**
     * This method will delete user`s player from this instance of
     * IBlackjackGame
     * <p>
     * @param userId   in single player game does not using
     * <p>
     * @param playerId Player`s id in this game
     * <p>
     * @return {@code true} if player deleted from game
     */
    @Override
    public boolean deletePlayerByUser(long userId, long playerId) {
        logger.logGameAction(true, EnumLogAction.GAME_STATE, "Delete player from game with id: " + playerId + ". User id: " + userId);
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
     *         will return only opened(not hidden) dealer cards.
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
     *         it return {@code null}
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
