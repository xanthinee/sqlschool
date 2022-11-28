package sqltask.groups;

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

@SpringBootTest
@ActiveProfiles("test")
class GroupsServiceTest {

    @Autowired
    private GroupService groupService;

    @MockBean
    private GroupDAOJdbc groupDao;

    @Test
    void compareGroups() {

        List<Group> result = new ArrayList<>(Arrays.asList(
                new Group(1,"aa-11"),
                new Group(2,"bb-22"),
                new Group(3, "cc-33")
        ));

        int groupId = 10;
        Mockito.when(groupDao.compareGroups(groupId)).thenReturn(result);
        assertEquals(result, groupService.compareGroups(groupId));
    }

    @Test
    void deleteAll() {
        groupService.deleteAll();
        verify(groupDao, times(1)).deleteAll();
    }

    @Test
    void getById() {

        int groupId = 10;
        Group expected = new Group(groupId, "aa-11");
        Mockito.when(groupDao.getById(groupId)).thenReturn(expected);
        assertEquals(expected, groupService.getById(groupId));
    }

    @Test
    void deleteById() {
        int groupId = 10;
        groupService.deleteById(groupId);
        verify(groupDao, times(1)).deleteById(groupId);
    }
    @Test
    void getAll() {

        List<Group> result = new ArrayList<>(Arrays.asList(
                new Group(1,"aa-11"),
                new Group(2,"bb-22"),
                new Group(3, "cc-33")
        ));
        Mockito.when(groupDao.getAll()).thenReturn(result);
        assertEquals(result, groupService.getAll());
    }
}
