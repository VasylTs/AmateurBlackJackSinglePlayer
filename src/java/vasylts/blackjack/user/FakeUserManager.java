/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.user;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import vasylts.blackjack.logger.FakeWalletLogger;
import vasylts.blackjack.main.SpecialFactory;
import vasylts.blackjack.user.wallet.FakeWallet;
import vasylts.blackjack.user.wallet.IWallet;

/**
 * Test implementation of IUserManager
 * @author VasylcTS
 */
public class FakeUserManager implements IUserManager {

    private final Set<IUser> users = new HashSet<>();

    public Long addUser(IUser user) {
        if (user != null) {
            Predicate<IUser> userAlreadyExists = (IUser someUser) -> {
                    return Objects.equals(user.getLogin(), someUser.getLogin());
            };
            if (!users.stream().anyMatch(userAlreadyExists)) {
                users.add(user);
                return users.size() - 1l;
            }
        }
        return null;
    }

    @Override
    public Long createUser(String login, String password) {
        Random rand = new Random();
        IUser user = new FakeUser(rand.nextLong(), login, password, new FakeWallet());
        user.getWallet().setWalletLogger(SpecialFactory.getNewWalletLogger());
        return addUser(user);
    }
    
    @Override
    public boolean deleteUser(Long id) {
        boolean isUserDeleted;
        try {
            isUserDeleted =  users.remove(getUser(id));
        } catch (NoSuchElementException ex) {
            isUserDeleted =  false;
        }
        return isUserDeleted;
    }
    
    @Override
    public boolean deleteUser(String login, String password) {
        boolean isUserDeleted;
        try {
            isUserDeleted =  users.remove(getUser(login, password));
        } catch (NoSuchElementException ex) {
            isUserDeleted =  false;
        }
        return isUserDeleted;
    }

    @Override
    public IUser getUser(Long id) {
        Optional<IUser> optUser = users.stream().filter((IUser us) -> {
            return Objects.equals(us.getId(), id);
        }).findFirst();

        if (optUser.isPresent()) {
            return optUser.get();
        } else {
            throw new NoSuchElementException("There is no such user!");
        }
    }

    @Override
    public IUser getUser(String login, String password) {
        Predicate<IUser> findUser = searchForUser -> {
            return Objects.equals(searchForUser.getLogin(), login) && Objects.equals(searchForUser.getPassword(), password);
        };
        Optional<IUser> optUser = users.stream().filter(findUser).findFirst();

        if (optUser.isPresent()) {
            return optUser.get();
        } else {
            throw new NoSuchElementException("There is no such user in our game!");
        }
    }
    
    public int getUserCount() {
        return users.size();
    }
}
