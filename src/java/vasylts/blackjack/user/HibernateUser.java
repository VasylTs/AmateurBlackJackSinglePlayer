/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.user;

import net.sf.ehcache.hibernate.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import vasylts.blackjack.user.databaseworker.hibernateworker.BlackjackHibernateUtil;
import vasylts.blackjack.user.databaseworker.hibernateworker.Blackjackuser;
import vasylts.blackjack.user.wallet.HibernateWallet;
import vasylts.blackjack.user.wallet.IWallet;

/**
 *
 * @author VasylcTS
 */
public class HibernateUser extends FakeUser {

    final private long walletId ;
    public HibernateUser(Long id, String login, String password, HibernateWallet wallet) {
        super(id, login, password, wallet);
        walletId = wallet.getWalletId();
    }

    @Override
    public void changePassword(String newPassword) throws HibernateException {
        Session session = BlackjackHibernateUtil.getSessionFactory().openSession();
        Blackjackuser user = new Blackjackuser();
        user.setId(super.getId());
        user.setLogin(super.getLogin());
        user.setPassword(newPassword);
        user.setWalletid(walletId);
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        
        session.close();
    }

}
