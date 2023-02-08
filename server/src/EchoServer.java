import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EchoServer {
    private final int port;

    private EchoServer(int port) {
        this.port = port;
    }

    static EchoServer bindToPort(int port){
        return new EchoServer(port);
    }

    public void run(){
        try(ServerSocket server = new ServerSocket(port)) {
            try (var clientSocket = server.accept()){
                handle(clientSocket);
            }

        }catch (IOException e){
            String fmtMsg = "Вероятнее всего порт %s занят";
            System.out.printf(fmtMsg,port);
            e.printStackTrace();
        }

    }
    private void handle(Socket socket) throws IOException{
        // handle
        var input = socket.getInputStream();
        var isr = new InputStreamReader(input,"UTF-8");
        var scanner = new Scanner(isr);


        try (scanner){
            while (true){
                var message = scanner.nextLine().strip();
                System.out.printf("Got %s%n",message);
                reverseStr(message);
                if ("bye".equalsIgnoreCase(message));
                System.out.printf("Bye bye!%n");
                return;
            }
        }catch (NoSuchElementException e){
            System.out.printf("Client dropped the connection!%n");
        }
    }

    private void reverseStr(String message){
        String reverse = "";
        char ch;
        for (int i = 0; i < message.length(); i++) {
            ch = message.charAt(i);
            reverse = ch+reverse;
        }
        System.out.println("Reverse answer: " + reverse);
    }
}
