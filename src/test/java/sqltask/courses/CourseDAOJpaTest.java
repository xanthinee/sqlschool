package sqltask.courses;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sqltask.TestDAOInterface;

@SpringBootTest
@ActiveProfiles(profiles = {"test", "jpa"})
public class CourseDAOJpaTest implements TestDAOInterface {

    @Override
    public void save_shouldSaveOnlyOneLine() {

    }

    @Override
    public void saveAll_shouldSaveSeveralRows() {

    }

    @Override
    public void deleteAll_shouldRetrieveZeroRows() {

    }

    @Override
    public void getAll_sizesShouldBeEqual() {

    }

    @Override
    public void getById_shouldRetrieveExactEntity() {

    }

    @Override
    public void deleteById_shouldCountZeroRows() {

    }
}
