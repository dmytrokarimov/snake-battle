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
// Название карты, на которой будет проводится битва

public class Client {
	// Карта, на которой проводится битва
	private static Map map;
	// Клиентский Socket
	Socket sClient = null;
	// Адрес сервера
	String host = "localhost";
	// Порт сервера
	int port = 65535;

	/**
	 * Осуществляет инициализацию подключения к серверу
	 * @param host - адрес хоста
	 * @param port - порт хоста
	 */
	private void connect(String host, int port){
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
	
	/**
	 * Получает объект из входящего потока указанного сокета 
	 * @param sClient
	 * @return
	 * @throws IOException
	 */
	private Object receiveObject(Socket sClient) throws IOException {
		// Получение входящего потока
		InputStream is = sClient.getInputStream();
		ObjectInputStream ois = new ObjectInputStream(is);
		Object receivedObject = null;
		
		try {
			// Чтение объекта из входящего потока
			receivedObject = ois.readObject();
			System.out.println(receivedObject.toString());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		ois.close();
		return receivedObject;
	}
	
	/**
	 * Конструктор нового клиента
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public Client() throws UnknownHostException, IOException {
		System.out.println("Client side");
		// Подключение к серверу
		connect(host, port);
		// Ход боя (получается с сервера)
		List<ActionList> al = new ArrayList<ActionList>();
		
		// Получение списка действий змеек в битве
		al = (List<ActionList>)receiveObject(sClient);
		// Воспроизвести битву на клиенте
		Play(al);
		
		System.out.println("Actions received");
		
		// Закрытие клиентского сокета
		sClient.close();
	}
	
	/**
	 * Проводит инициализацию GUI (Окно игры, меню, непосредственное поле битвы)
	 * @param mapName
	 * @throws InterruptedException
	 * @throws MapAlreadyExistException
	 * @throws MapNotExistException
	 * @throws ObjectAlreadyAddedException
	 */
	public void initInterface(String mapName) throws InterruptedException, MapAlreadyExistException, MapNotExistException, ObjectAlreadyAddedException{
		// Еси графика была выключена - включить
		if (!Screen.GRAPHICS_ON) Screen.GRAPHICS_ON = true;
		
		// Размеры экрана (для отрисовки границ)
		int width = Screen.instance.getWidth(),
			height = Screen.instance.getHeight();
		// Инициализация и отображение GUI
		new Screen();
		// Ожидание возможности отрисовки
		while (!Screen.instance.canDraw())
			Thread.sleep(100);
		
		// Регистрация указанной карты и её выбор для битвы
		map = Common.registerMap(new Map(mapName));
		// Задание границ игровой карты
		map.setBorder(width, height);
	}
	
	public void Play(final List<ActionList> actions) {
		Thread th = new Thread() {
			public void run() {
				try {
					// Имя карты, на которой будет проводится битва. Генерируется generateMap("battle")
					String mapName = "battleClient";
					// Действующие лица (змейки). В будущем - получаются от сервера
					Snake[] snakes = new Snake[] { new Snake(), new Snake(),
							new Snake(), new Snake() };
					Battle battle = new Battle();
					
					try {
						// Инициализация интерфейса битвы
						initInterface(mapName);
						// Инициализация самой битвы
						battle.init(mapName, snakes);
					} catch (InterruptedException | MapAlreadyExistException
							| MapNotExistException | ObjectAlreadyAddedException e) {
						e.printStackTrace();
					}
					
					// Объект для работы с картой, на которой проводится битва
					Map map = Common.selectMap(mapName);

					
					/* Тестовые наборы змеек */
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
					/*** Тестовые наборы змеек ***/
					
					/* Тестовые наборы мозгов */
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
					/*** Тестовые наборы мозгов ***/
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
	}*/
}