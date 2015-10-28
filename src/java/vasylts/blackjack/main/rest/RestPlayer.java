/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.main.rest;

import javax.ejb.Stateless;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import vasylts.blackjack.main.GameFactory;
import vasylts.blackjack.main.rest.error.BlackjackServerException;
import vasylts.blackjack.user.IUser;
import vasylts.blackjack.user.UserFactory;

/**
 * REST Web Service
 * <p>
 * @author VasylcTS
 */
@Stateless
@Path("game/{gameId}/player")
public class RestPlayer {


    /**
     * Test method, should delete this soon
     * @return 
     */
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    public Response getNewPlayerIdTest() {
        return Response.ok("This is test method").build();
    }
    
    /**
     * Create new player in game with help of {@code IUser} class
     * @param gameId id of game in wich we need to create player
     * @param userId user`s id who wants to play this game 
     * @return id of player in this game
     */
    @GET
    @Path("user/{userId}")
    @Produces({MediaType.TEXT_PLAIN})
    public Response getNewPlayerId(@PathParam("gameId") Long gameId, @PathParam("userId") Long userId) {
        try {
            IUser user = UserFactory.getSimpleUserManager().getUser(userId);
            Long playerId = GameFactory.getGame(gameId).addUserToGame(user);
            return Response.ok(playerId.toString()).build();
        } catch (Exception e) {
            throw new BlackjackServerException(e.getMessage());
        }
    }
    
    /**
     * Delete player from game
     * @param gameId id of game where need to delete player
     * @param playerId id of player in that game
     * @return {@code true} if user deleted, {@code false} otherwise
     */
    @DELETE
    @Path("{playerId}")
    public Response deletePlayerById(@PathParam("gameId") Long gameId, @PathParam("playerId") Long playerId) {
        try {
            Boolean deleted = GameFactory.getGame(gameId).deletePlayerByUser(0, playerId);
            return Response.ok(deleted).build();
        } catch (Exception e) {
            throw new BlackjackServerException(e.getMessage());
        }
    }
}
