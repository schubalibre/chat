package übung;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
	//Attribute
	Socket clientSocket;
	PrintWriter socketOutputStream;
	BufferedReader socketInputStream;
	String clientMessage;
	
	//Konstruktor
//	public ClientHandler(Socket clientSocket, PrintWriter clientOutputStream) {
	public ClientHandler(Socket clientSocket) {
		try {
			this.clientSocket = clientSocket;
			socketOutputStream = new PrintWriter(clientSocket.getOutputStream());
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
				System.out.println("Client sendet: " + clientMessage);
				//Output
				socketOutputStream.write(clientMessage);
				socketOutputStream.flush();
			} while (clientMessage != null);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
