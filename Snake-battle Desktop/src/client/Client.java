package client;

import gui.Common;
import gui.Common.ActionList;
import gui.Common.MapAlreadyExistException;
import gui.Common.MapNotExistException;
import gui.Element.PARTS;
import gui.MindPolyGraph.LOGIC_TYPES;
import gui.MindPolyGraph.OWNER_TYPES;
import gui.Dummy;
import gui.Element;
import gui.MindPolyGraph;
import gui.ObjectAlreadyAddedException;
import gui.Point;
import gui.Screen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import logic.ActionFactory;
import logic.Map;
import logic.Mind;
import logic.Mind.MindMap;
import logic.Snake;
import server.Battle;
import server.Commands;
import server.Message;

/**
 * ��������� ������� ����������
 * 
 * @author RED Developer
 */
public class Client {
	static class ClientThread extends Thread {
		public static Socket sClient; // ���������� �����
		public PrintWriter out;
		public BufferedReader in;
		// public static ObjectInputStream ois; // ������ ��������� ������
		// �������
		// public static ObjectOutputStream oos; // ������ ���������� ������
		// �������

		// ���������� ������������� ����������
		private Message message = null; // ���������, ���������� �� �������
		private List<ActionList> actions = null; // ������ ����� ��� (����������
													// � �������)
		private Snake[] snakes = null; // ������, ������������ � ��� (����������
										// � �������)
		private Snake mySnake = null; // ������ ������� (��������� �� ������)
		private Map map = null; // �����, �� ������� ���������� �����
		private Battle battle = null; // ������ ������ Battle, ���
										// ��������������� �����

		static String host = "localhost"; // ����� �������
		static int port = 65535; // ���� �������

		/**
		 * ����������� ������ �������
		 * 
		 * @throws IOException
		 */
		public ClientThread(Snake mySnake) throws IOException {
			this(mySnake, InetAddress.getByName(host), port);
		}

		/**
		 * ����������� ������ �������
		 * 
		 * @param host
		 * @param port
		 * @throws IOException
		 */
		public ClientThread(Snake mySnake, InetAddress host, int port) throws IOException {
			this.mySnake = mySnake; 
			connect(host, port); // ������������� ������ �������

			start(); // ������ ������
		}

		/* +++ ��������������� �������, ���������� �� ������ �� ������� +++ */
		/**
		 * ������������ ������������� ����������� � �������
		 * 
		 * @param host - ����� �����
		 * @param port - ���� �����
		 */
		private void connect(InetAddress host, int port) {
			System.out.println("[CLIENT]: Connecting to... " + host);
			// ���� �� ����������� - �������
			while (sClient == null)
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

		public synchronized String receive() {
			try {
				return in.readLine();
			} catch (Exception ex) {
				System.out.println("[CLIENT]: Exception during send: " + ex);
				System.exit(0);
			}
			return "";
		}

		public synchronized void send(String message) {
			try {
				out.println(message);
			} catch (Exception ex) {
				System.out.println("[CLIENT]: Exception during send: " + ex);
				System.exit(0);
			}
		}

		/**
		 * �������� ������������� GUI (���� ����, ����, ���������������� ����
		 * �����)
		 * 
		 * @param mapName
		 * @throws InterruptedException
		 * @throws MapAlreadyExistException
		 * @throws MapNotExistException
		 * @throws ObjectAlreadyAddedException
		 */
		public void initInterface(String mapName) throws InterruptedException,
				MapAlreadyExistException, MapNotExistException,
				ObjectAlreadyAddedException {
			// ��� ������� ���� ��������� - ��������
			if (!Screen.GRAPHICS_ON)
				Screen.GRAPHICS_ON = true;

			// ������������� � ����������� GUI
			new Screen();

			// ������� ������ (��� ��������� ������)
			int width = Screen.instance.getWidth(), height = Screen.instance.getHeight();

			// �������� ����������� ���������
			while (!Screen.instance.canDraw())
				Thread.sleep(100);

			// ����������� ��������� ����� � � ����� ��� �����
			map = Common.registerMap(new Map(mapName));
			// map = Common.selectMap(mapName);
			// ������� ������ ������� �����
			map.setBorder(width, height);
		}

		/**
		 * ��������� ��������, ����������� ��� ��������������� ����� �� �������
		 * 
		 * @throws ObjectAlreadyAddedException
		 * @throws MapNotExistException
		 * @throws MapAlreadyExistException
		 * @throws InterruptedException
		 */
		private void playBattle() throws InterruptedException,
				MapAlreadyExistException, MapNotExistException,
				ObjectAlreadyAddedException {
			initInterface(map.getName()); // �������������� ���������

			battle = new Battle();
			/*
			 * Common.removeMap(map); Common.registerMap(new
			 * Map(message.getMap().getName()));
			 */
			battle.init(map.getName(), snakes); // �������������� �����

			// ������ �� ���� ���
			for (int i = 0; i < snakes.length; i++)
				map.putSnake(snakes[i]);
			// ������ ��� ��������� �
			// .BattleInit!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			// ���������� � BattleInit ��������

			map.drawAll();

			int waitTime = 500;
			for (ActionList al : actions) {
				long timeold = System.currentTimeMillis();
				al.action.doAction(al.param);
				map.drawAll();
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
				// while (!sClient.isClosed()) {
				// �������� ���������� ������ �� ������
				// sendObject(oos, mySnake);
				System.out.println("[CLIENT]: init streams");
				in = new BufferedReader(new InputStreamReader(
						sClient.getInputStream()));
				out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
						sClient.getOutputStream())), true);
				//������������� ������ ����������
				new Thread(){
					public void run() {
						
					};
				};
				// oos = new ObjectOutputStream(os); // ������������� ����������
				// ������ �������

