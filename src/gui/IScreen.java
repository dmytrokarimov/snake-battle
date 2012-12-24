package gui;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JMenuBar;
import gui.Point;
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

	/**
	 * Выводит image на экран в точке p
	 */
	public void draw(Point p, BufferedImage image) ;

	public void repaint() ;

	public void setImage(BufferedImage image) ;

	/** Clears the entire image area by painting it with the current color. */
	public void clear(BufferedImage bi) ;

	public void clear(Point p, int width, int height) ;

	public void clear(Rectangle r) ;

	public void setScreen(String name) ;

	public void setScreen(logic.Map map) ;

	public JComponent getGui() ;

	public JMenuBar getMenuBar(boolean webstart) ;

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