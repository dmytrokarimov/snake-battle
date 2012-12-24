package server;
import gui.Common;
import gui.Common.ActionList;
import gui.Common.MapAlreadyExistException;
import gui.Common.MapNotExistException;
import gui.ObjectAlreadyAddedException;
import gui.Screen;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import logic.Map;
import logic.Snake;

public class Server {
	public static ServerSocket sServer; 		// ��������� �����
	private static int port = 65535; 			// �������������� ����
	
	public static void main(String[] args) throws IOException {
		byte connected = 0; 					// ���������� ������������ ��������
		
		sServer = new ServerSocket(port);
		System.out.println("Server Started");
		try {
			System.out.println("[SERVER]: Waiting for a client...");
			if (connected < 4) {
				Socket sClient = sServer.accept();
				System.out.println("[SERVER]: Client connected");
				try {
					System.out.println(connected++);
					new ServerOneThread(sClient);
				} catch (IOException e) {
					sClient.close();
				}
			}
		} catch (IOException e) {
			System.out.println("[SERVER]: Can't accept");
			System.exit(-1);
		} finally {
			sServer.close();
		}
	}
}

class ServerOneThread extends Thread{
	public static Socket sClient; 				// ���������� �����
	public static InputStream is; 				// ������ ��������� ������
	public static OutputStream os; 				// ������ ���������� ������
	public static ObjectInputStream ois; 		// ������ ��������� ������ �������
	public static ObjectOutputStream oos; 		// ������ ���������� ������ �������
	
	private Message message = null;				// ���������, ���������� �� �������
	private static Battle battle = null; 		// ������ ������ Battle ��� ������� ���������� �����
	private static Snake[] snakes = null;		// ������ ��������, ������������ � �����
	private static Map map = null;				// �����, �� ������� ���������� �����
	
	public ServerOneThread(Socket client) throws IOException
	{
		sClient = client;
		is = sClient.getInputStream(); 			// ������������� ��������� ������
		os = sClient.getOutputStream(); 		// ������������� ���������� ������
		start();
	}
	
	/** ��������� ����� ������� */
	public void run(){
		while(!sClient.isClosed()){
			// �������������
			battle = new Battle();
			snakes = battle.snake_fill();

			try {
				// �������� ������, �� ������� ����� ���������� ��� �������� �����
				new Screen();
				
				/** ���� �� �������� ������� */
				try {
					// ������������� �����, �����
					battle.init("serverMap", snakes);
					map = Common.selectMap("serverMap");
					//map.setBorder(800, 600);
				} catch (MapAlreadyExistException | MapNotExistException | ObjectAlreadyAddedException e) {
					e.printStackTrace();
				}
				List<ActionList> al = battle.battleCalc(snakes);
				System.out.println("[SERVER]: Battle ended");
				
				// �������� ����� �� ������
				ois = new ObjectInputStream(is); 	// ������������� ��������� ������ �������
				//oos = new ObjectOutputStream(os); 	// ������������� ���������� ������ �������
				
				// ������������� ������������� ���������
				message = new Message(map, snakes, al);
				System.out.println("[SERVER]: Message created");
				// �������� ��������� �� ������
				sendObject(sClient, message);
				System.out.println("[SERVER]: Message sended");
				
				sClient.close();
				System.out.println("[SERVER]: Sockets closed");
			} catch (IOException e) {}
		}
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
}