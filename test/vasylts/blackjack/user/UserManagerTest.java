/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.user;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import vasylts.blackjack.player.wallet.FakeWallet;
import vasylts.blackjack.player.wallet.IWallet;

/**
 *
 * @author VasylcTS
 */
public class UserManagerTest {
    
    public UserManagerTest() {
    }

    UserManager um;
    
    @Before
    public void setUp() {
        um = new UserManager();
        IUser userCarl = new SimpleUser(1l, "a", "1", new FakeWallet());
        IUser userJack = new SimpleUser(2l, "b", "2", new FakeWallet());
        IUser userLena = new SimpleUser(3l, "c", "3", new FakeWallet());
        um.addUser(userCarl);
        um.addUser(userJack);
        um.addUser(userLena);
    }
    
    @Test
    public void testSameUser() {
        int oldSize = um.getUserCount();
        FakeWallet wal = new FakeWallet();
        IUser userTaras1 = new SimpleUser(999l, "Taras", "999", wal);
        IUser userTaras2 = new SimpleUser(999l, "Taras", "999", wal);
        
        boolean firstTarasAdded = um.addUser(userTaras1);
        assertTrue(firstTarasAdded);
        boolean secondTarasAdded = um.addUser(userTaras2);
        assertFalse(secondTarasAdded);

        IUser userTaras3 = new SimpleUser(124125412l, "Taras", "4234", new FakeWallet());
        boolean thirdTarasAdded = um.addUser(userTaras3);
        assertFalse(thirdTarasAdded);
        
        assertEquals(oldSize + 1, um.getUserCount());    
    }
    
    @Test
    public void testDeleteUser() {
        IUser userTaras = new SimpleUser(999l, "Taras", "999", new FakeWallet());
        um.addUser(userTaras);
        int oldSize = um.getUserCount();
        
        boolean wrongUserDeleted = um.deleteUser("taras", "999");
        assertFalse(wrongUserDeleted);
        assertEquals(oldSize, um.getUserCount());
        
        boolean userDeleted = um.deleteUser("Taras", "999");
        assertTrue(userDeleted);
        assertEquals(oldSize - 1, um.getUserCount());
    }
   
    
}
