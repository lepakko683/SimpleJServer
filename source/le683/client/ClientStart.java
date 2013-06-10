package le683.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientStart {
	
	public static void mainClient(){
		Socket s = null;
		Scanner sc = new Scanner(System.in);
		String line = "exit";
		
		try{
			s=new Socket("192.168.0.49", 7887);
			if(s.isConnected()){
				while((line = sc.nextLine()) != null && !line.equalsIgnoreCase("exit")){
					s.getOutputStream().write((line + "\n").getBytes());
				}
				
				Thread.sleep(3000);
				s.close();
			}
		}catch(IOException | InterruptedException ox){
			ox.printStackTrace();
		}
	}
}
