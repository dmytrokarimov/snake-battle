package gui;

import java.awt.Point;

/**
 * Базовый интерфейс для графических элементов
 * @author Karimov
 *
 */
public interface Drawable {
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
	public Point getCoord();
}
