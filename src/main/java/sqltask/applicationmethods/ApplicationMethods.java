package sqltask.applicationmethods;

import sqltask.connection.ConnectionInfoGenerator;
import sqltask.courses.Course;
import sqltask.courses.CoursesTable;
import sqltask.groups.Group;
import sqltask.groups.GroupsTableDB;
import sqltask.students.Student;

import java.sql.*;
import java.util.*;
import java.util.Map.Entry;

@SuppressWarnings("java:S106")
public class ApplicationMethods {

    private final ConnectionInfoGenerator conInfo = new ConnectionInfoGenerator();
    String connectionFile = "data/connectioninfo";
    Random rd = new Random();
    Scanner sc = new Scanner(System.in);

    /**
     * 1st option
     **/

    private String resultOfComparison(List<String> withEqualSize, List<String> withFewerSize) {

        StringJoiner sj = new StringJoiner("");
        if (withFewerSize.size() == 0) {
            sj.add("There no group with FEWER amount of students.");
        } else {
            sj.add("Groups with FEWER amount of students: ");
            for (String names : withFewerSize) {
                sj.add(names + "; ");
            }
        }
        sj.add(System.lineSeparator());

        if (withEqualSize.size() == 0) {
            sj.add("There no group with EQUAL amount of students.");
        } else {
            sj.add("Groups with EQUAL amount of students: ");
            for (String names : withEqualSize) {
                sj.add(names + "; ");
            }
        }
        return sj.toString();
    }

