import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;


public class Server {

    private static Socket socket;

    public static void main(String[] args) throws Exception {

        connectSocket();
    }


    public static void connectSocket() throws IOException {

        //define a set that contains the winning numbers
        Set<String> win = new HashSet<>();
        win.add("2");
        win.add("4");
        win.add("6");
        win.add("8");

        int port = 8123;

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " +port );

            socket = serverSocket.accept();

            System.out.println("Accepted connection");

            //Variable for the message from client

            String code = RandNumbGen.getCode();


            System.out.println("the code was:  " + code);


            String lastChar = code.substring(code.length() - 1);

            OutputStream outStream = socket.getOutputStream();
            OutputStreamWriter outWriter = new OutputStreamWriter(outStream);
            BufferedWriter bw = new BufferedWriter(outWriter);


            if (win.contains(lastChar)) {
                bw.write("1");
                System.out.println("Winner");

            } else {
                bw.write("2");
                System.out.println("Loser");

            }

        bw.flush();
        serverSocket.close();



    }

}














