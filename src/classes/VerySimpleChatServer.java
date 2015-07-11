package classes;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class VerySimpleChatServer {
	
	/* Server hört auf Port 8080, ob jemand anmelden will. accept. kreiert clientServer, packt in thread rein, und hört wieder auf Port 8080.
	 * Manager für die Anmeldung.
	 * Diese Klasse beinhaltet den Main Server. Diesen brauchen wir als Vermittlungsstelle.
	 * Alle Clients müssen sich bei diesem Server anmelden, um am Chat teilnehmen zu können.
	 * Die Main Methode ruft den Server als Objekt auf und startet ihn.
	 * In der start-Methode generieren wir uns einen clientOutputStream (eine ArrayList),
	 * wo alle Clients, die sich anmelden, hereinkommen. Wir versuchen, den Server zu starten (mit new Server Socket)
	 * Der Server ist immer in seinem eigenen System (8080).
	 * In der while-Schleife (läuft immer, da true) hören wir, ob sich im Netzwerk jemand mit dem Server verbinden möchte.
	 * Damit das funktioniert, sagen wir, dass wir jede einkommende Anfrage akzeptieren.
	 * Wir holen uns den outputStream (Writer) und speichern sie in einem Array.
	 * Das Ganze lagern wir in ein internen Lebensfaden aus (Thread), um die Blockierung zu vermeiden.
	 * Im Thread ist nun das Objekt des ClientHandlers, und die Array List (outputStreams) wo die writer der anderen drin sind.
	 * mit t.start starten wir den Thread. 
	 */
	

	public static void main(String[] args) {
		new VerySimpleChatServer().start();
	}

	private void start() {
		ArrayList<PrintWriter> clientOutputStream = new ArrayList<PrintWriter>();

		try {
			ServerSocket serverSocket = new ServerSocket(8080);
			while(true){
				Socket clientScoket = serverSocket.accept();
				PrintWriter writer = new PrintWriter(clientScoket.getOutputStream());
				clientOutputStream.add(writer);
				
				Thread t = new Thread(new ClientHandler(clientScoket, clientOutputStream));
				t.start();
				System.out.println("client connection established");
			}
		} catch (Exception ex) {ex.printStackTrace();}
	}

}
