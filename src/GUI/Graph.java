package GUI;

import java.awt.Point;

/**
 * Основной класс для вывода графики
 * @author Karimov
 */
public abstract class Graph {
	/**
	 *  вывод элемента на экран
	 * @return 
	 */
	abstract void draw();
	
	/**
	 * проверка на то, находится ли точки внутри объекта
	 * @param p - точка, по которой ищется объект
	 * @return <b>true</b> если есть объект 
	 */
	boolean pointAt(Point p){
		return false;
	} 
	
	/**
	 * вызывается при коллизии с др. объектом
	 * @param obj с каким объектом произошла коллизия
	 */
	abstract void onCollision(Graph obj); 
	
	/**
	 * Точка, в которой данный элемент находится
	 * @return
	 */
	Point getCoord(){
		return null;
	}
	
	void setCoord(Point p){
		
	}
}
