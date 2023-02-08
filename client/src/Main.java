import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        EchoClient.connectTo(8788).run();
    }
}