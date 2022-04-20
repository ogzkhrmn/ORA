package com.example.orion.core.config;

import com.example.orion.core.i18n.CustomLocaleResolver;
import com.example.orion.core.i18n.Translator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.io.IOException;

@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public InternalResourceViewResolver defaultViewResolver() {
        return new InternalResourceViewResolver();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
        rs.addBasenames("org/hibernate/validator/ValidationMessages");
        try {
            for (Resource resource : applicationContext.getResources("classpath:/messages/**/*.properties")) {
                rs.addBasenames("messages/" + resource.getURL().getPath().split("messages/")[1].split(".properties")[0]);
            }
        } catch (IOException exception) {
            log.error("Message source open error");
        }
        rs.setDefaultEncoding("UTF-8");
        rs.setDefaultLocale(Translator.DEFAULT_LOCALE);
        rs.setUseCodeAsDefaultMessage(false);
        return rs;
    }

    @Bean
    @Override
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    public LocaleResolver localeResolver() {
        CustomLocaleResolver resolver = new CustomLocaleResolver();
        resolver.setDefaultLocale(Translator.DEFAULT_LOCALE);
        return resolver;
    }

}
