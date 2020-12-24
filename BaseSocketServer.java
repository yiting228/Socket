package connect.base;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BaseSocketServer extends Thread {

	private ServerSocket server;
	private Socket socket;
	private int port;

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

		ExecutorService threadExecutor = Executors.newCachedThreadPool();
		this.server = new ServerSocket(this.port);
		System.out.println("base socket server started.");

		while (true) {
			this.socket = server.accept();
			threadExecutor.execute(new InputThread(socket));
			threadExecutor.execute(new OutputThread(socket));
		}

	}

	class InputThread implements Runnable {
		private Socket clientSocket;

		public InputThread(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}

		public void run() {
			InputStream inputStream = null;
			try {
				inputStream = clientSocket.getInputStream();
				String addr = clientSocket.getLocalAddress().toString();
				System.out.println("[" + addr + "]Knock!Knock!");
				Scanner sc = new Scanner(inputStream);
				while (sc.hasNextLine()) {
					System.out.println("[" + addr + "]get info from client: " + sc.nextLine());
				}

				System.out.println("[" + addr + "]exit");
			} catch (IOException e) {

				e.printStackTrace();
			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	class OutputThread implements Runnable {
		private Socket clientSocket;

		public OutputThread(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}

		public void run() {
			InputStream inputStream = null;
			try {
				inputStream = clientSocket.getInputStream();
				String addr = clientSocket.getLocalAddress().toString();
				System.out.println("[" + addr + "]Knock!Knock!");
				Scanner sc = new Scanner(inputStream);
				while (sc.hasNextLine()) {
					System.out.println("[" + addr + "]get info from client: " + sc.nextLine());
				}

				System.out.println("[" + addr + "]exit");
			} catch (IOException e) {

				e.printStackTrace();
			} finally {
				try {
					inputStream.close();
//				socket.close();
//				server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
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
