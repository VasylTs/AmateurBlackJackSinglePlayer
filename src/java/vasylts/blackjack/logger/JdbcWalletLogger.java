/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import vasylts.blackjack.user.databaseworker.jdbcworker.MyConnectionJdbc;

/**
 *
 * @author VasylcTS
 */
public class JdbcWalletLogger implements IWalletLogger {

    @Override
    public void logChangingBalance(long walletId, double change, String shortDescription) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = "INSERT INTO log_wallet_action(wallet_id, change, description, log_time) values(?, ?, ?, ?)";
            con = MyConnectionJdbc.getConnection();
            st = con.prepareCall(query);
            st.setLong(1, walletId);
            st.setDouble(2, change);
            if (shortDescription == null) {
                st.setNull(3, Types.VARCHAR);
            } else {
                st.setString(3, shortDescription);
            }
            java.util.Date utilDate = new java.util.Date();
            st.setTimestamp(4, new Timestamp(utilDate.getTime()));
            st.execute();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            close(con, st, rs);
        }
    }

    private void close(Connection con, Statement st, ResultSet rs) {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
            if (st != null && !st.isClosed()) {
                st.close();
            }
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
