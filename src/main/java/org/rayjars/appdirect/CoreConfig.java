package org.rayjars.appdirect;

import com.google.common.collect.Lists;
import org.rayjars.appdirect.xml.XmlReader;
import org.rayjars.appdirect.xml.XmlWriter;
import org.rayjars.appdirect.xml.beans.Item;
import org.rayjars.appdirect.xml.beans.Order;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CoreConfig {

    @Bean
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer(){
        PropertyPlaceholderConfigurer prop = new PropertyPlaceholderConfigurer();
        prop.setLocation(new ClassPathResource("app.properties"));
        return prop;
    }

    @Bean
    public SubscriptionRepository repository(){
        Map<String, Subscription> dummies = new HashMap<>();
        dummies.put("dummy-account", new Subscription()
                .setId("dummy-account")
                .setOrder(new Order().setEditionCode("BASIC")
                .setPricingDuration("MONTHLY")));

        SubscriptionRepository subscriptionRepository = new SubscriptionRepository(dummies);
        return subscriptionRepository;
    }

    @Bean
    public XmlReader reader(){
        return new XmlReader();
    }

    @Bean
    public XmlWriter writer(){
        return new XmlWriter();
    }
}