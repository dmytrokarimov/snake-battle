package org.snakebattle.gui.screen;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import org.snakebattle.gui.events.EventListener;
public interface IScreen {

	/**
	 * ����������� �� �������
	 * @param eventListener ������, ������� ������������� �� �������
	 */
	public void subscribe(EventListener eventListener);
	
	/**
	 * ���������� �� �������
	 * @param eventListener ������, ������� ������������ �� �������
	 */
	public void unSubscribe(EventListener eventListener);
	
	/**
	 * ������ ����� �� ������ ������ ��-���������
	 */
	public void draw(Point point);

	/**
	 * ������ ����� �� ������
	 */
	public void draw(Point point, Color color) ;

	/**
	 * ������ ������������� �� ������ ������ ��-���������
	 */
	public void draw(Rectangle r) ;

	/**
	 * ������ �������������
	 */
	public void draw(Rectangle r, Color color) ;

	/**
	 * ������� ����� �� ����� ������ ��-���������
	 */
	public void draw(Point p, String text) ;

	/**
	 * ������� ����� �� �����
	 */
	public void draw(Point p, String text, Color color) ;

	public void repaint() ;

	public void clear(Point p, int width, int height) ;

	public void clear(Rectangle r) ;

	public void setScreen(String name) ;

	public void setScreen(org.snakebattle.logic.BattleMap battleMap) ;

	public boolean canExit() ;

	public boolean canDraw() ;

	/**
	 * This method calc screen size
	 */
	public int getWidth() ;

	/**
	 * This method calc screen size
	 */
	public int getHeight() ;
	
	public void fillRoundRect(int x, int y, int width, int height,
			int arcWidth, int arcHeight);

	public void setColor(Color color);
	
	public void drawRoundRect(int x, int y, int width, int height,
			int arcWidth, int arcHeight);
}