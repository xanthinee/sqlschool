package sqltask;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import sqltask.applicationmenu.AppMenu;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
class AppRunnerTest {

    @ContextConfiguration(initializers = {ContainersConfig.Initializer.class})
    @Nested
    @SpringBootTest(classes = ContainersConfig.class)
    @ActiveProfiles("test")
    public class AppRunnerWithContextTest {
        @MockBean
        AppRunner appRunner;
        @Test
        void whenContextLoads_thenRunnersRun() {
            verify(appRunner, times(1)).run(any());
        }
    }

    @Nested
    @ExtendWith(MockitoExtension.class)
    public class AppRunnerWithoutContextTest {

        @InjectMocks
        AppRunner appRunner;
        @Mock
        InitializeService initializeService;
        @Mock
        AppMenu appMenu;

        @Test
        public void appStart() {
            appRunner.run(null);
            verify(initializeService, times(1)).initializeTables();
            verify(appMenu, times(1)).doAction();
        }
    }
}