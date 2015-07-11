package classes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
	
	/* Holt alles aus InputStream und packt es in den outputStream der anderen.
	 * Der Konstruktor bekommt clientSocket (Verbindung, die der Server gegeben hat), und die outputStreams der anderen.
	 * Alles wird global zugreifbar gemacht.
	 * Wir holen uns den inputStream (Reader), den wir im Konstruktor initialisieren.
	 * 
	 * Diese Klasse implementiert das UnserInterface Runnable (nötig, um als Thread ausgeführt zu werden)
	 * Der Server führt diese Klasse als Thread aus.
	 * Runnable hat die Methode run, die wir hier überschreiben.
	 * Run: Wird vom Thread einmal aufgerufen und man muss sich selbst drum kümmern, wie oft sie danach ausgeführt werden soll.
	 * Es geht darum, Nachrichten abzufangen, die der Nutzer eingibt.
	 * Wir lesen ein vom BufferedReader.
	 * In der while-Schleife rufen wir broadcastMessage ein.
	 * Einlesen - Weitergeben - wieder einlesen
	 * 
	 * BroadcastMessage: nimmt sich Array List mit allen outputStreams und läuft es durch, um llen Clients die Nachrichten rauszuschicken.
	 * Flush schreibt aus dem Speicher raus.
	 * 
	 */

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
