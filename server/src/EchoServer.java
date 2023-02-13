import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {

    private ServerSocket serversocket;


    public EchoServer(ServerSocket serversocket) {
        this.serversocket = serversocket;
    }


    public void run() {
        try  {
            while (!serversocket.isClosed()) {
                Socket clientSocket = serversocket.accept();
                System.out.println("Новый клиент был подключен");
                Connection connection = new Connection(clientSocket);
                Thread thread = new Thread(connection);
                thread.start();

            }

        } catch (IOException e) {
            closeServer();
        }

    }

    public void closeServer() {
        try {
            if (serversocket != null){
                serversocket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(5454);
        EchoServer server = new EchoServer(socket);
        server.run();
    }





}



