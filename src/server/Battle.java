package server;

import gui.Common;
import gui.Dummy;
import gui.ObjectAlreadyAddedException;
import gui.Common.ActionList;
import gui.Common.MapAlreadyExistException;
import gui.Common.MapNotExistException;
import gui.Element;
import gui.Element.PARTS;
import gui.Screen;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import logic.Action.ACTION_TYPE;
import logic.Map;
import logic.Snake;
import logic.SnakeAlreadyInMapException;

/**
 * ����� ��� ������������� ����� � ������� � ������
 * @author RED Developer
 */
public class Battle {
	// ����� ������ !!!!!������� ������ (���������� ���-�� ��������)!!!!!!
	private int centerX = 400;//Screen.instance.getWidth() / 2;
	private int centerY = 300;//Screen.instance.getHeight() / 2;
	// ��������� ������� ����� �� ������ ���� ����� (�������� ��  ������� 1 �������� ������)
	private int deltaX = 10;
	private int deltaY = 10;
	
	// � ����� ��������� ������� �� snakeLimit �����
	private final byte snakeLimit = 4;
	// ���������� ����� �� ����� (��� ��� ��������� ��� ������������� �� ����-����)
	private final int timeLimit = 60000;	// 60c
	// ���������� ���������� ����� �� �����
	private final int stepsLimit = 1000;
	// ���������� ����� � ������
	private byte snakeCount = 0;
	// ��������� ����� ������ ������
	private final byte snakeLength = 20;
	// ��������� ���������� 0-� ������ (������������ ������)
	private Point snakeStart = new Point(0, -100);
	// ������ ������ �����
	private final Point snakeSize = new Point (10, 10);
	// �������� �������� ��� ��������� ������� �����
	private int headX = -1, headY = -1;
	// �������� �������� ��� ��������� ������� ��������� ���� (� ������)
	private int bodyX = 0, bodyY = 0;
	// �����, �� ������� ���������� �����
	private static Map map;
	
	/**
	 * �������� ������������ ���������� ������������ ������ ������
	 * @param x
	 * @param y
	 * @return Point
	 */
	private Point normal(int x, int y)
	{
		// ���������� ������ ������
		return new Point(x + centerX, y + centerY);
	}
	/**
	 * �������� ������������ ���������� ������������ ������ ������
	 * @param coord
	 * @param screen
	 * @return Point
	 */
	private Point normal(Point coord)
	{
		// ���������� ������ ������
		return new Point(coord.x + centerX, coord.y + centerY);
	}
	/**
	 * ������������ ������� ���������� �� 90 ��������
	 * @param coord
	 * @return Point
	 */
	private Point coordCorrect(Point coord)
	{
		if (coord.x != 0) headX *= -1;
		if (coord.y != 0) headY *= -1;
		Point p = new Point (coord.x + headX * deltaX * snakeSize.x, coord.y + headY * deltaY * snakeSize.y);
		
		return p;
	}
	
	/**
	 * �������������� ������
	 * @param map
	 * 				- �����, �� ������ ����� ����������� �����
	 * @param snakes
	 * 				- ������, ���������� �� ���
	 * @throws MapAlreadyExistException 
	 * @throws MapNotExistException 
	 */
	public void init(Map map, Snake[] snakes) throws MapAlreadyExistException, MapNotExistException{	
		this.map = map;
		// ���������� �����, ���������� �� ��� 
		byte snakesCount = (byte) snakes.length;
		// �������� ���������� ����� � ������
		for (byte i = 0; i < snakesCount; i++)
			if (snakes[i] != null) snakeCount++;
		// ������� �����
		byte iSnake = 0;
		// ��� �������� ��������� �����
		List<Element> el = new ArrayList<Element>();;
		
		// ������������� �����
		while (iSnake < snakeLimit && iSnake < snakesCount)
		{
			snakeStart = coordCorrect(snakeStart);
			// ���� ���� 2 - ��������� ���� �������� ����� 
			if (snakeCount == 2) snakeStart = coordCorrect(snakeStart);
			
			if (snakes[iSnake] != null) {
				// ������������� ����, ���� ��� ���� ��� �������
				snakes[iSnake].getElements().clear();
				// ����� ������ ��� ������ iSnake. "head? * snakeSize.?" - ����������� �����������
				el.add(new Element(PARTS.HEAD, normal(snakeStart.x,
						snakeStart.y), snakeSize.x, snakeSize.y, snakes[iSnake]));

				// � ����� ������� ����������� ����
				if (snakeStart.x < 0 && snakeStart.y == 0) {
					// �����
					bodyX = -1;
					bodyY = 0;
				} else if (snakeStart.x > 0 && snakeStart.y == 0) {
					// ������
					bodyX = 1;
					bodyY = 0;
				} else if (snakeStart.x == 0 && snakeStart.y < 0) {
					// �����
					bodyX = 0;
					bodyY = -1;
				} else if (snakeStart.x == 0 && snakeStart.y > 0) {
					// ����
					bodyX = 0;
					bodyY = 1;
				}
				// ������ ���� � �����
				for (byte i = 1; i < snakeLength - 1; i++) {
					el.add(new Element(PARTS.BODY, new Point(
							el.get(i - 1).getCoord().x + snakeSize.x * bodyX, 
							el.get(i - 1).getCoord().y + snakeSize.y * bodyY), 
							snakeSize.x, snakeSize.y, snakes[iSnake]));
				}
				el.add(new Element(PARTS.TAIL, new Point(
						el.get(snakeLength - 2).getCoord().x + snakeSize.x * bodyX, 
						el.get(snakeLength - 2).getCoord().y + snakeSize.y * bodyY), 
						snakeSize.x, snakeSize.y, snakes[iSnake]));

				// ���������� ��������� � ������
				snakes[iSnake].setElements(el);
				// ��� ����� ������ ����� ����� ������ ���������
				el.clear();
			}
			// ��������� ����
			iSnake++;
		}
	}
	
