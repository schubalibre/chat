package übung;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import classes.ClientHandler;
import classes.VerySimpleChatServer;

public class MeinServer {
	//Erzeugt ein Objekt von sich selbst
	public static void main(String[] args) {
		new MeinServer().start();
	}

	private void start() {
		try {
			//ServerSocket erzeugen, mit der Portnummer 4444
			ServerSocket serverSocket = new ServerSocket(4444);
			
			//Dauerschleife um Client-Anfragen entgegenzunehmen und in eigene Htreads zu stecken.
			while(true) {
				//Client-Verbindungsanfrage zulassen
				Socket clientSocket = serverSocket.accept();
				
				//Outputstream des Clients abgreifen
				PrintWriter clientOutputStream = new PrintWriter(clientSocket.getOutputStream());
				
				//Eigenen Thread für Client starten um auf neue Client-Anfrage reagieren zu können.
				Thread clientThread = new Thread(new übung.ClientHandler(clientSocket, clientOutputStream));
				clientThread.start();
			}

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
