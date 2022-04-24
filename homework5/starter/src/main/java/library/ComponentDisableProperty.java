package library;

import org.springframework.beans.factory.InitializingBean;

public class ComponentDisableProperty implements InitializingBean, MyComponent {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Initializing the ComponentDisableProperty bean");
    }
}
