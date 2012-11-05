package logic;


import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gui.*;

/**
 * Описывает возможные действия со змейкой
 * @author Karimov
 *
 */
public class Snake {
	private List<Element> elements;
	public List<Element> getElements() {
		return elements;
	}
	public void setElements(List<Element> elements) {
		this.elements = elements;
	}
	private Mind mind;
	private boolean snakeInMap = false;
	
	public Snake() {
		elements = new ArrayList<Element>();
		mind = new Mind();
	}
	/**
	 * Возвращает координату головы
	 * @return
	 */
	public Point getCoord() {
		for (int i = 0; i < elements.size(); i++)
			if (elements.get(i).getPart() == Element.PARTS.HEAD)
				return elements.get(i).getCoord();
		return null;
	}
	
	/**
	 * Передвигает змейку на координаты (dx;dy). 
	 * @throws SnakeAlreadyInMapException В случае если змейка уже добавлена на карту (двигать её можно после этого только с помощью Action
	 */
	public void moveTo(int dx, int dy) throws SnakeAlreadyInMapException{
		if (isSnakeInMap())
			throw new SnakeAlreadyInMapException();
		for (int i = 0; i < elements.size(); i++)
			{
				Point p = elements.get(i).getCoord();
				p.x = p.x + dx;
				p.y = p.y + dy;
			}
	}
	
	public Iterator<Element> iterator() {
		return elements.iterator();
	}

	public void setElements(Element[] elements) {
		this.elements.clear();
		for (int i = 0; i < elements.length; i++)
			this.elements.add(elements[i]);
	}

	public void clearElements() {
		this.elements.clear();
	}

	public void addElements(Element elements) {
		this.elements.add(elements);
	}

	public void removeElements(Element elements) {
		this.elements.remove(elements);
	}

	public void removeElements(int elements) {
		this.elements.remove(elements);
	}

	
	public void setMind(Mind mind) {
		this.mind = mind;
	}
	public Mind getMind() {
		return mind;
	}
	public boolean isSnakeInMap() {
		return snakeInMap;
	}
	public void setSnakeInMap(boolean snakeInMap) {
		this.snakeInMap = snakeInMap;
	}
}