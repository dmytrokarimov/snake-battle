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
					Common.registerMap(new Map("battle"));
					Map m = Common.selectMap("battle");

					Snake[] sn = new Snake[] { new Snake(), new Snake(),
							new Snake(), new Snake() };
					Battle b = new Battle();
					b.init(sn); // Инициализация змеек теперь есть в Battle
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
					el = new Element(PARTS.HEAD, new Point(10, 0), 10,
							10, snake);
					snake.addElement(el);
					for (i = 2; i < 5; i++) {
						el = new Element(PARTS.BODY, new Point(i * 10, 0), 10,
								10, snake);
						snake.addElement(el);
					}
					el = new Element(PARTS.TAIL, new Point(i * 10, 0), 10, 10,
							snake);
					snake.addElement(el);
					snake.moveTo(350, 300);
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
					int waitTime = 100;
					while (true) {
						long timeold = System.currentTimeMillis();
						for (i = 0; i < sn.length; i++) {
							Action a = sn[i].getMind().getAction(m);
							if (a.getType() == ACTION_TYPE.EAT_TAIL) {
								Element head = sn[i].getElements().get(0);
								if (head.getPart() != PARTS.HEAD)
									throw new HeadNoFirstException();
								int w = head.getWidth();
								int h = head.getHeight();
								int x = sn[i].getCoord().x;// + w / 2;
								int y = sn[i].getCoord().y;// + h / 2;
								Dummy headL = new Dummy(new Point(x - w, y), w,
										h);
								Dummy headR = new Dummy(new Point(x + w, y), w,
										h);
								Dummy headU = new Dummy(new Point(x, y - h), w,
										h);
								Dummy headD = new Dummy(new Point(x, y + h), w,
										h);

								if (m.getObject(headD.getCoord()) instanceof Element) {
									Element e = (Element) m.getObject(headD
											.getCoord());
									if (e.getPart() == PARTS.TAIL
											&& e.getSnake() != sn[i])
										a.doAction(sn[i], e.getSnake());
								}

								if (m.getObject(headL.getCoord()) instanceof Element) {
									Element e = (Element) m.getObject(headL
											.getCoord());
									if (e.getPart() == PARTS.TAIL
											&& e.getSnake() != sn[i])
										a.doAction(sn[i], e.getSnake());
								}

								if (m.getObject(headU.getCoord()) instanceof Element) {
									Element e = (Element) m.getObject(headU
											.getCoord());
									if (e.getPart() == PARTS.TAIL
											&& e.getSnake() != sn[i])
										a.doAction(sn[i], e.getSnake());
								}

								if (m.getObject(headR.getCoord()) instanceof Element) {
									Element e = (Element) m.getObject(headR
											.getCoord());
									if (e.getPart() == PARTS.TAIL
											&& e.getSnake() != sn[i])
										a.doAction(sn[i], e.getSnake());
								}
							} else
								a.doAction(sn[i]);
						}
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