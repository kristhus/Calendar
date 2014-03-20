package serverConnection;

import java.io.*;
import java.net.*;
 
public class Client {
	
	private static String hostName = "78.91.39.136";
//	private static String hostName = "129.241.127.115";
	private static int portNumber = 8997;
	private static Socket clientSocket;
	
        
    public Client() {    
        try {
        	clientSocket = new Socket(hostName, portNumber);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
        	e.printStackTrace();
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
    }
    
    public Object sendMsg(Object[] obj) {
    	try {
    		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
			out.println(obj[0]);
			oos.writeObject(obj);
			System.out.println("sendMsg");
			Object toReturn = null;
			try {
				toReturn = ois.readObject();
				System.out.println(toReturn + " Detta objektet");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return toReturn;
		} catch (IOException e) {
			e.printStackTrace();
		}
    	finally { }
    	Object[] toReturn = {};
    	return toReturn;
    }
    
	public void closeConnection() {
		try {
			System.out.println("Closing connection to server");
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			out.println("bye.");
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void reconnect() {
		
	}
    
}