				while (!sClient.isClosed()) {
					System.out.println("[CLIENT]: receive message");
					String command = receive();

					System.out.println("[CLIENT]: message: [" + command + "]");

					// ===�������� ���������� ������ �� ������=== \\
					if (command.equals(Commands.getSnake)) {
						send(Commands.COMMAND_NOT_SUPPORTED);
					}
					// ===�������� ������ ������ �� ������=== \\
					if (command.equals(Commands.GET_MIND)) {
						Mind mind = mySnake.getMind();
						for (int i = 0; i < mind.getMindMap().length; i++) {
							MindMap mm = mind.getMindMap(i);

							MindPolyGraph[][] mpg = mm.get();
							for (int j = 0; j < mpg.length; j++) {
								for (int k = 0; k < mpg[j].length; k++) {
									if (mpg[j][k] == null)
										continue;
									
									// 1.2.3.ENEMY.AND.BODY.RED
									String line = "";

									line += i + ".";
									line += j + "." + k + ".";
									line += mpg[j][k].getOwner()
											+ ".";
									line += mpg[j][k].getLogic()
											+ ".";
									//=========value=========
									if (mpg[j][k].getValue() instanceof Element)
										line += ((Element) mpg[j][k].getValue()).getPart();
									else
									if ((mpg[j][k].getValue() instanceof Dummy))//||(mpg[j][k].getValue() instanceof Wall)
										line += "Dummy";
									else
										line += mpg[j][k].getValue();

									line += ".";
									//=======================
									line += mpg[j][k].getFlags();
									System.out.println("[CLIENT]: send: " + line);
									send(line);
								}
							}
						}
						send(Commands.END_SENDING);
					}
					// [05.01.2013]
					// ===��������� ���� ����� �� �������=== \\
					// id_action.action_type.id_snake[.id_snake2]
					if (command.equals(Commands.actions)) {
						// ������ �������� � �����
						ArrayList<ActionList> al = new ArrayList<ActionList>();
						// ������ �������� ������� ����� �����
						ArrayList<Integer> order = new ArrayList<Integer>();
						String line = "";	// ���������� ������ �� ������� (���������)
						line = receive();	// ��������� ������ �� ������� (1-� ������)
						
						// ���� �� �������� ������� �� ���������� ��������� ���������
						while (!line.equals(Commands.END_SENDING)) {
							System.out.println("[CLIENT]: receive message: " + line);
							String[] lines = line.split("\\.");	// ��������� ������ �� ������ �� "."
							
							// ���������� ���������� ������
							order.add(Integer.valueOf(lines[0]));	// ���������� ����� ����
							// ���� � �������� ���������� ������ 1 ������
							if (lines.length == 3)
								al.add(/*order.get(order.size() - 1),*/ 						// �������� �������� � ������ �������
									   new ActionList(ActionFactory.getValue(lines[1]), 	// ��� ��������
											   		  snakes[Integer.valueOf(lines[2])]));	// ������, � ������� ��������� ��������
							// ���� � �������� ���������� ����� 1 ������
							else al.add(/*order.get(order.size() - 1),*/ 
										new ActionList(ActionFactory.getValue(lines[1]), 
												   	   snakes[Integer.valueOf(lines[2])],
												   	   snakes[Integer.valueOf(lines[3])]));
							
							line = receive();	// ��������� ������ �� �������
						}
						
						// ������������ ����� � ���������� �������
						for (Integer i : order) {
							// ��������� �������� ��� ������� i
							actions.add(al.get(i));
						}
						
						// ����� ����������, ���� �������������
						playBattle();
						/*
						 * message = (Message) receiveObject(ois); map = new
						 * Map(message.getMap().getName()); 
						 * snakes = message.getSnakes(); 
						 * actions = message.getAl();
						 * playBattle();
						 */
					}
					// [05.01.2013] \\
					// �������� 10��
					sleep(10);
				}
				/*
				 * // ��������� ��������� �� ������� message = (Message)
				 * receiveObject(ois); // ���������� ������ �� ��������� if
				 * (message != null) { map = new
				 * Map(message.getMap().getName()); // map =
				 * Common.registerMap(new //
				 * Map(Common.generateName("clientMap-"))); snakes =
				 * message.getSnakes(); actions = message.getAl(); }
				 */
				/*
				 * for (int i = 0; i < snakes.length; i++) {
				 * snakes[i].setSnakeInMap(false, map.getName()); }
				 */

