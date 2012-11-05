package logic;

import gui.Element;
/**
 * ��������� ����� ��� �������� ������� � ����������� ���������
 * @author Karimov
 *
 */
public class Mind {
	private MindMap mm;

	/**
	 * "����� �����" - ������ Element
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
	 * �� ����� ���������� ��������, ������� ������ "�����" ���������
	 * @param map ����� ��� ����������� ��������
	 * @param snake ������, ��� ������� ��������� ������
	 * @return ��������
	 */
	public static Action getAction(Map map, Snake snake) {
		return null;
	}
	
	/**
	 * �� ����� ���������� ��������, ������� ������ "�����" ���������
	 * @param map ����� ��� ����������� ��������
	 * @return ��������
	 */
	public Action getAction(Map map) {
		return null;
	}

	public MindMap getMindMap() {
		return mm;
	}
}
