package sqltask;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import sqltask.applicationmenu.Menu;
import sqltask.applicationmenu.AppMenu;

import java.util.List;

@Configuration
@EnableAspectJAutoProxy
public class MenuConfig {

    @Bean
    public AppMenu menuGroup(List<Menu> items) {
        return new AppMenu("SQL APP", items);
    }
}
