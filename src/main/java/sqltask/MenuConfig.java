package sqltask;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sqltask.applicationmenu.Menu;
import sqltask.applicationmenu.AppMenu;

import java.util.List;

@Configuration
public class MenuConfig {

    @Bean
    public AppMenu menuGroup(List<Menu> items) {
        return new AppMenu("SQL APP", items);
    }
}
