package org.rayjars.appdirect.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.rayjars.appdirect.SubscriptionRepository;
import org.rayjars.appdirect.OAuth;
import org.rayjars.appdirect.TestHelper;
import org.rayjars.appdirect.WebConfig;
import org.rayjars.appdirect.xml.XmlReader;
import org.rayjars.appdirect.xml.beans.Account;
import org.rayjars.appdirect.xml.beans.Event;
import org.rayjars.appdirect.xml.beans.User;
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
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, UserControllerTest.Config.class})
@WebAppConfiguration
public class UserControllerTest {

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    UserController controller;

    @Autowired
    SubscriptionRepository accountDao;

    @Autowired
    XmlReader eventReader;

    @Configuration
    public static class Config {
        @Bean
        public SubscriptionRepository accountDao() {
            return mock(SubscriptionRepository.class);
        }

        @Bean
        public XmlReader eventReader() {
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
        Mockito.reset(accountDao, eventReader);
    }


    @Test
    public void shouldAssignWhenAccountExist() throws Exception {

        Event value = TestHelper.emptyEvent();
        value.getPayload().setAccount(new Account("1234"));
        value.getPayload().setUser(new User("4321"));
        when(eventReader.read(anyString(), Matchers.<Class<Object>>any())).thenReturn(value);

        mockMvc.perform(get("/user/assign")
                .param("url", "https://www.appdirect.com/rest/api/events/dummyAssign")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_XML))
                .andExpect(xpath("/result/success").booleanValue(true))
                .andExpect(xpath("/result/message").string(containsString("Assign user with success")))
                .andExpect(xpath("/result/errorCode").doesNotExist());


    }


    @Test
    public void shouldUnassignWhenAccountAndUserExist() throws Exception {

        Event value = TestHelper.emptyEvent();
        value.getPayload().setAccount(new Account("1234"));
        value.getPayload().setUser(new User("4321"));
        when(eventReader.read(anyString(), Matchers.<Class<Object>>any())).thenReturn(value);

        mockMvc.perform(get("/user/unassign")
                .param("url", "https://www.appdirect.com/rest/api/events/dummyUnAssign")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_XML))
                .andExpect(xpath("/result/success").booleanValue(true))
                .andExpect(xpath("/result/message").string(containsString("UnAssign user with success")))
                .andExpect(xpath("/result/errorCode").doesNotExist());


    }
}

