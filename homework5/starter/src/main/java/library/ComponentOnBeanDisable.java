package library;

import org.springframework.beans.factory.InitializingBean;

public class ComponentOnBeanDisable implements InitializingBean, MyComponent {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Initializing the ComponentOnBeanDisable bean");
    }
}
