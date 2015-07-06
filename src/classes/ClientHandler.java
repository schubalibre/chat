package classes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ClientHandler implements Runnable {

	BufferedReader reader;
	Socket socket;

	public ClientHandler(Socket clientSocket) {
		try {
			socket = clientSocket;
			InputStreamReader isReader = new InputStreamReader(	socket.getInputStream());
			reader = new BufferedReader(isReader);
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
				message = reader.readLine();
			}

		} catch (SocketException sx) {
			System.out.println("client connection terminated");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private void broadcastMessage(String message) {
		try {

			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			out.println(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
