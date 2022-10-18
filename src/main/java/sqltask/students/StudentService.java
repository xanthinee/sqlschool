package sqltask.students;

import sqltask.connection.DataSource;
import sqltask.groups.Group;
import sqltask.groups.GroupDAOImpl;

import java.util.List;
import java.util.Random;

@SuppressWarnings("java:S106")
public class StudentService {

    private final StudentDAOImpl dao;
    private final DataSource ds = new DataSource();
    private final Random rd = new Random();

    public StudentService(StudentDAOImpl dao) {
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


        List<Student> students = StudentUtils.generateStudents();
        GroupDAOImpl groupsDAO = new GroupDAOImpl(ds, "groups");
        List<Group> groups = groupsDAO.getAll();
        for (Group group : groups) {
            int groupMembers = rd.nextInt(0, 31);
            if (groupMembers >= 10) {
                for (int i = 0; i < groupMembers; i++) {
                        Student student = students.get(rd.nextInt(0, students.size()));
                        if (student.getGroupId() == null) {
                            student.setGroupId(group.getId());
                        } else {
                            i--;
                        }
                }
            }
        }
        return students;
    }
}
