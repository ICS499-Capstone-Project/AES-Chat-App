package com.test2.ics483.edu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import org.apache.log4j.Logger;

class Server implements Runnable {
	private final static Logger LOGGER = Logger.getLogger(Server.class.getName());
	Socket connectionSocket;
	public static Vector<BufferedWriter> clients = new Vector<BufferedWriter>();

	public Server(Socket s) {
		try {
			System.out.println("Client Got Connected  ");
			connectionSocket = s;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connectionSocket.getOutputStream()));

			clients.add(writer);
			String data1 = "";
			while (true) {
				data1 = reader.readLine();

				LOGGER.info("Received: " + data1);
				for (int i = 0; i < clients.size(); i++) {
					try {
						BufferedWriter bw = (BufferedWriter) clients.get(i);
						bw.write(data1);
						bw.newLine();
						bw.flush();

					} catch (Exception e) {
						e.printStackTrace();
						LOGGER.error("Connection reset by peer: socket write error");
					}
				}
			}
		} catch (Exception e) {
			LOGGER.info("Connection Reset");
		}
	}

	public static void main(String argv[]) throws Exception {
		// System.out.println("Chat Server is Running ");
		@SuppressWarnings("resource")
		ServerSocket mysocket = new ServerSocket(999);
		System.out.println("Server is running on IP address: " + mysocket.getInetAddress() + ", on port: "
				+ mysocket.getLocalPort());

		while (true) {
			System.out.println("Waiting for connection...");
			Socket sock = mysocket.accept();
			System.out.println("Connection accepted");
			Server server = new Server(sock);
			Thread serverThread = new Thread(server);
			serverThread.start();

		}
	}
}