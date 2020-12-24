package connect.base;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class BaseSocketServer {
	private ServerSocket server;
	private Socket socket;
	private int port;
	private InputStream inputStream;


	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public BaseSocketServer(int port) {
		this.port = port;
	}

	public void runServerSingle() throws IOException {
		this.server = new ServerSocket(this.port);
		System.out.println("base socket server started.");
		// the code will block here till the request come.
		this.socket = server.accept();
		this.inputStream = this.socket.getInputStream();
		Scanner sc = new Scanner(this.inputStream);
		while (sc.hasNextLine()) {
			System.out.println("get info from client: " + sc.nextLine());
		} 
		inputStream.close();
		socket.close();
		server.close();
		System.out.println("exit");
	}

	public static void main(String[] args) {
		BaseSocketServer bs = new BaseSocketServer(9799);
		try {
			bs.runServerSingle();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}