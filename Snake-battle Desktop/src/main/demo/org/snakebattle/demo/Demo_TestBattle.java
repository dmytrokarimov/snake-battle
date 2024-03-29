package org.snakebattle.demo;

import java.awt.Point;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.snakebattle.gui.ObjectAlreadyAddedException;
import org.snakebattle.gui.primitive.snake.Element;
import org.snakebattle.gui.primitive.snake.Element.PARTS;
import org.snakebattle.gui.primitive.snake.MindPolyGraph;
import org.snakebattle.gui.primitive.snake.MindPolyGraph.LOGIC_TYPES;
import org.snakebattle.gui.primitive.snake.MindPolyGraph.OWNER_TYPES;
import org.snakebattle.gui.screen.EmptyScreen;
import org.snakebattle.gui.screen.Screen;
import org.snakebattle.logic.BattleMap;
import org.snakebattle.logic.Snake;
import org.snakebattle.logic.SnakeAlreadyInMapException;
import org.snakebattle.logic.SnakeMind;
import org.snakebattle.logic.SnakeMind.MindMap;
import org.snakebattle.server.Battle;
import org.snakebattle.utils.BattleMapUtils;
import org.snakebattle.utils.BattleMapUtils.ActionList;
import org.snakebattle.utils.BattleMapUtils.MapAlreadyExistException;
import org.snakebattle.utils.BattleMapUtils.MapNotExistException;

public class Demo_TestBattle {

	/**
	 * Just for testing interface and basic actions
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
					// Initialization
					Battle battle = new Battle();
					Snake[] snakes = battle.snake_fill();
					SnakeMind mind = snakes[0].getMind();
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
					BattleMap battleMap = null;

					new Screen(new EmptyScreen());
					try {
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
					
					
					
					if (!Screen.GRAPHICS_ON)
						Screen.GRAPHICS_ON = true;

					new Screen();

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
					

					BattleMap mapDraw = null;//BattleMapUtils.registerMap(new BattleMap("asdasd"));
					
					Battle battleInit = new Battle();
					/*
					 * BattleMapUtils.removeMap(map); BattleMapUtils.registerMap(new
					 * BattleMap(message.getMap().getName()));
					 */
					battleInit.init("asdasd", snakesDraw); // init battle

					mapDraw = BattleMapUtils.selectMap("asdasd");
					mapDraw.setBorder(800, 600);

					for (int i = 0; i < snakes.length; i++)
						mapDraw.putSnake(snakesDraw[i]);

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