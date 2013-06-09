package le683.server;

import java.io.IOException;


public class ConnectionHandler implements Runnable {
	
	private Connection[] connections;
	
	private boolean isStopRequested;
	
	protected boolean stopped;
	
	public ConnectionHandler(int maxConns){
		connections = new Connection[maxConns];
		this.isStopRequested = false;
		this.stopped = false;
	}
	
	public boolean addConnection(Connection c){
		int freeSlot=-1;
		if((freeSlot = getFreeSlot()) != -1){
			connections[freeSlot]=c;
			connections[freeSlot].startListener();
			return true;
		}
		return false;
	}
	
	/**return the number of slots emptied*/
	public int emptyUselessSlots(){
		int count=0;
		for(int i=0;i<connections.length;i++){
			if(connections[i] != null){
				if(connections[i].isUseless() && connections[i].isListenerStopped()){
					connections[i]=null;
					count++;
				}
			}
		}
		return count;
	}
	
	public int getFreeSlot(){
		for(int i=0;i<connections.length;i++){
			if(connections[i] == null){
				return i;
			}
		}
		return -1;
	}
	
	public void setStopRequested(boolean value){
		this.isStopRequested=value;
	}

	@Override
	public void run() {
		while(!this.isStopRequested){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				System.err.println("Interrupted!");
				e.printStackTrace();
			}
			//handle connections
			int clearCount = emptyUselessSlots();
			if(clearCount > 0){
				System.out.println("Emptied " + clearCount + " useless slots!");
			}
			//end
		}
		this.stopped = true;
	}

}
