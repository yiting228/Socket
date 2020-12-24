package connect.base;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Scanner;

/**
 * The very basic socket client that only send one single message.
 */
public class BaseSocketClient {
	private String serverHost;
	private int serverPort;
	private Socket socket;
	private OutputStream outputStream;

	public BaseSocketClient(String host, int port) {
		this.serverHost = host;
		this.serverPort = port;
	}

	public void connetServer() throws IOException {
		this.socket = new Socket(this.serverHost, this.serverPort);
		this.outputStream = socket.getOutputStream();
	}

	public void send(String message) throws IOException {
		String sendMsg = message + "\n"; // we mark \n as a end of line.
		try {
			this.outputStream.write(sendMsg.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		}
	}

	public void close() throws IOException {

		
		this.socket.shutdownOutput();
		this.outputStream.close();
		System.out.println("client  byby");
	}

	public static void main(String[] args) {
		BaseSocketClient cc = new BaseSocketClient("127.0.0.1", 9799);
		try {
			cc.connetServer();
			Scanner sc = new Scanner(System.in);
			while (sc.hasNext()) {
				String line = sc.nextLine();
				if (line.equals("exit")) {
					cc.close();
					break;
				} else {
					cc.send(line);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}