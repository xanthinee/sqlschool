package sqltask.students;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class StudentServiceTest {
    @Autowired
    private StudentService studentService;
    @MockBean
    private StudentDAOJdbc studentDao;

    @Test
    void deleteById_shouldBeNotNull() {

        int studId = 10;
        Student expected = new Student(studId, 1, "a", "b");
        Mockito.when(studentDao.getById(studId)).thenReturn(expected);
        assertEquals(expected, studentService.getById(studId));
    }

    @Test
    void save_should() {

        Student studentToSave = new Student(10,1,"a","b");
        studentService.save(studentToSave);
        verify(studentDao, times(1)).save(studentToSave);
    }

    @Test
    void deleteAll() {
        studentService.deleteAll();
        verify(studentDao, times(1)).deleteAll();
    }

    @Test
    void getAll() {

        List<Student> expected = new ArrayList<>(Arrays.asList(
                new Student(1,1,"a","a"),
                new Student(2,2,"b","b")
        ));

        Mockito.when(studentDao.getAll()).thenReturn(expected);
        assertEquals(expected, studentService.getAll());
    }

    @Test
    void getById() {

        int studId = 10;
        Student expected = new Student(studId, 1, "a", "b");
        Mockito.when(studentDao.getById(studId)).thenReturn(expected);
        assertEquals(expected, studentService.getById(studId));
    }
}
