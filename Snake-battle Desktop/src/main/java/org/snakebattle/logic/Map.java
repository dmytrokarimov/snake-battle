package org.snakebattle.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.snakebattle.gui.*;

/**
 * ����� � ���������� ��������� ��� ������
 * 
 * @author Karimov
 */
public class Map implements Iterable<Drawable>, Serializable {
	private static final long serialVersionUID = 3847259324576480684L;

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
	 * ����� ������ � ������
	 * 
	 * @throws ObjectAlreadyAddedException
	 *             ������������ ���� �� ����������� ������� obj � ������� ���
	 *             ���� ������
	 */
	public void put(Drawable obj) throws ObjectAlreadyAddedException {
		if (!checkExist(obj))
			list.add(obj);
		else
			throw new ObjectAlreadyAddedException("Coord:" + obj.getCoord());
	}

	/**
	 * ��������� �� ����� ������ ������� (�� ����������������� �������� - �
	 * ������ ������ �������� ��������)
	 * 
	 * @throws ObjectAlreadyAddedException
	 *             ���� ���� �� ���� ������� ������ ����������� � "�����"
	 */
	public void putSnake(Snake obj) throws ObjectAlreadyAddedException {
		Iterator<Element> it = obj.iterator();
		while (it.hasNext())
			put((Drawable) it.next());
		obj.setSnakeInMap(true, name);
	}

	/**
	 * ��������� �� ����� ������ ������� (����������������� �������� - � ������
	 * ������ �������� �� �����������)
	 * 
	 * @throws ObjectAlreadyAddedException
	 *             ���� ���� �� ���� ������� ������ ����������� � "�����"
	 */
	public void putSnakeTransactional(Snake obj)
			throws ObjectAlreadyAddedException {
		Iterator<Element> it1 = obj.iterator();
		while (it1.hasNext())
			if (checkExist(it1.next()))
				throw new ObjectAlreadyAddedException("Coord:"
						+ it1.next().getCoord());
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
	 * �������� � ���� �������� �� ����� ����� draw
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
		this.name = name.intern();
	}

	/**
	 * �������� �������
	 * 
	 * @param object
	 */
	public void remove(Graph object) {
		list.remove(object);
	}

	// ��������� ������� //
	/**
	 * ������������� ������� ����� �������� width*height
	 * 
	 * @param width
	 * @param height
	 */
	public void setBorder(int width, int height) {
		// Dummy
		Dummy d;

		try {
			// ������� �����
			d = new Dummy(new Point(-10, 0), 100, height);
			put(d);
			// ������� ������
			d = new Dummy(new Point(width, 0), 100, height);
			put(d);
			// ������� ������
			d = new Dummy(new Point(0, -100), width, 100);
			put(d);
			// ������� �����
			d = new Dummy(new Point(10, height), width, 100);
			put(d);
		} catch (ObjectAlreadyAddedException e) {
			e.printStackTrace();
		}
	}
}
