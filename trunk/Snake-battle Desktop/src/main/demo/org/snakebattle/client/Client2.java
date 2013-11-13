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
 * Описывает клиента приложения
 * @author RED Developer
 */
public class Client2 {
	static class ClientThread extends Thread {
		public static Socket sClient;								// Клиентский сокет
		public static InputStream is;								// Объект входящего потока
		public static OutputStream os;								// Объект исходящего потока
		public static ObjectInputStream ois;						// Объект входящего потока объекта
		public static ObjectOutputStream oos;						// Объект исходящего потока объекта
		
		// Клиентские специфические переменные
		private Message message = null;								// Сообщение, получаемое от сервера
		private	List<ActionList> actions = null;					// Запись ходов боя (получается с сервера)
		private Snake[] snakes = null;								// Змейки, учавствующие в бою (получаются с сервера)
		private Snake mySnake = null;								// Змейка клиента (передаётся на сервер)
		private BattleMap battleMap = null;										// Карта, на которой проводится битва
		private Battle battle = null;								// Объект класса Battle, для воспроизведения битвы
		
		static String host = "localhost";							// Адрес сервера
		static int port = 65535;									// Порт сервера
		/**
		 * Конструктор потока клиента
		 * @throws IOException
		 */
		public ClientThread() throws IOException {
			this(InetAddress.getByName(host), port);			
		}
		
		/**
		 * Конструктор потока клиента
		 * @param host
		 * @param port
		 * @throws IOException
		 */
		public ClientThread(InetAddress host, int port) throws IOException {
			connect(host, port);									// Инициализация сокета клиента
			
			os = sClient.getOutputStream();							// Инициализация исходящего потока
			//oos = new ObjectOutputStream(os);						// Инициализация исходящего потока объекта
			
			start();												// Запуск потока
		}
		
		/*+++ Вспомогательные функции, отвечающие за логику на клиенте +++*/
		/**
		 * Осуществляет инициализацию подключения к серверу
		 * @param host - адрес хоста
		 * @param port - порт хоста
		 */
		private void connect(InetAddress host, int port){
			System.out.println("[CLIENT]: Connecting to... " + host);
			// Пока не подключился - пытайся
			while(sClient == null)
				try {
					sClient = new Socket(host, port);
					System.out.println("[CLIENT]: Connected to " + host);		
				} catch (IOException e) {
					e.printStackTrace();
					try {
						// Интервал между попытками 5сек
						Thread.sleep(5000);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
		}
		
		/**
		 * Получает объект из указанного входящего потока
		 * @param ois - входящий поток
		 * @return
		 * @throws IOException
		 */
		private synchronized Object receiveObject(ObjectInputStream ois) throws IOException {
			Object receivedObject = null;							// Обект "полученный объект" 
			try {
				// Чтение объекта из входящего потока
				receivedObject = ois.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			//ois.close();
			return receivedObject;
		}
		
		/**
		 * Передаёт указанный объект в указанный исходящй поток 
		 * @param oos - исходящий поток
		 * @param sendingObject - пересылаемый объект
		 */
		private synchronized void sendObject(ObjectOutputStream oos, Object sendingObject) {
			// Сериализация рассчитанных данных
			try {
				// Получение исходящего потока socket'а
				oos = new ObjectOutputStream(sClient.getOutputStream());
				// Запись объекта в исходящий поток
				oos.writeObject(sendingObject);
				// ??? ??? ???
				oos.flush();
				// Закрытие исходящего потока
				//oos.close();
			} catch (Exception ex) {
				System.out.println("[CLIENT]: Exception during serialization: " + ex);
				System.exit(0);
			}
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
			
			// Инициализация и отображение GUI
			new Screen();
			
			// Размеры экрана (для отрисовки границ)
			int width = Screen.instance.getWidth(),
				height = Screen.instance.getHeight();
			
			// Ожидание возможности отрисовки
			while (!Screen.instance.canDraw())
				Thread.sleep(100);
			
			// Регистрация указанной карты и её выбор для битвы
			battleMap = BattleMapUtils.registerMap(new BattleMap(mapName));
			//battleMap = BattleMapUtils.selectMap(mapName);
			// Задание границ игровой карты
			battleMap.setBorder(width, height);
		}
		
		/**
		 * Описывает действия, необходимые для воспроизведения битвы на клиенте
		 * @throws ObjectAlreadyAddedException 
		 * @throws MapNotExistException 
		 * @throws MapAlreadyExistException 
		 * @throws InterruptedException 
		 */
		private void playBattle() throws InterruptedException, MapAlreadyExistException, MapNotExistException, ObjectAlreadyAddedException {
			initInterface(battleMap.getName());		// Инициализирует интерфейс
			
			battle = new Battle();
/*
			BattleMapUtils.removeMap(battleMap);
			BattleMapUtils.registerMap(new BattleMap(message.getMap().getName()));*/
			battle.init(battleMap.getName(), snakes);	// Инициализирует битву
			
			// Змейки на поле боя
			for (int i = 0; i < snakes.length; i++)
				battleMap.putSnake(snakes[i]);
			// Змейки уже добавлены в .BattleInit!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			// Добавление в BattleInit отменено
			
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
		/*--- Вспомогательные функции, отвечающие за логику на клиенте ---*/
		
		/**
		 * Описывает действия в потоке клиента
		 */
		public void run() {
			try {				
				// Выполнять, пока сокет не будет закрыт
				//while (!sClient.isClosed()) {
					// Отправка клиентской змейки на сервер
				mySnake = new Snake();
				oos = new ObjectOutputStream(os);	
				sendObject(oos, mySnake);
					

					is = sClient.getInputStream();							// Инициализация входящего потока
					ois = new ObjectInputStream(is);						// Инициализация входящего потока объекта
					
					// Получение сообщения от сервера
					message = (Message) receiveObject(ois);
					// Считывание данных из сообщения
					if (message != null) {
						battleMap = new BattleMap(message.getMap().getName());
						//battleMap = BattleMapUtils.registerMap(new BattleMap(BattleMapUtils.generateName("clientMap-")));
						snakes = message.getSnakes();
						actions = message.getAl();
					}
					
					// Показать битву на клиенте
					playBattle();
				//}
			} catch (IOException | InterruptedException | MapAlreadyExistException | MapNotExistException | ObjectAlreadyAddedException e) {
				e.printStackTrace();
			}  finally {
				// Закрыть сокет
				try {
					sClient.close();
				} catch (IOException e) {
					System.out.println("[CLIENT]: Socket not closed");
				}
			}
		}
	}
	
	// Создание объекта потока клиента
	public static ClientThread thread = null;
	
	/**
	 * Конструктор клиента
	 */
	public Client2() {
		// Запуск клиентского потока
		try {
			thread = new ClientThread();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Основная функция клиента
	 * @param args
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		new Client2();
	}
}