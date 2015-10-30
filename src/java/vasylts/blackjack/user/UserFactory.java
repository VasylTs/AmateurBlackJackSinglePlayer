/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.user;

/**
 * Class for getting user manager. Depending on what manager will return this
 * factory that versions of {@link IUser} and {@link IWallet} will be used. It
 * can return {@link FakeUserManager} wich does not work with real database and
 * uses a {@code Set<IUser>} to work with users. Or it can return some
 * realization of Database-userManager like {@link HibernateUserManager} or
 * {@link JdbcUserManager}
 *
 * @author VasylcTS
 */
public class UserFactory {

    private static IUserManager userManager;

    public static IUserManager getStandartUserManager() {
        return getHibernateUserManager();
    }

    private static IUserManager getFakeUserManager() {
        if (userManager == null) {
            userManager = new FakeUserManager();
        }
        return userManager;
    }

    private static IUserManager getHibernateUserManager() {
        if (userManager == null) {
            userManager = new HibernateUserManager();
        }
        return userManager;
    }

    private static IUserManager getJdbcUserManager() {
        if (userManager == null) {
            userManager = new JdbcUserManager();
        }
        return userManager;
    }
}
