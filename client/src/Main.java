import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        EchoClient.connectTo(5454).run();
    }
}