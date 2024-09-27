package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private String userName;

    public Client(String address, int port) throws IOException{
            socket = new Socket(address, port);
            System.out.println("Connection established for client" + socket);

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            new Thread(new ReceiveMessages()).start();
    }
    public void sendMessage(String msg){
        out.println( userName + ":" + msg );
    }

    public void stopConnection() throws IOException{
        in.close();
        out.close();
        socket.close();
    }

    private class ReceiveMessages implements Runnable {
        public void run() {
            String serverMessage;
            try {
                while ((serverMessage = in.readLine()) != null) {
                    System.out.println(serverMessage);
                }
            } catch (IOException e) {
                System.out.println("Connection closed: " + e.getMessage());
            }
        }
    }


    public static void main(String [] args) throws IOException{
        Scanner scanner = new Scanner(System.in);
        Client client= new Client("localhost", 2000);
        System.out.println("Please write your name");
        client.userName = scanner.nextLine();
        System.out.println("Connected to server");
        
        String input;
        while(!(input = scanner.nextLine()).equalsIgnoreCase("exit")){
            client.sendMessage(input);
        }
        client.stopConnection();
        scanner.close();
    }
}
