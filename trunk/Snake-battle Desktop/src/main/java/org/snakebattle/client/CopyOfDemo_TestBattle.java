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

public class CopyOfDemo_TestBattle {

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
					Map m = null;
					
					try {
						// Инициализация карты, змеек
						battle.init("serverMap", snakes);
						m = Common.selectMap("serverMap");
						for (int i = 0; i < snakes.length; i++) {
							m.putSnake(snakes[i]);
						}
						m.setBorder(800, 600);
					} catch (MapAlreadyExistException
							| MapNotExistException
							| ObjectAlreadyAddedException e) {
						e.printStackTrace();
					}
					
					List<ActionList> al = battle.battleCalc(snakes);
					//===============================================
					//================ВЫВОД==========================
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

					//for (int i = 0; i < snakes.length; i++) {
					//	snakesDraw[i].setMind(snakes[i].getMind());
					//}
					Map mapDraw = null;
					Battle battleShow = new Battle();
					battleShow.init("asdasd", snakesDraw); // Инициализирует битву

					mapDraw = Common.selectMap("asdasd");
					mapDraw.setBorder(800, 600);
					// Змейки на поле боя
					for (int i = 0; i < snakes.length; i++)
						mapDraw.putSnake(snakesDraw[i]);
					
					
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
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (MapAlreadyExistException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MapNotExistException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ObjectAlreadyAddedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		th.setDaemon(true);
		th.start();
	}
}