package client;

import java.awt.Color;
import java.awt.Point;

import logic.ActionFactory;
import logic.Map;
import logic.Snake;
import logic.SnakeAlreadyInMapException;

import gui.*;
import gui.Element.PARTS;

public class Demo {

	/**
	 * ������ ����������� ��������� � ������� ��������
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
					Map m = new Map("battle");
					Snake sn = new Snake();
					Element el = new Element(PARTS.HEAD, new Point(10, 0), 10,
							10, sn);
					
					sn.addElements(el);
					int i;
					for (i = 2; i < 39; i++) {
						el = new Element(PARTS.BODY, new Point(i * 10, 0), 10,
								10, sn);
						sn.addElements(el);
					}
					el = new Element(PARTS.TAIL, new Point(i * 10, 0), 10, 10,
							sn);
					sn.addElements(el);
					sn.moveTo(100, 100);
					m.putSnake(sn);
					
					Dummy d;
					for (i = 0; i < 60; i++) {
						d = new Dummy(new Point(-10, i*10), 10, 10);
						m.put(d);
					}
					
					for (i = 0; i < 60; i++) {
						d = new Dummy(new Point(810, i*10), 10, 10);
						m.put(d);
					}
					
					for (i = 0; i < 80; i++) {
						d = new Dummy(new Point(i*10, -10), 10, 10);
						m.put(d);
					}
					
					for (i = 0; i < 80; i++) {
						d = new Dummy(new Point(i*10, 610), 10, 10);
						m.put(d);
					}

					m.drawAll();
					int waitTime = 100;
					while (true) {
						long timeold = System.currentTimeMillis();
						sn.getMind().getAction(m).doAction(sn);
						m.drawAll();
						long timenow = System.currentTimeMillis() - timeold;
						if (waitTime - timenow > 0)
							Thread.sleep(waitTime - timenow);
						/*
						for (i = 0; i < 10; i++) {
							long timeold = System.currentTimeMillis();
							ActionFactory.getUp().doAction(sn);
							//Screen.instance.repaint();
							m.drawAll();
							long timenow = System.currentTimeMillis() - timeold;
							if (waitTime - timenow > 0)
								Thread.sleep(waitTime - timenow);
						}
						for (i = 0; i < 10; i++) {
							long timeold = System.currentTimeMillis();
							ActionFactory.getRight().doAction(sn);
							m.drawAll();
							long timenow = System.currentTimeMillis() - timeold;
							if (waitTime - timenow > 0)
								Thread.sleep(waitTime - timenow);
						}
						for (i = 0; i < 10; i++) {
							long timeold = System.currentTimeMillis();
							ActionFactory.getDown().doAction(sn);
							m.drawAll();
							long timenow = System.currentTimeMillis() - timeold;
							if (waitTime - timenow > 0)
								Thread.sleep(waitTime - timenow);
						}
						for (i = 0; i < 10; i++) {
							long timeold = System.currentTimeMillis();
							ActionFactory.getLeft().doAction(sn);
							m.drawAll();
							long timenow = System.currentTimeMillis() - timeold;
							if (waitTime - timenow > 0)
								Thread.sleep(waitTime - timenow);
						}
						*/
					}
				} catch (SnakeAlreadyInMapException | InterruptedException
						| ObjectAlreadyAddedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		th.setDaemon(true);
		th.start();
	}
}