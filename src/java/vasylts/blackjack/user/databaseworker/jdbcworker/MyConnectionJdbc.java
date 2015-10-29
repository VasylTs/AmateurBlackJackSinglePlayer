/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.user.databaseworker.jdbcworker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * It`s a bad example of how you can get connection to DB
 *
 * @author Admin
 */
public class MyConnectionJdbc {

    private static final String CONNECTION_STRING = "jdbc:derby://localhost:1527/BlackjackDB";
    private static final String DB_LOGIN = "BlackjackDBUser";
    private static final String DB_PASSWORD = "BlackjackDBPassword";

    static {
        try {
            initConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * It`s a bad example of how you can get connection to DB. Some day I will
     * make it with help of server DataSource and connection pool
     *
     * @return
     * @throws SQLException
     */
    @Deprecated
    static public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_STRING, DB_LOGIN, DB_PASSWORD);
    }

    static private void initConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
    }
}
