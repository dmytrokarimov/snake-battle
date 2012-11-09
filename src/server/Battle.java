package server;

import gui.Element;
import gui.Element.PARTS;
import gui.Screen;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import logic.Action;
import logic.Action.ACTION_TYPE;
import logic.Map;
import logic.Snake;

/**
 * ����� ��� ������������� ����� � ������� � ������
 * @author RED Developer
 */
public class Battle {
	// ����� ������ !!!!!������� ������ (���������� ���-�� ��������)!!!!!!
	private int centerX = Screen.instance.getWidth() / 2;
	private int centerY = Screen.instance.getHeight() / 2;
	// ��������� ������� ����� �� ������ ���� ����� (�������� ��  ������� 1 �������� ������)
	private int deltaX = 10;
	private int deltaY = 10;
	
	// � ����� ��������� ������� �� snakeLimit �����
	private final byte snakeLimit = 4;
	// ���������� ����� �� ����� (��� ��� ��������� ��� ������������� �� ����-����)
	private final int timeLimit = 60000;	// 60c
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
	
	/**
	 * �������� ������������ ���������� ������������ ������ ������
	 * @param x
	 * @param y
	 * @return
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
	 * @return
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
	 * @param snake
	 * 				- ������, ���������� �� ���
	 */
	public void init(Snake[] snake){
		// ���������� �����, ���������� �� ��� 
		byte snakes = (byte) snake.length;
		// �������� ���������� ����� � ������
		for (byte i = 0; i < snakes; i++)
			if (snake[i] != null) snakeCount++;
		// ������� �����
		byte iSnake = 0;
		// ��� �������� ��������� �����
		List<Element> el = new ArrayList<Element>();;
		
		// ������������� �����
		while (iSnake < snakeLimit && iSnake < snakes)
		{
			snakeStart = coordCorrect(snakeStart);
			// ���� ���� 2 - ��������� ���� �������� ����� 
			if (snakeCount == 2) snakeStart = coordCorrect(snakeStart);
			
			if (snake[iSnake] != null) {
				// ����� ������ ��� ������ iSnake. "head? * snakeSize.?" - ����������� �����������
				el.add(new Element(PARTS.HEAD, normal(snakeStart.x,
						snakeStart.y), snakeSize.x, snakeSize.y, snake[iSnake]));

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
							snakeSize.x, snakeSize.y, snake[iSnake]));
				}
				el.add(new Element(PARTS.TAIL, new Point(
						el.get(snakeLength - 2).getCoord().x + snakeSize.x * bodyX, 
						el.get(snakeLength - 2).getCoord().y + snakeSize.y * bodyY), 
						snakeSize.x, snakeSize.y, snake[iSnake]));

				// ���������� ��������� � ������
				snake[iSnake].setElements(el);
				// ��� ����� ������ ����� ����� ������ ���������
				el.clear();
			}
			// ��������� ����
			iSnake++;
		}
	}
	
	/**
	 * �������� �� ��������� ���
	 * @param snake
	 * @param time
	 * @return
	 */
	private boolean Stop(Snake[] snake, int time){
		// ���� ���� ������� ���� ���� ������
		for (int i = 0; i < snake.length; i++)
			if (snake[i].getMind().getAction(new Map(null)).getType() != ACTION_TYPE.IN_DEAD_LOCK)
				return false;
		
		// ���� ����� ��� ����
		if (time < timeLimit)
			return false;
		
		return true;
	}
	/**
	 * �������������� ������ ����� � ���������� �� ����������
	 * @param snake
	 * @return List of Actions
	 */
	protected List<Action> Start(Snake[] snake){
		// ��� ������� ����� (�������� �����)
		List<Action> actions = new ArrayList<Action>();
		Timer t = new Timer();
		
		// ���� ����� ��� - ������ ��� ��������
		while(!Stop(snake, 0))
		{
			actions.add(snake[0].getMind().getAction(new Map(null)));
		}
		
		return actions;
	}
	// =====================================================================
	// ===========================��� ������������==========================
	// =====================================================================
	/*
	public static void main(String[] args){
		Battle b = new Battle();
		
		Snake snake0 = new Snake();
		Snake snake1 = new Snake();
		Snake snake2 = new Snake();
		Snake snake3 = new Snake();
		
		b.init(new Snake[]{snake0, snake1, snake2, snake3});
		System.out.println(snake0.getElements().get(0).getCoord().x + "; " + snake0.getElements().get(0).getCoord().y);
		System.out.println(snake1.getElements().get(0).getCoord().x + "; " + snake1.getElements().get(0).getCoord().y);
		if (snake2 != null) System.out.println(snake2.getElements().get(0).getCoord().x + "; " + snake2.getElements().get(0).getCoord().y);
		System.out.println(snake3.getElements().get(0).getCoord().x + "; " + snake3.getElements().get(0).getCoord().y);
	}*/
}
