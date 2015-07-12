package übung;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
	//Attribute
	Socket clientSocket;
	BufferedReader socketInputStream;
	String clientMessage;
	ArrayList<PrintWriter> socketOutputStreams = new ArrayList();
	
	//Konstruktor
	public ClientHandler(Socket clientSocket, ArrayList<PrintWriter> socketOutputStreams) {
		try {
			this.socketOutputStreams = socketOutputStreams;
			this.clientSocket = clientSocket;
			socketInputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Methoden
	@Override
	public void run() {
		try {
			do {
				//Input
				clientMessage = socketInputStream.readLine();
				//Output
				for(PrintWriter index : socketOutputStreams) {
					index.println(clientMessage);
					index.flush();
				}
			} while (clientMessage != null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
