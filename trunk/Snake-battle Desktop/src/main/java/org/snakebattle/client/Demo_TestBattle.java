package org.snakebattle.client;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.snakebattle.gui.Common;
import org.snakebattle.gui.Element;
import org.snakebattle.gui.EmptyScreen;
import org.snakebattle.gui.MindPolyGraph;
import org.snakebattle.gui.ObjectAlreadyAddedException;
import org.snakebattle.gui.Point;
import org.snakebattle.gui.Screen;
import org.snakebattle.gui.SwingScreen;
import org.snakebattle.gui.Common.ActionList;
import org.snakebattle.gui.Common.MapAlreadyExistException;
import org.snakebattle.gui.Common.MapNotExistException;
import org.snakebattle.gui.Element.PARTS;
import org.snakebattle.gui.MindPolyGraph.LOGIC_TYPES;
import org.snakebattle.gui.MindPolyGraph.OWNER_TYPES;
import org.snakebattle.logic.Map;
import org.snakebattle.logic.Mind;
import org.snakebattle.logic.Snake;
import org.snakebattle.logic.SnakeAlreadyInMapException;
import org.snakebattle.logic.Mind.MindMap;
import org.snakebattle.server.Battle;

public class Demo_TestBattle {

	/**
	 * просто тестировать интерфейс и юазовые действия
	 * 
	 * @param args
	 * @throws ObjectAlreadyAddedException
	 * @throws InterruptedException
	 * @throws SnakeAlreadyInMapException
	 */
	public static void main(String[] args) throws ObjectAlreadyAddedException,
			InterruptedException, SnakeAlreadyInMapException {
		Screen.GRAPHICS_ON = true;
		new Screen();
		while (!Screen.instance.canDraw())
			Thread.sleep(100);
		Thread th = new Thread() {
			public void run() {
				try {
					// Инициализация
					Battle battle = new Battle();
					Snake[] snakes = battle.snake_fill();
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

					/*
					 * mpg = new MindPolyGraph(new Point(), 10, 10);
					 * mpg.setOwner(OWNER_TYPES.ENEMY); mpg.setValue(new
					 * Element(PARTS.TAIL, new Point(), 10,10, null));
					 * mm[0].setAt(2, 0, mpg);
					 */

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
					Map map = null;
					// Создание экрана, на котором будут происходит
					// все
					// действия
					// змеек
					new Screen(new EmptyScreen());
					/** Блок из тестовой функции */
					try {
						// Инициализация карты, змеек
						battle.init("serverMap", snakes);
						map = Common.selectMap("serverMap");
						for (int i = 0; i < snakes.length; i++) {
							map.putSnake(snakes[i]);
						}
						map.setBorder(800, 600);
					} catch (MapAlreadyExistException
							| MapNotExistException
							| ObjectAlreadyAddedException e) {
						e.printStackTrace();
					}
					List<ActionList> al = battle.battleCalc(snakes);
					System.out.println("[SERVER]: Battle end");
					
					
					
					//Screen.instance = null;
					//new Screen(new SwingScreen());
					//Screen.GRAPHICS_ON = true;
					// initInterface("asdasd");
					//while (!Screen.instance.canDraw())
					//	Thread.sleep(100);
					
					
					// Еси графика была выключена - включить
					if (!Screen.GRAPHICS_ON)
						Screen.GRAPHICS_ON = true;

					// Инициализация и отображение GUI
					new Screen();

					// Размеры экрана (для отрисовки границ)
					int width = Screen.instance.getWidth(), height = Screen.instance
							.getHeight();

					// Ожидание возможности отрисовки
					while (!Screen.instance.canDraw())
						Thread.sleep(100);
					
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
					

					// Регистрация указанной карты и её выбор для битвы
					Map mapDraw = null;//Common.registerMap(new Map("asdasd"));
					// map = Common.selectMap(mapName);
					// Задание границ игровой карты
					//mapDraw.setBorder(width, height);
					
					Battle battleInit = new Battle();
					/*
					 * Common.removeMap(map); Common.registerMap(new
					 * Map(message.getMap().getName()));
					 */
					battleInit.init("asdasd", snakesDraw); // Инициализирует битву

					mapDraw = Common.selectMap("asdasd");
					mapDraw.setBorder(800, 600);
					// Змейки на поле боя
					for (int i = 0; i < snakes.length; i++)
						mapDraw.putSnake(snakesDraw[i]);
					// Змейки уже добавлены в
					// .BattleInit!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
					// Добавление в BattleInit отменено

					mapDraw.drawAll();

					int waitTime = 5;
					while (Screen.instance.canDraw())
						for (ActionList a : al) {
							long timeold = System.currentTimeMillis();
							a.action.doAction(a.param);
							mapDraw.drawAll();
							long timenow = System.currentTimeMillis() - timeold;
							if (waitTime - timenow > 0)
								Thread.sleep(waitTime - timenow);
						}
					
				} catch (InterruptedException | ObjectAlreadyAddedException
						| MapAlreadyExistException | MapNotExistException e) {
					e.printStackTrace();
				}
			}
		};
		th.setDaemon(true);
		th.start();
	}
}