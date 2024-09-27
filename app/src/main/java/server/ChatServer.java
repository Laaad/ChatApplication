import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2000);
        System.out.println("Server started. Waiting for clients...");
        List<ClientHandler> clients = new ArrayList<>();

        while (true){
            Socket clientSocket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(clientSocket, clients );
            clients.add(clientHandler);
            System.out.println("Client connected." + clientSocket);
            new Thread(clientHandler).start();
        }
        }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private List<ClientHandler> clients;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Socket socket, List<ClientHandler> clients) throws IOException{
        this.clientSocket = socket;
        this.clients = clients;
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }

    public void run(){
        try{
            String inputLine;
            while((inputLine = in.readLine()) != null ){
                for (ClientHandler aClient : clients){
                    aClient.out.println(inputLine);
                }
            }
        }
        catch (IOException e){
            System.out.println("Error" + e.getMessage());        }
    finally {
        try{
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    }
}
