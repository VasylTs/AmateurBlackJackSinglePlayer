/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.user.databaseworker.jdbcworker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;
import vasylts.blackjack.main.SpecialFactory;
import vasylts.blackjack.user.JdbcUser;
import vasylts.blackjack.user.wallet.JdbcWallet;

/**
 *
 * @author VasylcTS
 */
public class DatabaseWorkerUserJdbc {

    public Long createNewUser(String login, String password) throws SQLException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            Long userId = null;
            JdbcWallet wallet = JdbcWallet.createNewWallet();
            String query = "INSERT INTO BlackjackUser(login, password, walletId) VALUES(?, ?, ?)";
            con = MyConnectionJdbc.getConnection();
            st = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, login);
            st.setString(2, password);
            st.setLong(3, wallet.getWalletId());
            st.executeUpdate();
            rs = st.getGeneratedKeys();
            if (rs.next()) {
                userId = rs.getLong(1);
            }
            return userId;
        } finally {
            close(con, st, rs);
        }

    }

    public JdbcUser getUser(long userId) throws SQLException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String quer = "SELECT id, "
                    + "       login, "
                    + "       password, "
                    + "       walletId "
                    + "FROM BlackjackUser "
                    + "WHERE id = ?";

            con = MyConnectionJdbc.getConnection();
            st = con.prepareStatement(quer);
            st.setLong(1, userId);
            rs = st.executeQuery();

            long walletId;
            if (rs.next()) {
                walletId = rs.getLong(4);
                JdbcWallet wallet = new JdbcWallet(walletId);
                wallet.setWalletLogger(SpecialFactory.getNewWalletLogger());
                JdbcUser user = new JdbcUser(rs.getLong(1), rs.getString(2), rs.getString(3), wallet);
                return user;
            } else {
                throw new NoSuchElementException("There is no such user!");
            }
        } finally {
            close(con, st, rs);
        }
    }

    public JdbcUser getUser(String login, String password) throws SQLException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String quer = "SELECT id, "
                    + "       login, "
                    + "       password, "
                    + "       walletId "
                    + "FROM BlackjackUser "
                    + "WHERE login = ? "
                    + "  AND password = ?";

            con = MyConnectionJdbc.getConnection();
            st = con.prepareStatement(quer);
            st.setString(1, login);
            st.setString(2, password);
            rs = st.executeQuery();

            long walletId;
            if (rs.next()) {
                walletId = rs.getLong(4);
                JdbcWallet wallet = new JdbcWallet(walletId);
                wallet.setWalletLogger(SpecialFactory.getNewWalletLogger());
                JdbcUser user = new JdbcUser(rs.getLong(1), rs.getString(2), rs.getString(3), wallet);
                return user;
            } else {
                throw new NoSuchElementException("There is no such user!");
            }
        } finally {
            close(con, st, rs);
        }
    }

    public boolean deleteUser(long userId) throws SQLException {
        Connection con = null;
        PreparedStatement st = null;
        try {
            String quer = "DELETE "
                    + "FROM BlackjackUser bu "
                    + "WHERE bu.id = ?";

            con = MyConnectionJdbc.getConnection();
            st = con.prepareStatement(quer);
            st.setLong(1, userId);
            return st.executeUpdate() == 1;
        } finally {
            close(con, st, null);
        }
    }

    public boolean deleteUser(String login, String password) throws SQLException {
        Connection con = null;
        PreparedStatement st = null;
        try {
            String quer = "DELETE "
                    + "FROM BlackjackUser bu "
                    + "WHERE bu.login = ? "
                    + "  AND bu.password = ?";

            con = MyConnectionJdbc.getConnection();
            st = con.prepareStatement(quer);
            st.setString(1, login);
            st.setString(2, password);
            return st.executeUpdate() == 1;
        } finally {
            close(con, st, null);
        }
    }

    public boolean changeUserPassword(long userId, String newPassword) throws SQLException {
        Connection con = null;
        PreparedStatement st = null;
        try {
            String quer = "UPDATE BlackjackUser "
                    + "SET password = ? "
                    + "WHERE id = ?";

            con = MyConnectionJdbc.getConnection();
            st = con.prepareStatement(quer);
            st.setLong(1, userId);
            st.setString(2, newPassword);
            return st.executeUpdate() >= 1;
        } finally {
            close(con, st, null);
        }
    }

    private void close(Connection con, Statement st, ResultSet rs) throws SQLException {
        if (con != null && !con.isClosed()) {
            con.close();
        }
        if (st != null && !st.isClosed()) {
            st.close();
        }
        if (rs != null && !rs.isClosed()) {
            rs.close();
        }
    }

}
