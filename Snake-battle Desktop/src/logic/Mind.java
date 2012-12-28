package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import logic.Action.ACTION_TYPE;

import gui.Common.ActionList;
import gui.Drawable;
import gui.Dummy;
import gui.Element;
import gui.Element.PARTS;
import gui.MindPolyGraph;
import gui.MindPolyGraph.LOGIC_FLAGS;
import gui.MindPolyGraph.LOGIC_TYPES;
import gui.MindPolyGraph.OWNER_TYPES;
import gui.Point;

/**
 * Описывает класс для принятия решения о принимаемых действиях
 * 
 * @author Karimov
 * 
 */
public class Mind implements Serializable, Cloneable{
	private static final long serialVersionUID = 1091988289276908947L;
	
	private static Random r = new Random(System.currentTimeMillis()
			+ new Random(System.currentTimeMillis()).nextInt(1000000)
			+ System.currentTimeMillis());
	private MindMap[] mm;
	private Snake sn;

	/**
	 * "Карта мозга" - массив Element
	 * 
	 * @author Karimov
	 * @param <mindmap>
	 * 
	 */
	public class MindMap implements Serializable{
		private static final long serialVersionUID = 1932178797914012920L;
		
		private MindPolyGraph[][] mindmap;

		public MindMap(int w, int h) {
			mindmap = new MindPolyGraph[w][];
			for (int i = 0; i < w; i++) {
				mindmap[i] = new MindPolyGraph[h];
			}
		}

		public MindPolyGraph[] getLine(int i) {
			return mindmap[i];
		}

		public MindPolyGraph getAt(int i, int j) {
			return mindmap[i][j];
		}

		public MindPolyGraph[][] get() {
			return mindmap;
		}

		public void setLine(int i, MindPolyGraph[] m) {
			mindmap[i] = m;
		}

		public void setAt(int i, int j, MindPolyGraph m) {
			mindmap[i][j] = m;
		}

		public void set(MindPolyGraph[][] m) {
			mindmap = m;
		}

