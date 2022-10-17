package sqltask.students;

import sqltask.connection.DataSource;
import sqltask.groups.GroupsTableDB;

import java.util.List;
import java.util.Random;

public class StudentService {

    private final StudentsTableDB dao;
    private final DataSource ds = new DataSource();
    private final Random rd = new Random();

    public StudentService(StudentsTableDB dao) {
        this.dao = dao;
    }

    public void deleteAll() {
        dao.deleteAll();
    }

    public List<Student> getAll() {
        return dao.getAll();
    }

    public Student getById(int id) {
        return dao.getById(id);
    }

    public void deleteById(int id) {
        dao.deleteById(id);
    }

    public void save(Student student) {
        dao.save(student);
    }

    public List<Student> setGroupsToStudents() {

        MethodsForStudents studMethods = new MethodsForStudents();
        List<Student> students = studMethods.generateStudents();
        GroupsTableDB groupsTab = new GroupsTableDB(ds, "groups");
        List<Integer> iDs = groupsTab.groupsIdList();
        for (int id : iDs) {
            int groupMembers = rd.nextInt(0, 31);
            if (groupMembers >= 10) {
                for (int i = 0; i < groupMembers; i++) {
                    students.get(studMethods.generateUniqueNum(0, students.size())).setGroupId(id);
                }
            }
        }
        return students;
    }
}
