package sqltask;

import java.util.stream.Stream;

public class ConnectionInfoGenerator {

    public UserConnection getConnectionInfo(String fileName) {

        FileConverter fileCon = new FileConverter();
        ConnectionParser conP = new ConnectionParser();
        Stream<String> stream = fileCon.readFile(fileName);
        return stream.map(conP::parse).toList().get(0);
    }
}
