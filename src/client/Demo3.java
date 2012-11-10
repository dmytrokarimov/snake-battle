package client;

import java.awt.Point;
import java.util.List;

import server.Battle;

import logic.Map;
import logic.Snake;
import logic.SnakeAlreadyInMapException;

import gui.*;
import gui.Common.ActionList;
import gui.Common.MapAlreadyExistException;
import gui.Common.MapNotExistException;

public class Demo3 {
	private static final boolean showEnd = true;
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
					Battle b = new Battle();
					Common.registerMap(new Map("battle"));
					Map map = Common.selectMap("battle");
					
					Common.registerMap(new Map("battle_show"));
					Map map_show = Common.selectMap("battle_show");
					
					Snake snake0 = new Snake();
					Snake snake1 = new Snake();
					Snake snake2 = new Snake();
					Snake snake3 = new Snake();
					Snake snakes[] = new Snake[] { snake0, snake1, snake2, snake3 };
					try{
						b.init(map, snakes);
					} 
					catch (MapAlreadyExistException | MapNotExistException e){
						e.printStackTrace();
					}
					
					Dummy d;
					int i;
					for (i = 0; i < 60; i++) {
						d = new Dummy(new Point(-10, i * 10), 10, 10);
						map.put(d);
						map_show.put(d);
					}

					for (i = 0; i < 60; i++) {
						d = new Dummy(new Point(810, i * 10), 10, 10);
						map.put(d);
						map_show.put(d);
					}

					for (i = 0; i < 80; i++) {
						d = new Dummy(new Point(i * 10, -10), 10, 10);
						map.put(d);
						map_show.put(d);
					}

					for (i = 0; i < 80; i++) {
						d = new Dummy(new Point(i * 10, 610), 10, 10);
						map.put(d);
						map_show.put(d);
					}

					for (i = 0; i < 60; i++) {
						d = new Dummy(new Point(10, i * 10), 10, 10);
						map.put(d);
						map_show.put(d);
					}			
					
					map.putSnake(snake0);
					map.putSnake(snake1);
					map.putSnake(snake2);
					map.putSnake(snake3);

					List<ActionList> calcBattle = b.battleCalc(new Snake[] { snake0, snake1, snake2, snake3 });
					int waitTime = 5;
					try {
						b.init(map, snakes);
						map_show.putSnake(snake0);
						map_show.putSnake(snake1);
						map_show.putSnake(snake2);
						map_show.putSnake(snake3);
					} catch (MapAlreadyExistException e) {
						e.printStackTrace();
					}
					
					// Воспроизведение битвы
					for (ActionList al : calcBattle)
					{
						long timeold = System.currentTimeMillis();
						al.action.doAction(al.param);
						if (!showEnd) map_show.drawAll();
						else map.drawAll();
						long timenow = System.currentTimeMillis() - timeold;
						if (waitTime - timenow > 0)
							Thread.sleep(waitTime - timenow);
					} 
				} catch (InterruptedException | ObjectAlreadyAddedException
						| MapNotExistException | MapAlreadyExistException e) {
					e.printStackTrace();
				}
			}
		};
		th.setDaemon(true);
		th.start();
	}
}