package org.rayjars.appdirect.web;



import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.rayjars.appdirect.SubscriptionRepository;
import org.rayjars.appdirect.Subscription;
import org.rayjars.appdirect.OAuth;
import org.rayjars.appdirect.WebConfig;
import org.rayjars.appdirect.exceptions.AccountNotFoundException;
import org.rayjars.appdirect.exceptions.UnknownErrorException;
import org.rayjars.appdirect.xml.XmlReader;
import org.rayjars.appdirect.xml.beans.Account;
import org.rayjars.appdirect.xml.beans.Event;
import org.rayjars.appdirect.xml.beans.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.rayjars.appdirect.TestHelper.emptyEvent;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, SubscriptionControllerTest.Config.class})
@WebAppConfiguration
public class SubscriptionControllerTest {

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    SubscriptionController controller;

    @Autowired
    SubscriptionRepository accountDao;

    @Autowired
    XmlReader xmlReader;

    @Configuration
    public static class Config{
        @Bean
        public SubscriptionRepository accountDao(){
            return mock(SubscriptionRepository.class);
        }

        @Bean
        public XmlReader xmlReader(){
            return mock(XmlReader.class);
        }

        @Bean
        public OAuth oAuth(){
            return new OAuth();
        }

    }

    @Before
    public void setUp() {
        //We have to reset our mock between tests because the mock objects
        //are managed by the Spring container. If we would not reset them,
        //stubbing and verified behavior would "leak" from one test to another.
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Mockito.reset(accountDao, xmlReader);
    }

    @Test
    public void shouldOrderWhenAccount() throws Exception {
        when(xmlReader.read(anyString(), Matchers.<Class<Object>>any())).thenReturn(emptyEvent());
        when(accountDao.create(any(Subscription.class))).thenReturn(new Subscription().setId("1234"));

        //String url = urlFromClasspath("dummyOrder.xml");

        mockMvc.perform(get("/subscription/order")
                       .param("url", "https://www.appdirect.com/rest/api/events/dummyOrder"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_XML))
                .andExpect(xpath("/result/success").booleanValue(true))
                .andExpect(xpath("/result/accountIdentifier").string("1234"))
                .andExpect(xpath("/result/errorCode").string(""));
    }

    @Test
    public void shouldOrderFailWhenUrlIsNotReachable() throws Exception {
        when(xmlReader.read(anyString(), Matchers.<Class<Object>>any())).thenThrow(new UnknownErrorException("Url is invalid"));

        mockMvc.perform(get("/subscription/order")
                .param("url", "https://www.appdirect.com/rest/api/events/dummyOrder")
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_XML))
                .andExpect(xpath("/result/success").booleanValue(false))
                .andExpect(xpath("/result/message").string("Url is invalid"))
                .andExpect(xpath("/result/errorCode").string("UNKNOWN_ERROR"));
    }

    @Test
     public void shouldChangeWhenAccountExist() throws Exception {
        Event event = emptyEvent();
        event.getPayload().setAccount(new Account("1234"));
        when(xmlReader.read(anyString(), Matchers.<Class<Object>>any())).thenReturn(event);
        when(accountDao.update(anyString(), any(Order.class))).thenReturn(new Subscription().setId("1234"));

        mockMvc.perform(get("/subscription/change")
                .param("url", "https://www.appdirect.com/rest/api/events/dummyChange")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_XML))
                .andExpect(xpath("/result/success").booleanValue(true))
                .andExpect(xpath("/result/message").string(containsString("The subscription has been updated")))
                .andExpect(xpath("/result/errorCode").doesNotExist());
    }

    @Test
    public void shouldCancelWhenAccountExist() throws Exception {
        Event event = emptyEvent();
        event.getPayload().setAccount(new Account("1234"));
        when(xmlReader.read(anyString(), Matchers.<Class<Object>>any())).thenReturn(event);

        mockMvc.perform(get("/subscription/cancel")
                .param("url", "https://www.appdirect.com/rest/api/events/dummyCancel")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_XML))
                .andExpect(xpath("/result/success").booleanValue(true))
                .andExpect(xpath("/result/message").string(containsString("The subscription has been deleted")))
                .andExpect(xpath("/result/errorCode").doesNotExist());
    }


    @Test
    public void shouldCancelFailWhenAccountDontExist() throws Exception {
        Event event = emptyEvent();
        event.getPayload().setAccount(new Account("1234"));
        when(xmlReader.read(anyString(), Matchers.<Class<Object>>any())).thenReturn(event);
        doThrow(new AccountNotFoundException("Not found subscription with 1234")).when(accountDao).delete(anyString());

        mockMvc.perform(get("/subscription/cancel")
                .param("url", "https://www.appdirect.com/rest/api/events/dummyCancel")
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_XML))
                .andExpect(xpath("/result/success").booleanValue(false))
                .andExpect(xpath("/result/message").string(containsString("Not found subscription with 1234")))
                .andExpect(xpath("/result/errorCode").string("ACCOUNT_NOT_FOUND"));
    }




}
