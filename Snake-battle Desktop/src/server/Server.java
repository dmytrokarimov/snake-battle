package server;

import gui.Common;
import gui.ObjectAlreadyAddedException;
import gui.Screen;
import gui.Common.ActionList;
import gui.Common.MapAlreadyExistException;
import gui.Common.MapNotExistException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import logic.Map;
import logic.Snake;

/**
 * �������� ������� ����������
 * 
 * @author RED Developer
 */
public class Server {
	static class ServerThread extends Thread{

		public static ServerSocket sServer; 		// ��������� �����
		public static Socket sClient; 				// ���������� �����
		public static InputStream is; 				// ������ ��������� ������
		public static OutputStream os; 				// ������ ���������� ������
		public static ObjectInputStream ois; 		// ������ ��������� ������ �������
		public static ObjectOutputStream oos; 		// ������ ���������� ������ �������

		// private static List<Socket> clients = null;
		private Message message = null;				// ���������, ���������� �� �������
		private static int port = 65535; 			// �������������� ����
		private static BufferedReader in = null; 	// ����� �����
		private static PrintWriter out = null;		// ����� ������

		private static Battle battle = null; 		// ������ ������ Battle ��� ������� ���������� �����
		private static Snake[] snakes = null;		// ������ ��������, ������������ � �����
		private static Map map = null;				// �����, �� ������� ���������� �����

		/**
		 * ����������� ������ �������
		 * @throws IOException
		 */
		public ServerThread() throws IOException {
			sServer = new ServerSocket(port);		// ������������� ���������� ������ �� ������������� ����� port
			//is = sClient.getInputStream(); 			// ������������� ��������� ������
			//os = sClient.getOutputStream(); 		// ������������� ���������� ������
			 //ois = new ObjectInputStream(is); 	// ������������� ��������� ������ �������
			 //oos = new ObjectOutputStream(os); 	// ������������� ���������� ������ �������

			start(); // ������ ������
		}
		
		/**
		 * �������� ������ �� ���������� ��������� ������
		 * @param ois - �������� �����
		 * @return
		 * @throws IOException
		 */
		private synchronized Object receiveObject(ObjectInputStream ois) throws IOException {
			Object receivedObject = null;							// ����� "���������� ������" 
			try {
				// ������ ������� �� ��������� ������
				receivedObject = ois.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			//ois.close();
			return receivedObject;
		}
		
		/**
		 * ������� ��������� ������ � ��������� �������� ����� 
		 * @param oos - ��������� �����
		 * @param sendingObject - ������������ ������
		 */
		private synchronized void sendObject(ObjectOutputStream oos, Object sendingObject) {
			// ������������ ������������ ������
			try {
				// ��������� ���������� ������ socket'�
				oos = new ObjectOutputStream(sClient.getOutputStream());
				// ������ ������� � ��������� �����
				oos.writeObject(sendingObject);
				// ??? ??? ???
				oos.flush();
				// �������� ���������� ������
				oos.close();
			} catch (Exception ex) {
				System.out.println("[SERVER]: Exception during serialization: " + ex);
				System.exit(0);
			}
		}
		
		/**
		 * ��������� ��������� ������ � ��������� ����� socket'�, �������������� ������������ ���
		 * @param socket
		 * @param object
		 */
		private void sendObject(Socket socket, Object object){
			// ��������� �����
			ObjectOutputStream oos = null;
			// ������������ ������������ ������
			try {
				// ��������� ���������� ������ socket'�
				oos = new ObjectOutputStream(socket.getOutputStream());
				// ������ ������� � ��������� �����
				oos.writeObject(object);
				// ??? ??? ???
				oos.flush();
				// �������� ���������� ������
				oos.close();
			} catch (Exception ex) {
				System.out.println("[SERVER]: Exception during serialization: " + ex);
				System.exit(0);
			}
		}

		/**
		 * ��������� ����� �������
		 */
		public void run() {
			while(true){
			// �������������
			battle = new Battle();
			snakes = battle.snake_fill();
			
			try {
				// �������� ������, �� ������� ����� ���������� ��� �������� �����
				new Screen();
				
				test(); } catch (IOException e) {}
			}
		}
		
		private void test() throws IOException{
			try {
				// ������������� �����, �����
				battle.init("serverMap", snakes);
				map = Common.selectMap("serverMap");
				//map.setBorder(800, 600);
			} catch (MapAlreadyExistException | MapNotExistException | ObjectAlreadyAddedException e) {
				e.printStackTrace();
			}
			System.out.println("[SERVER]: calc--->");
			List<ActionList> al = battle.battleCalc(snakes);
			System.out.println("[SERVER]: <---calc");
			
			// ������ �� ������������� �����
			/*try {
				sServer = new ServerSocket(port);
			} catch (IOException ex) {
				ex.printStackTrace();
				System.out.println("[SERVER]: Couldn't listen to port " + port);
				System.exit(-1);
			}*/

			// �������� ����������� �������
			//while(clients.size() < 4)
			try {
				//clients.add(new Socket());
				System.out.println("[SERVER]: Waiting for a client...");
				sClient = sServer.accept();
				System.out.println("[SERVER]: Client connected");
			} catch (IOException e) {
				System.out.println("[SERVER]: Can't accept");
				System.exit(-1);
			}
			
			// �������� ����� �� ������
			//sendObject(from�lient, al);
			/*r35 //broadcast(new ArrayList<Socket>(), al); */ 

			is = sClient.getInputStream(); 			// ������������� ��������� ������
			os = sClient.getOutputStream(); 		// ������������� ���������� ������
			ois = new ObjectInputStream(is); 	// ������������� ��������� ������ �������
			//oos = new ObjectOutputStream(os); 	// ������������� ���������� ������ �������

			
			// ������������� ������������� ���������
			message = new Message(map, snakes, al);
			System.out.println("[SERVER]: Message created");
			// �������� ��������� �� ������
			sendObject(sClient, message);
			System.out.println("[SERVER]: Message sended");
			
			sClient.close();
			//sServer.close();
			System.out.println("[SERVER]: Sockets closed");
		}
	}
	
	// ��������� �����
	private ServerThread thread = null;
	
	/**
	 * ����������� �������
	 */
	public Server() {
		// ������ ���������� ������
		try {
			thread = new ServerThread();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * �������� ������� �������
	 * @param args
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		new Server();
	}
}
