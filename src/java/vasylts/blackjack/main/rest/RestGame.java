/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.main.rest;

import com.google.gson.Gson;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;
import vasylts.blackjack.game.IBlackjackGame;
import vasylts.blackjack.main.GameFactory;
import vasylts.blackjack.main.rest.error.BlackjackServerException;

/**
 *
 * @author VasylcTS
 */
@Stateless
@Path("game")
public class RestGame {

    /**
     * Method for creating new blackjack game
     *
     * @return id of game
     */
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    public String createNewGame() {
        Long gameId = GameFactory.createGame();
        return gameId.toString();
    }

    /**
     * This method returns dealer cards
     *
     * @param gameId id of game in wich we need to search dealer
     * @return Collection of cards
     */
    @GET
    @Path("{gameId}/dealerCards")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getDealerCardsGame(@PathParam("gameId") Long gameId) {
        try {
            IBlackjackGame game = GameFactory.getGame(gameId);
            if (game == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                return Response.ok(new Gson().toJson(game.getDealerCards())).build();
            }
        } catch (Exception e) {
            throw new BlackjackServerException(e.toString());
        }
    }

    @GET
    @Path("{gameId}/dealerScore")
    @Produces({MediaType.TEXT_PLAIN})
    public Response getDealerScoreGame(@PathParam("gameId") Long gameId) {
        try {
            IBlackjackGame game = GameFactory.getGame(gameId);
            if (game == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                return Response.ok(new Gson().toJson(game.getDealerScore())).build();
            }
        } catch (Exception e) {
            throw new BlackjackServerException(e.toString());
        }
    }

    /**
     * Method returns all players in specific game
     *
     * @param gameId id of the game
     * @return
     */
    @GET
    @Path("{gameId}/players")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPlayersInGame(@PathParam("gameId") Long gameId) {
        try {
            IBlackjackGame game = GameFactory.getGame(gameId);
            if (game == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                return Response.ok(new Gson().toJson(game.getPlayersInGame())).build();
            }
        } catch (Exception e) {
            throw new BlackjackServerException(e.toString());
        }
    }

    /**
     * Checks and returns game state
     *
     * @param gameId id of the game
     * @return game state
     * @see EnumGameState
     */
    @GET
    @Path("{gameId}/state")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getGameState(@PathParam("gameId") Long gameId) {
        try {
            IBlackjackGame game = GameFactory.getGame(gameId);
            if (game == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                return Response.ok(new Gson().toJson(game.getState())).build();
            }
        } catch (Exception e) {
            throw new BlackjackServerException(e.toString());
        }
    }

    /**
     * Return all info about game in json format. It`s a cheat method (can show
     * all cards in deck)
     *
     * @param gameId id of the game
     * @return all info about game
     */
    @GET
    @Path("{gameId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getGame(@PathParam("gameId") Long gameId) {
        try {
            Gson g = new Gson();
            IBlackjackGame game = GameFactory.getGame(gameId);
            return Response.ok(g.toJson(game)).build();
        } catch (Exception e) {
            throw new BlackjackServerException(e.toString());
        }
    }
}
