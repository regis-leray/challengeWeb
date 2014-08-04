package org.rayjars.appdirect;



import org.rayjars.appdirect.xml.beans.Company;
import org.rayjars.appdirect.xml.beans.Item;
import org.rayjars.appdirect.xml.beans.Order;
import org.rayjars.appdirect.xml.beans.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;



public class Subscription {

    public static final String USER = "USER";
    private String id;

    private STATUS status = STATUS.ACTIVE;

    private Integer maxUser;

    private Company company;

    private Order subscription;

    private Map<String, User> users = new HashMap<String, User>();


    public String getId() {
        return id;
    }

    public Subscription setId(String id) {
        this.id = id;
        return this;
    }

    public Order getSubscription() {
        return subscription;
    }

    public Subscription setSubscription(Order subscription) {
        this.subscription = subscription;
        return this;
    }

    public Integer getMaxUser(){
        Integer max = 0;
        for(Item item : subscription.getItems()){
            if(item.getUnit().equalsIgnoreCase(USER)){
                max = item.getQuantity();
                break;
            }

        }

        return max;

    }

    public boolean addUser(User user) {
        if(isExist(user)){
            return false;
        }

        users.put(user.getUuid(), user);

        return true;
    }

    private boolean isExist(User user) {
        return users.containsKey(user.getUuid());
    }


    public boolean removeUser(User user) {
        if(!isExist(user)){
            return false;
        }

        users.remove(user.getUuid());
        return true;
    }


    public Collection<User> getUsers() {
        return users.values();
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public STATUS getStatus() {
        return status;
    }


    public Company getCompany() {
        return company;
    }

    public Subscription setCompany(Company company) {
        this.company = company;
        return this;
    }

    public enum STATUS {
        FREE_TRIAL,  FREE_TRIAL_EXPIRED, ACTIVE, SUSPENDED, CANCELLED;
    }
}
