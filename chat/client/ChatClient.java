package chat.client;
 
import java.net.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.*;
 
/**
 * Adapted from https://www.codejava.net/java-se/networking/how-to-create-a-chat-console-application-in-java-using-socket
 * 
 * This is the chat client program.
 * Send message 'bye' to terminate the program.
 *
 * Allow chat user to be specified using system property: -Dchat.user=UserName
 * @author www.codejava.net
 */
public class ChatClient {
	private static final Logger LOGGER = Logger.getLogger( ChatClient.class.getName() );
	
	private static final String DEFAULT_CHAT_SERVER_HOST = "localhost";
	private static final String DEFAULT_USER = "Chatterbox";
	private static final String CHAT_USER_SYS_PROP_KEY="chat.user"; 
	
	private static final String USAGE = 
        "Syntax: java [-Dchat.user=Chatterbox] ChatClient [chat-server-host] <chat-server-port>";
	
    private int port;
    private String hostname;
    private String userName;
 
    public ChatClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }
 
    public void execute() {
        try {
            Socket socket = new Socket(hostname, port);
 
            LOGGER.info("Connected to the chat server");
 
            new ReadThread(socket, this).start();
            new WriteThread(socket, this).start();
 
        } catch (UnknownHostException ex) {
        	LOGGER.log( Level.SEVERE, ex.toString(), ex );
            //System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
        	LOGGER.log( Level.SEVERE, ex.toString(), ex );
            //System.out.println("I/O Error: " + ex.getMessage());
        }
 
    }
 
    ChatClient setUserName(String userName) {
        this.userName = userName;
        return this;
    }
 
    String getUserName() {
        return this.userName;
    }
 
    public static void main(String[] args) {
    	String user = System.getProperty(CHAT_USER_SYS_PROP_KEY, DEFAULT_USER);
        switch (args.length) {
		case 1: {
			System.out.println("Using 'localhost' for chat server host name.");
        	new ChatClient(DEFAULT_CHAT_SERVER_HOST, Integer.parseInt(args[0])).setUserName(user).execute();
        	return;
		}
		case 2: {
        	new ChatClient(args[0], Integer.parseInt(args[1])).setUserName(user).execute();
        	return;
		}
		default:
			LOGGER.log( Level.SEVERE, USAGE);
			//System.err.println(USAGE);
			LOGGER.log( Level.SEVERE, "Missing or unexpected args: ",  Arrays.asList(args));
	        System.exit(3);
		}
    }
}