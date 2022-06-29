package sqltask.connection;

public class UserConnection {

    public String host;
    public String user;
    public String password;

    public UserConnection(String host, String user, String password) {
        this.host = host;
        this.user = user;
        this.password = password;
    }
}
