package gui;

import java.awt.Point;
import java.io.Serializable;

/**
 * Базовый интерфейс для графических элементов
 * @author Karimov
 *
 */
public interface Drawable extends Serializable{
	/**
	 *  вывод элемента на экран
	 * @return 
	 */
	public abstract void draw();
	
	/**
	 * проверка на то, находится ли точки внутри объекта
	 * @param p - точка, по которой ищется объект
	 * @return <b>true</b> если есть объект 
	 */
	public boolean pointAt(Point p);
	
	/**
	 * Точка, в которой данный элемент находится
	 */
	public void setCoord(Point p);
	
	/**
	 * Точка, в которой данный элемент находится
	 */
	public Point getCoord();
	
	public int getWidth();

	public void setWidth(int width);

	public int getHeight();

	public void setHeight(int height);
}
