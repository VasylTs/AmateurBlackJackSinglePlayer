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
public class JdbcLogger implements IActionLogger {

    private long gameId;


    @Override
    public void logGameAction(boolean isOk, EnumLogAction action, String desc) {
        logPlayerAction(isOk, null, action, desc);
    }

    @Override
    public void logPlayerAction(boolean isOk, Long playerId, EnumLogAction action, String desc) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = "INSERT INTO log_game_action(game_id, work_state, game_action, description, player_id, log_time) values(?, ?, ?, ?, ?, ?)";
            con = MyConnectionJdbc.getConnection();
            st = con.prepareCall(query);
            st.setLong(1, gameId);
            st.setString(2, isOk ? "OK" : "ERROR");
            st.setString(3, action.toString());
            if (desc == null) {
                st.setNull(4, Types.VARCHAR);
            } else {
                st.setString(4, desc);
            }
            if (playerId == null) {
                st.setNull(5, Types.BIGINT);
            } else {
                st.setLong(5, playerId);
            }
            java.util.Date utilDate = new java.util.Date();
            st.setTimestamp(6, new Timestamp(utilDate.getTime()));
            
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

    @Override
    public void setGameId(long id) {
        gameId = id;
    }
}
