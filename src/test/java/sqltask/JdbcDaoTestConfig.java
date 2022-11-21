package sqltask;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.testcontainers.containers.PostgreSQLContainer;
import sqltask.courses.CourseService;
import sqltask.groups.GroupService;
import sqltask.students.StudentService;

@TestConfiguration
public class JdbcDaoTestConfig {

    private final static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("postgreSQLTaskFoxmindedTests")
            .withUsername("postgres")
            .withPassword("7777")
            .withInitScript("sqldata/tables_creation.sql");
    static {
        postgreSQLContainer.start();
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Bean
    @Primary
    public CourseService mockCourseService() {
        return Mockito.mock(CourseService.class);
    }

    @Bean
    @Primary
    public GroupService mockGroupService() {
        return Mockito.mock(GroupService.class);
    }

    @Bean
    @Primary
    public StudentService mockStudentService() {
        return Mockito.mock(StudentService.class);
    }
}
