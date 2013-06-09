package le683.main;

import java.io.PrintStream;

import le683.client.ClientStart;
import le683.server.ServerStart;

public class Start {
	
	public static void main(String[] args){
		//java -jar *jarfile* client for client
		//java -jar *jarfile* server for server
		if(args.length >= 1){
			if(args[0].equals("server")){
				ServerStart.mainServer();
			}else if(args[0].equals("client")){
				ClientStart.mainClient();
			}
		}
	}
}
