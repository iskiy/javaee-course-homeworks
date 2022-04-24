package library;

import org.springframework.beans.factory.InitializingBean;

public class ComponentOnBeanEnable implements InitializingBean, MyComponent {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Initializing the ComponentOnBeanEnable bean");
    }
}
