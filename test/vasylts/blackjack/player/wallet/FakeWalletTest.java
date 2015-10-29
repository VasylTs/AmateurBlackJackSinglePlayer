/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.player.wallet;

import vasylts.blackjack.user.wallet.FakeWallet;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author VasylcTS
 */
public class FakeWalletTest {
    
    public FakeWalletTest() {
    }

    @Test
    public void test_withdrawMoney() {
        FakeWallet w = new FakeWallet();
        double oldBalance = w.getBalance();
        w.withdrawMoney(500);
        boolean isMoneyAdded = w.getBalance() > oldBalance;
        assertFalse(isMoneyAdded);
        
        w.withdrawMoney(-500);
        isMoneyAdded = w.getBalance() > oldBalance;
        assertFalse(isMoneyAdded);
    }
    
    @Test
    public void test_addFunds() {
        FakeWallet w = new FakeWallet();
        double oldBalance = w.getBalance();
        w.addFunds(500);
        boolean isMoneyAdded = w.getBalance() > oldBalance;
        assertTrue(isMoneyAdded);
        
        w.addFunds(-500);
        isMoneyAdded = w.getBalance() > oldBalance;
        assertTrue(isMoneyAdded);
    }
    
}
