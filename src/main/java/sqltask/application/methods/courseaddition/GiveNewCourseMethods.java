package sqltask.application.methods.courseaddition;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class GiveNewCourseMethods {

    public String printCoursesOfStud(List<String> courses) {
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("Entered STUDENT has next COURSES: ");
        int index = 1;
        for (String course : courses) {
            sj.add(index + ". " + course);
            index++;
        }
        return sj.toString();
    }

    public Map<Integer, String> findUnusedCourses(List<String> coursesOfStudent, Map<Integer, String> availableCourses) {

        for (Map.Entry<Integer, String> entry : new HashSet<>(availableCourses.entrySet())) {
            for (String course : coursesOfStudent) {
                if (entry.getValue().trim().equals(course.trim())) {
                    availableCourses.remove(entry.getKey());
                }
            }
        }
        return availableCourses;
    }

    public String completeCoursesInfo(List<String> coursesOfStudent, Map<Integer, String> mainCourses,
                                       Map<Integer, String> availableForStudentCourses) {
        int numOfCourses = coursesOfStudent.size();
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        if (numOfCourses != 0) {
            sj.add(printCoursesOfStud(coursesOfStudent));
            sj.add("You can give next COURSES to STUDENT:");
            int index = 1;
            for (Map.Entry<Integer, String> entry : availableForStudentCourses.entrySet()) {
                sj.add(index + ". " + entry.getValue());
                index++;
            }
        } else {
            sj.add("Chosen STUDENT has no any COURSES");
            sj.add("You can give next COURSES to STUDENT:");
            int index = 1;
            for (Map.Entry<Integer,String> entry : mainCourses.entrySet()) {
                sj.add(index + ". " + entry.getValue());
                index++;
            }
            return sj.toString();
        }
        return sj.toString();
    }
}
