package org.snakebattle.gui.screen;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import org.snakebattle.gui.events.EventListener;
public interface IScreen {

	/**
	 * Подписаться на события
	 * @param eventListener объект, который подписывается на события
	 */
	public void subscribe(EventListener eventListener);
	
	/**
	 * Отписаться от события
	 * @param eventListener объект, который отписывается от событий
	 */
	public void unSubscribe(EventListener eventListener);
	
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
	
	public void fillRoundRect(int x, int y, int width, int height,
			int arcWidth, int arcHeight);

	public void setColor(Color color);
	
	public void drawRoundRect(int x, int y, int width, int height,
			int arcWidth, int arcHeight);
}