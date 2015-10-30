/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.user.wallet;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import vasylts.blackjack.user.databaseworker.hibernateworker.BlackjackHibernateUtil;
import vasylts.blackjack.user.databaseworker.hibernateworker.Userwallet;

/**
 * Realization of IWallet with help of Hibernate
 * <p>
 * @author VasylcTS
 */
public class HibernateWallet implements IWallet {

    private final long walletId;

    /**
     * Create instance of {@link HibernateWallet} if it already registered in
     * database. If you need to create fully new wallet, call static
     * createNewWallet();
     * <p>
     * @param walletId id of wallet in database
     */
    public HibernateWallet(long walletId) {
        this.walletId = walletId;
    }

    /**
     * Returns current balance in this wallet
     * <p>
     * @return balance at this time
     */
    @Override
    public double getBalance() throws HibernateException {
        Session session = null;
        Transaction tr = null;
        try {
            session = BlackjackHibernateUtil.getSessionFactory().getCurrentSession();
            tr = session.beginTransaction();
            Userwallet wallet = (Userwallet) session.load(Userwallet.class, walletId);
            tr.commit();
            return wallet.getBalance();
        } finally {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * Take money from wallet. It ignores a sign of parameter and in both
     * situations(-amount/+amount) will decrease balance at amount
     * <p>
     * @param amount amout to take from wallet
     * <p>
     * @return current amount after taking money
     */
    @Override
    public double withdrawMoney(double amount) throws HibernateException {
        Session session = null;
        Transaction tr = null;
        try {
            session = BlackjackHibernateUtil.getSessionFactory().getCurrentSession();
            tr = session.beginTransaction();
            Userwallet wallet = (Userwallet) session.load(Userwallet.class, walletId);

            double newBalance = wallet.getBalance() - Math.abs(amount);
            wallet.setBalance(newBalance);
            session.save(wallet);
            tr.commit();
            return newBalance;
        } finally {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }

    /**
     * Put money to wallet. It ignores sign of parameter and in both
     * situations(-amount/+amount) will increase balance at amount
     * <p>
     * @param amount amount to put to wallet
     * <p>
     * @return current amount after put
     */
    @Override
    public double addFunds(double amount) throws HibernateException {
        Session session = null;
        Transaction tr = null;
        try {
            session = BlackjackHibernateUtil.getSessionFactory().getCurrentSession();
            tr = session.beginTransaction();
            Userwallet wallet = (Userwallet) session.load(Userwallet.class, walletId);
            double newBalance = wallet.getBalance() + Math.abs(amount);
            wallet.setBalance(newBalance);
            session.save(wallet);
            tr.commit();
            return newBalance;
        } finally {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * Create and register new wallet in database and get it id.
     * <p>
     * @return id of wallet in database
     */
    static public HibernateWallet createNewWallet() {
        Session session = null;
        Transaction tr = null;
        try {
            session = BlackjackHibernateUtil.getSessionFactory().getCurrentSession();
            Userwallet wallet = new Userwallet();
            wallet.setBalance(0.0);
            tr = session.beginTransaction();
            session.save(wallet);
            tr.commit();

            return new HibernateWallet(wallet.getId());
        } finally {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * Get id of this wallet in database
     * <p>
     * @return id of this wallet
     */
    public long getWalletId() {
        return walletId;
    }

}
