package org.snakebattle.server;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.snakebattle.gui.ObjectAlreadyAddedException;
import org.snakebattle.gui.primitive.snake.Element;
import org.snakebattle.gui.primitive.snake.Element.PARTS;
import org.snakebattle.logic.Action.ACTION_TYPE;
import org.snakebattle.logic.BattleMap;
import org.snakebattle.logic.Snake;
import org.snakebattle.utils.BattleMapUtils;
import org.snakebattle.utils.BattleMapUtils.ActionList;
import org.snakebattle.utils.BattleMapUtils.MapAlreadyExistException;
import org.snakebattle.utils.BattleMapUtils.MapNotExistException;

/**
 * Класс для инициализации битвы и расчёта её исхода
 * @author RED Developer
 */
public class Battle implements Serializable{
  private static final long serialVersionUID = 8068676336496071038L;
  // Центр экрана !!!!!Размеры экрана (необходимо как-то получать)!!!!!!
  private int centerX = 400;//Screen.instance.getWidth() / 2;
  private int centerY = 300;//Screen.instance.getHeight() / 2;
  // Размер ячейки змеек
  private final Point snakeSize = new Point (10, 10);
  // Множитель отступа змеек от центра поля битвы (множится на  размеры 1 элемента змейки)
  private int deltaX = snakeSize.x;
  private int deltaY = snakeSize.y;
  
  // В битве принимают участие до snakeLimit змеек
  private final byte snakeLimit = 4;
  // Выделенное время на битву (при его истечении бой заканчивается по тайм-ауту)
  private final int timeLimit = 60000;	// 60c
  // Выделенное количество шагов на битву
  private final int stepsLimit = 500;
  // Количество змеек в заявке
  private byte snakeCount = 0;
  // Начальная длина каждой змейки (ячеек)
  private final byte snakeLength = 5;
  // Начальная координата 0-й змейки (относительно центра)
  private Point snakeStart = new Point(0, -100);
  // Множтели поправок для координат змеиных голов
  private int headX = -1, headY = -1;
  // Множтели поправок для координат змеиных элементов тела (и хвоста)
  private int bodyX = 0, bodyY = 0;
  // Карта, на которой проводится битва
  private static BattleMap battleMap;
  
  /**
   * Проводит нормализацию координаты относительно центра экрана
   * @param x
   * @param y
   * @return Point
   */
  private Point normal(int x, int y)
  {
    // Координаты центра экрана
    return new Point(x + centerX, y + centerY);
  }
  /**
   * Проводит нормализацию координаты относительно центра экрана
   * @param coord
   * @param screen
   * @return Point
   */
  private Point normal(Point coord)
  {
    // Координаты центра экрана
    return new Point(coord.x + centerX, coord.y + centerY);
  }
  /**
   * Осуществляет поворот координаты на 90 градусов
   * @param coord
   * @return Point
   */
  private Point coordRotate(Point coord)
  {
    if (coord.x != 0) headX *= -1;
    if (coord.y != 0) headY *= -1;
    Point p = new Point (coord.x + headX * deltaX * snakeSize.x, coord.y + headY * deltaY * snakeSize.y);
    
    return p;
  }
  
