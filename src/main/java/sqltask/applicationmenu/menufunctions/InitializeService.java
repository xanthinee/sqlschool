package sqltask.applicationmenu.menufunctions;

import org.springframework.boot.ApplicationArguments;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;
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
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
@Component
@Service
public class InitializeService implements ApplicationRunner {

    @Autowired
    private final StudentDAOJdbc studentDao;
    @Autowired
    private final CourseDAOJdbc courseDao;
    @Autowired
    private final GroupDaoJdbc groupDao;
    private final JdbcTemplate jdbcTemplate;
    private static final String STUDENT_TABLE = "students";
    private static final String COURSE_TABLE = "courses";
    private static final String GROUP_TABLE = "groups";
    private static final String STUDENTS_COURSES_TABLE = "students_courses";
    private static final String SQL_SCRIPT_PATH = "src/main/resources/sqldata/tables_creation.sql";
    private static final String COURSES_LIST_PATH = "data/courses.txt";
    public InitializeService(StudentDAOJdbc studentDao, CourseDAOJdbc courseDao, GroupDaoJdbc groupDao, JdbcTemplate jdbcTemplate) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
        this.groupDao = groupDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        CourseService courseService = new CourseService(courseDao, studentDao);
        GroupService groupService = new GroupService(groupDao);
        StudentService studentService = new StudentService(studentDao, groupDao);

        DataSource ds = jdbcTemplate.getDataSource();
        if (ds != null) {
            try (Connection connection = DataSourceUtils.getConnection(ds)){
                DatabaseMetaData databaseMetaData = connection.getMetaData();
                ResultSet rs = databaseMetaData.getTables(null, null, null,new String[] {"TABLE"});
                List<String> required = new ArrayList<>(Arrays.asList(STUDENT_TABLE,GROUP_TABLE,COURSE_TABLE,STUDENTS_COURSES_TABLE));
                HashSet<String> tableNames = new HashSet<>();
                while (rs.next()) {
                    tableNames.add(rs.getString("Table_NAME"));
                }
                if (!tableNames.containsAll(required)) {
                    ScriptRunner sr = new ScriptRunner(connection);
                    Reader reader = new BufferedReader(new FileReader(SQL_SCRIPT_PATH));
                    sr.runScript(reader);

                    List<Course> courses = CourseUtils.makeCoursesList(COURSES_LIST_PATH);
                    courseDao.saveAll(courses);

                    List<Group> groups = groupService.generateGroups();
                    groupDao.saveAll(groups);

                    List<Student> students = studentService.setGroupsId(studentService.generateStudents());
                    studentDao.saveAll(students);

                    courseService.createStdCrsTable();
                }
            } catch (SQLException | FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

