package sqltask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import sqltask.students.StudentDAOJdbc;

import java.util.Properties;

import javax.sql.DataSource;

@SpringBootTest
@TestConfiguration
public class JdbcDaoTestConfig {

    @Bean
    public DataSource getDataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/postgreSQLTaskFoxmindedTests");
        dataSource.setUsername("postgres");
        dataSource.setPassword("7777");

        return dataSource;
    }

    @Bean
    public JdbcTemplate JdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }
}
