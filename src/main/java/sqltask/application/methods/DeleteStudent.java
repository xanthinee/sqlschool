package sqltask.application.methods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteStudent {

    Scanner sc = new Scanner(System.in);

    public void deleteStudent(Connection con) {

        System.out.println("Enter ID of student: ");
        int studentID = sc.nextInt();
        try (Connection connection = con;
             PreparedStatement deleteFromStudents = connection.prepareStatement("delete from students where student_id = ?")) {
            deleteFromStudents.setInt(1, studentID);
            deleteFromStudents.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
