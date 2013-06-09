package le683.server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerStart {
	
	private static ConnectionHandler conHand;
	
	public static boolean stopped;
	
	public static void mainServer(){
		ServerStart.stopped = false;
		
		ServerSocket ss = null;
		try{
			 ss = new ServerSocket(7887);
		}catch(IOException ix){
			ix.printStackTrace();
		}
		conHand = new ConnectionHandler(5);
		Thread ch = new Thread(conHand);
		ch.start();
		
		while(!ServerStart.stopped && ss != null){
			try {
				System.out.println("New connection: " + conHand.addConnection(new Connection(ss.accept())));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
