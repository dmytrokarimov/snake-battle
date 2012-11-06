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
		Map<Graph> m = new Map<Graph>("battle");
		Snake sn = new Snake();
		Element el = new Element(PARTS.HEAD, new Point(10, 0), 10, 10, sn);
		sn.addElements(el);
		int i;
		for (i = 2; i < 30; i++) {
			el = new Element(PARTS.BODY, new Point(i * 10, 0), 10, 10, sn);
			sn.addElements(el);
		}
		el = new Element(PARTS.TAIL, new Point(i * 10, 0), 10, 10, sn);
		sn.addElements(el);
		sn.moveTo(100, 100);
		m.putSnake(sn);
		m.drawAll();
		int waitTime = 500;
		while (true) {
			for (i = 0; i < 10; i++) {
				long timeold = System.currentTimeMillis();
				ActionFactory.getUp().doAction(sn);
				Screen.instance.repaint();
				//m.drawAll();
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
		}
	}
}
