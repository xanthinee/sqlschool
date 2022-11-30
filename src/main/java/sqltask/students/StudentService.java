package sqltask.students;

import org.springframework.stereotype.Service;
import sqltask.groups.Group;
import sqltask.groups.GroupDAO;
import sqltask.helpers.CustomFileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@SuppressWarnings("java:S106")
public class StudentService {

    private final StudentDAO studentDao;
    private final GroupDAO groupDao;
    private static final int TOTAL_AMOUNT_OF_STUDENTS = 200;
    private static final int MIN_AMOUNT_OF_STUDENTS_IN_GROUP = 10;
    private static final int MAX_AMOUNT_OF_STUDENTS_IN_GROUP = 30;
    private static final Random rd = new Random();

    public StudentService(StudentDAO studentDao, GroupDAO groupDao) {
        this.studentDao = studentDao;
        this.groupDao = groupDao;
    }

    public void deleteAll() {
        studentDao.deleteAll();
    }

    public List<Student> getAll() {
        return studentDao.getAll();
    }

    public Student getById(int id) {
        return studentDao.getById(id);
    }

    public void deleteById(int id) {
        studentDao.deleteById(id);
    }

    public void save(Student student) {
        studentDao.save(student);
    }
    public void saveAll(List<Student> students) {
        studentDao.saveAll(students);
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

        List<Group> groups = groupDao.getAll();
        List<Student> groupedStudents = new ArrayList<>();

        for (Group group : groups) {
            int groupMembers = rd.nextInt(MIN_AMOUNT_OF_STUDENTS_IN_GROUP, MAX_AMOUNT_OF_STUDENTS_IN_GROUP + 1);
                for (int i = 0; i < groupMembers; i++) {
                    if (!students.isEmpty()) {
                        int studIndex = rd.nextInt(0, students.size());
                        Student student = students.remove(studIndex);
                        student.setGroupId(group.getId());
                        groupedStudents.add(student);
                        if (students.isEmpty()) {
                            break;
                        }
                    }
                }
        }
        groupedStudents.addAll(students);
        return groupedStudents;
    }
}
