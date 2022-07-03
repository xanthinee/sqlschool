package sqltask.application.methods.coursemembers;

import sqltask.students.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentsByCourse {

    Scanner sc = new Scanner(System.in);

    public List<Student> findStudentsByCourse(Connection con) throws SQLException {

        System.out.println("ENTER name of COURSE bellow: ");
        String courseName = sc.next();
        List<Student> students = new ArrayList<>();
        try (Connection connection = con;
             PreparedStatement st = connection.prepareStatement("select s.student_id, s.group_id, s.first_name, s.second_name \n" +
                     "from students s inner join students_courses sc on sc.student_id = s.student_id where sc.course_id = ?")) {
            st.setString(1, courseName);
            ResultSet studentsRS = st.executeQuery();
            while (studentsRS.next()) {
                students.add(new Student(studentsRS.getInt("student_id"), studentsRS.getInt("group_id"),
                        studentsRS.getString("first_name"), studentsRS.getString("second_name")));
            }
        }
        return students;
    }
}
