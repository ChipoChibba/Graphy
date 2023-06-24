import java.awt.*;
import java.io.*;
import java.net.Socket;

/**
 * Handles communication between the server and one client, for SketchServer
 * @author Chikwanda Chisha
 * @author Chipo Chibbamulilo
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised Winter 2014 to separate SketchServerCommunicator
 */
public class SketchServerCommunicator extends Thread {
	private Socket sock;					// to talk with client
	private BufferedReader in;				// from client
	private PrintWriter out;				// to client
	private SketchServer server;// handling communication for
	private EditorMessages messages;
	private ServerMessages serverMessages;

	private Sketch sketch = new Sketch();

	public SketchServerCommunicator(Socket sock, SketchServer server) {
		this.sock = sock;
		this.server = server;
	}

	/**
	 * Sends a message to the client
	 * @param msg
	 */
	public void send(String msg) {
		out.println(msg);
	}
	
	/**
	 * Keeps listening for and handling (your code) messages from the client
	 */
	public void run() {
		try {
			System.out.println("someone connected");
			
			// Communication channel
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(), true);

			// Tell the client the current state of the world
			// TODO: YOUR CODE HERE

			//this is for sending all the elements in the tree map to the editor communicator
			for(int i: server.getSketch().getNaviKeySet()){
				String msg = "DRAW ";
				msg += server.getSketch().getShapeMap().get(i).toString() + " ";
				out.println(msg);
			}

			// Keep getting and handling messages from the client
			// TODO: YOUR CODE HERE
			String line;
			serverMessages = new ServerMessages();

			while((line = in.readLine())!=null) {

				System.out.println("received: "+ line);//this is what the user has sent
				String[] input = line.split(" ");

				serverMessages.msgDecoder(input,server);

//				System.out.println(server.getSketch().getShapeMap()); // for debugging purposes
				server.broadcast(line);
			}

			//Clean up -- note that also remove self from server's list so it doesn't broadcast here
			server.removeCommunicator(this);
			out.close();
			in.close();
			sock.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
