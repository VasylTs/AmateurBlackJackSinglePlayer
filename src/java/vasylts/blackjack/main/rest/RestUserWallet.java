/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.main.rest;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import vasylts.blackjack.main.rest.error.BlackjackServerException;
import vasylts.blackjack.user.UserFactory;

/**
 *
 * @author VasylcTS
 */
@Stateless
@Path("user/{userId}/wallet")
public class RestUserWallet {
    
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    public Response getBalance(@PathParam("userId") Long id) {
        try {
            return Response.ok(UserFactory.getSimpleUserManager().getUser(id).getWallet().getBalance()).build();
        } catch (Exception e) {
            throw new BlackjackServerException(e.getMessage());
        }
    }
    
    @PUT
    @Consumes({MediaType.TEXT_PLAIN})
    public Response addToBalance(Double amount, @PathParam("userId") Long id) {
        try {
            return Response.ok(UserFactory.getSimpleUserManager().getUser(id).getWallet().addFunds(amount)).build();
        } catch (Exception e) {
            throw new BlackjackServerException(e.getMessage());
        }
    }
    
    @POST
    @Consumes({MediaType.TEXT_PLAIN})
    public Response getFromBalance(Double amount, @PathParam("userId") Long id) {
        try {
            return Response.ok(UserFactory.getSimpleUserManager().getUser(id).getWallet().withdrawMoney(amount)).build();
        } catch (Exception e) {
            throw new BlackjackServerException(e.getMessage());
        }
    }
    
}