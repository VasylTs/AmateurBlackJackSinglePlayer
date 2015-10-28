/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.user;

import java.util.Objects;
import vasylts.blackjack.player.wallet.IWallet;

/**
 *
 * @author VasylcTS
 */
public class SimpleUser implements IUser {

    private final Long id;
    private String password;
    private final String login;
    private final IWallet wallet;
    
    public SimpleUser(Long id, String login, String password, IWallet wallet) {
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
    
    @Override 
    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (o instanceof IUser) {
            IUser us = (IUser)o;
            if (Objects.equals(us.getId(), id)
                    && Objects.equals(us.getLogin(), login)
                    && Objects.equals(us.getPassword(), password)
                    && Objects.equals(us.getWallet(), wallet)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.password);
        hash = 59 * hash + Objects.hashCode(this.login);
        hash = 59 * hash + Objects.hashCode(this.wallet);
        return hash;
    }
    
}