  /**
   * Инициализирует змейки
   * @param mapName - имя карты, на которой будет происходить битва
   * @param snakes - змейки, заявленные на бой
   * @throws MapAlreadyExistException 
   * @throws MapNotExistException 
   * @throws ObjectAlreadyAddedException 
   */
  public void init(String mapName, Snake[] snakes) throws MapAlreadyExistException, MapNotExistException, ObjectAlreadyAddedException{	
    if (mapName == "" || mapName == null || snakes == null) return;
    
    // Регистрация указанной карты и её выбор для битвы
    try{
      battleMap = BattleMapUtils.registerMap(new BattleMap(mapName));
    }
    catch(Exception ex){
      System.out.println("Карта " + mapName + " уже была создана, вибираем её");
      battleMap = BattleMapUtils.selectMap(mapName);
    }
    
    // Количество змеек, заявленных на бой 
    byte snakesCount = (byte) snakes.length;
    // Реальное количество змеек в заявке
    for (byte i = 0; i < snakesCount; i++)
      if (snakes[i] != null) snakeCount++;
    // Счётчик змеек
    byte iSnake = 0;
    // Для создания элементов змеек
    List<Element> el = new ArrayList<Element>();
    
    // Инициализация змеек
    while (iSnake < snakeLimit && iSnake < snakesCount)
    {
      snakeStart = coordRotate(snakeStart);
      // Если змей 2 - поставить друг напротив друга 
      if (snakeCount == 2) snakeStart = coordRotate(snakeStart);
      
      if (snakes[iSnake] != null) {
        // Перестраиваем змею, если она была уже готовой
        snakes[iSnake].getElements().clear();
        // Новая голова для змейки iSnake. "head? * snakeSize.?" - поправочный коэффициент
        el.add(new Element(PARTS.HEAD, normal(snakeStart.x,
            snakeStart.y), snakeSize.x, snakeSize.y, snakes[iSnake]));

        // В какую сторону отстраивать тело
        if (snakeStart.x < 0 && snakeStart.y == 0) {
          // Влево
          bodyX = -1;
          bodyY = 0;
        } else if (snakeStart.x > 0 && snakeStart.y == 0) {
          // Вправо
          bodyX = 1;
          bodyY = 0;
        } else if (snakeStart.x == 0 && snakeStart.y < 0) {
          // Вверх
          bodyX = 0;
          bodyY = -1;
        } else if (snakeStart.x == 0 && snakeStart.y > 0) {
          // Вниз
          bodyX = 0;
          bodyY = 1;
        }
        // Строим тело и хвост
        for (byte i = 1; i < snakeLength - 1; i++) {
          el.add(new Element(PARTS.BODY, new Point(
              el.get(i - 1).getCoord().x + snakeSize.x * bodyX, 
              el.get(i - 1).getCoord().y + snakeSize.y * bodyY), 
              snakeSize.x, snakeSize.y, snakes[iSnake]));
        }
        el.add(new Element(PARTS.TAIL, new Point(
            el.get(snakeLength - 2).getCoord().x + snakeSize.x * bodyX, 
            el.get(snakeLength - 2).getCoord().y + snakeSize.y * bodyY), 
            snakeSize.x, snakeSize.y, snakes[iSnake]));

        // Добавление элементов в змейку
        snakes[iSnake].setElements(el);
        // Для новой змейки будет новый список элементов
        el.clear();
      }
      // Добавление змейки на карту
      //battleMap.putSnake(snakes[iSnake]);
      // Следующая змея
      iSnake++;
    }
  }
  
  /**
   * Проверка на признак окончания боя
   * @param snake
   * @param time
   * @param steps
   * @return
   */
  private boolean Stop(Snake[] snakes, int time, int steps){
    if (snakes == null) return true;
    
    // Если время ещё есть && Если действий ещё не больше допустимого количества
    if (time < timeLimit && steps < stepsLimit)
      // Если всем змейкам есть куда ходить
      for (int i = 0; i < snakes.length; i++)
        if (snakes[i].getMind().getAction(battleMap).action.getType() != ACTION_TYPE.IN_DEAD_LOCK)
          return false;
    
    return true;
  }
  /**
   * Проводит рассчёт битвы и записывает ёё ход
   * @param snake
   * @return List of Actions
   */
  /*protected*/public List<ActionList> battleCalc(Snake[] snakes){
    // Лог событий битвы (действия змеек)
    List<ActionList> actions = new ArrayList<ActionList>();
    // Сколько времени уже прошло
    int timeElapsed = 0;
    // Сколько шагов сделано
    int stepsPassed = 0;
    
    // Пока битва идёт - писать лог действий
    while(!Stop(snakes, timeElapsed, stepsPassed))
    {
      for (ActionList al : BattleMapUtils.doStep(battleMap.getName(), snakes))
        actions.add(al);
      // Наращивание счётчика шагов
      stepsPassed++;
    }
    System.out.println("Битва окончена за " + stepsPassed + " шагов");
    return actions;
  }
  
  //
  private String generateMap(String prefix){
    return prefix + "-" + new Random().nextInt();
  }
  
  /**
   * Тестовый набор змеек
   * @param snakes
   */
  public Snake[] snake_fill(){
    Snake snake0 = new Snake();
    Snake snake1 = new Snake();
    Snake snake2 = new Snake();
    Snake snake3 = new Snake();
    return new Snake[] { snake0, snake1, snake2, snake3 };
  }
}