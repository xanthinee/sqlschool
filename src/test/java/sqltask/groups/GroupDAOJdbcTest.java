package sqltask.groups;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import sqltask.JdbcDaoTestConfig;
import sqltask.Main;
import sqltask.students.StudentDAOJdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(initializers = {GroupDAOJdbcTest.Initializer.class})
@Import(JdbcDaoTestConfig.class)
@SpringBootTest
@Testcontainers
public class GroupDAOJdbcTest {

    @Autowired
    ApplicationContext ctx;

    @Autowired
    GroupDaoJdbc dao;
    @Autowired
    JdbcTemplate jdbc;

    @Container
    public PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14")
            .withDatabaseName("jdbc:postgresql://localhost:5432/postgreSQLTaskFoxmindedTests")
            .withUsername("postgres")
            .withPassword("7777")
            .withInitScript("tables_creation.sql");

    class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    public void deleteById_shouldDeleteExistingRow() {
        jdbc.execute("INSERT INTO groups VALUES (1,'aa-12')");
        dao.deleteById(1);
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "groups"));
    }
}
