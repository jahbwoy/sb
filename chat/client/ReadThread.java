package chat.client;
 
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
 
/**
 * This thread is responsible for reading server's input and printing it
 * to the console.
 * It runs in an infinite loop until the client disconnects from the server.
 *
 * @author www.codejava.net
 */
public class ReadThread extends Thread {
	private static final Logger LOGGER = Logger.getLogger( ChatClient.class.getName() );

    private BufferedReader reader;
    private Socket socket;
    private ChatClient client;
 
    public ReadThread(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;
 
        try {
            InputStream input = this.socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException ex) {
        	LOGGER.log( Level.SEVERE, ex.toString(), ex );
            //System.out.println("Error getting input stream: " + ex.getMessage());
            //ex.printStackTrace();
        }
    }
 
    public void run() {
        while (true) {
            try {
                String response = reader.readLine();
                System.out.println("\n" + response);
 
                // prints the username after displaying the server's message
                if (client.getUserName() != null) {
                    System.out.print("[" + client.getUserName() + "]: ");
                }
            } catch (IOException ex) {
            	LOGGER.log( Level.SEVERE, ex.toString(), ex );
                //System.out.println("Error reading from server: " + ex.getMessage());
                //ex.printStackTrace();
                break;
            }
        }
    }
}