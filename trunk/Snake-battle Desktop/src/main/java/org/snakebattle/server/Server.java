
package org.snakebattle.server;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.snakebattle.gui.Graph;
import org.snakebattle.gui.ObjectAlreadyAddedException;
import org.snakebattle.gui.primitive.snake.Element;
import org.snakebattle.gui.primitive.snake.MindPolyGraph;
import org.snakebattle.gui.primitive.snake.Element.PARTS;
import org.snakebattle.gui.primitive.snake.MindPolyGraph.LOGIC_FLAGS;
import org.snakebattle.gui.primitive.snake.MindPolyGraph.LOGIC_TYPES;
import org.snakebattle.gui.primitive.snake.MindPolyGraph.OWNER_TYPES;
import org.snakebattle.gui.screen.EmptyScreen;
import org.snakebattle.gui.screen.Screen;
import org.snakebattle.gui.screen.SwingScreen;
import org.snakebattle.logic.BattleMap;
import org.snakebattle.logic.Mind;
import org.snakebattle.logic.Mind.MindMap;
import org.snakebattle.logic.Snake;
import org.snakebattle.utils.BattleMapUtils;
import org.snakebattle.utils.BattleMapUtils.ActionList;
import org.snakebattle.utils.BattleMapUtils.MapAlreadyExistException;
import org.snakebattle.utils.BattleMapUtils.MapNotExistException;

public class Server {

	public static ServerSocket sServer; // ��������� �����
	private static int port = 65535; // �������������� ����
	private static Vector<ClientSnake> plsnakes = null;

	private static final int MAX_PLAYERS = 4;

	/**
	 * �������� ������������� GUI (���� ����, ����, ���������������� ���� �����)
	 * 
	 * @param mapName
	 * @throws InterruptedException
	 * @throws MapAlreadyExistException
	 * @throws MapNotExistException
	 * @throws ObjectAlreadyAddedException
	 */
	public static void initInterface(String mapName)
			throws InterruptedException, MapAlreadyExistException,
			MapNotExistException, ObjectAlreadyAddedException {
		// ��� ������� ���� ��������� - ��������
		if (!Screen.GRAPHICS_ON)
			Screen.GRAPHICS_ON = true;

		// ������������� � ����������� GUI
		new Screen();

		// ������� ������ (��� ��������� ������)
		int width = Screen.instance.getWidth(), height = Screen.instance
				.getHeight();

		// �������� ����������� ���������
		while (!Screen.instance.canDraw())
			Thread.sleep(100);

		// ����������� ��������� ����� � � ����� ��� �����
		BattleMap battleMap = BattleMapUtils.registerMap(new BattleMap(mapName));
		// battleMap = BattleMapUtils.selectMap(mapName);
		// ������� ������ ������� �����
		battleMap.setBorder(width, height);
	}

	/**
	 * ��������� ��������, ����������� ��� ��������������� ����� �� �������
	 * 
	 * @throws ObjectAlreadyAddedException
	 * @throws MapNotExistException
	 * @throws MapAlreadyExistException
	 * @throws InterruptedException
	 */
	private static void playBattle(BattleMap battleMap, Snake[] snakes,
			List<ActionList> actions) throws InterruptedException,
			MapAlreadyExistException, MapNotExistException,
			ObjectAlreadyAddedException {

		initInterface(battleMap.getName()); // �������������� ���������

		Battle battle = new Battle();
		/*
		 * BattleMapUtils.removeMap(battleMap); BattleMapUtils.registerMap(new
		 * BattleMap(message.getMap().getName()));
		 */
		battle.init(battleMap.getName(), snakes); // �������������� �����

		// ������ �� ���� ���
		for (int i = 0; i < snakes.length; i++)
			battleMap.putSnake(snakes[i]);
		// ������ ��� ��������� �
		// .BattleInit!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		// ���������� � BattleInit ��������

		battleMap.drawAll();

		int waitTime = 50;
		while (Screen.instance.canDraw())
			for (ActionList al : actions) {
				long timeold = System.currentTimeMillis();
				al.action.doAction(al.param);
				battleMap.drawAll();
				long timenow = System.currentTimeMillis() - timeold;
				if (waitTime - timenow > 0)
					Thread.sleep(waitTime - timenow);
			}
	}

