package org.snakebattle.logic;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.snakebattle.gui.ObjectAlreadyAddedException;
import org.snakebattle.gui.primitive.snake.Element;
import org.snakebattle.gui.primitive.snake.Element.PARTS;
import org.snakebattle.utils.BattleMapUtils;
import org.snakebattle.utils.BattleMapUtils.MapNotExistException;

/**
 * Описывает возможные действия со змейкой
 * 
 * @author Karimov
 * 
 */
public class Snake implements Player, Serializable, Cloneable{
	private static final long serialVersionUID = 1L;

	private Map<PARTS, Color> colorTheme;
	
	public class ArrayList extends java.util.ArrayList<Element> {
		private static final long serialVersionUID = 2365497221591029860L;

		public boolean add(Element element) {
			try {
				if (snakeInMap) {
					//String name = BattleMapUtils.std_map.getName();
					//BattleMapUtils.selectMap(mapName);
					BattleMap battleMap = BattleMapUtils.selectMap(mapName);;
					battleMap.put(element);
					//BattleMapUtils.selectMap(name);
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
					BattleMap battleMap = BattleMapUtils.selectMap(mapName);;
					battleMap.put(element);

					/*String name = BattleMapUtils.std_map.getName();
					BattleMapUtils.selectMap(mapName);
					BattleMapUtils.std_map.put(element);
					BattleMapUtils.selectMap(name);*/
				}
				super.add(index, element);
			} catch (MapNotExistException | ObjectAlreadyAddedException e) {
				e.printStackTrace();
			}
		}

		public Element remove(int index) {
			try {
				if (snakeInMap) {
					BattleMap battleMap = BattleMapUtils.selectMap(mapName);;
					battleMap.remove(get(index));
/*
					String name = BattleMapUtils.std_map.getName();
					BattleMapUtils.selectMap(mapName);
					BattleMapUtils.std_map.remove(get(index));
					BattleMapUtils.selectMap(name);
					*/
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
					BattleMap battleMap = BattleMapUtils.selectMap(mapName);;
					battleMap.remove(o);
					/*
					String name = BattleMapUtils.std_map.getName();
					BattleMapUtils.selectMap(mapName);
					BattleMapUtils.std_map.remove(o);
					BattleMapUtils.selectMap(name);*/
				}
				return super.remove(o);
			} catch (MapNotExistException e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	private List<Element> elements;
	private SnakeMind mind;
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
		mind = new SnakeMind(this);
		colorTheme = new HashMap<>();
		colorTheme.put(PARTS.BODY, Color.GRAY);
		colorTheme.put(PARTS.HEAD, Color.red);
		colorTheme.put(PARTS.TAIL, Color.DARK_GRAY);
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

	public void setMind(SnakeMind mind) {
		this.mind = mind;
	}

	public SnakeMind getMind() {
		return mind;
	}

	public boolean isSnakeInMap() {
		return snakeInMap;
	}

	public void setSnakeInMap(boolean snakeInMap, String mapName) {
		this.snakeInMap = snakeInMap;
		this.mapName = mapName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((elements == null) ? 0 : elements.hashCode());
		result = prime * result + ((mapName == null) ? 0 : mapName.hashCode());
		result = prime * result + ((mind == null) ? 0 : mind.hashCode());
		result = prime * result + (snakeInMap ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Snake)) {
			return false;
		}
		Snake other = (Snake) obj;
		if (elements == null) {
			if (other.elements != null) {
				return false;
			}
		} else if (!elements.equals(other.elements)) {
			return false;
		}
		if (mapName == null) {
			if (other.mapName != null) {
				return false;
			}
		} else if (!mapName.equals(other.mapName)) {
			return false;
		}
		if (mind == null) {
			if (other.mind != null) {
				return false;
			}
		} else if (!mind.equals(other.mind)) {
			return false;
		}
		if (snakeInMap != other.snakeInMap) {
			return false;
		}
		return true;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		Snake sn = new Snake();
		sn.elements = new ArrayList();
		for (int i = 0; i < this.elements.size(); i++){
			sn.elements.add((Element) this.elements.get(i).clone());
		}
		sn.mapName = new String(this.mapName);
		sn.mind = (SnakeMind) this.mind.clone();
		sn.snakeInMap = this.snakeInMap;
		return sn;
	}

	@Override
	public boolean isPlayerCanTurn() {
		return getElements().size() < 2;
	}

	public Map<PARTS, Color> getColorTheme() {
		return colorTheme;
	}

	public void setColorTheme(Map<PARTS, Color> colorTheme) {
		this.colorTheme = colorTheme;
	}
}