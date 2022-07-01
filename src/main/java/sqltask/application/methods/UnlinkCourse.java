package sqltask.application.methods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import sqltask.application.methods.courseaddition.*;

@SuppressWarnings("java:S106")
public class UnlinkCourse {

    Scanner sc = new Scanner(System.in);

    public void unlinkCourse(Connection con) throws SQLException {

        GiveNewCourseMethods studCourses = new GiveNewCourseMethods();
        GiveNewCourse newCourse = new GiveNewCourse();
        System.out.println("Enter student_id of STUDENT: ");
        int studentID = sc.nextInt();
        List<String> coursesOfStudent = newCourse.getCoursesOfStudent(con, studentID);
        System.out.println(coursesOfStudent.size());
        System.out.println(studCourses.printCoursesOfStud(coursesOfStudent));
        System.out.println("You can DELETE one of them - ENTER bellow it's INDEX: ");
        String courseToDelete = sc.next();
        try (Connection connection = con;
             PreparedStatement unlinkCourse = connection.prepareStatement("delete from students_courses sc " +
                     "using courses c " +
                     "where c.course_id = sc.course_id and " +
                     "sc.student_id = ? and c.course_name = ?")) {
            unlinkCourse.setInt(1, studentID);
            unlinkCourse.setString(2, courseToDelete);
            unlinkCourse.executeUpdate();
        }
    }
}
