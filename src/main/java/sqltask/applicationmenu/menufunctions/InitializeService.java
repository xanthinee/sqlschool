package sqltask.applicationmenu.menufunctions;

import org.springframework.boot.ApplicationArguments;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sqltask.courses.*;
import sqltask.groups.Group;
import sqltask.groups.GroupDaoJdbc;
import sqltask.groups.GroupService;
import sqltask.students.Student;
import sqltask.students.StudentDAOJdbc;
import sqltask.students.StudentService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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

    public InitializeService(StudentDAOJdbc studentDao, CourseDAOJdbc courseDao, GroupDaoJdbc groupDao) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
        this.groupDao = groupDao;
    }


    @Override
    public void run(ApplicationArguments args) {
        CourseService courseService = new CourseService(courseDao, studentDao);
        GroupService groupService = new GroupService(groupDao);
        StudentService studentService = new StudentService(studentDao, groupDao);

        try {
            String url = "jdbc:postgresql://localhost:5432/postgreSQLTaskFoxminded";
            Connection con = DriverManager.getConnection(url, "postgres", "7777");
            ScriptRunner sr = new ScriptRunner(con);
            Reader reader = new BufferedReader(new FileReader("src/main/resources/sqldata/tables_creation.sql"));
            sr.runScript(reader);

            List<Course> courses = CourseUtils.makeCoursesList("data/courses.txt");
            courseDao.saveAll(courses);

            List<Group> groups = groupService.generateGroups();
            groupDao.saveAll(groups);

            List<Student> students = studentService.setGroupsId(studentService.generateStudents());
            studentDao.saveAll(students);

            courseService.createStdCrsTable();

        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

