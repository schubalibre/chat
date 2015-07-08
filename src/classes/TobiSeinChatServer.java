package classes;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TobiSeinChatServer {
	/*Es wird ein Objekt vom Server erstellt und die Methode start() ausgeführt. */
	public static void main(String[] args) {
		new TobiSeinChatServer().start();
	}
	
	/*Methode start()*/
	public void start() {
		//Die Collection enthält die OutputStreams aller Clients
		ArrayList<PrintWriter> clientOutputStreams = new ArrayList();
		
		try {
			//Diesen Socket beobachtet der Server
			ServerSocket serverSocket = new ServerSocket(8080);
			
			//Dauerschleife
			//Behandle Client anfragen
			//Genauer schreibe alle Outputstreams der Clients in die Colelction
			while(true) {
				//ClientSocet bekommt den Client zugewiesen, der grade am ServerSocket 8080 anfragt
				Socket clientSocket = serverSocket.accept();
				
				//Der Outputstream bekommt den OutputStream des Clients zugewiesen
				PrintWriter clientOutputStream = new PrintWriter(clientSocket.getOutputStream());
				
				//Der OutputStream des Clients wird der Collection hinzugefügt.
				clientOutputStreams.add(clientOutputStream);
				
				//Jeder Client bekommt seinen eigenen Thread
				//Im wird dabei ein ClientHandler zugewiesen
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
