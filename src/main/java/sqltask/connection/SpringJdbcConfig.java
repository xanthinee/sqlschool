package sqltask.connection;

import org.springframework.context.annotation.*;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;

import java.sql.SQLException;

@Configuration
@ComponentScan("sqltask")
public class SpringJdbcConfig {

    private static final String PATH_FOR_DATA_SOURCE = "data/connectioninfo.properties";

    @Bean
    public DataSource getDataSource() {
        return new DataSource(PATH_FOR_DATA_SOURCE);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        try {
            return new JdbcTemplate(getDataSource().getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
