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
 * Описание сервера приложения
 * 
 * @author RED Developer
 */
public class Server {
	static class ServerThread extends Thread{

		public static ServerSocket sServer; 		// Серверный сокет
		public static Socket sClient; 				// Клиентский сокет
		public static InputStream is; 				// Объект входящего потока
		public static OutputStream os; 				// Объект исходящего потока
		public static ObjectInputStream ois; 		// Объект входящего потока объекта
		public static ObjectOutputStream oos; 		// Объект исходящего потока объекта

		// private static List<Socket> clients = null;
		private Message message = null;				// Сообщение, получаемое от сервера
		private static int port = 65535; 			// Прослушиваемый порт
		private static BufferedReader in = null; 	// Поток ввода
		private static PrintWriter out = null;		// Поток вывода

		private static Battle battle = null; 		// Объект класса Battle для обсчёта результата битвы
		private static Snake[] snakes = null;		// Змейки клиентов, учавствующие в битве
		private static Map map = null;				// Карта, на которой проводится битва

		/**
		 * Конструктор потока клиента
		 * @throws IOException
		 */
		public ServerThread() throws IOException {
			sServer = new ServerSocket(port);		// Инициализация серверного сокета на прослушивание порта port
			//is = sClient.getInputStream(); 			// Инициализация входящего потока
			//os = sClient.getOutputStream(); 		// Инициализация исходящего потока
			 //ois = new ObjectInputStream(is); 	// Инициализация входящего потока объекта
			 //oos = new ObjectOutputStream(os); 	// Инициализация исходящего потока объекта

			start(); // Запуск потока
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
				oos.close();
			} catch (Exception ex) {
				System.out.println("[SERVER]: Exception during serialization: " + ex);
				System.exit(0);
			}
		}
		
		/**
		 * Позволяет отправить объект в исходящий поток socket'а, предварительно сериализовав его
		 * @param socket
		 * @param object
		 */
		private void sendObject(Socket socket, Object object){
			// Исходящий поток
			ObjectOutputStream oos = null;
			// Сериализация рассчитанных данных
			try {
				// Получение исходящего потока socket'а
				oos = new ObjectOutputStream(socket.getOutputStream());
				// Запись объекта в исходящий поток
				oos.writeObject(object);
				// ??? ??? ???
				oos.flush();
				// Закрытие исходящего потока
				oos.close();
			} catch (Exception ex) {
				System.out.println("[SERVER]: Exception during serialization: " + ex);
				System.exit(0);
			}
		}

		/**
		 * Описывает поток сервера
		 */
		public void run() {
			while(true){
			// Инициализация
			battle = new Battle();
			snakes = battle.snake_fill();
			
			try {
				// Создание экрана, на котором будут происходит все действия змеек
				new Screen();
				
				test(); } catch (IOException e) {}
			}
		}
		
		private void test() throws IOException{
			try {
				// Инициализация карты, змеек
				battle.init("serverMap", snakes);
				map = Common.selectMap("serverMap");
				//map.setBorder(800, 600);
			} catch (MapAlreadyExistException | MapNotExistException | ObjectAlreadyAddedException e) {
				e.printStackTrace();
			}
			System.out.println("[SERVER]: calc--->");
			List<ActionList> al = battle.battleCalc(snakes);
			System.out.println("[SERVER]: <---calc");
			
			// Запуск на прослушивание порта
			/*try {
				sServer = new ServerSocket(port);
			} catch (IOException ex) {
				ex.printStackTrace();
				System.out.println("[SERVER]: Couldn't listen to port " + port);
				System.exit(-1);
			}*/

			// Ожидание подключения клиента
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
			
			// Передача битвы на клиент
			//sendObject(fromСlient, al);
			/*r35 //broadcast(new ArrayList<Socket>(), al); */ 

			is = sClient.getInputStream(); 			// Инициализация входящего потока
			os = sClient.getOutputStream(); 		// Инициализация исходящего потока
			ois = new ObjectInputStream(is); 	// Инициализация входящего потока объекта
			//oos = new ObjectOutputStream(os); 	// Инициализация исходящего потока объекта

			
			// Инициализация передаваемого сообщения
			message = new Message(map, snakes, al);
			System.out.println("[SERVER]: Message created");
			// Передача сообщения на клиент
			sendObject(sClient, message);
			System.out.println("[SERVER]: Message sended");
			
			sClient.close();
			//sServer.close();
			System.out.println("[SERVER]: Sockets closed");
		}
	}
	
	// Серверный поток
	private ServerThread thread = null;
	
	/**
	 * Конструктор сервера
	 */
	public Server() {
		// Запуск серверного потока
		try {
			thread = new ServerThread();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Основная функция сервера
	 * @param args
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		new Server();
	}
}
