package org.snakebattle.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import org.snakebattle.gui.ObjectAlreadyAddedException;
import org.snakebattle.gui.screen.Screen;
import org.snakebattle.logic.BattleMap;
import org.snakebattle.logic.Snake;
import org.snakebattle.server.Battle;
import org.snakebattle.server.Message;
import org.snakebattle.utils.BattleMapUtils;
import org.snakebattle.utils.BattleMapUtils.ActionList;
import org.snakebattle.utils.BattleMapUtils.MapAlreadyExistException;
import org.snakebattle.utils.BattleMapUtils.MapNotExistException;

/**
 * ��������� ������� ����������
 * @author RED Developer
 */
public class Client2 {
	static class ClientThread extends Thread {
		public static Socket sClient;								// ���������� �����
		public static InputStream is;								// ������ ��������� ������
		public static OutputStream os;								// ������ ���������� ������
		public static ObjectInputStream ois;						// ������ ��������� ������ �������
		public static ObjectOutputStream oos;						// ������ ���������� ������ �������
		
		// ���������� ������������� ����������
		private Message message = null;								// ���������, ���������� �� �������
		private	List<ActionList> actions = null;					// ������ ����� ��� (���������� � �������)
		private Snake[] snakes = null;								// ������, ������������ � ��� (���������� � �������)
		private Snake mySnake = null;								// ������ ������� (��������� �� ������)
		private BattleMap battleMap = null;										// �����, �� ������� ���������� �����
		private Battle battle = null;								// ������ ������ Battle, ��� ��������������� �����
		
		static String host = "localhost";							// ����� �������
		static int port = 65535;									// ���� �������
		/**
		 * ����������� ������ �������
		 * @throws IOException
		 */
		public ClientThread() throws IOException {
			this(InetAddress.getByName(host), port);			
		}
		
		/**
		 * ����������� ������ �������
		 * @param host
		 * @param port
		 * @throws IOException
		 */
		public ClientThread(InetAddress host, int port) throws IOException {
			connect(host, port);									// ������������� ������ �������
			
			os = sClient.getOutputStream();							// ������������� ���������� ������
			//oos = new ObjectOutputStream(os);						// ������������� ���������� ������ �������
			
			start();												// ������ ������
		}
		
		/*+++ ��������������� �������, ���������� �� ������ �� ������� +++*/
		/**
		 * ������������ ������������� ����������� � �������
		 * @param host - ����� �����
		 * @param port - ���� �����
		 */
		private void connect(InetAddress host, int port){
			System.out.println("[CLIENT]: Connecting to... " + host);
			// ���� �� ����������� - �������
			while(sClient == null)
				try {
					sClient = new Socket(host, port);
					System.out.println("[CLIENT]: Connected to " + host);		
				} catch (IOException e) {
					e.printStackTrace();
					try {
						// �������� ����� ��������� 5���
						Thread.sleep(5000);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
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
				//oos.close();
			} catch (Exception ex) {
				System.out.println("[CLIENT]: Exception during serialization: " + ex);
				System.exit(0);
			}
		}
		
		/**
		 * �������� ������������� GUI (���� ����, ����, ���������������� ���� �����)
		 * @param mapName
		 * @throws InterruptedException
		 * @throws MapAlreadyExistException
		 * @throws MapNotExistException
		 * @throws ObjectAlreadyAddedException
		 */
		public void initInterface(String mapName) throws InterruptedException, MapAlreadyExistException, MapNotExistException, ObjectAlreadyAddedException{
			// ��� ������� ���� ��������� - ��������
			if (!Screen.GRAPHICS_ON) Screen.GRAPHICS_ON = true;
			
			// ������������� � ����������� GUI
			new Screen();
			
			// ������� ������ (��� ��������� ������)
			int width = Screen.instance.getWidth(),
				height = Screen.instance.getHeight();
			
			// �������� ����������� ���������
			while (!Screen.instance.canDraw())
				Thread.sleep(100);
			
			// ����������� ��������� ����� � � ����� ��� �����
			battleMap = BattleMapUtils.registerMap(new BattleMap(mapName));
			//battleMap = BattleMapUtils.selectMap(mapName);
			// ������� ������ ������� �����
			battleMap.setBorder(width, height);
		}
		
		/**
		 * ��������� ��������, ����������� ��� ��������������� ����� �� �������
		 * @throws ObjectAlreadyAddedException 
		 * @throws MapNotExistException 
		 * @throws MapAlreadyExistException 
		 * @throws InterruptedException 
		 */
		private void playBattle() throws InterruptedException, MapAlreadyExistException, MapNotExistException, ObjectAlreadyAddedException {
			initInterface(battleMap.getName());		// �������������� ���������
			
			battle = new Battle();
/*
			BattleMapUtils.removeMap(battleMap);
			BattleMapUtils.registerMap(new BattleMap(message.getMap().getName()));*/
			battle.init(battleMap.getName(), snakes);	// �������������� �����
			
			// ������ �� ���� ���
			for (int i = 0; i < snakes.length; i++)
				battleMap.putSnake(snakes[i]);
			// ������ ��� ��������� � .BattleInit!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			// ���������� � BattleInit ��������
			
			battleMap.drawAll();
			
			int waitTime = 500;
			for (ActionList al : actions) {
				long timeold = System.currentTimeMillis();
				al.action.doAction(al.param);
				battleMap.drawAll();
				long timenow = System.currentTimeMillis() - timeold;
				if (waitTime - timenow > 0)
					Thread.sleep(waitTime - timenow);
			}
		}
		/*--- ��������������� �������, ���������� �� ������ �� ������� ---*/
		
		/**
		 * ��������� �������� � ������ �������
		 */
		public void run() {
			try {				
				// ���������, ���� ����� �� ����� ������
				//while (!sClient.isClosed()) {
					// �������� ���������� ������ �� ������
				mySnake = new Snake();
				oos = new ObjectOutputStream(os);	
				sendObject(oos, mySnake);
					

					is = sClient.getInputStream();							// ������������� ��������� ������
					ois = new ObjectInputStream(is);						// ������������� ��������� ������ �������
					
					// ��������� ��������� �� �������
					message = (Message) receiveObject(ois);
					// ���������� ������ �� ���������
					if (message != null) {
						battleMap = new BattleMap(message.getMap().getName());
						//battleMap = BattleMapUtils.registerMap(new BattleMap(BattleMapUtils.generateName("clientMap-")));
						snakes = message.getSnakes();
						actions = message.getAl();
					}
					
					// �������� ����� �� �������
					playBattle();
				//}
			} catch (IOException | InterruptedException | MapAlreadyExistException | MapNotExistException | ObjectAlreadyAddedException e) {
				e.printStackTrace();
			}  finally {
				// ������� �����
				try {
					sClient.close();
				} catch (IOException e) {
					System.out.println("[CLIENT]: Socket not closed");
				}
			}
		}
	}
	
	// �������� ������� ������ �������
	public static ClientThread thread = null;
	
	/**
	 * ����������� �������
	 */
	public Client2() {
		// ������ ����������� ������
		try {
			thread = new ClientThread();
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
		new Client2();
	}
}