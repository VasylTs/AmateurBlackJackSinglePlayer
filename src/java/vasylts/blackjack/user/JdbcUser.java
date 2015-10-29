/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.user;

import vasylts.blackjack.user.databaseworker.jdbcworker.DatabaseWorkerUserJdbc;
import vasylts.blackjack.user.wallet.IWallet;
import vasylts.blackjack.user.wallet.JdbcWallet;

/**
 *
 * @author VasylcTS
 */
public class JdbcUser extends FakeUser {

    
    public JdbcUser(Long id, String login, String password, JdbcWallet wallet) {
        super(id, login, password, wallet);
    }

    @Override
    public void changePassword(String newPassword) {
        try {
            DatabaseWorkerUserJdbc worker = new DatabaseWorkerUserJdbc();
            if (worker.changeUserPassword(super.getId(), newPassword)) {
                super.changePassword(newPassword);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}