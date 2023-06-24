import java.awt.*;
import java.io.*;
import java.net.Socket;

/**
 * Handles communication to/from the server for the editor
 * @author Chikwanda Chisha
 * @author Chipo Chibbamulilo
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author Chris Bailey-Kellogg; overall structure substantially revised Winter 2014
 * @author Travis Peters, Dartmouth CS 10, Winter 2015; remove EditorCommunicatorStandalone (use echo server for testing)
 */
public class EditorCommunicator extends Thread {
	private PrintWriter out;		// to server
	private BufferedReader in;		// from server
	protected Editor editor; // handling communication for
	private EditorMessages messages;
	/**
	 * Establishes connection and in/out pair
	 */
	public EditorCommunicator(String serverIP, Editor editor) {
		this.editor = editor;
		System.out.println("connecting to " + serverIP + "...");
		try {
			Socket sock = new Socket(serverIP, 4242);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			System.out.println("...connected");
		}
		catch (IOException e) {
			System.err.println("couldn't connect");
			System.exit(-1);
		}
	}

	/**
	 * Sends message to the server
	 */
	public void send(String msg) {
		out.println(msg);
	}

	/**
	 * Keeps listening for and handling (your code) messages from the server
	 */
	public void run() {
		try {
			// Handle messages
			// TODO: YOUR CODE HERE
			messages = new EditorMessages();
			String line;

			while((line = in.readLine())!=null) {
				String[] input = line.split(" ");
				messages.msgDecoder(input, editor);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("server hung up");
		}
	}

	// Send editor requests to the server
	// TODO: YOUR CODE HERE

	/**
	 * sends draw request with shape to serve
	 * @param shape to be sent to server
	 */
	public void requestToDraw (Shape shape){
		send("DRAW " + shape);}

	/**
	 * sends move request from editor to server
	 * @param shapeID to be sent to server
	 * @param dx to be sent to server
	 * @param dy to be sent to server
	 */
	public void requestToMove(int shapeID, int dx, int dy){send("MOVE " + shapeID +" "+ dx +" "+ dy);}

	/**
	 * sends request to server to recolor a particular shape
	 * @param shapeID id of shape to be recolored
	 * @param color rgb value of color
	 */
	public void requestToColor(int shapeID, int color){send("RECOLOR " + shapeID +" "+ color);}

	/**
	 * sends  editor's delete request to server
	 * @param shapeID -id of the shape to be deleted
	 */
	public void requestToDelete(int shapeID){
		send("DELETE " + shapeID);
	}
}
