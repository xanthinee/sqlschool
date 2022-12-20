package sqltask.applicationmenu.menufunctions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sqltask.applicationmenu.Menu;
import sqltask.groups.Group;
import sqltask.groups.GroupService;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
@SuppressWarnings("java:S106")
public class CompareGroupsMenuItem implements Menu {

    private final GroupService service;
    private final InputStream inStream;
    private final PrintStream outStream;
    private static final int AMOUNT_OF_GROUPS = 10;

    @Autowired
    public CompareGroupsMenuItem(GroupService service) {
        this(service, System.in, System.out);
    }

    public CompareGroupsMenuItem(GroupService service, InputStream inStream, PrintStream outStream) {
        this.service = service;
        this.inStream = inStream;
        this.outStream = outStream;
    }

    @Override
    public String getLabel() {
        return "Compare groups";
    }

    @Override
    public void doAction() {

        List<Group> groups = service.getAll();
        List<Integer> groupNames = new ArrayList<>();
        for (Group group : groups) {
            groupNames.add(group.getId());
        }
        outStream.println("Enter group ID (1-" + AMOUNT_OF_GROUPS + "):");
        Scanner sc = new Scanner(inStream);
        try {
            int id = sc.nextInt();
            if(!groupNames.contains(id)) {
                throw new IllegalArgumentException("Wrong id");
            }
            List<Group> comparedGroups = service.compareGroups(id);
            for (Group group : comparedGroups) {
                outStream.println(group);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
