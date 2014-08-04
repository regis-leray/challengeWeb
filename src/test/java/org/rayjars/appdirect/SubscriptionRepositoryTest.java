package org.rayjars.appdirect;


import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.rayjars.appdirect.exceptions.AccountNotFoundException;
import org.rayjars.appdirect.exceptions.MaxUsersReachedException;
import org.rayjars.appdirect.exceptions.UserAlreadyExistException;
import org.rayjars.appdirect.exceptions.UserNotFoundException;
import org.rayjars.appdirect.xml.beans.Item;
import org.rayjars.appdirect.xml.beans.Order;
import org.rayjars.appdirect.xml.beans.User;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class SubscriptionRepositoryTest {

    SubscriptionRepository repository = new SubscriptionRepository();

    @Before
    public void createDao() {
        HashMap<String, Subscription> accounts = new HashMap<String, Subscription>();
        Order subscription = new Order().setItems(Lists.newArrayList(new Item().setQuantity(2).setUnit("USER")));
        Subscription accountDomain = new Subscription().setId("1234").setOrder(subscription);
        accountDomain.addUser(new User("dsq1232-432aa"));
        accounts.put("1234", accountDomain);
        repository = new SubscriptionRepository(accounts);
    }


    @Test
    public void shouldCreate() throws Exception {
        Subscription created = repository.create(new Subscription());
        assertThat(created.getId()).isNotNull();
    }

    @Test
    public void shouldCancel() throws Exception {
        repository.cancel("1234");
        assertThat(repository.find("1234").getStatus()).isEqualTo(Subscription.STATUS.CANCELLED);
    }

    @Test(expected = AccountNotFoundException.class)
    public void shouldDeleteFailWhenNoExistAccount() throws Exception {
        repository.cancel("1");

    }

    @Test
    public void shouldFindAll() throws Exception {
        List<Subscription> accounts = repository.all();
        assertThat(accounts).isNotEmpty();
        assertThat(accounts).hasSize(1);

    }

    @Test
    public void shouldUpdate() throws Exception {
        Order order = new Order()
                .setEditionCode("PREMIUM")
                .setPricingDuration("ANNUAL")
                .setItems(Lists.newArrayList(new Item()
                        .setQuantity(1)
                        .setUnit("USER")));

        repository.update("1234", order);

        Subscription updated = repository.find("1234");

        assertThat(updated.getOrder().getPricingDuration()).isEqualTo("ANNUAL");
        assertThat(updated.getOrder().getEditionCode()).isEqualTo("PREMIUM");
        assertThat(updated.getOrder().getItems()).extracting("unit", "quantity").contains(tuple("USER", 1));
    }

    @Test(expected = AccountNotFoundException.class)
    public void shouldUpdateFailWhenAccountNotFound() throws Exception {
        repository.update("1", null);
    }

    @Test
    public void shouldAssignUser() throws Exception {
        repository.assignUser("1234", new User("aaaa12-rrrr12"));

        Subscription found = repository.find("1234");
        assertThat(found.getUsers()).hasSize(2);
        assertThat(found.getUsers()).extracting("uuid").contains("aaaa12-rrrr12", "dsq1232-432aa");
    }

    @Test(expected = MaxUsersReachedException.class)
    public void shouldAssignUserFailWhenReacheddMaxValue() throws Exception {
        repository.assignUser("1234", new User("aaaa12-rrrr12"));
        repository.assignUser("1234", new User("aaaa12-rrrr13"));

    }

    @Test(expected = AccountNotFoundException.class)
    public void shouldAssignUserFailWhenAccountNotFound() throws Exception {
        repository.assignUser("1", new User("aaaa12-rrrr12"));
    }

    @Test(expected = UserAlreadyExistException.class)
    public void shouldAssignUserFailWhenUserAlreadyAssign() throws Exception {
        repository.assignUser("1234", new User("dsq1232-432aa"));
    }

    @Test
    public void shouldUnassignUser() throws Exception {
        repository.unassignUser("1234", new User("dsq1232-432aa"));

        Subscription found = repository.find("1234");
        assertThat(found.getUsers()).isEmpty();
    }

    @Test(expected = AccountNotFoundException.class)
    public void shouldUnassignUserFailWhenAccountNotFound() throws Exception {
        repository.unassignUser("1", new User("dsq1232-432aa"));
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldUnassignUserFailWhenUserNotFound() throws Exception {
        repository.unassignUser("1234", new User("dsq1232"));


    }
}
