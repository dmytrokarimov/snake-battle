package logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gui.*;
import gui.Common.MapNotExistException;

/**
 * Описывает возможные действия со змейкой
 * 
 * @author Karimov
 * 
 */
public class Snake {
	public class ArrayList extends java.util.ArrayList<Element> {
		public boolean add(Element element) {
			try {
				if (snakeInMap) {
					String name = Common.std_map.getName();
					Common.selectMap(mapName);
					Common.std_map.put(element);
					Common.selectMap(name);
				}
				return super.add(element);
			} catch (MapNotExistException | ObjectAlreadyAddedException e) {
				e.printStackTrace();
				return false;
			}
		}

		public void add(int index, Element element) {
			try {
				if (snakeInMap) {
					String name = Common.std_map.getName();
					Common.selectMap(mapName);
					Common.std_map.put(element);
					Common.selectMap(name);
				}
				super.add(index, element);
			} catch (MapNotExistException | ObjectAlreadyAddedException e) {
				e.printStackTrace();
			}
		}

		public Element remove(int index) {
			try {
				if (snakeInMap) {
					String name = Common.std_map.getName();
					Common.selectMap(mapName);
					Common.std_map.remove(get(index));
					Common.selectMap(name);
				}
				return super.remove(index);
			} catch (MapNotExistException e) {
				e.printStackTrace();
				return null;
			}
		}

		public boolean remove(Element o) {
			try {
				if (snakeInMap) {
					String name = Common.std_map.getName();
					Common.selectMap(mapName);
					Common.std_map.remove(o);
					Common.selectMap(name);
				}
				return super.remove(o);
			} catch (MapNotExistException e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	private List<Element> elements;
	private Mind mind;
	private boolean snakeInMap = false;
	private String mapName = "";

	public List<Element> getElements() {
		return elements;
	}

	/**
	 * Задаёт составные элементы змейки изменено Олегом, т.к. необходимо
	 * фактически изменять состав элементов, а не получать ссылку на них
	 * 
	 * @param elements
	 */
	public void setElements(List<Element> elements) {
		// this.elements = elements;
		this.elements.clear();
		for (int i = 0; i < elements.size(); i++)
			this.elements.add(elements.get(i));
	}

	public Snake() {
		elements = new ArrayList();
		mind = new Mind(this);
	}

	/**
	 * Возвращает координату головы
	 * 
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
	 * 
	 * @throws SnakeAlreadyInMapException
	 *             В случае если змейка уже добавлена на карту (двигать её можно
	 *             после этого только с помощью Action
	 */
	public void moveTo(int dx, int dy) throws SnakeAlreadyInMapException {
		if (isSnakeInMap())
			throw new SnakeAlreadyInMapException();
		for (int i = 0; i < elements.size(); i++) {
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

	public boolean addElement(Element element) {
		return this.elements.add(element);
	}

	public void addElement(int index, Element element) {
		this.elements.add(index, element);
	}

	public boolean removeElement(Element element) {
		return this.elements.remove(element);
	}

	public Element removeElement(int element) {
		return this.elements.remove(element);
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

	public void setSnakeInMap(boolean snakeInMap, String mapName) {
		this.snakeInMap = snakeInMap;
		this.mapName = mapName;
	}
}