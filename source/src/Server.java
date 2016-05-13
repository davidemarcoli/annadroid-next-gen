import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by txc37 on 13.05.2016.
 */
public class Server {
    public static void main(String[] args) throws Exception{

        //create socket
        int port = 8123;
        ServerSocket serverSocket = new ServerSocket(port);
        System.err.println("Started Server on port " + port);

        //repeatedly wait for connections and process
        while (true){

            //"blocking" call that waits until a connection is requested
            Socket clientSocket = serverSocket.accept();
            System.err.println("Accepted connection from client");

            //Open up IO Streams
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());


            /*Close the connection and streams
            System.err.println("Closing connection");
            out.close();
            in.close();
            clientSocket.close();*/


        }

    }
}
