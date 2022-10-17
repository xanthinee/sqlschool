package sqltask.students;

import sqltask.applicationmenu.DAO;

public interface StudentDAO extends DAO<Student>{

    void addNewStudent(String name, String surname, int groupID);
}
