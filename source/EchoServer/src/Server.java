import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;


public class Server {

    private static Socket socket;

    public static void main(String[] args) throws Exception {




        String msg;

        //define a set that contains the winning numbers
        Set<String> win = new HashSet<>();
        win.add("2");
        win.add("4");
        win.add("6");
        win.add("8");

        try {
            //define the port number for the server
            int port = 8123;

            //Initialise a new connection on the port
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started and listening on port " +port);

            //Keep the server running with a while loop
            while (true){
                //reading the message from the client
                //let the socket accept connections
                socket = serverSocket.accept();
                //open up an input stream
                InputStream inStream = socket.getInputStream();
                //initialise an input stream reader
               InputStreamReader inReader = new InputStreamReader(inStream);
                BufferedReader buffReader = new BufferedReader(inReader);
                //Variable for the message from client
                String code = buffReader.readLine();


                    System.out.println("Message from client: " +code);



                String lastChar = code.substring(code.length()-1);

                OutputStream outStream = socket.getOutputStream();
                OutputStreamWriter outWriter = new OutputStreamWriter(outStream);
                BufferedWriter bw = new BufferedWriter(outWriter);

                if (win.contains(lastChar)){
                    bw.write("Winner");
                    System.out.println("Winner");
                }else {
                    bw.write("Loser");
                    System.out.println("Loser");
                }
                bw.flush();

            }


        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            socket.close();
            System.out.println("Connection closed");
        }



        }





    }














