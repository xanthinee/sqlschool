package sqltask;

import java.util.*;

public class CoursesTable {

    Course math = new Course(1, "Math", "mathematical science with geometry features");
    Course computerScience = new Course(2,"Computer science", "Study of computation and automation");

    List<Course> courses = List.of(math, computerScience);
}
