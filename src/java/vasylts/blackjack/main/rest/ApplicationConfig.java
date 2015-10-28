/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.main.rest;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author VasylcTS
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(vasylts.blackjack.main.rest.RestGame.class);
        resources.add(vasylts.blackjack.main.rest.RestHand.class);
        resources.add(vasylts.blackjack.main.rest.RestPlayer.class);
        resources.add(vasylts.blackjack.main.rest.RestUser.class);
        resources.add(vasylts.blackjack.main.rest.RestUserWallet.class);
    }
    
}
