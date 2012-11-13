package client;

import gui.Common;
import gui.Common.MapAlreadyExistException;
import gui.Common.MapNotExistException;
import gui.Dummy;
import gui.Element;
import gui.Element.PARTS;
import gui.MindPolyGraph.LOGIC_TYPES;
import gui.MindPolyGraph.OWNER_TYPES;
import gui.MindPolyGraph;
import gui.ObjectAlreadyAddedException;
import gui.Screen;

import java.awt.Point;

import logic.Map;
import logic.Mind;
import logic.Mind.MindMap;
import logic.Snake;
import logic.SnakeAlreadyInMapException;
import server.Battle;

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
		new Screen();
		while (!Screen.instance.canDraw())
			Thread.sleep(100);
		Thread th = new Thread() {
			public void run() {
				try {
					Common.registerMap(new Map("battle"));
					Map m = Common.selectMap("battle");

					Snake[] sn = new Snake[] { new Snake(), new Snake(),
							new Snake(), new Snake() };
					Battle b = new Battle();
					b.init(m, sn);
					
					/*
					 * Element el = new Element(PARTS.HEAD, new Point(10, 0),
					 * 10, 10, sn);
					 * 
					 * sn.addElement(el); int i; for (i = 2; i < 39; i++) { el
					 * = new Element(PARTS.BODY, new Point(i * 10, 0), 10, 10,
					 * sn); sn.addElement(el); } el = new Element(PARTS.TAIL,
					 * new Point(i * 10, 0), 10, 10, sn); sn.addElement(el);
					 */
					// sn.moveTo(100, 100);
					for (int i = 0; i < sn.length; i++)
						m.putSnake(sn[i]);

					Dummy d;
					int i;
					for (i = 0; i < 60; i++) {
						d = new Dummy(new Point(-10, i * 10), 10, 10);
						m.put(d);
					}

					for (i = 0; i < 60; i++) {
						d = new Dummy(new Point(810, i * 10), 10, 10);
						m.put(d);
					}

					for (i = 0; i < 80; i++) {
						d = new Dummy(new Point(i * 10, -10), 10, 10);
						m.put(d);
					}

					for (i = 0; i < 80; i++) {
						d = new Dummy(new Point(i * 10, 610), 10, 10);
						m.put(d);
					}

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
					el = new Element(PARTS.HEAD, new Point(10, 0), 10,
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
					snake.moveTo(350, 290);
					m.putSnake(snake);

					snake = new Snake();
					el = new Element(PARTS.HEAD, new Point(0, 0), 10,
							10, snake);
					snake.addElement(el);
					el = new Element(PARTS.BODY, new Point(0, 10), 10,
							10, snake);
					snake.addElement(el);
					el = new Element(PARTS.TAIL, new Point(0, 20), 10, 10,
							snake);
					snake.addElement(el);
					snake.moveTo(350, 280);
					m.putSnake(snake);

					snake = new Snake();
					el = new Element(PARTS.HEAD, new Point(10, 0), 10,
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
					snake.moveTo(350, 230);
					m.putSnake(snake);
					
					snake = new Snake();
					el = new Element(PARTS.HEAD, new Point(10, 0), 10,
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
					snake.moveTo(350, 210);
					m.putSnake(snake);
					
					snake = new Snake();
					el = new Element(PARTS.HEAD, new Point(10, 0), 10,
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
					snake.moveTo(280, 220);
					m.putSnake(snake);
					
					snake = new Snake();
					el = new Element(PARTS.HEAD, new Point(10, 0), 10,
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
					snake.moveTo(350 + 16*10, 210);
					m.putSnake(snake);
					
					
					m.drawAll();
					int waitTime = 50;
					
					//-------exemple:  add element to mind
					snake = new Snake();
					
					el = new Element(PARTS.HEAD, new Point(310, 270), 10,
							10, snake);	
					snake.addElement(el);

					el = new Element(PARTS.BODY, new Point(310, 280), 10,
							10, snake);	
					snake.addElement(el);

					el = new Element(PARTS.BODY, new Point(310, 290), 10,
							10, snake);	
					snake.addElement(el);

					
					el = new Element(PARTS.TAIL, new Point(310, 300), 10,
							10, snake);	
					snake.addElement(el);
					
					m.putSnake(snake);
					
					Mind mind = sn[0].getMind();
					MindMap[] mm = mind.getMindMap();
					MindPolyGraph mpg = new MindPolyGraph(new Point(), 10, 10);
					mpg.setOwner(OWNER_TYPES.SNAKE);
					mpg.setValue(new Element(PARTS.HEAD, new Point(), 10,10, null));
					mm[0].setAt(2, 2, mpg);
					
					mpg = new MindPolyGraph(new Point(), 10, 10);
					mpg.setOwner(OWNER_TYPES.ENEMY);
					mpg.setLogic(LOGIC_TYPES.OR);
					mpg.setValue(new Element(PARTS.TAIL, new Point(), 10,10, null));
					mm[0].setAt(2, 1, mpg);

					mpg = new MindPolyGraph(new Point(), 10, 10);
					mpg.setOwner(OWNER_TYPES.ENEMY);
					mpg.setLogic(LOGIC_TYPES.OR);
					mpg.setValue(new Element(PARTS.TAIL, new Point(), 10,10, null));
					mm[0].setAt(2, 0, mpg);

					/*
					mpg = new MindPolyGraph(new Point(), 10, 10);
					mpg.setOwner(OWNER_TYPES.ENEMY);
					mpg.setValue(new Element(PARTS.TAIL, new Point(), 10,10, null));
					mm[0].setAt(2, 0, mpg);*/
					
					mind = sn[0].getMind();
					MindMap mm1 = mind.getMindMap(1);
					mpg = new MindPolyGraph(new Point(), 10, 10);
					mpg.setOwner(OWNER_TYPES.SNAKE);
					mpg.setValue(new Element(PARTS.HEAD, new Point(), 10,10, null));
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

					//-------end exemple
					
					while (true) {
						long timeold = System.currentTimeMillis();
						Common.doStep(m.getName(), sn);
						m.drawAll();
						long timenow = System.currentTimeMillis() - timeold;
						if (waitTime - timenow > 0)
							Thread.sleep(waitTime - timenow);
						/*
						 * for (i = 0; i < 10; i++) { long timeold =
						 * System.currentTimeMillis();
						 * ActionFactory.getUp().doAction(sn);
						 * //Screen.instance.repaint(); m.drawAll(); long
						 * timenow = System.currentTimeMillis() - timeold; if
						 * (waitTime - timenow > 0) Thread.sleep(waitTime -
						 * timenow); } for (i = 0; i < 10; i++) { long timeold =
						 * System.currentTimeMillis();
						 * ActionFactory.getRight().doAction(sn); m.drawAll();
						 * long timenow = System.currentTimeMillis() - timeold;
						 * if (waitTime - timenow > 0) Thread.sleep(waitTime -
						 * timenow); } for (i = 0; i < 10; i++) { long timeold =
						 * System.currentTimeMillis();
						 * ActionFactory.getDown().doAction(sn); m.drawAll();
						 * long timenow = System.currentTimeMillis() - timeold;
						 * if (waitTime - timenow > 0) Thread.sleep(waitTime -
						 * timenow); } for (i = 0; i < 10; i++) { long timeold =
						 * System.currentTimeMillis();
						 * ActionFactory.getLeft().doAction(sn); m.drawAll();
						 * long timenow = System.currentTimeMillis() - timeold;
						 * if (waitTime - timenow > 0) Thread.sleep(waitTime -
						 * timenow); }
						 */
					} 
				} catch (InterruptedException | ObjectAlreadyAddedException
						| MapAlreadyExistException | MapNotExistException | SnakeAlreadyInMapException e) {
					e.printStackTrace();
				}
			}
		};
		th.setDaemon(true);
		th.start();
	}
}