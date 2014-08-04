package org.rayjars.appdirect;


import org.rayjars.appdirect.exceptions.*;
import org.rayjars.appdirect.xml.beans.Order;
import org.rayjars.appdirect.xml.beans.User;

import java.util.*;

public class SubscriptionRepository {

    private final Map<String, Subscription> accounts;

    public SubscriptionRepository() {
        this(new HashMap<String, Subscription>());
    }

    public SubscriptionRepository(Map<String, Subscription> accounts){
        this.accounts = accounts;
    }

    public Subscription find(String id) throws AccountNotFoundException{

        if(!isExist(id)) {
            throw new AccountNotFoundException("the account doesnt exist with id = "+id);
        }

        return accounts.get(id);
    }


	public Subscription create(Subscription account){
        account.setId(UUID.randomUUID().toString());
        accounts.put(account.getId(), account);

        return account;
	}

    private boolean isExist(String id) {
        return accounts.containsKey(id);
    }

	public void delete(String id)
			throws AccountNotFoundException {

        find(id);
        accounts.remove(id);
	}

	public List<Subscription> all() {
        return new ArrayList<Subscription>(accounts.values());
	}

    public Subscription update(String id, Order order) throws AccountNotFoundException {
        Subscription found = find(id);
        found.setSubscription(order);
        return found;
    }


    public void assignUser(String id, User user) throws AppException {
        Subscription found = find(id);

        if(isMaxUserReached(found)){
           throw new MaxUsersReachedException("Max user has been reached "
                   +found.getMaxUser());
        }

        if(!found.addUser(user)){
            throw new UserAlreadyExistException("The user is already assign "
                    +user.getUuid() + " :: " + user.getEmail());
        }

    }

    private boolean isMaxUserReached(Subscription found) {
        return found.getMaxUser() <= found.getUsers().size();
    }

    public void unassignUser(String id, User user) throws AppException {
        Subscription found = find(id);

        if(!found.removeUser(user)){
            throw new UserNotFoundException("Cannot delete the user with id "+user.getUuid());
        }

    }
}