	public static void main(String[] args) throws IOException {
		byte connected = 0; // ���������� ������������ ��������

		sServer = new ServerSocket(port);

		plsnakes = new Vector<ClientSnake>();

		System.out.println("Server Started");
		try {
			serverSocket: while (!sServer.isClosed()) {
				Thread.sleep(10);
				System.out.println("[SERVER]: Waiting for a org.snakebattle.client...");

				Socket sClient = sServer.accept();
				System.out.println("[SERVER]: Client connected: "
						+ sClient.getInetAddress());
				int id = new Random(System.currentTimeMillis())
						.nextInt(Integer.MAX_VALUE);
				ServerOneThread sot = new ServerOneThread(sClient, id);
				Snake sn = (Snake) sot.getSnake();// .clone();

				plsnakes.add(new ClientSnake(id, sn, sot));

				if (plsnakes.size() >= MAX_PLAYERS) {
					int battleId = new Random(System.currentTimeMillis())
							.nextInt(Integer.MAX_VALUE);
					System.out.println("Start battle[" + battleId + "]!");
					// ���������� ����� ��������
					Vector<ClientSnake> players = new Vector<ClientSnake>();
					Snake[] snakes = new Snake[MAX_PLAYERS];
					for (int i = 0; i < MAX_PLAYERS; i++) {
						if (plsnakes.get(0).sot.sClient.isClosed()) {
							for (int j = 0; j < players.size(); j++) {
								plsnakes.add(players.get(j));
							}
							continue serverSocket;
						}
						players.add(plsnakes.get(0));
						snakes[i] = plsnakes.get(0).snake;
						plsnakes.remove(0);
					}

					new Thread() {
						Snake[] snakes = null;
						Vector<ClientSnake> players = null;

						public void start(Snake[] snakes,
								Vector<ClientSnake> players) {
							this.snakes = snakes;
							this.players = players;
							super.start();
						}

						public void run() { // ����� �����
							// �������������
							Battle battle = new Battle();
							// Snake[] snakes = battle.snake_fill();
							try {
								BattleMap battleMap = null;
								// �������� ������, �� ������� ����� �����������
								// ��� �������� �����
								new Screen(new EmptyScreen());
								/** ���� �� �������� ������� */
								try {
									// ������������� �����, �����
									battle.init("serverMap", snakes);
									battleMap = BattleMapUtils.selectMap("serverMap");
									for (int i = 0; i < snakes.length; i++) {
										battleMap.putSnake(snakes[i]);
									}
									battleMap.setBorder(800, 600);
								} catch (MapAlreadyExistException
										| MapNotExistException
										| ObjectAlreadyAddedException e) {
									e.printStackTrace();
								}
								List<ActionList> al = battle.battleCalc(snakes);
								System.out.println("[SERVER]: Battle end");

								
								//==================================================
								//===================�����==========================
								battleMap = new BattleMap("ServerMap");
								HashSet<Snake> hs = new HashSet<>();
								for (int i = 0; i < al.size(); i++) {
									for (int j = 0; j < al.get(i).param.length; j++) {
										hs.add(al.get(i).param[j]);
									}
								}
								Snake[] snakesDraw = new Snake[hs.size()];
								Iterator<Snake> it = hs.iterator();
								int pos = 0;
								while (it.hasNext()) {
									snakesDraw[pos] = it.next();
									pos++;
								}

								for (int i = 0; i < snakes.length; i++) {
									snakesDraw[i].setMind(snakes[i].getMind());
								}
								Screen.instance = null;
								new Screen(new SwingScreen());
								Screen.GRAPHICS_ON = true;
								// initInterface("ServerMap");
								while (!Screen.instance.canDraw())
									Thread.sleep(100);
								// ===��������� �����=== \\
								playBattle(battleMap, snakesDraw, al);								

								// �������� ����� �� ������
								System.out.println("[SERVER]: Sending ActionList");
								for (int i = 0; i < players.size(); i++) {
									/*
									 * players.get(i).sot.sendObject(Commands.actions);
									 * Message mes = new Message(battleMap, snakes, al);
									 * players.get(i).sot.sendObject(mes);
									 */
								}
								System.out.println("[SERVER]: Send successful");
								/*
								 * ois = new ObjectInputStream(is); //
								 * ������������� // ��������� // ������ �������
								 * // oos = new ObjectOutputStream(os); //
								 * ������������� // ���������� // ������ �������
								 * 
								 * // ������������� ������������� ���������
								 * message = new Message(battleMap, snakes, al);
								 * System
								 * .out.println("[SERVER]: Message created"); //
								 * �������� ��������� �� ������
								 * sendObject(sClient, message);
								 * System.out.println
								 * ("[SERVER]: Message sended");
								 * 
								 * sClient.close();
								 * System.out.println("[SERVER]: Sockets closed"
								 * );
								 */
							} catch (Exception e) {
								e.printStackTrace();
							}
						};
					}.start(snakes, players);
				}
			}
		} catch (IOException | InterruptedException e) {
			System.out.println("[SERVER]: Can't accept");
			System.exit(-1);
		} finally {
			sServer.close();
		}
	}
}

class ClientSnake {
	public ClientSnake(int id2, Snake sn, ServerOneThread sot) {
		id = id2;
		snake = sn;
		this.sot = sot;
	}

	public int id = 0;
	public Snake snake = null;
	public ServerOneThread sot = null;

}

class ServerOneThread extends Thread {
	public Socket sClient; // ���������� �����
	public PrintWriter out;
	public BufferedReader in;
	// public ObjectInputStream ois; // ������ ��������� ������ �������
	// public ObjectOutputStream oos; // ������ ���������� ������ �������

	private Snake sn = null;

