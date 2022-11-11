package sqltask.students;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import sqltask.JdbcDaoTestConfig;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@Import(JdbcDaoTestConfig.class)
@SpringBootTest
public class StudentDAOJdbcTest {

    @Autowired
    StudentDAOJdbc dao;
    @Autowired
    JdbcTemplate jdbc;

//    @Autowired
//    public StudentDAOJdbcTest(JdbcTemplate jdbc) {
//        this.jdbc = jdbc;
//    }

    @Test
    public void deleteById_shouldDeleteExistingRow() {
        jdbc.execute("INSERT INTO students VALUES (42, 'foo', 'bar')");
        dao.deleteById(42);
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "courses"));
    }
}
