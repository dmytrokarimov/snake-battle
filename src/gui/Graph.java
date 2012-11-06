package gui;

import java.awt.Point;

/**
 * Основной класс для вывода графики
 * @author Karimov
 */
public abstract class Graph implements Drawable{
	protected Point coord;
	protected int width;
	protected int height;
	
	/**
	 * Пока будут только квадратные модели
	 * @param coord верхняя левая точка
	 * @param width ширина
	 * @param height высота
	 */
	public Graph(Point coord, int width, int height){
		if(coord == null) coord = new Point(0,0);
		this.coord = coord;
		this.width = width;
		this.height = height;
	}
	

	
	/**
	 * проверка на то, находится ли точки внутри объекта
	 * @param p - точка, по которой ищется объект
	 * @return <b>true</b> если есть объект 
	 */
	public boolean pointAt(Point p){
		return (p.x > coord.x && p.x < coord.x + width) && (p.y > coord.y && p.y < coord.y + height);
	} 
	
	/**
	 * вызывается при коллизии с др. объектом
	 * @param obj с каким объектом произошла коллизия
	 * @return возвращает true если движение разрешено
	 */
	public abstract boolean onCollision(Graph obj);

	/**
	 * Точка, в которой данный элемент находится
	 */
	public Point getCoord() {
		return coord;
	}

	public void setCoord(Point coord) {
		if(coord == null) return;
		Screen.instance.clear(getCoord(), getWidth(), getHeight());
		this.coord = coord;
		draw();
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		Screen.instance.clear(getCoord(), getWidth(), getHeight());
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		Screen.instance.clear(getCoord(), getWidth(), getHeight());
		this.height = height;
	} 
	
	
}
