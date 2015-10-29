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
public interface IUserManager {
    public Long createUser(String login, String password);
    public boolean deleteUser(Long id);
    public boolean deleteUser(String login, String password);
    public IUser getUser(Long id);
    public IUser getUser(String login, String password);
}
