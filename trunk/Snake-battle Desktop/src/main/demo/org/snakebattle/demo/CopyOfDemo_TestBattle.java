package org.snakebattle.demo;

import java.awt.Point;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.snakebattle.gui.ObjectAlreadyAddedException;
import org.snakebattle.gui.primitive.snake.Element;
import org.snakebattle.gui.primitive.snake.MindPolyGraph;
import org.snakebattle.gui.primitive.snake.Element.PARTS;
import org.snakebattle.gui.primitive.snake.MindPolyGraph.LOGIC_TYPES;
import org.snakebattle.gui.primitive.snake.MindPolyGraph.OWNER_TYPES;
import org.snakebattle.gui.screen.Screen;
import org.snakebattle.logic.BattleMap;
import org.snakebattle.logic.Mind;
import org.snakebattle.logic.Mind.MindMap;
import org.snakebattle.logic.Snake;
import org.snakebattle.logic.SnakeAlreadyInMapException;
import org.snakebattle.server.Battle;
import org.snakebattle.utils.BattleMapUtils;
import org.snakebattle.utils.BattleMapUtils.ActionList;
import org.snakebattle.utils.BattleMapUtils.MapAlreadyExistException;
import org.snakebattle.utils.BattleMapUtils.MapNotExistException;

public class CopyOfDemo_TestBattle {

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
					// init
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
					BattleMap m = null;
					
					try {
						// init map and snakes
						battle.init("serverMap", snakes);
						m = BattleMapUtils.selectMap("serverMap");
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
					//=================Draw==========================
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
					BattleMap mapDraw = null;
					Battle battleShow = new Battle();
					battleShow.init("asdasd", snakesDraw); // init battle

					mapDraw = BattleMapUtils.selectMap("asdasd");
					mapDraw.setBorder(800, 600);
					// put snakes into battle map
					for (int i = 0; i < snakes.length; i++)
						mapDraw.putSnake(snakesDraw[i]);
					
					
					// If graphics has been off - enable it
					if (!Screen.GRAPHICS_ON)
						Screen.GRAPHICS_ON = true;

					// Init and show GUI
					new Screen();

					//int width = Screen.instance.getWidth(), height = Screen.instance
					//		.getHeight();

					// Waiting for the screen initialization
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
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		th.setDaemon(true);
		th.start();
	}
}