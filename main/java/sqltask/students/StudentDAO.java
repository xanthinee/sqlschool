package sqltask.students;

import sqltask.applicationmenu.DAO;

import java.util.List;

public interface StudentDAO extends DAO<Student>{

    void saveAll(List<Student> students);
    void save(Student entity);
    void updateGroupIdByStudId(Student student, int groupID);
}
