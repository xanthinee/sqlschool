package sqltask.applicationmenu.menufunctions;

import sqltask.applicationmenu.Menu;
import sqltask.groups.Group;
import sqltask.groups.GroupService;
import sqltask.groups.GroupsTableDB;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("java:S106")
public class GroupsByStudentCountMenuItem implements Menu {

    private final GroupService service;
    private final InputStream inStream;
    private final PrintStream outStream;

    public GroupsByStudentCountMenuItem(GroupService service) {
        this(service, System.in, System.out);
    }

    public GroupsByStudentCountMenuItem(GroupService service, InputStream inStream, PrintStream outStream) {
        this.service = service;
        this.inStream = inStream;
        this.outStream = outStream;
    }

    @Override
    public String getLabel() {
        return "Group members comparison";
    }

    @Override
    public void doAction() {
        outStream.println("Enter group ID:");
        try (Scanner scanner = new Scanner(inStream)) {
            int id = scanner.nextInt();
            List<Group> groups = service.compareGroups(id);
            for (Group group : groups) {
                outStream.println(group);
            }
        }
    }
}
