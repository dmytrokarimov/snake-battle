package client;

import java.io.IOException;
import java.net.UnknownHostException;

import server.Server;
import client.Client;


public class DemoReplay {
	public static void main(String[] args) throws UnknownHostException,
			IOException, InterruptedException {
		new Thread() {
			public void run() {
				try {
					new Server();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("server");
			}
		}.start();

		new Thread() {
			public void run() {
				try {
					try {
						// Ожидание, пока сервер запустится
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					new Client();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("client");
			}
		}.start();
	}
}