/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.user;

import vasylts.blackjack.user.databaseworker.jdbcworker.DatabaseWorkerUserJdbc;


/**
 *
 * @author VasylcTS
 */
public class JdbcUserManager implements IUserManager {

    @Override
    public Long createUser(String login, String password) {
        try {
            DatabaseWorkerUserJdbc worker = new DatabaseWorkerUserJdbc();
            return worker.createNewUser(login, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public boolean deleteUser(Long id) {
        try {
            DatabaseWorkerUserJdbc worker = new DatabaseWorkerUserJdbc();
            return worker.deleteUser(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public boolean deleteUser(String login, String password) {
        try {
            DatabaseWorkerUserJdbc worker = new DatabaseWorkerUserJdbc();
            return worker.deleteUser(login, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IUser getUser(Long id) {
        try {
            DatabaseWorkerUserJdbc worker = new DatabaseWorkerUserJdbc();
            return worker.getUser(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IUser getUser(String login, String password) {
        
        try {
            DatabaseWorkerUserJdbc worker = new DatabaseWorkerUserJdbc();
            return worker.getUser(login, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

