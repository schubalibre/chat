package classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ChatFensterUI extends Application {
	
	/*
	 * Oberfläche für den Chat.
	 */
	
	ListView<String> incoming;
	TextField outgoing;
	BorderPane chatFrame = new BorderPane();
	BufferedReader reader;
	PrintWriter writer;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		setUpChatFrame();
		
		Scene scene = new Scene(chatFrame, 250, 500);
		
		primaryStage.setTitle("Chat");
		primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	/*
	 * incoming sind die ankommenden Nachrichten.
	 * Scrollen ermöglichen.
	 * outgoing ist das Textfeld zum Schreiben.
	 * Wenn man auf Enter klickt, ruft sie die Methode sendMessage auf.
	 * Button macht das Gleiche.
	 */
	private void setUpChatFrame() {
		incoming = new ListView<>();

		incoming.setEditable(false);
		ScrollPane qScroller = new ScrollPane(incoming);
		
		
		outgoing = new TextField();
		outgoing.setPrefWidth(200);
		outgoing.setOnAction(e -> sendMessage());
		
		Button sendButton = new Button("Send");
		sendButton.setOnAction(e -> sendMessage());
		
		chatFrame.setCenter(qScroller);
		chatFrame.setBottom(new HBox(outgoing,sendButton));
		
		setUpNetworking();
		
		//Thread für IncomingReader starten
		Thread readerThread = new Thread(new IncomingReader(reader,incoming));
		readerThread.start();
		
	}

	private void setUpNetworking() {
		/*
		 * Socket initialisieren, input und output zugreifbar.
		 * 
		 */
		try {
			Socket socket = new Socket("192.168.0.109", 8080);
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			InputStreamReader streamReader = new InputStreamReader(in);
			reader = new BufferedReader(streamReader);
			writer = new PrintWriter(out);
			System.out.println("networking established");
		} catch (IOException ex) { ex.printStackTrace();}
		
	}

	/*
	 * Holt sich den Text aus der Textbox und packt es in den Writer, den Kanal zum Rausschicken.
	 * Flush schickt es los.
	 * TEXTbox wieder leer machen.
	 */
	private void sendMessage() {
		writer.println(outgoing.getText());
		writer.flush();
		outgoing.setText("");
		outgoing.requestFocus();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
