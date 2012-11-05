package client;

import java.awt.Color;
import java.awt.Point;

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
	public static void main(String[] args) throws ObjectAlreadyAddedException, InterruptedException, SnakeAlreadyInMapException {
		new Screen();
		while (!Screen.instance.canDraw())
			Thread.sleep(100);
		Map<Graph> m = new Map<Graph>("battle");
		Snake sn = new Snake();
		Element el = new Element(PARTS.HEAD, new Point(10, 0),
				10, 10, sn);
		sn.addElements(el);
		el = new Element(PARTS.BODY, new Point(20, 0), 10, 10, sn);
		sn.addElements(el);
		el = new Element(PARTS.TAIL, new Point(30, 0), 10, 10, sn);
		sn.addElements(el);
		sn.moveTo(100, 100);
		m.putSnake(sn);
		m.drawAll();
	}
}
