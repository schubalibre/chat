package classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class TobiSeinClientHandler implements Runnable {
	//Attribute
	BufferedReader reader;
	Socket socket;
	
	//Konstruktor
	//Hier wird 
	public TobiSeinClientHandler(Socket clientSocket, ArrayList<PrintWriter> clientOutputStreams) {
		try {
			socket = clientSocket;
			InputStreamReader clientSocketStream = new InputStreamReader(socket.getInputStream());
			reader = new BufferedReader(clientSocketStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		
	}

}
