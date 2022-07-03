package sqltask.application.methods;

import sqltask.application.menu.IMenu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class PutNewStudent implements IMenu {

    Scanner sc = new Scanner(System.in);

    @Override
    public String getMenuText() {
        return "Put NEW STUDENT to DataBase";
    }
    @Override
    public String doAction(Connection con) {

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "NEW STUDENT WAS ADDED";
    }
}
