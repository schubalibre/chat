package übung;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;

import javafx.application.Platform;
import javafx.scene.control.ListView;

public class SocketReader implements Runnable {
	//Attribute
	BufferedReader socketInputStream;
	ListView<String> clientHandlerMessage;
	String message;
	
	//Konstruktor
	public SocketReader(BufferedReader socketInputStream, ListView<String> clientHandlerMessage) {
		this.socketInputStream = socketInputStream;
		this.clientHandlerMessage = clientHandlerMessage;
	}
	
	//Methoden
	@Override
	public void run() {
		try {
			do {
				message = socketInputStream.readLine();
//				System.out.println("ClientHandler sendet: " + message);
				
				//Fix für FX hat sonst probleme mit FX
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						clientHandlerMessage.getItems().add(message + "\n");
					}
				});
			} while (message != null);
		} catch (SocketException sx) {
			System.out.println("client connection terminated");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
