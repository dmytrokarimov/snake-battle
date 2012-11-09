package client;

import java.awt.Point;
import server.Battle;

import logic.Action;
import logic.Action.ACTION_TYPE;
import logic.HeadNoFirstException;
import logic.Map;
import logic.Snake;
import logic.SnakeAlreadyInMapException;

import gui.*;
import gui.Common.MapAlreadyExistException;
import gui.Common.MapNotExistException;
import gui.Element.PARTS;

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
					//Common.registerMap(new Map("battle"));
					

					Snake[] sn = new Snake[] { new Snake(), new Snake(),
							new Snake(), new Snake() };
					Battle b = new Battle();
					b.init(sn); // Инициализация змеек теперь есть в Battle
					Map m = Common.selectMap("battle");
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
					while (true) {
						long timeold = System.currentTimeMillis();
						Common.doStep(sn);
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