package übung;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import classes.IncomingReader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Chat extends Application {
	//Attribute
	BufferedReader socketInputStream;
	PrintWriter socketOutputStream;
	BorderPane chatFrame = new BorderPane();
	ListView<String> clientHandlerMessage = new ListView();
	TextField clientMessage = new TextField();
	Button sendButton = new Button("Send");
	Thread socketOutputStreamReaderThread;
	
	@Override
	public void start(Stage stage) throws Exception {
		setUpNetworking();
		setUpChatFrame();
		socketOutputStreamReaderThread = new Thread(new GetClientHandlerMessage(socketInputStream, clientHandlerMessage));
		socketOutputStreamReaderThread.start();

		
		Scene scene = new Scene(chatFrame, 250, 500);
		stage.setTitle("Chat");
		stage.setScene(scene);
		stage.show();
	}
	
	private void setUpNetworking() {
		try {
			//Meine IP
			Socket socket = new Socket("127.0.0.1", 4444);
			socketInputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			socketOutputStream = new PrintWriter(socket.getOutputStream());
		} catch (IOException ex) { ex.printStackTrace();}
	}

	private void setUpChatFrame() {
		chatFrame.setCenter(clientHandlerMessage);
		chatFrame.setBottom(new HBox(clientMessage,sendButton));
		clientMessage.setOnAction(e -> sendMessage());
		sendButton.setOnAction(e -> sendMessage());
	}
	
	private void sendMessage() {
		socketOutputStream.println(clientMessage.getText());
		socketOutputStream.flush();
		clientMessage.setText("");
		clientMessage.requestFocus();
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
