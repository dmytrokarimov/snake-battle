package logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gui.Dummy;
import gui.Element;
import gui.Element.PARTS;

/**
 * Описывает класс для принятия решения о принимаемых действиях
 * 
 * @author Karimov
 * 
 */
public class Mind {
	private MindMap[] mm;
	private Snake sn;

	/**
	 * "Карта мозга" - массив Element
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
		 * Разворачивает карту до тех пор, пока не повернет её 4 раза, или не
		 * найдет соответствие
		 * 
		 * @param coord
		 *            координата, в которой проводить поиск
		 * @param map
		 *            реальная карта
		 * @return возвращает номер поворота, при котором карта мозга совпала с
		 *         реальной картой или -1, если не совпала
		 */
		public int check(Map map, Point coord) {
			// Карта, повёрнутая на 90 градусов
			Element[][] map90 = new Element[mindmap.length][mindmap.length];
			// Карта, повёрнутая на 180 градусов
			Element[][] map180 = new Element[mindmap.length][mindmap.length];
			// Карта, повёрнутая на 270 градусов
			Element[][] map270 = new Element[mindmap.length][mindmap.length];

			// Расчёт поворотов
			for (int i = 0; i < mindmap.length; i++)
				for (int j = 0; j < mindmap.length; j++)
					map90[i][j] = mindmap[j][mindmap.length - 1 - i];
			for (int i = 0; i < mindmap.length; i++)
				for (int j = 0; j < mindmap.length; j++)
					map180[i][j] = mindmap[mindmap.length - 1 - i][mindmap.length - 1 - j];
			for (int i = 0; i < mindmap.length; i++)
				for (int j = 0; j < mindmap.length; j++)
					map270[i][j] = map90[map90.length - 1 - i][map90.length - 1	- j];
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
	 * По карте определяет действие, которое змейка "хочет" выполнить
	 * 
	 * @param map
	 *            карта для определения действия
	 * @param snake
	 *            змейка, для которой проводить расчет
	 * @return действие
	 */
	public static Action getAction(Map map, Snake snake) {
		Element head = snake.getElements().get(0);
		if (head.getPart() != PARTS.HEAD)
			throw new HeadNoFirstException();
		int w = head.getWidth();
		int h = head.getHeight();
		int x = snake.getCoord().x;// + w / 2;
		int y = snake.getCoord().y;// + h / 2;
		Dummy headL = new Dummy(new Point(x - w, y), w, h);
		Dummy headR = new Dummy(new Point(x + w, y), w, h);
		Dummy headU = new Dummy(new Point(x, y - h), w, h);
		Dummy headD = new Dummy(new Point(x, y + h), w, h);
		List<Action> a = new ArrayList<Action>();

		if (!map.checkExist(headD))
			a.add(ActionFactory.getDown());

		if (!map.checkExist(headU))
			a.add(ActionFactory.getUp());

		if (!map.checkExist(headL))
			a.add(ActionFactory.getLeft());

		if (!map.checkExist(headR))
			a.add(ActionFactory.getRight());

		// leave battle
		if (map.getObject(headD.getCoord()) instanceof Element) {
			Element e = (Element) map.getObject(headD.getCoord());
			if (e.getPart() == PARTS.THIS_IS_BAD_IDEA)
				a.add(ActionFactory.getLeaveBattle());
		}

		if (map.getObject(headL.getCoord()) instanceof Element) {
			Element e = (Element) map.getObject(headL.getCoord());
			if (e.getPart() == PARTS.THIS_IS_BAD_IDEA)
				a.add(ActionFactory.getLeaveBattle());
		}

		if (map.getObject(headU.getCoord()) instanceof Element) {
			Element e = (Element) map.getObject(headU.getCoord());
			if (e.getPart() == PARTS.THIS_IS_BAD_IDEA)
				a.add(ActionFactory.getLeaveBattle());
		}

		if (map.getObject(headR.getCoord()) instanceof Element) {
			Element e = (Element) map.getObject(headR.getCoord());
			if (e.getPart() == PARTS.THIS_IS_BAD_IDEA)
				a.add(ActionFactory.getLeaveBattle());
		}

		// eat tail
		if (map.getObject(headD.getCoord()) instanceof Element) {
			Element e = (Element) map.getObject(headD.getCoord());
			if (e.getPart() == PARTS.TAIL && e.getSnake() != snake)
				a.add(ActionFactory.getEatTail());
		}

		if (map.getObject(headL.getCoord()) instanceof Element) {
			Element e = (Element) map.getObject(headL.getCoord());
			if (e.getPart() == PARTS.TAIL && e.getSnake() != snake)
				a.add(ActionFactory.getEatTail());
		}

		if (map.getObject(headU.getCoord()) instanceof Element) {
			Element e = (Element) map.getObject(headU.getCoord());
			if (e.getPart() == PARTS.TAIL && e.getSnake() != snake)
				a.add(ActionFactory.getEatTail());
		}

		if (map.getObject(headR.getCoord()) instanceof Element) {
			Element e = (Element) map.getObject(headR.getCoord());
			if (e.getPart() == PARTS.TAIL && e.getSnake() != snake)
				a.add(ActionFactory.getEatTail());
		}
		
		if (a.size() == 0)
			if (map.checkExist(headL))
				if (map.checkExist(headR))
					if (map.checkExist(headU))
						if (map.checkExist(headD))
							;//return ActionFactory.getInDeadlock();

		for (int i = 0; i < snake.getMind().getMindMap().length; i++)
			if (snake.getMind().getMindMap(i).check(map, head.getCoord()) != -1) {
				return ActionFactory.getInDeadlock();
			}

		if (a.size() > 0) {
			int i;
			i = new Random(System.currentTimeMillis()
					+ new Random(System.currentTimeMillis()).nextInt(1000000))
					.nextInt(a.size() * 100000);
			return a.get(i % a.size());
		}
		return ActionFactory.getInDeadlock();
	}

	/**
	 * По карте определяет действие, которое змейка "хочет" выполнить
	 * 
	 * @param map
	 *            карта для определения действия
	 * @return действие
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
