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
public class Map<E extends Drawable> implements Iterable<E> {
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
	public E getObject(Point coord) {
		Iterator<E> it = iterator();
		while (it.hasNext())
			if (((Drawable) it.next()).pointAt(coord))
				return it.next();
		return null;
	}

	/**
	 * Ложит объект в массив
	 * 
	 * @throws ObjectAlreadyAddedException
	 *             генерируется если по координатам объекта obj в массиве уже
	 *             есть объект
	 */
	public void put(E obj) throws ObjectAlreadyAddedException {
		if (getObject(obj.getCoord()) == null)
			list.add(obj);
		else
			throw new ObjectAlreadyAddedException();
	}

	/**
	 * Добавляет на карту змейку целеком (не транзакциональная операция - в
	 * случае ошибки элементы остаются)
	 * 
	 * @throws ObjectAlreadyAddedException
	 *             если хотя бы один элемент змейки конфликтует с "миром"
	 */
	public void putSnake(Snake obj) throws ObjectAlreadyAddedException {
		Iterator<Element> it = obj.getElements();
		while (it.hasNext())
			put((E) it.next());
	}

	/**
	 * Добавляет на карту змейку целеком (транзакциональная операция - в случае
	 * ошибки элементы не добавляются)
	 * 
	 * @throws ObjectAlreadyAddedException
	 *             если хотя бы один элемент змейки конфликтует с "миром"
	 */
	public void putSnakeTransactional(Snake obj) throws ObjectAlreadyAddedException {
		Iterator<Element> it1 = obj.getElements();
		while (it1.hasNext())
			if (getObject(it1.next().getCoord()) != null)
				throw new ObjectAlreadyAddedException();
		Iterator<Element> it = obj.getElements();
		while (it.hasNext())
			put((E) it.next());
	}

	public Iterator<E> iterator() {
		return new Iterator<E>() {
			private int pos = -1;

			public void remove() {
				list.remove(list.size() - 1);
			}

			public E next() {
				if (pos >= list.size())
					throw new ArrayIndexOutOfBoundsException();
				return (E) list.get(pos);
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
		Iterator<E> it = iterator();
		while (it.hasNext())
			it.next().draw();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