		private boolean check(MindPolyGraph[][] mindmap, Map map, Point coord) {
			boolean flag = true;
			int dx = -1;
			int dy = -1;
			boolean[][] color_flags = LOGIC_FLAGS.getColorFlagsBoolTrue();

			loop1: for (int i = 0; i < mindmap.length; i++)
				for (int j = 0; j < mindmap[0].length; j++) {
					if (mindmap[i][j] != null
							&& mindmap[i][j].getOwner() == OWNER_TYPES.SNAKE
							&& mindmap[i][j].getValue() instanceof Element
							&& ((Element) mindmap[i][j].getValue()).getPart() == PARTS.HEAD) {
						dx = i * mindmap[i][j].getWidth();
						dy = j * mindmap[i][j].getHeight();
						break loop1;
					}
				}

			dx = sn.getElements().get(0).getCoord().x - dx;
			dy = sn.getElements().get(0).getCoord().y - dy;
			if (dx == -1 || dy == -1)
				return false;

			// установка флагов
			for (int i = 0; i < mindmap.length; i++)
				for (int j = 0; j < mindmap[0].length; j++) {
					if (mindmap[i][j] == null)
						continue;
					color_flags[mindmap[i][j].getFlags().ordinal()][mindmap[i][j]
							.getLogic().ordinal()] = LOGIC_TYPES
							.getDefault(mindmap[i][j].getLogic().ordinal());
				}

			for (int i = 0; i < mindmap.length; i++)
				for (int j = 0; j < mindmap[0].length; j++) {
					if (mindmap[i][j] == null)
						continue;
					if (mindmap[i][j].getValue() == null
							&& mindmap[i][j].getOwner() != OWNER_TYPES.NEUTRAL)
						continue;
					// в ячейке должно быть пусто так же как и в карте мозга
					if (mindmap[i][j].getValue() == null
							&& mindmap[i][j].getOwner() == OWNER_TYPES.NEUTRAL) {
						Drawable d = map.getObject(new Point(i
								* mindmap[i][j].getWidth() + dx, j
								* mindmap[i][j].getHeight() + dy));
						color_flags[mindmap[i][j].getFlags().ordinal()][mindmap[i][j]
								.getLogic().ordinal()] = mindmap[i][j]
								.getLogic().compare(
										color_flags[mindmap[i][j].getFlags()
												.ordinal()][mindmap[i][j]
												.getLogic().ordinal()],
										d == null);
						continue;
					}
					Drawable md = mindmap[i][j].getValue();
					Drawable d = map.getObject(new Point(i
							* mindmap[i][j].getWidth() + dx, j
							* mindmap[i][j].getHeight() + dy));
					if (d != null) {
						int x = i * mindmap[i][j].getWidth() + dx;
						int y = j * mindmap[i][j].getHeight() + dy;
						md.setCoord(new Point(x, y));
						boolean mdEd = md.equals(d);
						if (mdEd)
							if (mindmap[i][j].getOwner() != OWNER_TYPES.NEUTRAL) {
								if (mindmap[i][j].getOwner() == OWNER_TYPES.SNAKE) {
									if (d instanceof Element) {
										color_flags[mindmap[i][j].getFlags()
												.ordinal()][mindmap[i][j]
												.getLogic().ordinal()] = mindmap[i][j]
												.getLogic()
												.compare(
														color_flags[mindmap[i][j]
																.getFlags()
																.ordinal()][mindmap[i][j]
																.getLogic()
																.ordinal()],
														((Element) d)
																.getSnake()
																.equals(sn));
										continue;
										// return false;
									}
								} else if (mindmap[i][j].getOwner() == OWNER_TYPES.ENEMY) {
									if (d instanceof Element) {
										color_flags[mindmap[i][j].getFlags()
												.ordinal()][mindmap[i][j]
												.getLogic().ordinal()] = mindmap[i][j]
												.getLogic()
												.compare(
														color_flags[mindmap[i][j]
																.getFlags()
																.ordinal()][mindmap[i][j]
																.getLogic()
																.ordinal()],
														!((Element) d)
																.getSnake()
																.equals(sn));
										continue;
										// return false;
									}
								}
							} else {
								color_flags[mindmap[i][j].getFlags().ordinal()][mindmap[i][j]
										.getLogic().ordinal()] = mindmap[i][j]
										.getLogic()
										.compare(
												color_flags[mindmap[i][j]
														.getFlags().ordinal()][mindmap[i][j]
														.getLogic().ordinal()],
												false);
								continue;
							}
					} else {
						color_flags[mindmap[i][j].getFlags().ordinal()][mindmap[i][j]
								.getLogic().ordinal()] = mindmap[i][j]
								.getLogic().compare(
										color_flags[mindmap[i][j].getFlags()
												.ordinal()][mindmap[i][j]
												.getLogic().ordinal()], false);
						continue;
						// return false;
						// break loop1;
					}
				}
			for (int i = 0; i < LOGIC_FLAGS.FLAGS_COUNT; i++) {
				for (int j = 0; j < LOGIC_TYPES.TYPES_COUNT; j++) {
					flag = flag && color_flags[i][j];
				}
			}
			return flag;
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
			boolean flag = false;
			for (int i = 0; i < mindmap.length; i++)
				for (int j = 0; j < mindmap[0].length; j++) {
					if (mindmap[i][j] != null) {
						if (mindmap[i][j].getValue() == null
								&& mindmap[i][j].getOwner() == OWNER_TYPES.NEUTRAL)
							flag = true;
						if (mindmap[i][j].getValue() != null)
							flag = true;
					}
				}

			if (!flag)
				return -1;
			/*
			 * int dx = 0; int dy = 0;
			 * 
			 * loop1: for (int i = 0; i < mindmap.length; i++) for (int j = 0; j
			 * < mindmap[0].length; j++) { if (mindmap[i][j] != null &&
			 * mindmap[i][j].getOwner() == OWNER_TYPES.SNAKE &&
			 * mindmap[i][j].getValue() instanceof Element && ((Element)
			 * mindmap[i][j].getValue()).getPart() == PARTS.HEAD) { dx =
			 * mindmap[i][j].getCoord().x; dy = mindmap[i][j].getCoord().y;
			 * break loop1; } }
			 * 
			 * dx = sn.getElements().get(0).getCoord().x - dx; dy =
			 * sn.getElements().get(0).getCoord().y - dy;
			 * 
			 * loop1: for (int i = 0; i < mindmap.length; i++) for (int j = 0; j
			 * < mindmap[0].length; j++) { Drawable md =
			 * mindmap[i][j].getValue(); if (mindmap[i][j].getValue() == null)
			 * continue; Drawable d = map .getObject(new
			 * Point(mindmap[i][j].getCoord().x + dx, mindmap[i][j].getCoord().y
			 * + dy)); if (d != null) { int x = d.getCoord().x; int y =
			 * d.getCoord().y; md.setCoord(new Point(x,y)); if (md.equals(d))
			 * continue; } else { break loop1; } }
			 */
			if (check(mindmap, map, coord))
				return 0;

			// Карта, повёрнутая на 90 градусов
			MindPolyGraph[][] map90 = new MindPolyGraph[mindmap.length][mindmap.length];
			// Расчёт поворотов
			for (int i = 0; i < mindmap.length; i++)
				for (int j = 0; j < mindmap.length; j++)
					map90[i][j] = mindmap[j][mindmap.length - 1 - i];

			if (check(map90, map, coord))
				return 1;

			// Карта, повёрнутая на 180 градусов
			MindPolyGraph[][] map180 = new MindPolyGraph[mindmap.length][mindmap.length];
			for (int i = 0; i < mindmap.length; i++)
				for (int j = 0; j < mindmap.length; j++)
					map180[i][j] = mindmap[mindmap.length - 1 - i][mindmap.length
							- 1 - j];

			if (check(map180, map, coord))
				return 2;

			// Карта, повёрнутая на 270 градусов
			MindPolyGraph[][] map270 = new MindPolyGraph[mindmap.length][mindmap.length];
			for (int i = 0; i < mindmap.length; i++)
				for (int j = 0; j < mindmap.length; j++)
					map270[i][j] = map90[map90.length - 1 - i][map90.length - 1
							- j];

			if (check(map270, map, coord))
				return 3;

			return -1;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			//result = prime * result + getOuterType().hashCode();
			result = prime * result + Arrays.hashCode(mindmap);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof MindMap)) {
				return false;
			}
			MindMap other = (MindMap) obj;
			//if (!getOuterType().equals(other.getOuterType())) {
			//	return false;
			//}
			if (!Arrays.deepEquals(mindmap, other.mindmap)) {
				return false;
			}
			return true;
		}

		private Mind getOuterType() {
			return Mind.this;
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
	 * 
	 * @param map
	 * @param snake
	 * @param headE
	 * @return can return nex actions: EatTail, LeaveBattle
	 */
	private static ActionList getEatTail(Map map, Snake snake, Dummy headE) {
		Drawable d = map.getObject(headE.getCoord());
		if (d instanceof Element) {
			Element e = (Element) d;
			return getEatTail(e, map, snake, headE);
		}
		return null;
	}

	private static ActionList getEatTail(Element e, Map map, Snake snake,
			Dummy headE) {
		if (e.getPart() == PARTS.TAIL && e.getSnake() != snake)
			return new ActionList(ActionFactory.getEatTail(), snake,
					e.getSnake());
		else if (e.getPart() == PARTS.THIS_IS_BAD_IDEA)
			return new ActionList(ActionFactory.getLeaveBattle(), snake);
		return null;
	}

	private static ActionList getSmartAction(Action primaryAction, Map map,
			Snake snake, Dummy headE) {
		Drawable d = map.getObject(headE.getCoord());
		if (d == null)
			return new ActionList(primaryAction, snake);
		else {
			if (d instanceof Element)
				return getEatTail((Element) d, map, snake, headE);
		}
		return new ActionList(ActionFactory.getInDeadlock(), snake);
	}

	/**
	 * По карте определяет действие, которое змейка "хочет" выполнить
	 * 
	 * @param map
	 *            карта для определения действия
	 * @param snake
	 *            змейка, для которой проводить расчет
	 * @return действия
	 */
	public static ActionList getAction(Map map, Snake snake) {
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
		List<ActionList> a = new ArrayList<ActionList>();

		for (int i = 0; i < snake.getMind().getMindMap().length; i++) {
			int check = snake.getMind().getMindMap(i)
					.check(map, head.getCoord());
			if (check != -1) {

				ActionList al = null;
				if (check == 0)
					al =  getSmartAction(ActionFactory.getUp(), map, snake,
							headU);

				if (check == 1)
					al =  getSmartAction(ActionFactory.getRight(), map, snake,
							headR);

				if (check == 2)
					al =  getSmartAction(ActionFactory.getDown(), map, snake,
							headD);

				if (check == 3)
					al =  getSmartAction(ActionFactory.getLeft(), map, snake,
							headL);
				if (al != null && al.action.getType() != ACTION_TYPE.IN_DEAD_LOCK)
					return al;
			}
		}

		/*
		 * if (!map.checkExist(headD)) a.add(new
		 * ActionList(ActionFactory.getDown(), snake));
		 * 
		 * if (!map.checkExist(headU)) a.add(new
		 * ActionList(ActionFactory.getUp(), snake));
		 * 
		 * if (!map.checkExist(headL)) a.add(new
		 * ActionList(ActionFactory.getLeft(), snake));
		 * 
		 * if (!map.checkExist(headR)) a.add(new
		 * ActionList(ActionFactory.getRight(), snake));
		 * 
		 * // leave battle if (map.getObject(headD.getCoord()) instanceof
		 * Element) { Element e = (Element) map.getObject(headD.getCoord()); if
		 * (e.getPart() == PARTS.THIS_IS_BAD_IDEA) a.add(new
		 * ActionList(ActionFactory.getLeaveBattle(), snake)); }
		 * 
		 * if (map.getObject(headL.getCoord()) instanceof Element) { Element e =
		 * (Element) map.getObject(headL.getCoord()); if (e.getPart() ==
		 * PARTS.THIS_IS_BAD_IDEA) a.add(new
		 * ActionList(ActionFactory.getLeaveBattle(), snake)); }
		 * 
		 * if (map.getObject(headU.getCoord()) instanceof Element) { Element e =
		 * (Element) map.getObject(headU.getCoord()); if (e.getPart() ==
		 * PARTS.THIS_IS_BAD_IDEA) a.add(new
		 * ActionList(ActionFactory.getLeaveBattle(), snake)); }
		 * 
		 * if (map.getObject(headR.getCoord()) instanceof Element) { Element e =
		 * (Element) map.getObject(headR.getCoord()); if (e.getPart() ==
		 * PARTS.THIS_IS_BAD_IDEA) a.add(new
		 * ActionList(ActionFactory.getLeaveBattle(), snake)); }
		 * 
		 * // eat tail ActionList al = null; al = getEatTail(map, snake, headD);
		 * if (al != null) { a.add(al); }
		 * 
		 * al = getEatTail(map, snake, headU); if (al != null) { a.add(al); }
		 * 
		 * al = getEatTail(map, snake, headL); if (al != null) { a.add(al); }
		 * 
		 * al = getEatTail(map, snake, headR); if (al != null) { a.add(al); }
		 * 
		 * if (a.size() == 0) if (map.checkExist(headL)) if
		 * (map.checkExist(headR)) if (map.checkExist(headU)) if
		 * (map.checkExist(headD)) ;// return ActionFactory.getInDeadlock();
		 */
		a.add(getSmartAction(ActionFactory.getUp(), map, snake, headU));
		a.add(getSmartAction(ActionFactory.getDown(), map, snake, headD));
		a.add(getSmartAction(ActionFactory.getLeft(), map, snake, headL));
		a.add(getSmartAction(ActionFactory.getRight(), map, snake, headR));
		List<ActionList> temp = a;
		a = new ArrayList<ActionList>();
		for (int i = 0; i < temp.size(); i++) {
			if (temp.get(i) != null
					&& temp.get(i).action.getType() != ACTION_TYPE.IN_DEAD_LOCK)
				a.add(temp.get(i));
		}
		if (a.size() > 0) {
			int i;
			i = r.nextInt(a.size() * 100000);
			return a.get(i % a.size());
		}
		return new ActionList(ActionFactory.getInDeadlock(), snake);
	}

	/**
	 * По карте определяет действие, которое змейка "хочет" выполнить
	 * 
	 * @param map
	 *            карта для определения действия
	 * @return действие
	 */
	public ActionList getAction(Map map) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(mm);
		result = prime * result + ((sn == null) ? 0 : 1);//sn.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Mind)) {
			return false;
		}
		Mind other = (Mind) obj;
		if (!Arrays.equals(mm, other.mm)) {
			return false;
		}
		if (sn == null) {
			if (other.sn != null) {
				return false;
			}
		} else if (!sn.equals(other.sn)) {
			return false;
		}
		return true;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Mind m = new Mind(this.sn);
		m.mm = this.mm.clone();
		// TODO Auto-generated method stub
		return m;
	}
}
