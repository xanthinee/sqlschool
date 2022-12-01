package sqltask;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import sqltask.students.Student;
import sqltask.students.StudentDAO;
import sqltask.students.StudentDAOJdbc;

@SpringBootApplication
@EnableAspectJAutoProxy
@SuppressWarnings("java:S106")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        ApplicationContext ctx = SpringApplication.run(Main.class);
        StudentDAO studentDAO = ctx.getBean(StudentDAOJdbc.class);

        Student student = new Student(1,1,"a","a");
        studentDAO.save(student);
    }
}


