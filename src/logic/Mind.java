package logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gui.Dummy;
import gui.Element;
import gui.Element.PARTS;

/**
 * ��������� ����� ��� �������� ������� � ����������� ���������
 * 
 * @author Karimov
 * 
 */
public class Mind {
	private MindMap[] mm;
	private Snake sn;

	/**
	 * "����� �����" - ������ Element
	 * 
	 * @author Karimov
	 * @param <mindmap>
	 * 
	 */
	public class MindMap {
		private Element[][] mindmap;

		public MindMap(int w, int h) {
			mindmap = new Element[w][];
			for (int i = 0; i < w; i++) {
				mindmap[i] = new Element[h];
			}
		}

		public Element[] getLine(int i) {
			return mindmap[i];
		}

		public Element getAt(int i, int j) {
			return mindmap[i][j];
		}

		public Element[][] get() {
			return mindmap;
		}

		public void setLine(int i, Element[] m) {
			mindmap[i] = m;
		}

		public void setAt(int i, int j, Element m) {
			mindmap[i][j] = m;
		}

		public void set(Element[][] m) {
			mindmap = m;
		}

		/**
		 * ������������� ����� �� ��� ���, ���� �� �������� � 4 ����, ��� ��
		 * ������ ������������
		 * 
		 * @param coord
		 *            ����������, � ������� ��������� �����
		 * @param map
		 *            �������� �����
		 * @return ���������� ����� ��������, ��� ������� ����� ����� ������� �
		 *         �������� ������ ��� -1, ���� �� �������
		 */
		public int check(Map map, Point coord) {
			return -1;
		}
	}

	public Mind(Snake snake) {
		sn = snake;
		mm = new MindMap[9];
		for (int i = 0; i < 9; i++) {
			mm[i] = new MindMap(9, 9);
		}
	}

	/**
	 * �� ����� ���������� ��������, ������� ������ "�����" ���������
	 * 
	 * @param map
	 *            ����� ��� ����������� ��������
	 * @param snake
	 *            ������, ��� ������� ��������� ������
	 * @return ��������
	 */
	public static Action getAction(Map map, Snake snake) {
		Element head = snake.getElements().get(0);
		if (head.getPart() != PARTS.HEAD)
			throw new HeadNoFirstException();
		int w = head.getWidth();
		int h = head.getHeight();
		int x = snake.getCoord().x;// + w / 2;
		int y = snake.getCoord().y;// + h / 2;
		Dummy headL = new Dummy(new Point(x-w,y), w, h);
		Dummy headR = new Dummy(new Point(x+w,y), w, h);
		Dummy headU = new Dummy(new Point(x,y-h), w, h);
		Dummy headD = new Dummy(new Point(x,y+h), w, h);
		if (map.checkExist(headL))
			if (map.checkExist(headR))
				if (map.checkExist(headU))
					if (map.checkExist(headD))
						return ActionFactory.getInDeadlock();

		for (int i = 0; i < snake.getMind().getMindMap().length; i++)
			if (snake.getMind().getMindMap(i).check(map, head.getCoord()) != -1) {
				return ActionFactory.getInDeadlock();
			}

		List<Action> a = new ArrayList<Action>();

		if (!map.checkExist(headD))
			a.add(ActionFactory.getDown());

		if (!map.checkExist(headU))
			a.add(ActionFactory.getUp());

		if (!map.checkExist(headL))
			a.add(ActionFactory.getLeft());

		if (!map.checkExist(headR))
			a.add(ActionFactory.getRight());

		if (a.size() > 0){
		int i;
		i = new Random(System.currentTimeMillis()).nextInt(a.size());
		return a.get(i);
		}
		return ActionFactory.getInDeadlock();
	}

	/**
	 * �� ����� ���������� ��������, ������� ������ "�����" ���������
	 * 
	 * @param map
	 *            ����� ��� ����������� ��������
	 * @return ��������
	 */
	public Action getAction(Map map) {
		return getAction(map, sn);
	}

	public MindMap getMindMap(int i) {
		return mm[i];
	}

	public MindMap[] getMindMap() {
		return mm;
	}

	public Snake getSnake() {
		return sn;
	}
}
