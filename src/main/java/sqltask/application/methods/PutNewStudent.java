package sqltask.application.methods;

import sqltask.connection.ConnectionInfoGenerator;
import sqltask.connection.UserConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class PutNewStudent {

    Scanner sc = new Scanner(System.in);

    public void putNewStudent(Connection con) throws SQLException {

        System.out.println("Enter NAME of student: ");
        String studentName = sc.next();
        System.out.println("Enter SURNAME of student");
        String studentSurname = sc.next();
        System.out.println("Enter ID of GROUP which new STUDENT will have bellow: ");
        int groupId = sc.nextInt();

        try (Connection connection = con;
             PreparedStatement putStudent = connection.prepareStatement("insert into students values (default,?,?,?)")) {
            putStudent.setInt(1,groupId);
            putStudent.setString(2, studentName);
            putStudent.setString(3, studentSurname);
            putStudent.executeUpdate();
        }
    }
}
