package client;

import gui.Common;
import gui.Common.ActionList;
import gui.Common.MapAlreadyExistException;
import gui.Common.MapNotExistException;
import gui.Element;
import gui.Element.PARTS;
import gui.MindPolyGraph;
import gui.MindPolyGraph.LOGIC_TYPES;
import gui.MindPolyGraph.OWNER_TYPES;
import gui.ObjectAlreadyAddedException;
import gui.Screen;

import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import logic.Map;
import logic.Mind;
import logic.Mind.MindMap;
import logic.Snake;
import logic.SnakeAlreadyInMapException;
import server.Battle;
import server.Server;
// �������� �����, �� ������� ����� ���������� �����

public class Client {
	// �����, �� ������� ���������� �����
	private static Map map;
	// ���������� Socket
	Socket sClient = null;
	// ����� �������
	String host = "localhost";
	// ���� �������
	int port = 65535;

	/**
	 * ������������ ������������� ����������� � �������
	 * @param host - ����� �����
	 * @param port - ���� �����
	 */
	private void connect(String host, int port){
		System.out.println("[Client]Connecting to... " + host);
		// ���� �� ����������� - �������
		while(sClient == null)
			try {
				sClient = new Socket(host, port);
			} catch (IOException e) {
				//e.printStackTrace();
				try {
					// �������� ����� ��������� 5���
					Thread.sleep(5000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
	}
	
	/**
	 * �������� ������ �� ��������� ������ ���������� ������ 
	 * @param sClient
	 * @return
	 * @throws IOException
	 */
	private Object receiveObject(Socket sClient) throws IOException {
		// ��������� ��������� ������
		InputStream is = sClient.getInputStream();
		ObjectInputStream ois = new ObjectInputStream(is);
		Object receivedObject = null;
		
		try {
			// ������ ������� �� ��������� ������
			receivedObject = ois.readObject();
			System.out.println(receivedObject.toString());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		ois.close();
		return receivedObject;
	}
	
	/**
	 * ����������� ������ �������
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public Client() throws UnknownHostException, IOException {
		System.out.println("Client side");
		// ����������� � �������
		connect(host, port);
		// ��� ��� (���������� � �������)
		List<ActionList> al = new ArrayList<ActionList>();
		
		// ��������� ������ �������� ����� � �����
		al = (List<ActionList>)receiveObject(sClient);
		// ������������� ����� �� �������
		Play(al);
		
		System.out.println("Actions received");
		
		// �������� ����������� ������
		sClient.close();
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
		
		// ������� ������ (��� ��������� ������)
		int width = Screen.instance.getWidth(),
			height = Screen.instance.getHeight();
		// ������������� � ����������� GUI
		new Screen();
		// �������� ����������� ���������
		while (!Screen.instance.canDraw())
			Thread.sleep(100);
		
		// ����������� ��������� ����� � � ����� ��� �����
		map = Common.registerMap(new Map(mapName));
		// ������� ������ ������� �����
		map.setBorder(width, height);
	}
	
	public void Play(final List<ActionList> actions) {
		Thread th = new Thread() {
			public void run() {
				try {
					// ��� �����, �� ������� ����� ���������� �����. ������������ generateMap("battle")
					String mapName = "battleClient";
					// ����������� ���� (������). � ������� - ���������� �� �������
					Snake[] snakes = new Snake[] { new Snake(), new Snake(),
							new Snake(), new Snake() };
					Battle battle = new Battle();
					
					try {
						// ������������� ���������� �����
						initInterface(mapName);
						// ������������� ����� �����
						battle.init(mapName, snakes);
					} catch (InterruptedException | MapAlreadyExistException
							| MapNotExistException | ObjectAlreadyAddedException e) {
						e.printStackTrace();
					}
					
					// ������ ��� ������ � ������, �� ������� ���������� �����
					Map map = Common.selectMap(mapName);

					
					/* �������� ������ ����� */
					int i;

					Snake snake = new Snake();
					Element el = new Element(PARTS.HEAD, new Point(10, 0), 10,
							10, snake);
					snake.addElement(el);
					for (i = 2; i < 16; i++) {
						el = new Element(PARTS.BODY, new Point(i * 10, 0), 10,
								10, snake);
						snake.addElement(el);
					}
					el = new Element(PARTS.TAIL, new Point(i * 10, 0), 10, 10,
							snake);
					snake.addElement(el);
					snake.moveTo(350, 310);
					map.putSnake(snake);

					snake = new Snake();
					el = new Element(PARTS.HEAD, new Point(10, 0), 10, 10,
							snake);
					snake.addElement(el);
					for (i = 2; i < 16; i++) {
						el = new Element(PARTS.BODY, new Point(i * 10, 0), 10,
								10, snake);
						snake.addElement(el);
					}
					el = new Element(PARTS.TAIL, new Point(i * 10, 0), 10, 10,
							snake);
					snake.addElement(el);
					snake.moveTo(350, 280);
					map.putSnake(snake);

					snake = new Snake();
					el = new Element(PARTS.HEAD, new Point(0, 0), 10, 10, snake);
					snake.addElement(el);
					el = new Element(PARTS.BODY, new Point(0, 10), 10, 10,
							snake);
					snake.addElement(el);
					el = new Element(PARTS.TAIL, new Point(0, 20), 10, 10,
							snake);
					snake.addElement(el);
					snake.moveTo(350, 290);
					map.putSnake(snake);

					snake = new Snake();
					el = new Element(PARTS.HEAD, new Point(10, 0), 10, 10,
							snake);
					snake.addElement(el);
					for (i = 2; i < 16; i++) {
						el = new Element(PARTS.BODY, new Point(i * 10, 0), 10,
								10, snake);
						snake.addElement(el);
					}
					el = new Element(PARTS.TAIL, new Point(i * 10, 0), 10, 10,
							snake);
					snake.addElement(el);
					snake.moveTo(350, 230);
					map.putSnake(snake);

					snake = new Snake();
					el = new Element(PARTS.HEAD, new Point(10, 0), 10, 10,
							snake);
					snake.addElement(el);
					for (i = 2; i < 16; i++) {
						el = new Element(PARTS.BODY, new Point(i * 10, 0), 10,
								10, snake);
						snake.addElement(el);
					}
					el = new Element(PARTS.TAIL, new Point(i * 10, 0), 10, 10,
							snake);
					snake.addElement(el);
					snake.moveTo(350, 210);
					map.putSnake(snake);

					snake = new Snake();
					el = new Element(PARTS.HEAD, new Point(10, 0), 10, 10,
							snake);
					snake.addElement(el);
					for (i = 2; i < 16; i++) {
						el = new Element(PARTS.BODY, new Point(i * 10, 0), 10,
								10, snake);
						snake.addElement(el);
					}
					el = new Element(PARTS.TAIL, new Point(i * 10, 0), 10, 10,
							snake);
					snake.addElement(el);
					snake.moveTo(280, 220);
					map.putSnake(snake);

					snake = new Snake();
					el = new Element(PARTS.HEAD, new Point(10, 0), 10, 10,
							snake);
					snake.addElement(el);
					for (i = 2; i < 16; i++) {
						el = new Element(PARTS.BODY, new Point(i * 10, 0), 10,
								10, snake);
						snake.addElement(el);
					}
					el = new Element(PARTS.TAIL, new Point(i * 10, 0), 10, 10,
							snake);
					snake.addElement(el);
					snake.moveTo(350 + 16 * 10, 210);
					map.putSnake(snake);

					map.drawAll();
					int waitTime = 50;

					// -------exemple: add element to mind
					snake = new Snake();

					el = new Element(PARTS.HEAD, new Point(310, 270), 10, 10,
							snake);
					snake.addElement(el);

					el = new Element(PARTS.BODY, new Point(310, 280), 10, 10,
							snake);
					snake.addElement(el);

					el = new Element(PARTS.BODY, new Point(310, 290), 10, 10,
							snake);
					snake.addElement(el);

					el = new Element(PARTS.TAIL, new Point(310, 300), 10, 10,
							snake);
					snake.addElement(el);

					map.putSnake(snake);
					/*** �������� ������ ����� ***/
					
					/* �������� ������ ������ */
					Mind mind = snakes[0].getMind();
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

					mind = snakes[0].getMind();
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
					/*** �������� ������ ������ ***/
					//snakes = null;
					i = 0;
					for (ActionList al : actions) {
						long timeold = System.currentTimeMillis();
						al.action.doAction(al.param);
						System.out.println(i++ + "<--");
						map.drawAll();
						long timenow = System.currentTimeMillis() - timeold;
						if (waitTime - timenow > 0)
							Thread.sleep(waitTime - timenow);
					}
				} catch (InterruptedException | ObjectAlreadyAddedException
						| MapNotExistException | SnakeAlreadyInMapException e) {
					e.printStackTrace();
				}
			}
		};
		th.setDaemon(true);
		th.start();
	}
	
	/*public static void main(String[] args) throws UnknownHostException,
			IOException, InterruptedException {
		new Thread() {
			public void run() {
				try {
					try {
						// ��������, ���� ������ ����������
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
	}*/
}