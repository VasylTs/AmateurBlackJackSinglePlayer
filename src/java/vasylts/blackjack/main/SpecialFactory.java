/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.main;

import vasylts.blackjack.logger.FakeActionLogger;
import vasylts.blackjack.logger.FakeWalletLogger;
import vasylts.blackjack.logger.IActionLogger;
import vasylts.blackjack.logger.IWalletLogger;
import vasylts.blackjack.logger.JdbcLogger;
import vasylts.blackjack.logger.JdbcWalletLogger;
import vasylts.blackjack.user.FakeUserManager;
import vasylts.blackjack.user.HibernateUserManager;
import vasylts.blackjack.user.IUserManager;
import vasylts.blackjack.user.JdbcUserManager;

/**
 *
 * @author VasylcTS
 */
public class SpecialFactory {

    enum DatabaseWorker {

        FAKE,
        JDBC,
        HIBERNATE;
    }

    private static DatabaseWorker dbWorkerType = DatabaseWorker.FAKE;
    private static IUserManager userManager = null;

    public static IUserManager getStandartUserManager() {
        if (userManager == null) {
            switch (dbWorkerType) {
                case FAKE: {
                    userManager = new FakeUserManager();
                    break;
                }
                case JDBC: {
                    userManager = new JdbcUserManager();
                    break;
                }
                case HIBERNATE: {
                    userManager = new HibernateUserManager();
                    break;
                }
                default: {
                    throw new RuntimeException("Unsupported enum type: " + dbWorkerType.name());
                }
            }
        }
        return userManager;
    }

    public static IActionLogger getNewActionLogger() {
        IActionLogger loggerInstance;
        switch (dbWorkerType) {
            case FAKE: {
                loggerInstance = new FakeActionLogger();
                break;
            }
            case JDBC: {
                loggerInstance = new JdbcLogger();
                break;
            }
            case HIBERNATE: {
                throw new RuntimeException("Unsupported enum type: " + dbWorkerType.name());
            }
            default: {
                throw new RuntimeException("Unsupported enum type: " + dbWorkerType.name());
            }
        }
        return loggerInstance;
    }
    
    public static IWalletLogger getNewWalletLogger() {
        IWalletLogger loggerInstance;
        switch (dbWorkerType) {
            case FAKE: {
                loggerInstance = new FakeWalletLogger();
                break;
            }
            case JDBC: {
                loggerInstance = new JdbcWalletLogger();
                break;
            }
            case HIBERNATE: {
                throw new RuntimeException("Unsupported enum type: " + dbWorkerType.name());
            }
            default: {
                throw new RuntimeException("Unsupported enum type: " + dbWorkerType.name());
            }
        }
        return loggerInstance;
    }

}
