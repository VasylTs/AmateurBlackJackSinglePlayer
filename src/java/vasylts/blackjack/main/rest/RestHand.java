/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.main.rest;

import com.google.gson.Gson;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import vasylts.blackjack.game.IBlackjackGame;
import vasylts.blackjack.jaxb.EntityPlayerHandStatus;
import vasylts.blackjack.jaxb.ObjectFactory;
import vasylts.blackjack.jaxb.PlayerHandStatus;
import vasylts.blackjack.main.GameFactory;
import vasylts.blackjack.main.rest.error.BlackjackServerException;

/**
 *
 * @author VasylcTS
 */
@Stateless
@Path("game/{gameId}/player/{playerId}/hand")
//@Path("hand")
public class RestHand {

    /**
     * Test method
     *
     * @param gameId
     * @param playerId
     * @return
     */
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    public Response getTest(@PathParam("gameId") Long gameId, @PathParam("playerId") Long playerId) {
        try {
            return Response.ok("This is test method").build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BlackjackServerException(e.toString());
        }
    }

    /**
     * Get score of player in specific game. Returns zero if game not started
     * yet
     *
     * @param gameId id of game
     * @param playerId id of player
     * @return player`s score
     */
    @GET
    @Path("score")
    @Produces({MediaType.TEXT_PLAIN})
    public Response getScore(@PathParam("gameId") Long gameId, @PathParam("playerId") Long playerId) {
        try {
            IBlackjackGame game = GameFactory.getGame(gameId);
            if (game == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("There is no such game.").build();
            } else {
                return Response.ok(game.getPlayerScore(playerId)).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BlackjackServerException(e.toString());
        }
    }

    /**
     * Get cards of specific player in game. It returns empty list if game not
     * started yet.
     *
     * @param gameId id of game
     * @param playerId id of player in that game
     * @return Collection of cards
     * @throws BlackjackServerException if there is no such game/player.
     */
    @GET
    @Path("cards")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCardList(@PathParam("gameId") Long gameId, @PathParam("playerId") Long playerId) {
        try {
            IBlackjackGame game = GameFactory.getGame(gameId);
            if (game == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("There is no such game.").build();
            } else {
                return Response.ok(new Gson().toJson(game.getPlayerCards(playerId))).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BlackjackServerException(e.toString());
        }
    }

    /**
     * Get player`s bet in game.
     *
     * @param gameId id of game
     * @param playerId id of player in that game
     * @return amount of money
     * @throws BlackjackServerException if there is no such game/player.
     */
    @GET
    @Path("bet")
    @Produces({MediaType.TEXT_PLAIN})
    public Response getBet(@PathParam("gameId") Long gameId, @PathParam("playerId") Long playerId) {
        try {
            IBlackjackGame game = GameFactory.getGame(gameId);
            if (game == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("There is no such game.").build();
            } else {
                return Response.ok(game.getPlayerBet(playerId)).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BlackjackServerException(e.toString());
        }
    }

    /**
     * Set player`s bet
     *
     * @param gameId id of game
     * @param playerId id of player in that game
     * @param bet amount of money placed
     * @throws BlackjackServerException if there is no such game/player.
     */
    @POST
    @Path("bet")
    @Consumes({MediaType.TEXT_PLAIN})
    public Response setBet(@PathParam("gameId") Long gameId, @PathParam("playerId") Long playerId, Double bet) {
        try {
            IBlackjackGame game = GameFactory.getGame(gameId);
            if (game == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("There is no such game.").build();
            } else {
                return Response.ok(game.placeBet(0, playerId, bet)).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BlackjackServerException(e.toString());
        }
    }

    /**
     * Get new card from deck to player
     *
     * @param gameId id of game
     * @param playerId id of player in that game
     * @return Card that player received
     * @throws BlackjackServerException if there is no such game/player or game
     * in wrong state.
     */
    @GET
    @Path("card")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getNewCardToHand(@PathParam("gameId") Long gameId, @PathParam("playerId") Long playerId) {
        try {
            IBlackjackGame game = GameFactory.getGame(gameId);
            if (game == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("There is no such game.").build();
            } else {
                String resp = new Gson().toJson(game.hitPlayer(0, playerId));
                return Response.ok(resp).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BlackjackServerException(e.toString());
        }
    }

    /**
     * This method is needed if there is more than one player in game In this
     * example of single player game it is not needed
     */
//    @POST
//    @Path("status")
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Response setPlayerStatus(@PathParam("gameId") Long gameId, @PathParam("playerId") Long playerId, EntityPlayerStatus playerStatus) {
//        try {
//            IBlackjackGame game = GameFactory.getGame(gameId);
//            if (game == null) {
//                return Response.status(Response.Status.NOT_FOUND).entity("There is no such game.").build();
//            } else { 
//                switch (playerStatus.getStatusType()) {
//                    case READY_TO_START: {
//                        game.readyPlayerToStart(playerId);
//                        break;
//                    }
//                    case STAND: {
//                          game.standPlayer(playerId, userId); break;
//                      }
//                    case READY_TO_FINISH: {
//                        game.readyPlayerToEnd(playerId);
//                        break;
//                    }
//                    default: {
//                        throw new IllegalArgumentException("Can not define new player status. Received status: " + playerStatus.getStatusType().toString());
//                    }
//                }
//                return Response.ok().build();
//            }
//        } catch (Exception e) {
//            throw new BlackjackServerException(e.toString());
//        }
//    }
    /**
     * Set action "STAND" to player.
     *
     * @param gameId id of game
     * @param playerId id of player in that game
     * @throws BlackjackServerException if there is no such game/player or game
     * in wrong state.
     */
    @POST
    @Path("stand")
    @Consumes({MediaType.TEXT_PLAIN})
    public Response setPlayerStand(@PathParam("gameId") Long gameId, @PathParam("playerId") Long playerId, boolean isStand) {
        try {
            IBlackjackGame game = GameFactory.getGame(gameId);
            if (game == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("There is no such game.").build();
            } else if (isStand) {
                game.standPlayer(0, playerId);
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Status not changed, plese send boolean: true, if you want to change status.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BlackjackServerException(e.toString());
        }
    }

    /**
     * Send message to game that player is confirmed his bet and ready to start
     * new game. This method maked with hope of creating mutliplayer game in
     * future
     *
     * @param gameId id of game
     * @param playerId id of player in that game
     * @throws BlackjackServerException if there is no such game/player.
     */
    @POST
    @Path("readyToStart")
    @Consumes({MediaType.TEXT_PLAIN})
    public Response setPlayerReadyToStart(@PathParam("gameId") Long gameId, @PathParam("playerId") Long playerId, boolean isReady) {
        try {
            IBlackjackGame game = GameFactory.getGame(gameId);
            if (game == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("There is no such game.").build();
            } else if (isReady) {
                game.readyPlayerToStart(playerId);
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Status not changed, plese send boolean: true, if you want to change status.").build();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new BlackjackServerException(e.toString());
        }
    }

    /**
     * Send message to game that player is confirmed that he received all needed
     * info(like dealer hand, score and other players) and ready to end this
     * game. This method maked with hope of creating mutliplayer game in future
     *
     * @param gameId id of game
     * @param playerId id of player in that game
     * @throws BlackjackServerException if there is no such game/player.
     */
    @POST
    @Path("readyToEnd")
    @Consumes({MediaType.TEXT_PLAIN})
    public Response setPlayerReadyToEnd(@PathParam("gameId") Long gameId, @PathParam("playerId") Long playerId, boolean isReady) {
        try {
            IBlackjackGame game = GameFactory.getGame(gameId);
            if (game == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("There is no such game.").build();
            } else if (isReady) {
                game.readyPlayerToEnd(playerId);
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Status not changed, plese send boolean: true, if you want to change status.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BlackjackServerException(e.toString());
        }
    }

    /**
     * Get status of player hand
     * @param gameId id of game
     * @param playerId id of player in that game
     * @throws BlackjackServerException if there is no such game/player.
     * @see EntityPlayerHandStatus
     */
    @GET
    @Path("status")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPlayerHandStatus(@PathParam("gameId") Long gameId, @PathParam("playerId") Long playerId) {
        try {
            IBlackjackGame game = GameFactory.getGame(gameId);
            if (game == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("There is no such game.").build();
            } else {
                EntityPlayerHandStatus entHandStatus = new ObjectFactory().createEntityPlayerHandStatus();
                PlayerHandStatus handStatus;
                if (game.getPlayerCards(playerId).isEmpty()) {
                    handStatus = PlayerHandStatus.NO_CARDS;
                } else if (game.isPlayerCanTakeCard(playerId)) {
                    handStatus = PlayerHandStatus.CAN_TAKE_CARD;
                } else if (game.isPlayerBusted(playerId)) {
                    handStatus = PlayerHandStatus.BUSTED;
                } else {
                    handStatus = PlayerHandStatus.CAN_NOT_TAKE_CARD;
                }
                entHandStatus.setStatusType(handStatus);
                String resp = new Gson().toJson(entHandStatus);
                return Response.ok(resp).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BlackjackServerException(e.toString());
        }
    }

}
