package sqltask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;
import java.sql.Statement;

public class GroupInjector {

    Random rd;
    public GroupInjector(Random rd) {
        this.rd = rd;
    }

    private String generateGroupName() {

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 2; i++) {
            sb.append((char) rd.nextInt('A','Z'));
        }
        sb.append("--");
        for(int i = 0; i < 2; i++) {
            sb.append(rd.nextInt(0, 9));
        }
        return sb.toString();
    }

    public void putGroupIntoTable() throws SQLException {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(ConnectionInfo.host,
                    ConnectionInfo.user, ConnectionInfo.password);
            Statement st = connection.createStatement();
            for (int i = 1; i < 11; i++) {
                Group newGroup = new Group();
                newGroup.setId(i);
                newGroup.setName(generateGroupName());
                st.executeUpdate("INSERT INTO public.groups VALUES('"+newGroup.getId()+"', '" +newGroup.getName()+ "')");
            }
//            for (int i = 1; i < 11; i++) {
//                st.executeUpdate("DELETE FROM public.groups WHERE group_id = '"+i+"'");
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
