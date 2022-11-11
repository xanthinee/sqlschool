package sqltask.connection;

import org.apache.ibatis.annotations.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
public class SpringJdbcConfig {

    Properties properties;

    @Value("${jdbc.driver-class-name:org.postgresql.Driver}")
    private String jdbcDriver;

    @Value( "${jdbc.url:jdbc:postgresql://localhost:5432/postgreSQLTaskFoxminded}" )
    private String jdbcUrl;

    @Value( "${jdbc.username:postgres}")
    private String jdbcUser;

    @Value( "${jdbc.password:7777}")
    private String jdbcPassword;

    @Autowired
    private Environment env;

    @Bean
    public DataSource getDataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty(jdbcDriver));
        dataSource.setUrl(env.getProperty(jdbcUrl));
        dataSource.setUsername(env.getProperty(jdbcUser));
        dataSource.setPassword(env.getProperty(jdbcPassword));

        return dataSource;
    }

//    @Bean
//    public DataSource getDataSource() {
//
//        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.driverClassName(properties.getProperty("spring.datasource.driver-class-name"));
//        dataSourceBuilder.url(properties.getProperty("spring.datasource.url"));
//        dataSourceBuilder.username(properties.getProperty("spring.datasource.username"));
//        dataSourceBuilder.password(properties.getProperty("spring.datasource.password"));
//        return dataSourceBuilder.build();
//    }

//    @Bean
//    public DataSource DataSource() {
//
//        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.driverClassName("org.postgresql.Driver");
//        dataSourceBuilder.url("jdbc:postgresql://localhost:5432/postgreSQLTaskFoxminded");
//        dataSourceBuilder.username("postgres");
//        dataSourceBuilder.password("7777");
//        return dataSourceBuilder.build();
//    }

//    @Bean
//    public DataSource getDataSource() {
//
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setUrl("jdbc:postgresql://localhost:5432/postgreSQLTaskFoxminded");
//        dataSource.setUsername("postgres");
//        dataSource.setPassword("7777");
//        return dataSource;
//    }

//    @Bean
//    public JdbcTemplate JdbcTemplate() {
//        return new JdbcTemplate(getDataSource());
//    }
}