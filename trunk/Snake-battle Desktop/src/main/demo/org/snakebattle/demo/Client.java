package org.snakebattle.demo;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.snakebattle.gui.ObjectAlreadyAddedException;
import org.snakebattle.gui.primitive.Dummy;
import org.snakebattle.gui.primitive.snake.Element;
import org.snakebattle.gui.primitive.snake.Element.PARTS;
import org.snakebattle.gui.primitive.snake.MindPolyGraph;
import org.snakebattle.gui.primitive.snake.MindPolyGraph.LOGIC_TYPES;
import org.snakebattle.gui.primitive.snake.MindPolyGraph.OWNER_TYPES;
import org.snakebattle.gui.screen.Screen;
import org.snakebattle.logic.ActionFactory;
import org.snakebattle.logic.BattleMap;
import org.snakebattle.logic.Snake;
import org.snakebattle.logic.SnakeMind;
import org.snakebattle.logic.SnakeMind.MindMap;
import org.snakebattle.server.Battle;
import org.snakebattle.server.Commands;
import org.snakebattle.server.Message;
import org.snakebattle.utils.BattleMapUtils;
import org.snakebattle.utils.BattleMapUtils.ActionList;
import org.snakebattle.utils.BattleMapUtils.MapAlreadyExistException;
import org.snakebattle.utils.BattleMapUtils.MapNotExistException;

/**
 * РћРїРёСЃС‹РІР°РµС‚ РєР»РёРµРЅС‚Р° РїСЂРёР»РѕР¶РµРЅРёСЏ
 * 
 * @author RED Developer
 */
public class Client {
  static class ClientThread extends Thread {
    public static Socket sClient; // РљР»РёРµРЅС‚СЃРєРёР№ СЃРѕРєРµС‚
    public PrintWriter out;
    public BufferedReader in;
    // public static ObjectInputStream ois; // РћР±СЉРµРєС‚ РІС…РѕРґСЏС‰РµРіРѕ РїРѕС‚РѕРєР°
    // РѕР±СЉРµРєС‚Р°
    // public static ObjectOutputStream oos; // РћР±СЉРµРєС‚ РёСЃС…РѕРґСЏС‰РµРіРѕ РїРѕС‚РѕРєР°
    // РѕР±СЉРµРєС‚Р°

    // РљР»РёРµРЅС‚СЃРєРёРµ СЃРїРµС†РёС„РёС‡РµСЃРєРёРµ РїРµСЂРµРјРµРЅРЅС‹Рµ
    private Message message = null; // РЎРѕРѕР±С‰РµРЅРёРµ, РїРѕР»СѓС‡Р°РµРјРѕРµ РѕС‚ СЃРµСЂРІРµСЂР°
    private List<ActionList> actions = null; // Р—Р°РїРёСЃСЊ С…РѕРґРѕРІ Р±РѕСЏ (РїРѕР»СѓС‡Р°РµС‚СЃСЏ
                          // СЃ СЃРµСЂРІРµСЂР°)
    private Snake[] snakes = null; // Р—РјРµР№РєРё, СѓС‡Р°РІСЃС‚РІСѓСЋС‰РёРµ РІ Р±РѕСЋ (РїРѕР»СѓС‡Р°СЋС‚СЃСЏ
                    // СЃ СЃРµСЂРІРµСЂР°)
    private Snake mySnake = null; // Р—РјРµР№РєР° РєР»РёРµРЅС‚Р° (РїРµСЂРµРґР°С‘С‚СЃСЏ РЅР° СЃРµСЂРІРµСЂ)
    private BattleMap battleMap = null; // РљР°СЂС‚Р°, РЅР° РєРѕС‚РѕСЂРѕР№ РїСЂРѕРІРѕРґРёС‚СЃСЏ Р±РёС‚РІР°
    private Battle battle = null; // РћР±СЉРµРєС‚ РєР»Р°СЃСЃР° Battle, РґР»СЏ
                    // РІРѕСЃРїСЂРѕРёР·РІРµРґРµРЅРёСЏ Р±РёС‚РІС‹

    static String host = "localhost"; // РђРґСЂРµСЃ СЃРµСЂРІРµСЂР°
    static int port = 65535; // РџРѕСЂС‚ СЃРµСЂРІРµСЂР°

