package org.snakebattle.client;

import java.awt.Point;

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
import org.snakebattle.utils.BattleMapUtils.MapAlreadyExistException;
import org.snakebattle.utils.BattleMapUtils.MapNotExistException;

public class Demo {

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
					String mapName = "battle";
					Snake[] sn = new Snake[] { new Snake(), new Snake(),
							new Snake(), new Snake() };
					Battle b = new Battle();
					b.init(mapName, sn);
										
					BattleMap m = BattleMapUtils.selectMap(mapName);

					for (int i = 0; i < sn.length; i++) {
						m.putSnake(sn[i]);
					}

					
					m.setBorder(800, 600);
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
					m.putSnake(snake);

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
					snake.moveTo(350, 290);
					m.putSnake(snake);

					snake = new Snake();
					el = new Element(PARTS.HEAD, new Point(0, 0), 10, 10, snake);
					snake.addElement(el);
					el = new Element(PARTS.BODY, new Point(0, 10), 10, 10,
							snake);
					snake.addElement(el);
					el = new Element(PARTS.TAIL, new Point(0, 20), 10, 10,
							snake);
					snake.addElement(el);
					snake.moveTo(350, 280);
					m.putSnake(snake);

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
					m.putSnake(snake);

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
					m.putSnake(snake);

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
					m.putSnake(snake);

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
					m.putSnake(snake);

					m.drawAll();
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

					m.putSnake(snake);

					Mind mind = sn[0].getMind();
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

					mind = sn[0].getMind();
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

					while (true) {
						long timeold = System.currentTimeMillis();
						BattleMapUtils.doStep(m.getName(), sn);
						m.drawAll();
						long timenow = System.currentTimeMillis() - timeold;
						if (waitTime - timenow > 0)
							Thread.sleep(waitTime - timenow);
					}
				} catch (InterruptedException | ObjectAlreadyAddedException
						| MapAlreadyExistException | MapNotExistException
						| SnakeAlreadyInMapException e) {
					e.printStackTrace();
				}
			}
		};
		th.setDaemon(true);
		th.start();
	}
}