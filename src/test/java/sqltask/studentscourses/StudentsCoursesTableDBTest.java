package sqltask.studentscourses;

import org.junit.jupiter.api.Test;
import sqltask.ConnectionProviderTest;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class StudentsCoursesTableDBTest {

    ConnectionProviderTest conTests = new ConnectionProviderTest();
    StudentsCoursesTableDB testObjDB = new StudentsCoursesTableDB();
    Connection connection = conTests.getConnection();

    StudentsCoursesTableDBTest() throws SQLException {
    }

    @Test
    void createStdCrsTable_() {

    }

}