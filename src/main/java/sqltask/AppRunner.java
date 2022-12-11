package sqltask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import sqltask.applicationmenu.AppMenu;

import java.util.Arrays;

@Component
@Profile("!test")

public class AppRunner implements ApplicationRunner {

    @Autowired
    private Environment environment;
    private final InitializeService initializeService;
    private final AppMenu appMenu;

    public AppRunner(InitializeService initializeService, AppMenu appMenu) {
        this.initializeService = initializeService;
        this.appMenu = appMenu;
    }

    @Override
    public void run(ApplicationArguments args) {
        initializeService.initializeTables();
        appMenu.doAction();
    }
}
