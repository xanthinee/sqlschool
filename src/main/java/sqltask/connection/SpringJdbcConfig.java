package sqltask.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
//import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@ComponentScan("sqltask")
public class SpringJdbcConfig {

    private static final String PATH_FOR_DATA_SOURCE = "application.properties";

    Properties properties;
    public SpringJdbcConfig() {
        try {
            this.properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream(PATH_FOR_DATA_SOURCE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
