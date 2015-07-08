package classes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

	BufferedReader reader;
	Socket socket;
	ArrayList<PrintWriter> clientOutputStream;
	

	public ClientHandler(Socket clientSocket, ArrayList<PrintWriter> clientOutputStream) {
		try {
			socket = clientSocket;
			InputStreamReader isReader = new InputStreamReader(	socket.getInputStream());
			reader = new BufferedReader(isReader);
			this.clientOutputStream = clientOutputStream;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override

	public void run() {
		String message;
		try {
			message = reader.readLine();

			while (message != null) {

				System.out.println("read " + message);
				broadcastMessage(message);
				
			}


		} catch (SocketException sx) {
			System.out.println("client connection terminated");
		} catch (Exception ex) {
			ex.printStackTrace();
		}


	}

	private void broadcastMessage(String message) {
		
		for(PrintWriter outClients : clientOutputStream){
			try {
				outClients.println(message);
				outClients.flush();
	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
