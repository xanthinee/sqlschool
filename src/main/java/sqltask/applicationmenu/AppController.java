package sqltask.applicationmenu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sqltask.groups.Group;
import sqltask.students.Student;
import java.util.*;

public class AppController {

    @Autowired
    private final AppService service;

    AppController(AppService service) {
        this.service = service;
    }

    @PostMapping("/saveStudent")
    public void save(@RequestBody Student student) {
        service.save(student);
    }

    @DeleteMapping("/deleteStudent/{id}")
    public void deleteById(@PathVariable int id) {
        service.deleteById(id);
    }

    @GetMapping("/getMembers/{courseName}")
    public List<Student> getCourseMembers(@PathVariable String courseName) {
        return service.getCourseMembers(courseName);
    }

    @DeleteMapping("/unlinkCourse/{id,name}")
    public void unlinkCourse(@PathVariable int id, String name) {
        service.unlinkCourse(id, name);
    }

    @PostMapping("/setCourse/{id,name}")
    public void setNewCourse(@PathVariable int id, String name) {
        service.setNewCourse(id, name);
    }

    @GetMapping("/compareGroups/{id}")
    public List<Group> compareGroups(@PathVariable int id) {
        return service.compareGroups(id);
    }

}
