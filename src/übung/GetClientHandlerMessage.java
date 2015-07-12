package übung;

import java.io.BufferedReader;
import java.io.IOException;

import javafx.scene.control.ListView;

public class GetClientHandlerMessage implements Runnable {
	//Attribute
	BufferedReader socketInputStream;
	ListView<String> clientHandlerMessage;
	String message;
	
	//Konstruktor
	public GetClientHandlerMessage(BufferedReader socketInputStream, ListView<String> clientHandlerMessage) {
		this.socketInputStream = socketInputStream;
		this.clientHandlerMessage = clientHandlerMessage;
	}
	
	//Methoden
	@Override
	public void run() {
		try {
			do {
				message = socketInputStream.readLine();
				System.out.println("ClientHandler sendet: " + clientHandlerMessage);
				clientHandlerMessage.getItems().add(message + "\n");
				
			} while (message != null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