    /**
     * РљРѕРЅСЃС‚СЂСѓРєС‚РѕСЂ РїРѕС‚РѕРєР° РєР»РёРµРЅС‚Р°
     * 
     * @throws IOException
     */
    public ClientThread(Snake mySnake) throws IOException {
      this(mySnake, InetAddress.getByName(host), port);
    }

    /**
     * РљРѕРЅСЃС‚СЂСѓРєС‚РѕСЂ РїРѕС‚РѕРєР° РєР»РёРµРЅС‚Р°
     * 
     * @param host
     * @param port
     * @throws IOException
     */
    public ClientThread(Snake mySnake, InetAddress host, int port) throws IOException {
      this.mySnake = mySnake; 
      connect(host, port); // Р�РЅРёС†РёР°Р»РёР·Р°С†РёСЏ СЃРѕРєРµС‚Р° РєР»РёРµРЅС‚Р°

      start(); // Р—Р°РїСѓСЃРє РїРѕС‚РѕРєР°
    }

    /* +++ Р’СЃРїРѕРјРѕРіР°С‚РµР»СЊРЅС‹Рµ С„СѓРЅРєС†РёРё, РѕС‚РІРµС‡Р°СЋС‰РёРµ Р·Р° Р»РѕРіРёРєСѓ РЅР° РєР»РёРµРЅС‚Рµ +++ */
    /**
     * РћСЃСѓС‰РµСЃС‚РІР»СЏРµС‚ РёРЅРёС†РёР°Р»РёР·Р°С†РёСЋ РїРѕРґРєР»СЋС‡РµРЅРёСЏ Рє СЃРµСЂРІРµСЂСѓ
     * 
     * @param host - Р°РґСЂРµСЃ С…РѕСЃС‚Р°
     * @param port - РїРѕСЂС‚ С…РѕСЃС‚Р°
     */
    private void connect(InetAddress host, int port) {
      System.out.println("[CLIENT]: Connecting to... " + host);
      // РџРѕРєР° РЅРµ РїРѕРґРєР»СЋС‡РёР»СЃСЏ - РїС‹С‚Р°Р№СЃСЏ
      while (sClient == null)
        try {
          sClient = new Socket(host, port);
          System.out.println("[CLIENT]: Connected to " + host);
        } catch (IOException e) {
          e.printStackTrace();
          try {
            // Р�РЅС‚РµСЂРІР°Р» РјРµР¶РґСѓ РїРѕРїС‹С‚РєР°РјРё 5СЃРµРє
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
     * РџСЂРѕРІРѕРґРёС‚ РёРЅРёС†РёР°Р»РёР·Р°С†РёСЋ GUI (РћРєРЅРѕ РёРіСЂС‹, РјРµРЅСЋ, РЅРµРїРѕСЃСЂРµРґСЃС‚РІРµРЅРЅРѕРµ РїРѕР»Рµ
     * Р±РёС‚РІС‹)
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
      // Р•СЃРё РіСЂР°С„РёРєР° Р±С‹Р»Р° РІС‹РєР»СЋС‡РµРЅР° - РІРєР»СЋС‡РёС‚СЊ
      if (!Screen.GRAPHICS_ON)
        Screen.GRAPHICS_ON = true;

      // Р�РЅРёС†РёР°Р»РёР·Р°С†РёСЏ Рё РѕС‚РѕР±СЂР°Р¶РµРЅРёРµ GUI
      new Screen();

      // Р Р°Р·РјРµСЂС‹ СЌРєСЂР°РЅР° (РґР»СЏ РѕС‚СЂРёСЃРѕРІРєРё РіСЂР°РЅРёС†)
      int width = Screen.instance.getWidth(), height = Screen.instance.getHeight();

      // РћР¶РёРґР°РЅРёРµ РІРѕР·РјРѕР¶РЅРѕСЃС‚Рё РѕС‚СЂРёСЃРѕРІРєРё
      while (!Screen.instance.canDraw())
        Thread.sleep(100);

      // Р РµРіРёСЃС‚СЂР°С†РёСЏ СѓРєР°Р·Р°РЅРЅРѕР№ РєР°СЂС‚С‹ Рё РµС‘ РІС‹Р±РѕСЂ РґР»СЏ Р±РёС‚РІС‹
      battleMap = BattleMapUtils.registerMap(new BattleMap(mapName));
      // battleMap = BattleMapUtils.selectMap(mapName);
      // Р—Р°РґР°РЅРёРµ РіСЂР°РЅРёС† РёРіСЂРѕРІРѕР№ РєР°СЂС‚С‹
      battleMap.setBorder(width, height);
    }

    /**
     * РћРїРёСЃС‹РІР°РµС‚ РґРµР№СЃС‚РІРёСЏ, РЅРµРѕР±С…РѕРґРёРјС‹Рµ РґР»СЏ РІРѕСЃРїСЂРѕРёР·РІРµРґРµРЅРёСЏ Р±РёС‚РІС‹ РЅР° РєР»РёРµРЅС‚Рµ
     * 
     * @throws ObjectAlreadyAddedException
     * @throws MapNotExistException
     * @throws MapAlreadyExistException
     * @throws InterruptedException
     */
    private void playBattle() throws InterruptedException,
        MapAlreadyExistException, MapNotExistException,
        ObjectAlreadyAddedException {
      initInterface(battleMap.getName()); // Р�РЅРёС†РёР°Р»РёР·РёСЂСѓРµС‚ РёРЅС‚РµСЂС„РµР№СЃ

      battle = new Battle();
      /*
       * BattleMapUtils.removeMap(battleMap); BattleMapUtils.registerMap(new
       * BattleMap(message.getMap().getName()));
       */
      battle.init(battleMap.getName(), snakes); // Р�РЅРёС†РёР°Р»РёР·РёСЂСѓРµС‚ Р±РёС‚РІСѓ

      // Р—РјРµР№РєРё РЅР° РїРѕР»Рµ Р±РѕСЏ
      for (int i = 0; i < snakes.length; i++)
        battleMap.putSnake(snakes[i]);
      // Р—РјРµР№РєРё СѓР¶Рµ РґРѕР±Р°РІР»РµРЅС‹ РІ
      // .BattleInit!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
      // Р”РѕР±Р°РІР»РµРЅРёРµ РІ BattleInit РѕС‚РјРµРЅРµРЅРѕ

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

    /*--- Р’СЃРїРѕРјРѕРіР°С‚РµР»СЊРЅС‹Рµ С„СѓРЅРєС†РёРё, РѕС‚РІРµС‡Р°СЋС‰РёРµ Р·Р° Р»РѕРіРёРєСѓ РЅР° РєР»РёРµРЅС‚Рµ ---*/

    /**
     * РћРїРёСЃС‹РІР°РµС‚ РґРµР№СЃС‚РІРёСЏ РІ РїРѕС‚РѕРєРµ РєР»РёРµРЅС‚Р°
     */
    public void run() {
      try {
        // Р’С‹РїРѕР»РЅСЏС‚СЊ, РїРѕРєР° СЃРѕРєРµС‚ РЅРµ Р±СѓРґРµС‚ Р·Р°РєСЂС‹С‚
        // while (!sClient.isClosed()) {
        // РћС‚РїСЂР°РІРєР° РєР»РёРµРЅС‚СЃРєРѕР№ Р·РјРµР№РєРё РЅР° СЃРµСЂРІРµСЂ
        // sendObject(oos, mySnake);
        System.out.println("[CLIENT]: init streams");
        in = new BufferedReader(new InputStreamReader(
            sClient.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
            sClient.getOutputStream())), true);
        //РёРЅРёС†РёР°Р»РёР·Р°С†РёСЏ РїРѕС‚РѕРєР° СЃС‡РёС‚С‹РІР°РЅРёСЏ
        new Thread(){
          public void run() {
            
          };
        };
        // oos = new ObjectOutputStream(os); // Р�РЅРёС†РёР°Р»РёР·Р°С†РёСЏ РёСЃС…РѕРґСЏС‰РµРіРѕ
        // РїРѕС‚РѕРєР° РѕР±СЉРµРєС‚Р°

        while (!sClient.isClosed()) {
          System.out.println("[CLIENT]: receive message");
          String command = receive();

          System.out.println("[CLIENT]: message: [" + command + "]");

          // ===РћС‚РїСЂР°РІРєР° РїР°СЂР°РјРµС‚СЂРѕРІ Р·РјРµР№РєРё РЅР° СЃРµСЂРІРµСЂ=== \\
          if (command.equals(Commands.getSnake)) {
            send(Commands.COMMAND_NOT_SUPPORTED);
          }
          // ===РћС‚РїСЂР°РІРєР° РјРѕР·РіРѕРІ Р·РјРµР№РєРё РЅР° СЃРµСЂРІРµСЂ=== \\
          if (command.equals(Commands.GET_MIND)) {
        	  SnakeMind mind = mySnake.getMind();
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
          // ===РџРѕР»СѓС‡РµРЅРёРµ С…РѕРґР° Р±РёС‚РІС‹ РѕС‚ СЃРµСЂРІРµСЂР°=== \\
          // id_action.action_type.id_snake[.id_snake2]
          if (command.equals(Commands.actions)) {
            // РЎРїРёСЃРѕРє РґРµР№СЃС‚РІРёР№ РІ Р±РёС‚РІРµ
            ArrayList<ActionList> al = new ArrayList<ActionList>();
            // РЎРїРёСЃРѕРє СЃРѕРґРµСЂР¶РёС‚ РїРѕСЂСЏРґРѕРє С…РѕРґРѕРІ Р·РјРµРµРє
            ArrayList<Integer> order = new ArrayList<Integer>();
            String line = "";	// РџРѕР»СѓС‡Р°РµРјС‹Рµ РґР°РЅРЅС‹Рµ РѕС‚ СЃРµСЂРІРµСЂР° (РїРѕСЃС‚СЂРѕС‡РЅРѕ)
            line = receive();	// РџРѕР»СѓС‡РµРЅРёРµ РґР°РЅРЅС‹С… РѕС‚ СЃРµСЂРІРµСЂР° (1-СЏ СЃС‚СЂРѕРєР°)
            
            // РџРѕРєР° РЅРµ РїРѕР»СѓС‡РµРЅРѕ РєРѕРјР°РЅРґС‹ РЅР° Р·Р°РІРµСЂС€РµРЅРёРµ РїРѕР»СѓС‡РµРЅРёСЏ СЃРѕРѕР±С‰РµРЅРёСЏ
            while (!line.equals(Commands.END_SENDING)) {
              System.out.println("[CLIENT]: receive message: " + line);
              String[] lines = line.split("\\.");	// Р Р°Р·Р±РёРµРЅРёРµ РґР°РЅРЅС‹С… РЅР° СЃС‚СЂРѕРєРё РїРѕ "."
              
              // РЎС‡РёС‚С‹РІР°РЅРёРµ РїРѕР»СѓС‡Р°РµРјС‹С… РґР°РЅРЅС‹С…
              order.add(Integer.valueOf(lines[0]));	// РџРѕСЂСЏРґРєРѕРІС‹Р№ РЅРѕРјРµСЂ С…РѕРґР°
              // Р•СЃР»Рё РІ РґРµР№СЃС‚РІРёРё СѓС‡Р°РІСЃС‚РІСѓРµС‚ С‚РѕР»СЊРєРѕ 1 Р·РјРµР№РєР°
              if (lines.length == 3)
                al.add(/*order.get(order.size() - 1),*/ 						// Р’СЃС‚Р°РІРёС‚СЊ РґРµР№СЃС‚РІРёРµ РІ РЅСѓР¶РЅРѕРј РїРѕСЂСЏРґРєРµ
                     new ActionList(ActionFactory.getValue(lines[1]), 	// РўРёРї РґРµР№СЃС‚РІРёСЏ
                         		  snakes[Integer.valueOf(lines[2])]));	// Р—РјРµР№РєР°, Рє РєРѕС‚РѕСЂРѕР№ РѕС‚РЅРѕСЃРёС‚СЃСЏ РґРµР№СЃС‚РІРёРµ
              // Р•СЃР»Рё РІ РґРµР№СЃС‚РІРёРё СѓС‡Р°РІСЃС‚РІСѓРµС‚ Р±РѕР»РµРµ 1 Р·РјРµР№РєРё
              else al.add(/*order.get(order.size() - 1),*/ 
                    new ActionList(ActionFactory.getValue(lines[1]), 
                           	   snakes[Integer.valueOf(lines[2])],
                           	   snakes[Integer.valueOf(lines[3])]));
              
              line = receive();	// РџРѕР»СѓС‡РµРЅРёРµ РґР°РЅРЅС‹С… РѕС‚ СЃРµСЂРІРµСЂР°
            }
            
            // Р Р°СЃРїРѕР»РѕР¶РµРЅРёРµ С…РѕРґРѕРІ РІ РїСЂР°РІРёР»СЊРЅРѕРј РїРѕСЂСЏРґРєРµ
            for (Integer i : order) {
              // Р”РѕР±Р°РІР»СЏРµРј РґРµР№СЃС‚РІРёРµ РїРѕРґ РЅРѕРјРµСЂРѕРј i
              actions.add(al.get(i));
            }
            
            // Р‘РёС‚РІР° СЂР°СЃСЃС‡РёС‚Р°РЅР°, РЅР°РґРѕ РІРѕСЃРїСЂРѕРёР·РІРµСЃС‚Рё
            playBattle();
            /*
             * message = (Message) receiveObject(ois); battleMap = new
             * BattleMap(message.getMap().getName()); 
             * snakes = message.getSnakes(); 
             * actions = message.getAl();
             * playBattle();
             */
          }
          // [05.01.2013] \\
          // РћР¶РёРґР°РЅРёРµ 10РјСЃ
          sleep(10);
        }
        /*
         * // РџРѕР»СѓС‡РµРЅРёРµ СЃРѕРѕР±С‰РµРЅРёСЏ РѕС‚ СЃРµСЂРІРµСЂР° message = (Message)
         * receiveObject(ois); // РЎС‡РёС‚С‹РІР°РЅРёРµ РґР°РЅРЅС‹С… РёР· СЃРѕРѕР±С‰РµРЅРёСЏ if
         * (message != null) { battleMap = new
         * BattleMap(message.getMap().getName()); // battleMap =
         * BattleMapUtils.registerMap(new //
         * BattleMap(BattleMapUtils.generateName("clientMap-"))); snakes =
         * message.getSnakes(); actions = message.getAl(); }
         */
        /*
         * for (int i = 0; i < snakes.length; i++) {
         * snakes[i].setSnakeInMap(false, battleMap.getName()); }
         */

        // РџРѕРєР°Р·Р°С‚СЊ Р±РёС‚РІСѓ РЅР° РєР»РёРµРЅС‚Рµ
        // playBattle();
        // }
      } catch (IOException | InterruptedException | MapAlreadyExistException | MapNotExistException | ObjectAlreadyAddedException e) {
        e.printStackTrace();
      } finally {
        // Р—Р°РєСЂС‹С‚СЊ СЃРѕРєРµС‚
        try {
          sClient.close();
        } catch (IOException e) {
          System.out.println("[CLIENT]: Socket not closed");
        }
      }
    }
  }

  private Snake mySnake;
  // РЎРѕР·РґР°РЅРёРµ РѕР±СЉРµРєС‚Р° РїРѕС‚РѕРєР° РєР»РёРµРЅС‚Р°
  public static ClientThread thread = null;

  /**
   * РљРѕРЅСЃС‚СЂСѓРєС‚РѕСЂ РєР»РёРµРЅС‚Р°
   */
  public Client(Snake mySnake) {
    this.mySnake = mySnake;
    // Р—Р°РїСѓСЃРє РєР»РёРµРЅС‚СЃРєРѕРіРѕ РїРѕС‚РѕРєР°
    try {
      thread = new ClientThread(mySnake);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * РћСЃРЅРѕРІРЅР°СЏ С„СѓРЅРєС†РёСЏ РєР»РёРµРЅС‚Р°
   * 
   * @param args
   * @throws UnknownHostException
   * @throws IOException
   */
  public static void main(String[] args) throws UnknownHostException,
      IOException {
    Snake sn = new Snake();
    SnakeMind mind = sn.getMind();
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