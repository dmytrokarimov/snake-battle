package logic;

//import gui.Graph;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gui.*;

/**
 * Карта с различными объектами для вывода
 * 
 * @author Karimov
 */
public class Map implements Iterable<Drawable> {
	private String name;
	private List<Object> list;

	public Map(String name) {
		this.name = name;
		list = new ArrayList<Object>();
	}

	/**
	 * Ищет объект по координате
	 * 
	 * @param coord
	 *            координата объекта
	 * @return null если объекта нет или объект, если объект по заданным
	 *         координатам есть
	 */
	public Drawable getObject(Point coord) {
		Iterator<Drawable> it = iterator();
		while (it.hasNext()) {
			Drawable d = it.next();
			if (d.pointAt(coord))
				return it.next();
			if (d.getCoord().x == coord.x && d.getCoord().y == coord.y)
				return it.next();
		}
		return null;
	}

	public boolean checkExist(Drawable obj) {
		if (getObject(new Point(obj.getCoord().x + obj.getWidth() / 2,
				obj.getCoord().y + obj.getHeight() / 2)) != null)
			return true;
		return false;
	}

	/**
	 * Ложит объект в массив
	 * 
	 * @throws ObjectAlreadyAddedException
	 *             генерируется если по координатам объекта obj в массиве уже
	 *             есть объект
	 */
	public void put(Drawable obj) throws ObjectAlreadyAddedException {
		if (!checkExist(obj))
			list.add(obj);
		else
			throw new ObjectAlreadyAddedException("Coord:" + obj.getCoord());
	}

	/**
	 * Добавляет на карту змейку целеком (не транзакциональная операция - в
	 * случае ошибки элементы остаются)
	 * 
	 * @throws ObjectAlreadyAddedException
	 *             если хотя бы один элемент змейки конфликтует с "миром"
	 */
	public void putSnake(Snake obj) throws ObjectAlreadyAddedException {
		Iterator<Element> it = obj.iterator();
		while (it.hasNext())
			put((Drawable) it.next());
		obj.setSnakeInMap(true, name);
	}

	/**
	 * Добавляет на карту змейку целеком (транзакциональная операция - в случае
	 * ошибки элементы не добавляются)
	 * 
	 * @throws ObjectAlreadyAddedException
	 *             если хотя бы один элемент змейки конфликтует с "миром"
	 */
	public void putSnakeTransactional(Snake obj)
			throws ObjectAlreadyAddedException {
		Iterator<Element> it1 = obj.iterator();
		while (it1.hasNext())
			if (checkExist(it1.next()))
				throw new ObjectAlreadyAddedException("Coord:" + it1.next().getCoord());
		putSnake(obj);
		Iterator<Element> it = obj.iterator();
	}
	
	public Iterator<Drawable> iterator() {
		return new Iterator<Drawable>() {
			private int pos = -1;

			public void remove() {
				list.remove(list.size() - 1);
			}

			public Drawable next() {
				if (pos >= list.size())
					throw new ArrayIndexOutOfBoundsException();
				return (Drawable) list.get(pos);
			}

			public boolean hasNext() {
				return ++pos < list.size();
			}
		};
	}

	/**
	 * Вызывает у всех объектов на карте метод draw
	 */
	public void drawAll() {
		// Screen.instance.repaintOnEveryDraw = false;
		Iterator<Drawable> it = iterator();
		while (it.hasNext()) {
			it.next().draw();
		}
		Screen.instance.repaint();
		// Screen.instance.repaintOnEveryDraw = true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Удаление объекта
	 * 
	 * @param object
	 */
	public void remove(Graph object) {
		list.remove(object);
	}
}
