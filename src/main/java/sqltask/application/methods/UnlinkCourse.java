package sqltask.application.methods;

import sqltask.connection.ConnectionInfoGenerator;
import sqltask.connection.UserConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UnlinkCourse {

    Scanner sc = new Scanner(System.in);

    public void unlinkCourse(Connection con) throws SQLException {

        GiveNewCourseToStudent studCourses = new GiveNewCourseToStudent();
        System.out.println("Enter student_id of STUDENT: ");
        int studentID = sc.nextInt();
        List<String> coursesOfStudent = studCourses.getCoursesOfStudent(con, studentID);
        System.out.println(coursesOfStudent.size());
        System.out.println(studCourses.printCoursesOfStud(coursesOfStudent));
        System.out.println("You can DELETE one of them - ENTER bellow it's NAME: ");
        String courseToDelete = sc.next();
        try (Connection connection = con;
             PreparedStatement unlinkCourse = connection.prepareStatement("delete from students_courses " +
                     "where student_id = ? and course_name = ?")) {
            unlinkCourse.setInt(1, studentID);
            unlinkCourse.setString(2, courseToDelete);
            unlinkCourse.executeUpdate();
        }
    }
}
