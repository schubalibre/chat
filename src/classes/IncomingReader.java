package classes;

import java.io.BufferedReader;
import java.io.IOException;

import javafx.application.Platform;
import javafx.scene.control.ListView;

public class IncomingReader implements Runnable {
	private String message;
	private BufferedReader reader;
	private ListView<String> incoming = new ListView<>();

	@Override
	public void run() {
		try {

			while((message = reader.readLine()) != null){
				System.out.println("client read " + message);
				
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						incoming.getItems().add(0, message + "\n");	
					}
				});
			}
		} catch (IOException ex) { ex.printStackTrace();}

	}

}
