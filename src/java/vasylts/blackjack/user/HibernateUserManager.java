/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.user;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import vasylts.blackjack.user.databaseworker.hibernateworker.BlackjackHibernateUtil;
import vasylts.blackjack.user.databaseworker.hibernateworker.Blackjackuser;
import vasylts.blackjack.user.wallet.HibernateWallet;

/**
 *
 * @author VasylcTS
 */
public class HibernateUserManager implements IUserManager {

    @Override
    public Long createUser(String login, String password) {
        Session session = null;
        Transaction tr = null;
        try {
            Blackjackuser user = new Blackjackuser();
            user.setLogin(login);
            user.setPassword(password);
            HibernateWallet wallet = HibernateWallet.createNewWallet();
            user.setWalletid(wallet.getWalletId());
            session = BlackjackHibernateUtil.getSessionFactory().openSession();
            tr = session.beginTransaction();
            session.save(user);
            tr.commit();
            return user.getId();
        } catch (ConstraintViolationException e) {
            throw new RuntimeException("User with such login already exists! ");
        } finally {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public boolean deleteUser(Long id) {
        Session session = null;
        Transaction tr = null;
        try {
            Blackjackuser user = new Blackjackuser();
            user.setId(id);
            session = BlackjackHibernateUtil.getSessionFactory().openSession();
            tr = session.beginTransaction();
            session.delete(user);
            tr.commit();
        } finally {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return true;
    }

    @Override
    public boolean deleteUser(String login, String password) {
        Session session = null;
        Transaction tr = null;
        try {
            Blackjackuser user = new Blackjackuser();
            user.setLogin(login);
            user.setPassword(password);
            session = BlackjackHibernateUtil.getSessionFactory().openSession();
            tr = session.beginTransaction();
            session.delete(user);
            tr.commit();
        } finally {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return true;
    }

    @Override
    public IUser getUser(Long id) {
        Session session = null;
        Transaction tr = null;
        try {
            session = BlackjackHibernateUtil.getSessionFactory().openSession();
            tr = session.beginTransaction();
            Blackjackuser hibUser = (Blackjackuser) session.load(Blackjackuser.class, id);
            tr.commit();
            HibernateWallet hibWallet = new HibernateWallet(hibUser.getWalletid());
            HibernateUser user = new HibernateUser(hibUser.getId(), hibUser.getLogin(), hibUser.getPassword(), hibWallet);
            return user;
        } finally {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public IUser getUser(String login, String password) {
        Session session = null;
        Transaction tr = null;
        try {
            session = BlackjackHibernateUtil.getSessionFactory().openSession();
            tr = session.beginTransaction();
            Criteria criteria = session.createCriteria(Blackjackuser.class);
            criteria.add(Restrictions.eq("login", login));
            criteria.add(Restrictions.eq("password", password));
            List results = criteria.list();
            Blackjackuser hibUser = (Blackjackuser) results.get(0);
            tr.commit();
            HibernateWallet hibWallet = new HibernateWallet(hibUser.getWalletid());
            HibernateUser user = new HibernateUser(hibUser.getId(), hibUser.getLogin(), hibUser.getPassword(), hibWallet);
            return user;
        } finally {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

}
