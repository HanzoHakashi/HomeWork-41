import javax.swing.text.DefaultHighlighter;
import java.io.*;
import java.net.Socket;
import java.util.*;

public class Connection implements Runnable  {

    private Socket socket;
    public static ArrayList<Connection> connections = new ArrayList<>();
    private BufferedWriter out;
    private BufferedReader in;
    private String username;

    public Connection(Socket socket) {
        try {
            this.socket =socket;
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = in.readLine();
            connections.add(this);
            broadCastMessage("SERVER" + username + "Вошел в чат");

        }catch (IOException e ){
            closeEverything(socket,in,out);
        }
    }

    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()){
            try {
                messageFromClient = in.readLine();
                broadCastMessage(messageFromClient);
            }catch (IOException e){
                closeEverything(socket,in,out);
                break;
            }

        }
    }

    public void broadCastMessage(String messageToSend){
        for (Connection connection :connections) {
            try {
                    if (!connection.username.equals(username)){
                        connection.out.write(messageToSend);
                        connection.out.newLine();
                        connection.out.flush();
                    }
            }catch (IOException e){
                closeEverything(socket,in,out);
            }

        }
    }

    public void disconnect(){
        connections.remove(this);
        broadCastMessage("SERVER "+ username + "Покинул чат");
    }

    public void closeEverything(Socket socket, BufferedReader in , BufferedWriter out){
        disconnect();
        try {
            if (in != null){
                in.close();
            }
            if (out != null){
                out.close();
            }
            if (socket != null){
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }





}

