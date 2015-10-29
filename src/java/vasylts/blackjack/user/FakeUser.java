/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.user;

import java.util.Objects;
import vasylts.blackjack.user.wallet.IWallet;

/**
 * Test implementation of IUser
 * @author VasylcTS
 */
public class FakeUser implements IUser {

    private final Long id;
    private String password;
    private final String login;
    private final IWallet wallet;
    
    public FakeUser(Long id, String login, String password, IWallet wallet) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.wallet = wallet;
    }
    
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getLogin() {
        return login; 
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public IWallet getWallet() {
        return wallet;
    }

    @Override
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
    
}
