package sqltask.students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sqltask.groups.Group;
import sqltask.groups.GroupDaoJdbc;
import sqltask.helpers.CustomFileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@SuppressWarnings("java:S106")
public class StudentService {

    @Autowired
    private final StudentDAOJdbc studentDAOJdbc;
    @Autowired
    private final GroupDaoJdbc groupDaoJdbc;
    private static final int TOTAL_AMOUNT_OF_STUDENTS = 200;
    private static final int MIN_AMOUNT_OF_STUDENTS_IN_GROUP = 10;
    private static final int MAX_AMOUNT_OF_STUDENTS_IN_GROUP = 30;
    private static final Random rd = new Random();

    public StudentService(StudentDAOJdbc studentDAOJdbc, GroupDaoJdbc groupDaoJdbc) {
        this.studentDAOJdbc = studentDAOJdbc;
        this.groupDaoJdbc = groupDaoJdbc;
    }

    public void deleteAll() {
        studentDAOJdbc.deleteAll();
    }

    public List<Student> getAll() {
        return studentDAOJdbc.getAll();
    }

    public Student getById(int id) {
        return studentDAOJdbc.getById(id);
    }

    public void deleteById(int id) {
        studentDAOJdbc.deleteById(id);
    }

    public void save(Student student) {
        studentDAOJdbc.save(student);
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

    public List<Student> setGroupsId(List<Student> students) {

        List<Group> groups = groupDaoJdbc.getAll();
        List<Student> groupedStudents = new ArrayList<>();

        for (Group group : groups) {
            int groupMembers = rd.nextInt(0, MAX_AMOUNT_OF_STUDENTS_IN_GROUP + 1);
            if (groupMembers >= MIN_AMOUNT_OF_STUDENTS_IN_GROUP) {
                for (int i = 0; i < groupMembers; i++) {
                    int studIndex = rd.nextInt(0, students.size());
                    Student student = students.get(studIndex);
                    student.setGroupId(group.getId());
                    groupedStudents.add(student);
                    students.remove(studIndex);
                    if (students.isEmpty()) {
                        break;
                    }
                }
            }
        }
        return groupedStudents;
    }
}
