package classes;

import java.io.BufferedReader;
import java.io.IOException;

import javafx.application.Platform;
import javafx.scene.control.ListView;

public class IncomingReader implements Runnable {
	private String message;
	private BufferedReader reader;
	private ListView<String> incoming;
	
	public IncomingReader(BufferedReader reader,ListView<String> incoming) {
		this.reader = reader;
		this.incoming = incoming;
	}
	
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
				Thread.sleep(1000);
			}
		} catch (IOException | InterruptedException ex) { ex.printStackTrace();}

	}

}
