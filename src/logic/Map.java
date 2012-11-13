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
public class Map implements Iterable<Drawable> {
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
		this.name = name;
	}

	/**
	 * �������� �������
	 * 
	 * @param object
	 */
	public void remove(Graph object) {
		list.remove(object);
	}
}