	/**
	 * �������� �� ������� ��������� ���
	 * @param snake
	 * @param time
	 * @param steps
	 * @return
	 */
	private boolean Stop(Snake[] snake, int time, int steps){
		// ���� ����� ��� ���� && ���� �������� ��� �� ������ ����������� ����������
		if (time < timeLimit && steps < stepsLimit)
			// ���� ���� ������� ���� ���� ������
			for (int i = 0; i < snake.length; i++)
				if (snake[i].getMind().getAction(map).action.getType() != ACTION_TYPE.IN_DEAD_LOCK)
					return false;
		
		return true;
	}
	/**
	 * �������� ������� ����� � ���������� �� ���
	 * @param snake
	 * @return List of Actions
	 */
	/*protected*/public List<ActionList> battleCalc(Snake[] snakes){
		// ��� ������� ����� (�������� �����)
		List<ActionList> actions = new ArrayList<ActionList>();
		// ������� ������� ��� ������
		int timeElapsed = 0;
		// ������� ����� �������
		int stepsPassed = 0;
		
		// ���� ����� ��� - ������ ��� ��������
		while(!Stop(snakes, timeElapsed, stepsPassed))
		{
			for (ActionList al : Common.doStep(map.getName(), snakes))
				actions.add(al);
			// ����������� �������� �����
			System.out.println(stepsPassed++);
		}
		
		return actions;
	}
	// =====================================================================
	// ===========================��� ������������==========================
	// =====================================================================
	public static void main(String[] args) throws ObjectAlreadyAddedException,
			InterruptedException, SnakeAlreadyInMapException {
		new Screen();
		
		while (!Screen.instance.canDraw())
			Thread.sleep(100);
		
		Thread th = new Thread() {
			public void run() {
				try {
					Battle b = new Battle();
					Snake snake0 = new Snake();
					Snake snake1 = new Snake();
					Snake snake2 = new Snake();
					Snake snake3 = new Snake();
					
					try{
						b.init(new Map("battle"), new Snake[] { snake0, snake1, snake2, snake3 });
					} 
					catch (MapAlreadyExistException | MapNotExistException e){
						e.printStackTrace();
					}
					
					Dummy d;
					int i;
					for (i = 0; i < 60; i++) {
						d = new Dummy(new Point(-10, i * 10), 10, 10);
						map.put(d);
					}

					for (i = 0; i < 60; i++) {
						d = new Dummy(new Point(810, i * 10), 10, 10);
						map.put(d);
					}

					for (i = 0; i < 80; i++) {
						d = new Dummy(new Point(i * 10, -10), 10, 10);
						map.put(d);
					}

					for (i = 0; i < 80; i++) {
						d = new Dummy(new Point(i * 10, 610), 10, 10);
						map.put(d);
					}

					for (i = 0; i < 60; i++) {
						d = new Dummy(new Point(10, i * 10), 10, 10);
						map.put(d);
					}			
					
					map.putSnake(snake0);
					map.putSnake(snake1);
					map.putSnake(snake2);
					map.putSnake(snake3);

					b.battleCalc(new Snake[] { snake0, snake1, snake2, snake3 });

					map.drawAll();
					
				} catch (ObjectAlreadyAddedException e) {
					e.printStackTrace();
				}
			}
		};
		th.setDaemon(true);
		th.start();
	}
}