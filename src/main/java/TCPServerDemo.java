/**
 * 2018.12.12   -   Aki
 *
 * Example TCP Server
 * Minimal function for demo in class.
 * You need to fix it to work for chat exercise.
 *
 */

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class TCPServerDemo {
    private static String line = "dummy";
    private static String token;
    private static String fileName;

    public static void main(String argv[]) throws Exception {
        // Test jenkins
        System.out.println("starting main");
        boolean go_on = true;
        int counter = 0;

        ServerSocket welcomeSocket = new ServerSocket(5656);
        System.out.println("we have a socket");
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        while(go_on)
        {
            counter++;
            System.out.println("Waiting for a connection...");
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream  outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            readAllLines(inFromClient);

            System.out.println("I'm sending back to client: Ho, ho, ho,..." + counter);
            outToClient.writeBytes("HTTP/1.1 200 OK\r\n");
            outToClient.writeBytes("Server: Aki's demo server\r\n");
            outToClient.writeBytes("\r\n");
            outToClient.writeBytes("Hum hum, foo, bar,... and crap. counter = " + counter);
            outToClient.writeBytes("\r\n");
            outToClient.writeBytes("Filename: " + fileName);
            connectionSocket.close();
            if (fileName.equals("/quit"))
            {
                go_on = false;
            }
            System.out.println("Sent data to client.");
        }

        System.out.println("Quitting the Server and closing the main socket!!!!!!!!!!!!!!!**************");
        welcomeSocket.close();
    }

    static private void readAllLines(BufferedReader ifc) throws Exception
    {
        BufferedReader inFromClient = ifc;
        line = inFromClient.readLine();
        while (line.length() > 0)
        {
            System.out.println("FROM CLIENT: " + line);
            StringTokenizer tokenizer = new StringTokenizer(line);
            token = tokenizer.nextToken();
            if (token.equals("GET"))
            {
                fileName = tokenizer.nextToken();
            }
            line = inFromClient.readLine();
        }
    }


}