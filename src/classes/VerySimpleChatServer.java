package classes;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class VerySimpleChatServer {
	
	//Erzeugt ein Objekt von sich selbst
	public static void main(String[] args) {
		new VerySimpleChatServer().start();
	}

	private void start() {
		ArrayList<PrintWriter> clientOutputStream = new ArrayList<PrintWriter>();
		try {
			ServerSocket serverSocket = new ServerSocket(8080);
			while(true){
				Socket clientSocket = serverSocket.accept();
				
				// Ist der Server jetzt blockiert bis eine client-Anfrage kommt?
				// Dann müsste der Thread nämlich früher gestartet werden, schon vor .accept()?
				
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
				clientOutputStream.add(writer);
				

				Thread clientThread = new Thread(new ClientHandler(clientSocket, clientOutputStream));
				clientThread.start();
				System.out.println("client connection established");
			}
		} catch (Exception ex) {ex.printStackTrace();}
	}
}
