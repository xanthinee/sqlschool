package sqltask.application.methods;

import lombok.Data;
import sqltask.application.menu.IMenu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteStudent implements IMenu {

    Scanner sc = new Scanner(System.in);

    @Override
    public String getMenuText() {
        return "Delete STUDENT from DataBase";
    }
    @Override
    public String doAction(Connection con) {

        System.out.println("Enter ID of student: ");
        int studentID = sc.nextInt();
        try (Connection connection = con;
             PreparedStatement deleteFromStudents = connection.prepareStatement("delete from students where student_id = ?")) {
            deleteFromStudents.setInt(1, studentID);
            deleteFromStudents.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "STUDENT WAS DELETED";
    }
}
