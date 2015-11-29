import java.io.*;
import java.net.*;

public class Client {
    private Socket socket = null;
    private DataOutputStream os = null;
    private DataInputStream is = null;

    private static void initializeSocket(String host, int port) {
        // Attempts to connect to the specified server at the specified port.
        // If this constructor does not throw an exception,
        // the connection is successful and the client is connected to the server.
        try {
            socket = new Socket(host, port);
            os = new DataOutputStream(socket.getOutputStream());
            is = new DataInputStream(socket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + host);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + port);
        }
    }

    private static void closeSocket() {
        // Clean up: close the output stream, close the input stream, close the socket
        os.close();
        is.close();
        socket.close();
    }


    public static void main(String[] args) {
        // Try to open a socket on port 25
        // Try to open input and output streams
        Client.initializeSocket("hostname", 25);
        
        // If everything has been initialized then we want to write some data
        // to the socket we have opened a connection to on port 25
        if (socket != null && os != null && is != null) {
            try {
                // The capital string before each colon has a special meaning to SMTP
                // you may want to read the SMTP specification, RFC1822/3
                os.writeBytes("HELO\n");    
                    os.writeBytes("MAIL From: k3is@fundy.csd.unbsj.ca\n");
                    os.writeBytes("RCPT To: k3is@fundy.csd.unbsj.ca\n");
                    os.writeBytes("DATA\n");
                    os.writeBytes("From: k3is@fundy.csd.unbsj.ca\n");
                    os.writeBytes("Subject: testing\n");
                    os.writeBytes("Hi there\n"); // message body
                    os.writeBytes("\n.\n");
                os.writeBytes("QUIT");
                // keep on reading from/to the socket till we receive the "Ok" from SMTP,
                // once we received that then we want to break.
                String responseLine;
                while ((responseLine = is.readLine()) != null) {
                    System.out.println("Server: " + responseLine);
                    if (responseLine.indexOf("Ok") != -1) {
                      break;
                    }
                }
                // Close the socket
                Client.closeSocket();   
            } catch (UnknownHostException e) {
                System.err.println("Trying to connect to unknown host: " + e);
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
            }
        }
    }           
}