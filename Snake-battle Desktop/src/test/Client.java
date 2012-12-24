package test;

import gui.Common.ActionList;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Client {
	Socket sClient = null;
	String host = "localhost";
	int port = 65535;
	
	public void connect(String host, int port){
		System.out.println("[Client]Connecting to... " + host);
		// Пока не подключился - пытайся
		while(sClient == null)
			try {
				sClient = new Socket(host, port);
			} catch (IOException e) {
				//e.printStackTrace();
				try {
					// Интервал между попытками 5сек
					Thread.sleep(5000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
	}
	public Client() throws UnknownHostException, IOException {
		connect(host, port);
		//BufferedReader in = new BufferedReader(new InputStreamReader(sClient.getInputStream()));
		PrintWriter out = new PrintWriter(sClient.getOutputStream(), true);
		//BufferedReader inu = new BufferedReader(new InputStreamReader(sClient.getInputStream()));

		// Что мы получили от сервера (в сериализованном виде)
		String fuser;
		/*while ((fuser = inu.readLine()) != null) {
			System.out.println(fuser);
		}*/
		//--==--==--==-=-=--=-----===-=--=-=-=-===-=--=-=-=-===-=-=---====-=-=--=-=-=-==-=---=--\\
		List<ActionList> al = new ArrayList<ActionList>();
		InputStream is = sClient.getInputStream();
		ObjectInputStream ois = new ObjectInputStream(is);
		try {
			Object readObject = ois.readObject();
			System.out.println(readObject.toString());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		ois.close();
		
		//System.out.println(al.get(0).action.getType());
		
		System.out.println();

		out.close();
		//in.close();
		//inu.close();
		sClient.close();
	}
}
