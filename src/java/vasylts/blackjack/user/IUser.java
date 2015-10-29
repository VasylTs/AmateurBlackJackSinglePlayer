/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.user;

import vasylts.blackjack.user.wallet.IWallet;

/**
 *
 * @author VasylcTS
 */
public interface IUser {
    public Long getId();
    public String getLogin();
    public String getPassword();
    public void changePassword(String newPassword);
    public IWallet getWallet();
}
