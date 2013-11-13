package org.snakebattle.gui.screen;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
public interface IScreen {

	/**
	 * Рисует точку на экране цветом по-умолчанию
	 */
	public void draw(Point point);

	/**
	 * Рисует точку на экране
	 */
	public void draw(Point point, Color color) ;

	/**
	 * Рисует прямоугольник на экране цветом по-умолчанию
	 */
	public void draw(Rectangle r) ;

	/**
	 * Рисует прямоугольник
	 */
	public void draw(Rectangle r, Color color) ;

	/**
	 * Выводит текст на экран цветом по-умолчанию
	 */
	public void draw(Point p, String text) ;

	/**
	 * Выводит текст на экран
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
}