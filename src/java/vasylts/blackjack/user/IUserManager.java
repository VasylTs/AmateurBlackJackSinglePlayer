/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.user;

import vasylts.blackjack.player.wallet.IWallet;

/**
 *
 * @author VasylcTS
 */
public interface IUserManager {
    public boolean addUser(IUser user);
    public boolean createUser(String login, String password, IWallet wallet);
    public boolean deleteUser(Long id);
    public boolean deleteUser(String login, String password);
    public IUser getUser(Long id);
    public IUser getUser(String login, String password);
}
