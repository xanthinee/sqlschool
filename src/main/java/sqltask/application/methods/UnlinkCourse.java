package sqltask.application.methods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import sqltask.application.menu.IMenu;
import sqltask.application.methods.courseaddition.*;

@SuppressWarnings("java:S106")
public class UnlinkCourse implements IMenu {

    Scanner sc = new Scanner(System.in);

    @Override
    public String getMenuText() {
        return "Unlink COURSE from STUDENT";
    }

    @Override
    public String doAction(Connection con) {

        CourseAdditionMethods studCourses = new CourseAdditionMethods();
        CourseAddition newCourse = new CourseAddition();
        System.out.println("Enter student_id of STUDENT: ");
        int studentID = sc.nextInt();
        List<String> coursesOfStudent = newCourse.getCoursesOfStudent(con, studentID);
        System.out.println(studCourses.printCoursesOfStud(coursesOfStudent));
        System.out.println("You can DELETE one of them - ENTER bellow it's NAME: ");
        String courseToDelete = sc.next();
        try (Connection connection = con;
             PreparedStatement unlinkCourse = connection.prepareStatement("delete from students_courses sc " +
                     "using courses c " +
                     "where c.course_id = sc.course_id and " +
                     "sc.student_id = ? and c.course_name = ?")) {
            unlinkCourse.setInt(1, studentID);
            unlinkCourse.setString(2, courseToDelete);
            unlinkCourse.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "COURSE WAS UNLINKED";
    }
}
