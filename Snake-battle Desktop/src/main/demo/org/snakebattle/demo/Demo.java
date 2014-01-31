package org.snakebattle.demo;

import java.awt.Point;

import org.snakebattle.gui.ObjectAlreadyAddedException;
import org.snakebattle.gui.primitive.snake.Element;
import org.snakebattle.gui.primitive.snake.Element.PARTS;
import org.snakebattle.gui.screen.Screen;
import org.snakebattle.logic.BattleMap;
import org.snakebattle.logic.Snake;
import org.snakebattle.logic.SnakeAlreadyInMapException;
import org.snakebattle.utils.BattleMapUtils;
import org.snakebattle.utils.BattleMapUtils.MapAlreadyExistException;
import org.snakebattle.utils.BattleMapUtils.MapNotExistException;

public class Demo {

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
					Snake[] sn = { new Snake(), new Snake(),
							new Snake(), new Snake() };
					BattleMap m = DemoUtils.createAndInitBattleMap("battle", sn);
					DemoUtils.createFourSnakes(m);
					
					int waitTime = 50;

					// -------exemple: add element to mind
					Snake snake = new Snake();

					Element el = new Element(PARTS.HEAD, new Point(310, 270), 10, 10,
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

					DemoUtils.createSimpleMind(sn[0].getMind());

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