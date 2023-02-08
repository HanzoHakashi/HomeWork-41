import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EchoClient {
    private final int port;
    private final String host;

    private EchoClient(String host ,int port ) {
        this.port = port;
        this.host = host;
    }

    public static EchoClient connectTo(int port){
        var localhost = "192.168.1.13";
        return new EchoClient(localhost,port);
    }


    public void run() throws IOException{
        System.out.printf("Напишите 'bye' для выхода%n%n%n");

        try(Socket socket = new Socket(host, port)) {
            Scanner scanner = new Scanner(System.in, "UTF-8");
            var input = socket.getInputStream();
            var isr = new InputStreamReader(input,"UTF-8");
            var scanner2 = new Scanner(isr);
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output);

            try (scanner;writer){
                while (true){
                    String message = scanner.nextLine();
                    writer.write(message);
                    writer.write(System.lineSeparator());
                    writer.flush();
                    var message2 = scanner2.nextLine().strip();
                    System.out.printf("%s",message2);

                    if ("bye".equalsIgnoreCase(message)){
                        return;
                    }
                }
            }

        }catch (NoSuchElementException e){
            System.out.printf("Connection dropped!%n");
        }catch (IOException e){
            System.out.printf("Can't connect to %s:%s!%n",host,port);
            e.printStackTrace();
        }

    }




}
