package sqltask;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import sqltask.courses.CourseService;
import sqltask.groups.GroupService;
import sqltask.students.StudentService;

@TestConfiguration
public class ServiceLayerTestConfig {

    @Bean
    @Primary
    public CourseService mockCourseService() {
        return Mockito.mock(CourseService.class);
    }

    @Bean
    @Primary
    public GroupService mockGroupService() {
        return Mockito.mock(GroupService.class);
    }

    @Bean
    @Primary
    public StudentService mockStudentService() {
        return Mockito.mock(StudentService.class);
    }
}
