package sqltask.application.menu;

import sqltask.application.methods.DeleteStudent;
import sqltask.application.methods.PutNewStudent;
import sqltask.application.methods.UnlinkCourse;
import sqltask.application.methods.comparison.GroupsComparison;
import sqltask.application.methods.courseaddition.CourseAddition;
import sqltask.application.methods.coursemembers.StudentsByCourse;

import java.util.List;

public class MenuList {

    static List<Menu> menu = List.of(new GroupsComparison(), new CourseAddition(), new StudentsByCourse(), new DeleteStudent(),
            new PutNewStudent(), new UnlinkCourse());
}
