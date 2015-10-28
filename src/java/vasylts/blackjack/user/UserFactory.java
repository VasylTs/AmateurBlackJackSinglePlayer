/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.user;

/**
 *
 * @author VasylcTS
 */
public class UserFactory {
    
    private static IUserManager userManager;
    
    public static IUserManager getSimpleUserManager() {
        if (userManager == null) {
            userManager = new UserManager();
        }
        return userManager;
    }
}
