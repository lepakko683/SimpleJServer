package le683.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Connection extends Thread implements Runnable {
	
	private Socket client;
	
	private boolean listenerStarted;
	private boolean listenerStopped;
	
	private long startTime;
	private long lastResponse;
	
	public Connection(Socket client){
		this.client = client;
		this.listenerStarted = false;
		this.listenerStopped = false;
		this.startTime = System.currentTimeMillis();
		this.lastResponse = this.startTime;
	}
	
	public boolean isConnected(){
		try{
			return client.isConnected();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	public boolean isListenerStarted(){
		return this.listenerStarted;
	}
	public boolean isListenerStopped(){
		return this.listenerStopped;
	}
	
	public void startListener() throws IllegalThreadStateException{
		if (!listenerStarted) {
			this.listenerStarted = true;
			this.start();
		}
	}
	
	public synchronized boolean isTimedOut(){
		return System.currentTimeMillis()-this.lastResponse >= 10000;
	}
	public synchronized void forceCloseConnection(){
		try {
			client.close();
			client = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			while(!isTimedOut() && client != null){
				
				String ln = null;
				try{
					ln = br.readLine();
				}catch(Exception e){
					e.printStackTrace();
					this.listenerStopped = true;
					System.out.println("stopped");
				}
				
				if(ln != null){
					System.out.println(ln);
					this.lastResponse = System.currentTimeMillis();
					System.out.println("Connection refresh!");
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		this.listenerStopped = true;
		System.out.println("got To the END");
	}
	
}
