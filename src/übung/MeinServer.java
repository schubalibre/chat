package übung;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import classes.ClientHandler;
import classes.VerySimpleChatServer;

public class MeinServer {
	//Erzeugt ein Objekt von sich selbst
	public static void main(String[] args) {
		new MeinServer().start();
	}

	private void start() {
		try {
			ArrayList<PrintWriter> socketOutputStreams = new ArrayList();

			//ServerSocket erzeugen, mit der Portnummer 4444
			ServerSocket serverSocket = new ServerSocket(4444);

			//Dauerschleife um Client-Anfragen entgegenzunehmen und in eigene Htreads zu stecken.
			while(true) {
				//Client-Verbindungsanfrage zulassen
				Socket clientSocket = serverSocket.accept();
				PrintWriter socketOutputStream = new PrintWriter(clientSocket.getOutputStream());
				socketOutputStreams.add(socketOutputStream);
				
				Thread clientThread = new Thread(new übung.ClientHandler(clientSocket, socketOutputStreams));
				clientThread.start();
			}

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
