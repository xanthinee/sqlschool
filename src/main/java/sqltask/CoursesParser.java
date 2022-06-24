package sqltask;

public class CoursesParser implements Parser<Course> {

    @Override
    public Course parse(String str) {
        String[] parts = str.split("_");
        return new Course(Integer.parseInt(parts[0]), parts[1], parts[2]);
    }
}
