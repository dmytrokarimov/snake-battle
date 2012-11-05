package logic;

//import gui.Graph;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gui.*;

/**
 * ����� � ���������� ��������� ��� ������
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
	 * ���� ������ �� ����������
	 * 
	 * @param coord
	 *            ���������� �������
	 * @return null ���� ������� ��� ��� ������, ���� ������ �� ��������
	 *         ����������� ����
	 */
	public E getObject(Point coord) {
		Iterator<E> it = iterator();
		while (it.hasNext())
			if (((Drawable) it.next()).pointAt(coord))
				return it.next();
		return null;
	}

	/**
	 * ����� ������ � ������
	 * 
	 * @throws ObjectAlreadyAddedException
	 *             ������������ ���� �� ����������� ������� obj � ������� ���
	 *             ���� ������
	 */
	public void put(E obj) throws ObjectAlreadyAddedException {
		if (getObject(obj.getCoord()) == null)
			list.add(obj);
		else
			throw new ObjectAlreadyAddedException();
	}

	/**
	 * ��������� �� ����� ������ ������� (�� ����������������� �������� - �
	 * ������ ������ �������� ��������)
	 * 
	 * @throws ObjectAlreadyAddedException
	 *             ���� ���� �� ���� ������� ������ ����������� � "�����"
	 */
	public void putSnake(Snake obj) throws ObjectAlreadyAddedException {
		Iterator<Element> it = obj.getElements();
		while (it.hasNext())
			put((E) it.next());
	}

	/**
	 * ��������� �� ����� ������ ������� (����������������� �������� - � ������
	 * ������ �������� �� �����������)
	 * 
	 * @throws ObjectAlreadyAddedException
	 *             ���� ���� �� ���� ������� ������ ����������� � "�����"
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
	 * �������� � ���� �������� �� ����� ����� draw
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
