package org.snakebattle.client;

import java.awt.Point;

import org.snakebattle.gui.ObjectAlreadyAddedException;
import org.snakebattle.gui.controls.NiceButton;
import org.snakebattle.gui.screen.Screen;
import org.snakebattle.logic.BattleMap;
import org.snakebattle.logic.Snake;
import org.snakebattle.logic.SnakeAlreadyInMapException;
import org.snakebattle.server.Battle;
import org.snakebattle.utils.BattleMapUtils;
import org.snakebattle.utils.BattleMapUtils.MapAlreadyExistException;
import org.snakebattle.utils.BattleMapUtils.MapNotExistException;

public class DemoButtons {
	static Snake[] sn = { new Snake(), new Snake(),
			new Snake(), new Snake() };
	static String mapName = "battle";
	static BattleMap m;
	static volatile boolean enableBattle = false;
	
	public static void main(String[] args) throws InterruptedException,
			MapAlreadyExistException, MapNotExistException,
			ObjectAlreadyAddedException {
		Screen.GRAPHICS_ON = true;
		new Screen();
		while (!Screen.instance.canDraw())
			Thread.sleep(100);

		//Создаем кнопку, а в обработчике создаем новую игру
		NiceButton niceButton = new NiceButton("New game", new Point(300, 300), 100, 30) {
			
			private static final long serialVersionUID = 800384528686843000L;

			@Override
			public void onMouseClick(int x, int y) {
				m.removeAll();
				try {
					DemoUtils.fillBatleMap(m, sn);
					for (Snake snake : sn) {
						DemoUtils.createSimpleMind(snake.getMind());
					}
					DemoUtils.createFourSnakes(m);
					enableBattle = true;
				} catch ( ObjectAlreadyAddedException | SnakeAlreadyInMapException e) {
					e.printStackTrace();
				}
			}
		};

		NiceButton niceButton2 = new NiceButton("Exit", new Point(300, 350), 100, 30) {
			
			private static final long serialVersionUID = 800384528686843000L;

			@Override
			public void onMouseClick(int x, int y) {
				System.exit(0);
			}
		};
		Battle b = new Battle();
		b.init(mapName, sn);

		m = BattleMapUtils.selectMap(mapName);

		m.put(niceButton);
		m.put(niceButton2);

		Thread th = new Thread() {
			public void run() {
				int waitTime = 50;

				while (true) {
					long timeold = System.currentTimeMillis();
					if (enableBattle){
						BattleMapUtils.doStep(m.getName(), sn);
					}
					m.drawAll();
					long timenow = System.currentTimeMillis() - timeold;
					if (waitTime - timenow > 0)
						try {
							Thread.sleep(waitTime - timenow);
						} catch (InterruptedException e) {
						}
				}
			};
		};
		th.setDaemon(true);
		th.start();
	}
}
