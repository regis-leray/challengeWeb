package org.rayjars.appdirect;

import org.rayjars.appdirect.xml.XmlReader;
import org.rayjars.appdirect.xml.XmlWriter;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

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
        return new SubscriptionRepository();
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