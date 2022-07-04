package sqltask.applicationmenu;

import sqltask.connection.ConnectionProvider;
import sqltask.courses.CoursesTableDB;
import sqltask.groups.GroupsTableDB;
import sqltask.students.StudentsTableDB;
import sqltask.studentscourses.StudentsCoursesTableDB;

import java.sql.SQLException;

public class Controller {

    ConnectionProvider conProvider = new ConnectionProvider();
    GroupsTableDB groupDB = new GroupsTableDB();
    StudentsCoursesTableDB stdCrsDB = new StudentsCoursesTableDB();
    StudentsTableDB studentsDB = new StudentsTableDB();
    CoursesTableDB coursesDB = new CoursesTableDB();

    public Object compareGroup() throws SQLException {
        return groupDB.compareGroups(conProvider.getConnection());
    }

    public Object findStudentsByCourse() throws SQLException{
        return stdCrsDB.getCourseMembers(conProvider.getConnection());
    }

    public Object addStudent() throws SQLException{
        return studentsDB.putNewStudent(conProvider.getConnection());
    }

    public Object deleteStudent() throws SQLException {
        return studentsDB.deleteStudent(conProvider.getConnection());
    }

    public Object setNewCourse() throws SQLException {
        return coursesDB.setNewCourse(conProvider.getConnection());
    }

    public Object unlinkCourse() throws SQLException {
        return stdCrsDB.unlinkCourse(conProvider.getConnection());
    }


}
