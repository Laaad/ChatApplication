package client;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String [] args) throws IOException{
        Socket socket= new Socket("localhost", 2000);
        System.out.println("Connected to server");
    }
}
