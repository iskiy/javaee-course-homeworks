package library;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LibraryAutoConfig {

    @Bean
    @ConditionalOnProperty(value = "property", havingValue = "enable")
    ComponentEnableProperty componentEnableProperty() {
        return new ComponentEnableProperty();
    }

    @Bean
    @ConditionalOnBean(ComponentEnableProperty.class)
    ComponentOnBeanEnable componentOnBeanEnable() {return new ComponentOnBeanEnable();}

    @Bean
    @ConditionalOnProperty(value = "property", havingValue = "disable")
    ComponentDisableProperty componentDisableProperty() {
        return new ComponentDisableProperty();
    }

    @Bean
    @ConditionalOnMissingBean(ComponentDisableProperty.class)
    ComponentOnBeanDisable componentOnBeanDisable() {return new ComponentOnBeanDisable();}
}
