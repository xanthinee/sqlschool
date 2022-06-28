package sqltask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

@SuppressWarnings("java:S106")
public class ApplicationMethods {

    ConnectionInfoGenerator conInfo = new ConnectionInfoGenerator();
    String connectionFile = "data/connectioninfo";
    Random rd = new Random();

    /** 1st option **/
    public String compareGroup() throws SQLException {

        Scanner sc = new Scanner(System.in);
        int groupID = sc.nextInt();
        GroupsTable gt = new GroupsTable();
        ResultSet groupsRS = gt.getGroupsFromTable();
        Map<String, Integer> groupMembers = new HashMap<>();
        try (Connection connection = conInfo.getConnection(connectionFile)) {
            PreparedStatement prepSt = connection.prepareStatement("select g.group_name, count(s.student_id) from groups g " +
                    "left outer join students s on g.group_id = s.group_id group by g.group_name");
            ResultSet rs = prepSt.executeQuery();
            while (rs.next()) {
                groupMembers.put(rs.getString("group_name"), rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Map<Integer, Group> groups = new HashMap<>();
        while (groupsRS.next()) {
            groups.put(groupsRS.getInt("group_id"), new Group(groupsRS.getInt("group_id"),
                    groupsRS.getString("group_name")));
        }

        int amountOfStudentsInInputGroup = groupMembers.get(groups.get(groupID).getName());
        List<String> withEqualSize = new ArrayList<>();
        List<String> withFewerSize = new ArrayList<>();
        for (Map.Entry<Integer, Group> entry : groups.entrySet()) {
            if (entry.getKey() != groupID && (groupMembers.get(entry.getValue().getName()) < amountOfStudentsInInputGroup)) {
                withFewerSize.add(entry.getValue().getName());
            }
            if (entry.getKey() != groupID && groupMembers.get(entry.getValue().getName()) == amountOfStudentsInInputGroup) {
                withEqualSize.add(entry.getValue().getName());
            }
        }

        StringJoiner sj = new StringJoiner("");
        if (withFewerSize.size() == 0) {
            sj.add("There no group with FEWER amount of students.");
        } else {
            sj.add("Groups with fewer amount of students: ");
            for (String names : withFewerSize) {
                sj.add(names + "; ");
            }
        }
        sj.add(System.lineSeparator());

        if (withEqualSize.size() == 0) {
            sj.add("There no group with EQUAL amount of students.");
        } else {
            sj.add("Groups with equal amount of students: ");
            for (String names : withEqualSize) {
                sj.add(names + "; ");
            }
        }
        return sj.toString();
    }

    /** 2nd option **/
    public String findStudentsByCourse() throws SQLException {

        Scanner sc = new Scanner(System.in);
        String courseName = sc.next();
        List<Student> students = new ArrayList<>();
        try (Connection connection = conInfo.getConnection(connectionFile)) {
            PreparedStatement st = connection.prepareStatement("select students.student_id, students.group_id, students.first_name, students.second_name \n" +
                    "from students inner join students_courses on students_courses.student_id = students.student_id where course_name = ?");
            st.setString(1, courseName);
            ResultSet studentsRS = st.executeQuery();
            while (studentsRS.next()) {
                students.add(new Student(studentsRS.getInt("student_id"), studentsRS.getInt("group_id"),
                        studentsRS.getString("first_name"), studentsRS.getString("second_name")));
            }
        }
        StringJoiner sj = new StringJoiner("");
        sj.add("Students which have " + courseName.toUpperCase() + " courses:");
        sj.add(System.lineSeparator());
        for (Student student : students) {
            sj.add(String.format("%-6d", student.getStudentId()) + " | ");
            sj.add(String.format("%-6d", student.getGroupId()) + " | ");
            sj.add(String.format("%s", student.getName().trim()) + " ");
            sj.add(String.format("%-13s", student.getSurname().trim()));
            sj.add(System.lineSeparator());
        }
        return sj.toString();
    }

    private int generateNewId(int leftBound, int rightBound, List<Integer> used) {
        int newId = rd.nextInt(leftBound, rightBound);
        if (used.contains(newId)) {
            newId = rd.nextInt(leftBound, rightBound);
        }
        used.add(newId);
        return newId;
    }

    /** 3rd option **/
    public void putNewStudent() throws SQLException {

        StudentsTable studTab = new StudentsTable();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter NAME of student: ");
        String studentName = sc.next();
        System.out.println("Enter SURNAME of student");
        String studentSurname = sc.next();
        List<Integer> takenIDsList = new ArrayList<>();

        try (Connection connection = conInfo.getConnection(connectionFile)) {
            List<Integer> groupIDs = studTab.groupsIdList();
            PreparedStatement takenIDs = connection.prepareStatement("select student_id from students");
            ResultSet takenIDRs = takenIDs.executeQuery();
            while (takenIDRs.next()) {
                takenIDsList.add(takenIDRs.getInt("student_id"));
            }
            int studentID = generateNewId(1000, 10000, takenIDsList);
            takenIDsList.clear();
            int groupIndex = rd.nextInt(0, groupIDs.size());
            PreparedStatement putStudent = connection.prepareStatement("insert into students values (?,?,?,?)");
            putStudent.setInt(1, studentID);
            putStudent.setInt(2, groupIDs.get(groupIndex));
            putStudent.setString(3, studentName);
            putStudent.setString(4, studentSurname);
            putStudent.executeUpdate();
        }
    }

    /** 4th option **/
    public void deleteStudent() throws SQLException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter ID of student: ");
        int studentID = sc.nextInt();
        try (Connection connection = conInfo.getConnection(connectionFile)){
            PreparedStatement deleteFromStudents = connection.prepareStatement("delete from students where student_id = ?");
            deleteFromStudents.setInt(1, studentID);
            PreparedStatement deleteFromStudentsCourses = connection.prepareStatement("delete from students_courses where student_id = ?");
            deleteFromStudentsCourses.setInt(1, studentID);
            deleteFromStudents.executeUpdate();
            deleteFromStudentsCourses.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<String> getCoursesOfStudent(int studentID) {

        List<String> coursesOfStudent = new ArrayList<>();
        try (Connection connection = conInfo.getConnection(connectionFile)) {
            PreparedStatement getCoursesOfStud = connection.prepareStatement("select * from students_courses where student_id = ?");
            getCoursesOfStud.setInt(1, studentID);
            ResultSet coursesOfStud = getCoursesOfStud.executeQuery();
            while(coursesOfStud.next()) {
                coursesOfStudent.add(coursesOfStud.getString("course_name".trim()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coursesOfStudent;
    }
    /** 5th option **/
    public void giveCourseToStudent() throws SQLException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter student_id of STUDENT: ");
        int studentID = sc.nextInt();
        List<String> coursesOfStudent = getCoursesOfStudent(studentID);
        int numOfCourses = coursesOfStudent.size();

        CoursesTable courseTab = new CoursesTable();
        List<Course> allAvailableCourses = courseTab.makeCoursesList("data/descriptions");

        Map<Integer, String> availableCourses = new HashMap<>();
        for (Course course : allAvailableCourses) {
            availableCourses.put(course.getId(), course.getName());
        }

        StringJoiner sj = new StringJoiner("");
        if (numOfCourses != 0) {
            sj.add("Chosen STUDENT already has next COURSES: ");
            sj.add(System.lineSeparator());
            int c = numOfCourses - 1;
            for (String course : coursesOfStudent) {
                sj.add((numOfCourses - c) + ". " + course);
                sj.add(System.lineSeparator());
                c--;
            }
            System.out.println(sj);
            System.out.println("You can give next COURSES to STUDENT:");

            for (Entry<Integer, String> entry : new HashSet<>(availableCourses.entrySet())) {
                for (String course : coursesOfStudent) {
                    if (entry.getValue().trim().equals(course.trim())) {
                        availableCourses.remove(entry.getKey());
                    }
                }
            }

            StringJoiner strOfAvbCourses = new StringJoiner("");
            int index = 1;
            for (Entry<Integer, String> entry : availableCourses.entrySet()) {
                strOfAvbCourses.add(index + ". " + entry.getValue() + System.lineSeparator());
                index++;
            }
            System.out.println(strOfAvbCourses);

        } else {
            System.out.println("Chosen STUDENT has no any COURSES");
            System.out.println("You can give next COURSES to STUDENT:");
            StringJoiner strOfAvbCourses = new StringJoiner("");
            int index = 1;
            for (Course course : allAvailableCourses) {
                strOfAvbCourses.add(index + ". " + course.getName() + System.lineSeparator());
                index++;
            }
            System.out.println(strOfAvbCourses);
        }

        if (numOfCourses < 3) {
            System.out.println("Amount of new available COURSES is: " + (3 - numOfCourses));
            System.out.println("Enter NAME (Only 1 by attempt) of COURSE which you want to ADD: ");
            String courseName = sc.next();
            List<Integer> usedRowIDs = new ArrayList<>();
            try (Connection connection = conInfo.getConnection(connectionFile)) {
                PreparedStatement getUsedIDs = connection.prepareStatement("select row_id from students_courses");
                ResultSet usedIDs = getUsedIDs.executeQuery();
                while (usedIDs.next()) {
                    usedRowIDs.add(usedIDs.getInt("row_id"));
                }
                PreparedStatement addCourseToStudent = connection.prepareStatement("insert into students_courses values (?,?,?)");
                addCourseToStudent.setInt(1, generateNewId(1000, 10000, usedRowIDs));
                addCourseToStudent.setInt(2, studentID);
                addCourseToStudent.setString(3, courseName);
                addCourseToStudent.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalStateException("Chosen STUDENT already has maximum amount of COURSES, delete some before");
        }
    }

    /** 6th option **/
    public void unlinkCourse() throws SQLException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter student_id of STUDENT: ");
        int studentID = sc.nextInt();
        CoursesTable courseTab = new CoursesTable();
        List<String> coursesOfStudent = getCoursesOfStudent(studentID);
        System.out.println("Entered STUDENT has next COURSES: ");
        StringJoiner sj = new StringJoiner("");
        int index = 1;
        for (String course : coursesOfStudent) {
            sj.add(index + ". " + course);
            sj.add(System.lineSeparator());
            index++;
        }
        System.out.println(sj);
        System.out.println("You can DELETE one of them - ENTER bellow it's NAME: ");
        String courseToDelete = sc.next();
        try (Connection connection = conInfo.getConnection(connectionFile)) {
            PreparedStatement unlinkCourse = connection.prepareStatement("delete from students_courses " +
                    "where student_id = ? and course_name = ?");
            unlinkCourse.setInt(1, studentID);
            unlinkCourse.setString(2, courseToDelete);
            unlinkCourse.executeUpdate();
        }
    }
}