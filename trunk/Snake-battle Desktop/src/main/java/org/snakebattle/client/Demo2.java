package org.snakebattle.client;

import org.snakebattle.gui.*;
import org.snakebattle.gui.Common.MapAlreadyExistException;
import org.snakebattle.gui.Common.MapNotExistException;
import org.snakebattle.gui.Element.PARTS;
import org.snakebattle.logic.Action;
import org.snakebattle.logic.ActionFactory;
import org.snakebattle.logic.HeadNoFirstException;
import org.snakebattle.logic.Map;
import org.snakebattle.logic.Snake;
import org.snakebattle.logic.SnakeAlreadyInMapException;
import org.snakebattle.logic.Action.ACTION_TYPE;
import org.snakebattle.server.Battle;

public class Demo2 {

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
					
					for (i = 0; i < 60; i++) {
						d = new Dummy(new Point(10, i * 10), 10, 10);
						m.put(d);
					}
					Snake snake1 = new Snake();
					Element el = new Element(PARTS.HEAD, new Point(0, 10), 10,
							10, snake1);
					snake1.addElement(el);
					for (i = 2; i < 10; i++) {
						el = new Element(PARTS.BODY, new Point(0, i * 10), 10,
								10, snake1);
						snake1.addElement(el);
					}
					el = new Element(PARTS.TAIL, new Point(0, i * 10), 10, 10,
							snake1);
					snake1.addElement(el);
					snake1.moveTo(0, 0);
					m.putSnake(snake1);
					
					Snake snake2 = new Snake();
					el = new Element(PARTS.HEAD, new Point(0, 10), 10,
							10, snake2);
					snake2.addElement(el);
					for (i = 2; i < 5; i++) {
						el = new Element(PARTS.BODY, new Point(0, i * 10), 10,
								10, snake2);
						snake2.addElement(el);
					}
					el = new Element(PARTS.TAIL, new Point(0, i * 10), 10, 10,
							snake2);
					snake2.addElement(el);
					snake2.moveTo(0, 300);
					m.putSnake(snake2);
					
					m.drawAll();
					Snake[] sn = new Snake[] { snake1, snake2};
					int waitTime = 100;
					while (true) {
						//if (snake1.getElements().size() > 1)
						//	ActionFactory.getEatTail().doAction(snake2, snake1);
						//else ActionFactory.getRight().doAction(snake2);
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
					} // EXCEPTION закомментил, т.к. из-за него какую-то ошибку
						// выдавало... Не разбирался
				} catch (InterruptedException | ObjectAlreadyAddedException
						| MapAlreadyExistException | MapNotExistException | SnakeAlreadyInMapException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		th.setDaemon(true);
		th.start();
	}
}