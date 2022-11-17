package sqltask.applicationmenu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sqltask.courses.CourseDAOJdbc;
import sqltask.groups.Group;
import sqltask.groups.GroupDaoJdbc;
import sqltask.students.Student;
import sqltask.students.StudentDAOJdbc;

import java.util.List;

@Service
public class AppService {

    @Autowired
    private final StudentDAOJdbc studentDao;
    @Autowired
    private final CourseDAOJdbc courseDao;
    @Autowired
    private final GroupDaoJdbc groupDao;


    public AppService(StudentDAOJdbc studentDao, CourseDAOJdbc courseDao, GroupDaoJdbc groupDao) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
        this.groupDao = groupDao;
    }

    public void save(Student student) {
        studentDao.save(student);
    }

    public void deleteById(int id) {
        studentDao.deleteById(id);
    }

    public List<Student> getCourseMembers(String courseName) {
        return courseDao.getCourseMembers(courseName);
    }

    public void unlinkCourse(int studentID, String courseToDelete) {
        courseDao.unlinkCourse(studentID, courseToDelete);
    }

    public void setNewCourse(int studentID, String courseName) {
        courseDao.setNewCourse(studentID, courseName);
    }

    public List<Group> compareGroups(int groupID) {
        return groupDao.compareGroups(groupID);
    }
}
