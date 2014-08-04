package org.rayjars.appdirect;

import org.rayjars.appdirect.web.HeaderAuthorizationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@ComponentScan(value = "org.rayjars.appdirect.web", excludeFilters = @ComponentScan.Filter(value= Configuration.class, type = FilterType.ANNOTATION))
@EnableWebMvc
@Configuration
public class WebConfig {

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver(){
        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(org.springframework.web.servlet.view.JstlView.class);
        viewResolver.setOrder(1);
        return viewResolver;
    }
}
