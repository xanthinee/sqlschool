package sqltask.courses;


import sqltask.helpers.*;

public class CoursesParser implements Parser<Course> {

    @Override
    public Course parse(String str) {
        String[] parts = str.split("_");
        return new Course((Integer) null, parts[1], parts[2]);
    }
}
