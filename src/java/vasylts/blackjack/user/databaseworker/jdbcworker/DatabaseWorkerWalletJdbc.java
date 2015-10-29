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

/**
 *
 * @author VasylcTS
 */
public class DatabaseWorkerWalletJdbc {

    public Long createNewWallet() throws SQLException {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            Long walletId = null;
            String query = "insert into UserWallet(Balance) values(0)";
            con = MyConnectionJdbc.getConnection();
            st = con.createStatement();
            st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            rs = st.getGeneratedKeys();
            if (rs.next()) {
                walletId = rs.getLong(1);
            }
            return walletId;
        } finally {
            close(con, st, rs);
        }
    }

    public Double getBalance(long walletId) throws SQLException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            Double balance = null;
            String query = "select balance from UserWallet where id = ?";

            con = MyConnectionJdbc.getConnection();
            st = con.prepareStatement(query);
            st.setLong(1, walletId);
            rs = st.executeQuery();

            if (rs.next()) {
                balance = rs.getDouble(1);
            }
            return balance;
        } finally {
            close(con, st, rs);
        }
    }

    public boolean changeBalance(long walletId, double money) throws SQLException {
        Connection con = null;
        PreparedStatement st = null;
        try {
            String query = "update UserWallet set balance = balance + ? where id = ?";

            con = MyConnectionJdbc.getConnection();
            st = con.prepareStatement(query);
            st.setDouble(1, money);
            st.setLong(2, walletId);
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
