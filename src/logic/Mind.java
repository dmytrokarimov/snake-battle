package logic;

import gui.Element;
/**
 * ќписывает класс дл€ прин€ти€ решени€ о принимаемых действи€х
 * @author Karimov
 *
 */
public class Mind {
	private MindMap mm;

	/**
	 * " арта мозга" - массив Element
	 * @author Karimov
	 *
	 */
	public class MindMap {
		private Element[][] mindmap;

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
	}

	/**
	 * ѕо карте определ€ет действие, которое змейка "хочет" выполнить
	 * @param map карта дл€ определени€ действи€
	 * @param snake змейка, дл€ которой проводить расчет
	 * @return действие
	 */
	public static Action getAction(Map map, Snake snake) {
		return null;
	}
	
	/**
	 * ѕо карте определ€ет действие, которое змейка "хочет" выполнить
	 * @param map карта дл€ определени€ действи€
	 * @return действие
	 */
	public Action getAction(Map map) {
		return null;
	}

	public MindMap getMindMap() {
		return mm;
	}
}