	private Message message = null; // ���������, ���������� �� �������
	private static Battle battle = null; // ������ ������ Battle ��� �������
											// ���������� �����
	private static Snake[] snakes = null; // ������ ��������, ������������ �
											// �����
	private static BattleMap battleMap = null; // �����, �� ������� ���������� �����

	public ServerOneThread(Socket client, int id) throws IOException {
		sClient = client;
		sn = new Snake();
		start();
	}

	/** ��������� ����� ������� */
	public void run() {
		try {
			System.out.println("[SERVER]: init streams");
			in = new BufferedReader(new InputStreamReader(
					sClient.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					sClient.getOutputStream())), true);
			// oos = new ObjectOutputStream(os);

			// System.out.println("[SERVER]: send command: [" +
			// 					   Commands.getSnake + "]");

			// send(Commands.getSnake);

			// System.out.print("[SERVER]: get snake: ");
			sn = new Snake();

			String command = "";
			// get color, name ...
			// =================
			// ....some code....
			// =================

			// get mind
			// =================
			command = "";
			sleep(100);
			
			System.out.println("[SERVER]: send command [" + Commands.GET_MIND + "]");
			send(Commands.GET_MIND);
			Mind mind = sn.getMind();

			String line = receive(); // line ���� "1.2.3.ENEMY.AND.BODY.RED",
										// ��� 1 - ����� �����,
										// 2 � 3 - ���������� , AND -
										// ���������� �����������,
										// BODY - �������� ��������
			while (!line.equals(Commands.END_SENDING)) {
				System.out.println("[SERVER] receive message: " + line);
				String[] lines = line.split("\\.");
				MindMap mm1 = mind.getMindMap(Integer.valueOf(lines[0]));
				MindPolyGraph mpg = new MindPolyGraph(new Point(),
						Commands.MAX_W, Commands.MAX_W);
				mpg.setOwner(OWNER_TYPES.valueOf(lines[3]));
				mpg.setLogic(LOGIC_TYPES.valueOf(lines[4]));
				Graph gr = null;
				switch (lines[5]) {
				case "HEAD":
					gr = new Element(PARTS.HEAD, new Point(), Commands.MAX_W,
							Commands.MAX_W, null);
					break;
				case "BODY":
					gr = new Element(PARTS.BODY, new Point(), Commands.MAX_W,
							Commands.MAX_W, null);
					break;
				case "TAIL":
					gr = new Element(PARTS.TAIL, new Point(), Commands.MAX_W,
							Commands.MAX_W, null);
					break;

				default:
					break;
				}
				mpg.setValue(gr);
				mpg.setFlags(LOGIC_FLAGS.valueOf(lines[6]));
				mm1.setAt(Integer.valueOf(lines[1]), Integer.valueOf(lines[2]),
						mpg);
				line = receive();
			}
			// =================
			
			// [05.01.2013]
			// ������ ����� � �������� �� ������� � ��������
			/*sleep(100);
			
			System.out.println("[SERVER]: send command [" + Commands.actions + "]");
			send(Commands.actions);
			List<ActionList> al = null;
			try {
				battle.init(battleMap.getName(), snakes);
				al = battle.battleCalc(snakes);
			} catch (MapAlreadyExistException | MapNotExistException
					| ObjectAlreadyAddedException e) {
				e.printStackTrace();
			}
			// ������������ ������������� ���������
			String message = "";
			for(int i = 0; i < al.size(); i++)
			{
				// i - id ��������
				// al.get(i).action - ��� ��������
				// message += "." + al.get(i).param[j] - ����������� ������
				message += i + "." + al.get(i).action;
				// ���� ����������� ����� ������ 1-��
				for(int j = 0; j < al.get(i).param.length; j++)
					message += "." + al.get(i).param[j];
			}
			
			// �������� ���������
			send(message);
			// ���������� ��������
			send(Commands.END_SENDING);*/
			
			//ActionList Action.param
			//					{length}
			//           id.name{.N}.sn1.....snN
			//ACTION:    id.name.snake[.snake2]
			// 			 1.ActionUp.1
			// 			 2.ActionUp.2
			// 			 3.ActionLeft.1
			//						snake=sn[Integer.valueOf(lines[2])];
			//			   a=ActionFactory.valueOf(lines[1]);
			//ActionList al = new ActionList(a,snake);
			//			 alList.add(al);
			// [05.01.2013] \\
			
			//setSnake(sn);
			while (!sClient.isClosed()) {
				sleep(10);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while (!sClient.isClosed()) {

		}
	}

	public synchronized String receive() {
		try {
			return in.readLine();
		} catch (Exception ex) {
			System.out.println("[SERVER]: Exception during send: " + ex);
			System.exit(0);
		}
		return "";
	}

	public synchronized void send(String message) {
		try {
			out.println(message);
		} catch (Exception ex) {
			System.out.println("[SERVER]: Exception during send: " + ex);
			System.exit(0);
		}
	}

	public Snake getSnake() {
		return sn;
	}

	public void setSnake(Snake snake) {
		this.sn = snake;
	}
}