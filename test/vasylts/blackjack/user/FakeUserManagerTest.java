/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.user;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import vasylts.blackjack.user.wallet.FakeWallet;

/**
 *
 * @author VasylcTS
 */
public class FakeUserManagerTest {
    
    public FakeUserManagerTest() {
    }

    FakeUserManager um;
    
    @Before
    public void setUp() {
        um = new FakeUserManager();
        IUser userCarl = new FakeUser(1l, "a", "1", new FakeWallet());
        IUser userJack = new FakeUser(2l, "b", "2", new FakeWallet());
        IUser userLena = new FakeUser(3l, "c", "3", new FakeWallet());
        um.addUser(userCarl);
        um.addUser(userJack);
        um.addUser(userLena);
    }
    
    @Test
    public void testSameUser() {
        int oldSize = um.getUserCount();
        FakeWallet wal = new FakeWallet();
        IUser userTaras1 = new FakeUser(999l, "Taras", "999", wal);
        IUser userTaras2 = new FakeUser(999l, "Taras", "999", wal);
        
        Long firstTarasAdded = um.addUser(userTaras1);
        assertTrue(firstTarasAdded != null);
        Long secondTarasAdded = um.addUser(userTaras2);
        assertFalse(secondTarasAdded != null);

        IUser userTaras3 = new FakeUser(124125412l, "Taras", "4234", new FakeWallet());
        Long thirdTarasAdded = um.addUser(userTaras3);
        assertFalse(thirdTarasAdded != null);
        
        assertEquals(oldSize + 1, um.getUserCount());    
    }
    
    @Test
    public void testDeleteUser() {
        IUser userTaras = new FakeUser(999l, "Taras", "999", new FakeWallet());
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