				// �������� ����� �� �������
				// playBattle();
				// }
			} catch (IOException | InterruptedException | MapAlreadyExistException | MapNotExistException | ObjectAlreadyAddedException e) {
				e.printStackTrace();
			} finally {
				// ������� �����
				try {
					sClient.close();
				} catch (IOException e) {
					System.out.println("[CLIENT]: Socket not closed");
				}
			}
		}
	}

	private Snake mySnake;
	// �������� ������� ������ �������
	public static ClientThread thread = null;

	/**
	 * ����������� �������
	 */
	public Client(Snake mySnake) {
		this.mySnake = mySnake;
		// ������ ����������� ������
		try {
			thread = new ClientThread(mySnake);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �������� ������� �������
	 * 
	 * @param args
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static void main(String[] args) throws UnknownHostException,
			IOException {
		Snake sn = new Snake();
		Mind mind = sn.getMind();
		MindMap[] mm = mind.getMindMap();
		MindPolyGraph mpg = new MindPolyGraph(new Point(), 10, 10);
		mpg.setOwner(OWNER_TYPES.SNAKE);
		mpg.setValue(new Element(PARTS.HEAD, new Point(), 10, 10,
				null));
		mm[0].setAt(2, 2, mpg);

		mpg = new MindPolyGraph(new Point(), 10, 10);
		mpg.setOwner(OWNER_TYPES.ENEMY);
		mpg.setLogic(LOGIC_TYPES.OR);
		mpg.setValue(new Element(PARTS.TAIL, new Point(), 10, 10,
				null));
		mm[0].setAt(2, 1, mpg);

		mpg = new MindPolyGraph(new Point(), 10, 10);
		mpg.setOwner(OWNER_TYPES.ENEMY);
		mpg.setLogic(LOGIC_TYPES.OR);
		mpg.setValue(new Element(PARTS.TAIL, new Point(), 10, 10,
				null));
		mm[0].setAt(2, 0, mpg);

		/*
		 * mpg = new MindPolyGraph(new Point(), 10, 10);
		 * mpg.setOwner(OWNER_TYPES.ENEMY); mpg.setValue(new
		 * Element(PARTS.TAIL, new Point(), 10,10, null));
		 * mm[0].setAt(2, 0, mpg);
		 */

		mind = sn.getMind();
		MindMap mm1 = mind.getMindMap(1);
		mpg = new MindPolyGraph(new Point(), 10, 10);
		mpg.setOwner(OWNER_TYPES.SNAKE);
		mpg.setValue(new Element(PARTS.HEAD, new Point(), 10, 10,
				null));
		mm1.setAt(3, 3, mpg);

		mpg = new MindPolyGraph(new Point(), 10, 10);
		mpg.setOwner(OWNER_TYPES.NEUTRAL);
		mpg.setValue(null);
		mm1.setAt(3, 2, mpg);

		mpg = new MindPolyGraph(new Point(), 10, 10);
		mpg.setOwner(OWNER_TYPES.NEUTRAL);
		mpg.setValue(null);
		mm1.setAt(3, 1, mpg);

		mpg = new MindPolyGraph(new Point(), 10, 10);
		mpg.setOwner(OWNER_TYPES.NEUTRAL);
		mpg.setValue(null);
		mm1.setAt(3, 0, mpg);
		
		new Client(sn);
	}
}
