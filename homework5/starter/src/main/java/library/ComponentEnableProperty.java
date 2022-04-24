package library;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class ComponentEnableProperty implements InitializingBean, MyComponent {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Initializing the ComponentEnableProperty bean");
    }
}
