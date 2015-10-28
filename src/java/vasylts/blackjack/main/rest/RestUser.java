/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.main.rest;

import com.google.gson.Gson;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import vasylts.blackjack.jaxb.EntityUser;
import vasylts.blackjack.main.rest.error.BlackjackServerException;
import vasylts.blackjack.player.wallet.WalletFactory;
import vasylts.blackjack.user.IUser;
import vasylts.blackjack.user.UserFactory;

/**
 *
 * @author VasylcTS
 */
@Stateless
@Path("user")
public class RestUser {

    public RestUser() {
    }

    /**
     * Create new user on this server.
     *
     * @param user json entity of {@code EntityUser} with only login and
     * password inside. Example: {"login":"User1","password":"pass1"}
     * @return id of created user
     * @throws BlackjackServerException if there is user with same login
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createUser(EntityUser user) {
        boolean isUserCreated = UserFactory.getSimpleUserManager().createUser(user.getLogin(), user.getPassword(), WalletFactory.getNewWallet());
        if (isUserCreated) {
            return Response.ok().build();
        } else {
            throw new BlackjackServerException("Such user already exists!");
        }

    }

    /**
     * Returns user`s info by user id
     *
     * @param id id of user
     * @return user`s id, login, wallet balance or status NOT_FOUND if there is
     * no such user
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserById(@PathParam("id") Long id) {
        try {
            IUser user = UserFactory.getSimpleUserManager().getUser(id);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                EntityUser returningUser = new EntityUser();
                returningUser.setId(user.getId());
                returningUser.setLogin(user.getLogin());
                //returningUser.setPassword(user.getPassword());
                returningUser.setBalance(new BigDecimal(user.getWallet().getBalance()));
                return Response.ok(returningUser).build();
            }
        } catch (Exception e) {
            throw new BlackjackServerException(e.getMessage());
        }
    }

    /**
     * Returns user`s info by user login and password
     *
     * @param login user`s login
     * @param password user`s password
     * @return user`s id, login, wallet balance or status NOT_FOUND if there is
     * no such user
     */    
    @GET
    @Path("{login}/{password}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserByLogPass(@PathParam("login") String login, @PathParam("password") String password) {
        try {
            IUser user = UserFactory.getSimpleUserManager().getUser(login, password);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                EntityUser returningUser = new EntityUser();
                returningUser.setId(user.getId());
                returningUser.setLogin(user.getLogin());
                returningUser.setPassword(user.getPassword());
                returningUser.setBalance(new BigDecimal(user.getWallet().getBalance()));
                return Response.ok(returningUser).build();
            }
        } catch (Exception e) {
            throw new BlackjackServerException(e.getMessage());
        }
    }

    /**
     * Delete user from this server
     * @param login user`s login
     * @param password user`s password
     */
    @DELETE
    @Path("{login}/{password}")
    public void deleteUserByLogPass(@PathParam("login") String login, @PathParam("password") String password) {
        UserFactory.getSimpleUserManager().deleteUser(login, password);
    }

    /**
     * Delete user from this server
     * @param id id of user
     */
    @DELETE
    @Path("{id}")
    public void deleteUserById(@PathParam("id") Long id) {
        UserFactory.getSimpleUserManager().deleteUser(id);
    }

}
