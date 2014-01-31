package org.snakebattle.demo;

import java.awt.Point;

import org.snakebattle.gui.ObjectAlreadyAddedException;
import org.snakebattle.gui.primitive.snake.Element;
import org.snakebattle.gui.primitive.snake.MindPolyGraph;
import org.snakebattle.gui.primitive.snake.Element.PARTS;
import org.snakebattle.gui.primitive.snake.MindPolyGraph.LOGIC_TYPES;
import org.snakebattle.gui.primitive.snake.MindPolyGraph.OWNER_TYPES;
import org.snakebattle.logic.BattleMap;
import org.snakebattle.logic.Mind;
import org.snakebattle.logic.Snake;
import org.snakebattle.logic.SnakeAlreadyInMapException;
import org.snakebattle.logic.Mind.MindMap;
import org.snakebattle.server.Battle;
import org.snakebattle.utils.BattleMapUtils;
import org.snakebattle.utils.BattleMapUtils.MapAlreadyExistException;
import org.snakebattle.utils.BattleMapUtils.MapNotExistException;

public class DemoUtils {
	//Create simple mind - mind can hunt on snakes, but not very good
	public static void createSimpleMind(Mind mind){
		MindMap[] mm = mind.getMindMap();
		MindPolyGraph mpg = new MindPolyGraph(new Point(), 10, 10);
		mpg.setOwner(OWNER_TYPES.SNAKE);
		mpg.setValue(new Element(PARTS.HEAD, new Point(), 10, 10,
				null));
		mm[0].setAt(2, 2, mpg);

		mpg = new MindPolyGraph(new Point(), 10, 10);
		mpg.setOwner(OWNER_TYPES.ENEMY);
		mpg.setLogic(LOGIC_TYPES.OR);
		mpg.setValue(new Element(PARTS.TAIL, new Point(), 10, 10,
				null));
		mm[0].setAt(2, 1, mpg);

		mpg = new MindPolyGraph(new Point(), 10, 10);
		mpg.setOwner(OWNER_TYPES.ENEMY);
		mpg.setLogic(LOGIC_TYPES.OR);
		mpg.setValue(new Element(PARTS.TAIL, new Point(), 10, 10,
				null));
		mm[0].setAt(2, 0, mpg);

		/*
		 * mpg = new MindPolyGraph(new Point(), 10, 10);
		 * mpg.setOwner(OWNER_TYPES.ENEMY); mpg.setValue(new
		 * Element(PARTS.TAIL, new Point(), 10,10, null));
		 * mm[0].setAt(2, 0, mpg);
		 */

		MindMap mm1 = mind.getMindMap(1);
		mpg = new MindPolyGraph(new Point(), 10, 10);
		mpg.setOwner(OWNER_TYPES.SNAKE);
		mpg.setValue(new Element(PARTS.HEAD, new Point(), 10, 10,
				null));
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
	}
	
	/**
	 * Put snakes on the battleMap. Also set border.
	 * @param m
	 * @param sn
	 * @throws ObjectAlreadyAddedException
	 */
	public static void fillBatleMap(BattleMap m, Snake[] sn) throws ObjectAlreadyAddedException{
		for (int i = 0; i < sn.length; i++) {
			m.putSnake(sn[i]);
		}

		
		m.setBorder(800, 600);
	}
	
	/**
	 * Create new BattleMap, put snakes on the map and set border.
	 * @param mapName
	 * @param sn
	 * @return
	 * @throws MapAlreadyExistException
	 * @throws MapNotExistException
	 * @throws ObjectAlreadyAddedException
	 */
	public static BattleMap createAndInitBattleMap(String mapName, Snake[] sn) throws MapAlreadyExistException, MapNotExistException, ObjectAlreadyAddedException{
		Battle b = new Battle();
		b.init(mapName, sn);
							
		BattleMap m = BattleMapUtils.selectMap(mapName);

		fillBatleMap(m, sn);
		
		return m;
	}
	
	/**
	 * Creates 4 empty snakes
	 * @param m
	 * @throws SnakeAlreadyInMapException
	 * @throws ObjectAlreadyAddedException
	 */
	public static void createFourSnakes(BattleMap m) throws SnakeAlreadyInMapException, ObjectAlreadyAddedException{
		int i;
		
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
		el = new Element(PARTS.HEAD, new Point(10, 0), 10, 10,
				snake);
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
		el = new Element(PARTS.HEAD, new Point(0, 0), 10, 10, snake);
		snake.addElement(el);
		el = new Element(PARTS.BODY, new Point(0, 10), 10, 10,
				snake);
		snake.addElement(el);
		el = new Element(PARTS.TAIL, new Point(0, 20), 10, 10,
				snake);
		snake.addElement(el);
		snake.moveTo(350, 280);
		m.putSnake(snake);

		snake = new Snake();
		el = new Element(PARTS.HEAD, new Point(10, 0), 10, 10,
				snake);
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
		el = new Element(PARTS.HEAD, new Point(10, 0), 10, 10,
				snake);
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
		el = new Element(PARTS.HEAD, new Point(10, 0), 10, 10,
				snake);
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
		el = new Element(PARTS.HEAD, new Point(10, 0), 10, 10,
				snake);
		snake.addElement(el);
		for (i = 2; i < 16; i++) {
			el = new Element(PARTS.BODY, new Point(i * 10, 0), 10,
					10, snake);
			snake.addElement(el);
		}
		el = new Element(PARTS.TAIL, new Point(i * 10, 0), 10, 10,
				snake);
		snake.addElement(el);
		snake.moveTo(350 + 16 * 10, 210);
		m.putSnake(snake);

		m.drawAll();
	}
}
