import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {


    public static void main(String[] args) throws Exception {

        //create socket
        int port = 8123;

        String msg;


        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.err.println("Started Server on port " + port);
            //"blocking" call that waits until a connection is requested
            Socket clientSocket = serverSocket.accept();
            System.err.println("Accepted connection from client");

            //Open up input stream and read the message from the client


            BufferedReader buffReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());

            try {
                while ((msg = buffReader.readLine()) != null){
                    System.out.println("Message from mobile phone: " + msg);

                    String string = args[0];
                    System.out.println("last character is: "+ string.substring(string.length() -1));

                }

            }catch (IOException e){
                e.printStackTrace();
            }



            System.out.println("Closing connection");
            buffReader.close();
            writer.flush();
            writer.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }




}









