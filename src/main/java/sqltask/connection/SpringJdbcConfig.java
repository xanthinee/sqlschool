package sqltask.connection;

import org.springframework.context.annotation.*;
//import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@ComponentScan("sqltask")
public class SpringJdbcConfig {

    private static final String PATH_FOR_DATA_SOURCE = "data/application.properties";

    Properties properties;
    public SpringJdbcConfig() {
        try {
            this.properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream(PATH_FOR_DATA_SOURCE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean
    public DataSource getDataSource() {

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(properties.getProperty("spring.datasource.driver-class-name"));
        dataSourceBuilder.url(properties.getProperty("spring.datasource.url"));
        dataSourceBuilder.username(properties.getProperty("spring.datasource.username"));
        dataSourceBuilder.password(properties.getProperty("spring.datasource.password"));
        return dataSourceBuilder.build();
    }

    public Connection getJDBCConnection() throws SQLException {
        return DriverManager.getConnection(properties.getProperty("spring.datasource.url"), properties.getProperty("spring.datasource.username")
                , properties.getProperty("spring.datasource.password"));
    }
    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(getDataSource());
    }
}
