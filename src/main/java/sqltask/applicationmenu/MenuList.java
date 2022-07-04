package sqltask.applicationmenu;

import java.util.List;

public class MenuList {

    static List<Menu> menu = List.of(new GroupsComparison(), new CourseAddition(), new StudentsByCourse(), new DeleteStudent(),
            new PutNewStudent(), new UnlinkCourse());
}
