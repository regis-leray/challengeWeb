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

@Configuration
public class SecurityConfig extends WebMvcConfigurerAdapter {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationHandler()).addPathPatterns("/user/*", "/subscription/*");
    }

    @Bean
    public HeaderAuthorizationHandler authorizationHandler(){
        return new HeaderAuthorizationHandler();
    }

    @Bean
    public OAuth oAuth(){
        return new OAuth();
    }

}
