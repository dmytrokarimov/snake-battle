package org.snakebattle.logic;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.snakebattle.gui.Drawable;
import org.snakebattle.gui.ObjectAlreadyAddedException;
import org.snakebattle.gui.events.EventListener;
import org.snakebattle.gui.primitive.Dummy;
import org.snakebattle.gui.primitive.snake.Element;
import org.snakebattle.gui.screen.IScreen;
import org.snakebattle.gui.screen.Screen;

/**
 * ����� � ���������� ��������� ��� ������
 * 
 * @author Karimov
 */
public class BattleMap implements Iterable<Drawable>, Serializable {
	private static final long serialVersionUID = 3847259324576480684L;

	private String name;
	private List<Drawable> list;

	public BattleMap(String name) {
		this.name = name;
		list = new ArrayList<Drawable>();
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
	 * ����� ������ � ������. ���� ��� {@link EventListener}, �� ����������� ��
	 * ������� � ������� {@link IScreen#subscribe(EventListener)}
	 * 
	 * @throws ObjectAlreadyAddedException
	 *             ������������ ���� �� ����������� ������� obj � ������� ���
	 *             ���� ������
	 */
	public void put(Drawable obj) throws ObjectAlreadyAddedException {
		if (obj instanceof EventListener) {
			Screen.instance.subscribe((EventListener) obj);
		}
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
		Screen.instance.setColor(Color.white);
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
	 * �������� ������� ���� ��� {@link EventListener}, �� ���������� �� �������
	 * � ������� {@link IScreen#unSubscribe(EventListener)}
	 * 
	 * @param object
	 */
	public void remove(Drawable object) {
		if (object instanceof EventListener) {
			Screen.instance.unSubscribe((EventListener) object);
		}
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

	/**
	 * ������� ��� �������. ���������� ���� ���������� �������
	 */
	public void removeAll() {
		while (list.size() > 0) {
			remove(list.get(0));
		}
	}
}
