package org.snakebattle.server;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import org.snakebattle.gui.ObjectAlreadyAddedException;
import org.snakebattle.gui.screen.Screen;
import org.snakebattle.logic.BattleMap;
import org.snakebattle.logic.Snake;
import org.snakebattle.utils.BattleMapUtils;
import org.snakebattle.utils.BattleMapUtils.ActionList;
import org.snakebattle.utils.BattleMapUtils.MapAlreadyExistException;
import org.snakebattle.utils.BattleMapUtils.MapNotExistException;

public class Server2 {
  public static ServerSocket sServer; 		// Серверный сокет
  private static int port = 65535; 			// Прослушиваемый порт
  
  public static void main(String[] args) throws IOException {
    byte connected = 0; 					// Количество подключённых клиентов
    
    sServer = new ServerSocket(port);
    System.out.println("Server Started");
    try {
      System.out.println("[SERVER]: Waiting for a org.snakebattle.client...");
      //if (connected < 4) {
        Socket sClient = sServer.accept();
        System.out.println("[SERVER]: Client connected");
        try {
          System.out.println(connected++);
          new ServerOneThread2(sClient);
        } catch (IOException e) {
          sClient.close();
        }
      //}
    } catch (IOException e) {
      System.out.println("[SERVER]: Can't accept");
      System.exit(-1);
    } finally {
      sServer.close();
    }
  }
}

class ServerOneThread2 extends Thread{
  public static Socket sClient; 				// Клиентский сокет
  public static InputStream is; 				// Объект входящего потока
  public static OutputStream os; 				// Объект исходящего потока
  public static ObjectInputStream ois; 		// Объект входящего потока объекта
  public static ObjectOutputStream oos; 		// Объект исходящего потока объекта
  
  private Message message = null;				// Сообщение, получаемое от сервера
  private static Battle battle = null; 		// Объект класса Battle для обсчёта результата битвы
  private static Snake[] snakes = null;		// Змейки клиентов, учавствующие в битве
  private static BattleMap battleMap = null;				// Карта, на которой проводится битва
  
  public ServerOneThread2(Socket client) throws IOException
  {
    sClient = client;
    is = sClient.getInputStream(); 			// Инициализация входящего потока
    os = sClient.getOutputStream(); 		// Инициализация исходящего потока
    start();
  }
  
  /** Описывает поток сервера */
  public void run(){
    // Инициализация входящего потока объекта
    while(!sClient.isClosed()){
      try {
        if (ois == null) ois = new ObjectInputStream(is);
        Snake clientSnake = (Snake)receiveObject(ois);
        snakes = new Snake[]{clientSnake, new Snake(), new Snake(), new Snake()};
        
        // Инициализация
        battle = new Battle();
        //snakes = battle.snake_fill();
        
        // Создание экрана, на котором будут происходит все действия змеек
        new Screen();
        
        /** Блок из тестовой функции */
        try {
          // Инициализация карты, змеек
          battle.init("serverMap", snakes);
          battleMap = BattleMapUtils.selectMap("serverMap");
          //battleMap.setBorder(800, 600);
        } catch (MapAlreadyExistException | MapNotExistException | ObjectAlreadyAddedException e) {
          e.printStackTrace();
        }
        List<ActionList> al = battle.battleCalc(snakes);
        System.out.println("[SERVER]: Battle ended");
        
        // Передача битвы на клиент
        /*ois = new ObjectInputStream(is); 	// Инициализация входящего потока объекта
        Snake clientSnake = (Snake)receiveObject(ois);*/
        //oos = new ObjectOutputStream(os); 	// Инициализация исходящего потока объекта
        
        // Инициализация передаваемого сообщения
        message = new Message(battleMap, snakes, al);
        System.out.println("[SERVER]: Message created");
        // Передача сообщения на клиент
        sendObject(sClient, message);
        System.out.println("[SERVER]: Message sended");
        
        sClient.close();
        System.out.println("[SERVER]: Sockets closed");
      } catch (IOException e) {}
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
}