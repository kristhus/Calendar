package serverConnection;

import java.io.*;
import java.net.*;

import visual.MainFrame;
 
public class Client {
	
//	private static String hostName = "78.91.39.136";
	private static String hostName = "129.241.127.115";
	private static int portNumber = 8997;
	private static Socket clientSocket;
	
    private PrintWriter out;
    private ObjectOutputStream oos;
    ObjectInputStream ois;
	
	
    public Client() {    
    	MainFrame.setClient(this);
    }
    
    public void connect() {
    	System.out.println("connect");
        try {
        	clientSocket = new Socket(hostName, portNumber);
    		out = new PrintWriter(clientSocket.getOutputStream(), true);
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			ois = new ObjectInputStream(clientSocket.getInputStream());  // HER E DET NÅKKE SOM SKJÆR SEJ
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
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
    		connect();
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
			closeConnection();
			return toReturn;
		} catch (IOException e) {
			e.printStackTrace();
		}
    	finally {
    	}
    	closeConnection();
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
	
	public void setPort(int port) {
		portNumber = port;
	}
	public void setHostName(String ip) {
		hostName = ip;
	}
    
}
