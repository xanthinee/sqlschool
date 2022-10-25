package sqltask.students;

import sqltask.groups.Group;
import sqltask.groups.GroupDAO;
import sqltask.helpers.CustomFileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("java:S106")
public class StudentService {

    private final StudentDAO dao;
    private final GroupDAO groupDAO;
    private static final int TOTAL_AMOUNT_OF_STUDENTS = 200;
    private static final Random rd = new Random();

    public StudentService(StudentDAO dao, GroupDAO groupDAO) {
        this.dao = dao;
        this.groupDAO = groupDAO;
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

    public List<Student> generateStudents() {

        CustomFileReader fileCon = new CustomFileReader();
        List<String> names = fileCon.readFile("data/names.txt").toList();
        List<String> surnames = fileCon.readFile("data/surnames.txt").toList();
        List<Student> students = new ArrayList<>();

        for (int i = 0; i < TOTAL_AMOUNT_OF_STUDENTS; i++) {
            students.add(new Student(null, null,
                    names.get(rd.nextInt(0, names.size())),
                    surnames.get(rd.nextInt(0, surnames.size()))));
        }
        return students;
    }

    public void setGroupsToStudents() {

        List<Student> students = dao.getAll();
        List<Group> groups = groupDAO.getAll();
        for (Group group : groups) {
            int groupMembers = rd.nextInt(10, 30);
                for (int i = 0; i < groupMembers; i++) {
                    int studIndex = rd.nextInt(0, students.size());
                    Student student = students.get(studIndex);
                    if (student.getGroupId() == 0) {
                        dao.updateGroupIdByStudId(student, group.getId());
                        students.remove(studIndex);
                        if (students.isEmpty()) {
                            break;
                        }
                    }
                }
        }
    }
}
