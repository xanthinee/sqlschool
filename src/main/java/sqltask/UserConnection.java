package sqltask;

public class UserConnection {

    protected String host;
    protected String user;
    protected String password;

    public UserConnection(String host, String user, String password) {
        this.host = host;
        this.user = user;
        this.password = password;
    }
}
