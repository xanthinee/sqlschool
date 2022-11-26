package sqltask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import sqltask.courses.*;
import sqltask.groups.Group;
import sqltask.groups.GroupDaoJdbc;
import sqltask.groups.GroupService;
import sqltask.students.Student;
import sqltask.students.StudentDAOJdbc;
import sqltask.students.StudentService;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.*;
import java.sql.*;

@Service
@Profile("!test")
public class InitializeService implements ApplicationRunner {

    private final JdbcTemplate jdbcTemplate;
    private final CourseService courseService;
    private final StudentService studentService;
    private final GroupService groupService;
    private static final String SQL_SCRIPT_PATH = "src/main/resources/sqldata/tables_creation.sql";
    private static final String COURSES_LIST_PATH = "data/courses.txt";
    public InitializeService(JdbcTemplate jdbcTemplate, CourseService courseService,
                             StudentService studentService, GroupService groupService) {
        this.jdbcTemplate = jdbcTemplate;
        this.courseService = courseService;
        this.studentService = studentService;
        this.groupService = groupService;
    }

    @Override
    public void run(ApplicationArguments args) {

        DataSource ds = jdbcTemplate.getDataSource();
        if (ds != null) {
            try (Connection connection = DataSourceUtils.getConnection(ds)) {
                ScriptRunner sr = new ScriptRunner(connection);
                Reader reader = new BufferedReader(new FileReader(SQL_SCRIPT_PATH));
                sr.runScript(reader);

                List<Course> courses = courseService.makeCoursesList(COURSES_LIST_PATH);
                courseService.saveAll(courses);

                List<Group> groups = groupService.generateGroups();
                groupService.saveAll(groups);

                List<Student> students = studentService.setGroupsId(studentService.generateStudents());
                studentService.saveAll(students);

                courseService.createStdCrsTable();

            } catch (SQLException | FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}