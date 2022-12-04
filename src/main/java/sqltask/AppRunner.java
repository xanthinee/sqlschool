package sqltask;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import sqltask.applicationmenu.AppMenu;

@Component
@Profile("!test")
public class AppRunner implements ApplicationRunner {

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
