package sqltask;

public class ConnectionParser implements Parser<UserConnection> {

    @Override
    public UserConnection parse(String str) {
        String[] connectionDetails = str.split("_");
        return new UserConnection(connectionDetails[0], connectionDetails[1], connectionDetails[2]);
    }
}
