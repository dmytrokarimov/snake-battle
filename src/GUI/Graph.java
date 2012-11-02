package GUI;

import java.awt.Point;

/**
 * Основной класс для вывода графики
 * @author Karimov
 */
public abstract class Graph {
	private Point coord;
	private int width;
	private int height;
	
	/**
	 * Пока будут только квадратные модели
	 * @param coord верхняя левая точка
	 * @param width ширина
	 * @param height высота
	 */
	public Graph(Point coord, int width, int height){
		this.coord = coord;
		this.width = width;
		this.height = height;
	}
	
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
	public boolean pointAt(Point p){
		return (p.x >= coord.x && p.x <= coord.x + width) && (p.y >= coord.y && p.y <= coord.y + height);
	} 
	
	/**
	 * вызывается при коллизии с др. объектом
	 * @param obj с каким объектом произошла коллизия
	 */
	abstract void onCollision(Graph obj);

	/**
	 * Точка, в которой данный элемент находится
	 */
	public Point getCoord() {
		return coord;
	}

	public void setCoord(Point coord) {
		this.coord = coord;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	} 
	
	
}
