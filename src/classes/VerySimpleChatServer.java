package classes;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class VerySimpleChatServer {
	
	ArrayList<PrintWriter> clientOutputStream;
	
	public static void main(String[] args) {
		new VerySimpleChatServer().start();
	}

	private void start() {
		clientOutputStream = new ArrayList<PrintWriter>();
		try {
			ServerSocket serverSocket = new ServerSocket(8080);
			while(true){
				Socket clientScoket = serverSocket.accept();
				PrintWriter writer = new PrintWriter(clientScoket.getOutputStream());
				clientOutputStream.add(writer);
				
				Thread t = new Thread(new ClientHandler(clientScoket));
				t.start();
				System.out.println("client connection established");
			}
		} catch (Exception ex) {ex.printStackTrace();}
	}
}