    public String compareGroup() throws SQLException {

        GroupsTableDB gt = new GroupsTableDB();
        System.out.println("Enter GROUP to COMPARE bellow: ");
        int groupID = sc.nextInt();
        List<Group> groupsList = gt.getGroupsFromTable();
        Map<String, Integer> groupMembers = new HashMap<>();
        try (Connection connection = conInfo.getConnection(connectionFile)) {
            PreparedStatement getGroupsMembers = connection.prepareStatement("select g.group_name, count(s.student_id) from groups g " +
                    "left outer join students s on g.group_id = s.group_id group by g.group_name");
            ResultSet rs = getGroupsMembers.executeQuery();
            while (rs.next()) {
                groupMembers.put(rs.getString("group_name"), rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Map<Integer, Group> groups = new HashMap<>();
        for (Group group : groupsList) {
            groups.put(group.getId(), group);
        }

        int amountOfStudentsInInputGroup = groupMembers.get(groups.get(groupID).getName());
        List<String> withEqualSize = new ArrayList<>();
        List<String> withFewerSize = new ArrayList<>();
        for (Entry<Integer, Group> entry : groups.entrySet()) {
            if (entry.getKey() != groupID && (groupMembers.get(entry.getValue().getName()) < amountOfStudentsInInputGroup)) {
                withFewerSize.add(entry.getValue().getName());
            }
            if (entry.getKey() != groupID && groupMembers.get(entry.getValue().getName()) == amountOfStudentsInInputGroup) {
                withEqualSize.add(entry.getValue().getName());
            }
        }
        System.out.println(resultOfComparison(withEqualSize, withFewerSize));
        return resultOfComparison(withEqualSize, withFewerSize);
    }

    /**
     * 2nd option
     **/
    public String findStudentsByCourse() throws SQLException {

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

    /**
     * 3rd option
     **/
    public void putNewStudent() throws SQLException {

        GroupsTableDB groupTab = new GroupsTableDB();
        System.out.println("Enter NAME of student: ");
        String studentName = sc.next();
        System.out.println("Enter SURNAME of student");
        String studentSurname = sc.next();
        List<Integer> takenIDsList = new ArrayList<>();

        try (Connection connection = conInfo.getConnection(connectionFile)) {
            List<Integer> groupIDs = groupTab.groupsIdList();
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
            int groupPresence = rd.nextInt(0,2);
            if (groupPresence == 0) {
                putStudent.setInt(2, Types.NULL);
            } else {
                putStudent.setInt(2, groupIDs.get(groupIndex));
            }
            putStudent.setString(3, studentName);
            putStudent.setString(4, studentSurname);
            putStudent.executeUpdate();
        }
    }

    /**
     * 4th option
     **/
    public void deleteStudent() throws SQLException {

        System.out.println("Enter ID of student: ");
        int studentID = sc.nextInt();
        try (Connection connection = conInfo.getConnection(connectionFile)) {
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

    /**
     * 5th option
     **/

    private List<String> getCoursesOfStudent(int studentID) {

        List<String> coursesOfStudent = new ArrayList<>();
        try (Connection connection = conInfo.getConnection(connectionFile)) {
            PreparedStatement getCoursesOfStud = connection.prepareStatement("select * from students_courses where student_id = ?");
            getCoursesOfStud.setInt(1, studentID);
            ResultSet coursesOfStud = getCoursesOfStud.executeQuery();
            while (coursesOfStud.next()) {
                coursesOfStudent.add(coursesOfStud.getString("course_name".trim()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coursesOfStudent;
    }
    private String coursesOfStudentInfo(List<String> courses) {
        StringJoiner sj = new StringJoiner("");
        sj.add("Entered STUDENT has next COURSES: " + System.lineSeparator());
        int index = 1;
        for (String course : courses) {
            sj.add(index + ". " + course);
            sj.add(System.lineSeparator());
            index++;
        }
        return sj.toString();
    }

    private Map<Integer, String> findUnusedCourses(List<String> coursesOfStudent, Map<Integer, String> availableCourses) {

        for (Entry<Integer, String> entry : new HashSet<>(availableCourses.entrySet())) {
            for (String course : coursesOfStudent) {
                if (entry.getValue().trim().equals(course.trim())) {
                    availableCourses.remove(entry.getKey());
                }
            }
        }
        return availableCourses;
    }

    private String completeCoursesInfo(List<String> coursesOfStudent, Map<Integer, String> mainCourses,
                                      Map<Integer, String> availableForStudentCourses) {
        int numOfCourses = coursesOfStudent.size();
        StringJoiner sj = new StringJoiner("");
        if (numOfCourses != 0) {
            sj.add(coursesOfStudentInfo(coursesOfStudent));
            sj.add(System.lineSeparator());
            sj.add("You can give next COURSES to STUDENT:" + System.lineSeparator());
            int index = 1;
            for (Entry<Integer, String> entry : availableForStudentCourses.entrySet()) {
                sj.add(index + ". " + entry.getValue() + System.lineSeparator());
                index++;
            }
        } else {
            sj.add("Chosen STUDENT has no any COURSES");
            sj.add("You can give next COURSES to STUDENT:");
            int index = 1;
            for (Entry<Integer,String> entry : mainCourses.entrySet()) {
                sj.add(index + ". " + entry.getValue() + System.lineSeparator());
                index++;
            }
            return sj.toString();
        }
        return sj.toString();
    }
    public void giveCourseToStudent() throws SQLException {

        System.out.println("Enter student_id of STUDENT: ");
        int studentID = sc.nextInt();
        CoursesTable courseTab = new CoursesTable();
        List<Course> tempListOfAvailableCourses = courseTab.makeCoursesList("data/descriptions");
        Map<Integer, String> mainCourses = new HashMap<>();
        for (Course course : tempListOfAvailableCourses) {
            mainCourses.put(course.getId(), course.getName());
        }
        List<String> coursesOfStudent = getCoursesOfStudent(studentID);
        Map<Integer, String> availableForStudentCourse = findUnusedCourses(coursesOfStudent,mainCourses);
        int numOfCourses = coursesOfStudent.size();

        System.out.println(completeCoursesInfo(coursesOfStudent, mainCourses,availableForStudentCourse));

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

        System.out.println("Enter student_id of STUDENT: ");
        int studentID = sc.nextInt();
        List<String> coursesOfStudent = getCoursesOfStudent(studentID);
        System.out.println(coursesOfStudentInfo(coursesOfStudent));
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

    public List<MenuTable> getMenuTable() {

        List<MenuTable> menuTable = new ArrayList<>();
        menuTable.add(new MenuTable(1, "COMPARE GROUP WITH OTHERS", "compareGroup"));
        menuTable.add(new MenuTable(2, "FIND STUDENTS FROM COURSE","findStudentsByCourse"));
        menuTable.add(new MenuTable(3, "CREATE NEW STUDENTS", "putNewStudent"));
        menuTable.add(new MenuTable(4, "DELETE STUDENT", "deleteStudent"));
        menuTable.add(new MenuTable(5, "SET NEW COURSE TO STUDENT", "giveCourseToStudent"));
        menuTable.add(new MenuTable(6, "UNLINK COURSE FROM STUDENT", "unlinkCourse"));
        return menuTable;
    }
}