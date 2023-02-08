import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
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
        OutputStream output = socket.getOutputStream();
        var isr = new InputStreamReader(input,"UTF-8");
        var scanner = new Scanner(isr);
        PrintWriter writer = new PrintWriter(output);


        try (scanner){
            while (true){
                var message = scanner.nextLine().strip();
                System.out.printf("Got %s%n",message);
                String reverse = reverseStr(message);
                String host = socket.getInetAddress().toString();
                writer.println(reverse);
                writer.flush();
                if ("bye".equalsIgnoreCase(message)){
                    System.out.printf("Bye bye!%n");
                    return;}
            }
        }catch (NoSuchElementException e){
            System.out.printf("Client dropped the connection!%n");
        }
    }

    private String reverseStr(String message){
        String reverse = "";
        char ch;
        for (int i = 0; i < message.length(); i++) {
            ch = message.charAt(i);
            reverse = ch+reverse;
        }
        return  reverse;

    }
}
