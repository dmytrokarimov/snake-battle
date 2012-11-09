package gui;

import gui.Element.PARTS;

import java.awt.Point;
import java.util.*;

import server.Battle;

import logic.Action;
import logic.HeadNoFirstException;
import logic.Map;
import logic.Snake;
import logic.SnakeAlreadyInMapException;
import logic.Action.ACTION_TYPE;

/**
 * Class for work with map and can do actions and get result in record
 * 
 * @author Karimov
 * 
 */
public class Common {
	public static Map std_map;
	private static List<Map> registeredMaps = new ArrayList<Map>();

	/**
	 * Включает (может быть активна только одна карта) карту с именем name
	 * 
	 * @param name
	 *            имя карты (карта должна быть уже добавлена)
	 * @return карту с именем name
	 * @throws MapNotExistException
	 *             если карты нет в списке зарегестрированных
	 */
	public static Map selectMap(String name) throws MapNotExistException {
		std_map = null;
		for (int i = 0; i < registeredMaps.size(); i++)
			if (registeredMaps.get(i).getName().equals(name)) {
				std_map = registeredMaps.get(i);
				break;
			}
		if (std_map == null)
			throw new MapNotExistException("Map " + name + " no exist");
		return std_map;
	}

	public static class MapAlreadyExistException extends Exception {
		public MapAlreadyExistException(String string) {
			super(string);
		}

		private static final long serialVersionUID = 4780051374649338074L;
	}

	public static class MapNotExistException extends Exception {
		public MapNotExistException(String string) {
			super(string);
		}

		private static final long serialVersionUID = 4780051374649338074L;
	}

	/**
	 * Register map in internal map archive
	 * 
	 * @param map
	 *            map
	 * @throws MapAlreadyExistException
	 *             if map exist this exception will be throwen
	 */
	public static Map registerMap(Map map) throws MapAlreadyExistException {
		for (int i = 0; i < registeredMaps.size(); i++)
			if (registeredMaps.get(i).getName().equals(map.getName())) {
				throw new MapAlreadyExistException("Map " + map.getName()
						+ " already exist");
			}
		registeredMaps.add(map);
		return map;
	}

	/**
	 * Remvoe map in internal map archive
	 * 
	 * @param map
	 *            registered map
	 * @throws MapNotExistException
	 *             if map not exist this exception will be throwen
	 */
	public static void removeMap(Map map) throws MapNotExistException {
		removeMap(map.getName());
	}

	/**
	 * Register map in internal map archive
	 * 
	 * @param name
	 *            registered map
	 * @throws MapNotExistException
	 *             if map exist this exception will be throwen
	 */
	public static void removeMap(String name) throws MapNotExistException {
		boolean deleted = false;
		for (int i = 0; i < registeredMaps.size(); i++)
			if (registeredMaps.get(i).getName().equals(name)) {
				registeredMaps.remove(i);
				deleted = true;
			}
		if (!deleted)
			throw new MapNotExistException("Map " + name + " no exist");
	}

	public static class ActionList {
		public final Snake[] param;
		public final Action action;
		public ActionList(Action action, Snake... snakes){
			this.action = action;
			param = snakes;
		}
	}

	/**
	 * This method use "battle" map
	 * Calc next step and return step result
	 */
	public static List<ActionList> doStep(Snake... sn) {
		List<ActionList> al = new ArrayList<ActionList>(); 
		try {
			Map m = Common.selectMap("battle");
			for (int i = 0; i < sn.length; i++) {
				if (sn[i].getElements().size() < 2)
					continue;
				Action a = sn[i].getMind().getAction(m);
				if (a.getType() == ACTION_TYPE.EAT_TAIL) {
					Element head = sn[i].getElements().get(0);
					if (head.getPart() != PARTS.HEAD)
						throw new HeadNoFirstException();
					int w = head.getWidth();
					int h = head.getHeight();
					int x = sn[i].getCoord().x;// + w / 2;
					int y = sn[i].getCoord().y;// + h / 2;
					Dummy headL = new Dummy(new Point(x - w, y), w, h);
					Dummy headR = new Dummy(new Point(x + w, y), w, h);
					Dummy headU = new Dummy(new Point(x, y - h), w, h);
					Dummy headD = new Dummy(new Point(x, y + h), w, h);
					Element e = null;

					if (m.getObject(headD.getCoord()) instanceof Element) {
						e = (Element) m.getObject(headD.getCoord());
					}
					if (e != null && e.getPart() != PARTS.TAIL && m.getObject(headL.getCoord()) instanceof Element) {
						e = (Element) m.getObject(headL.getCoord());
					}
					if (e != null && e.getPart() != PARTS.TAIL && m.getObject(headU.getCoord()) instanceof Element) {
						e = (Element) m.getObject(headU.getCoord());
					}
					if (e != null && e.getPart() != PARTS.TAIL && m.getObject(headR.getCoord()) instanceof Element) {
						e = (Element) m.getObject(headR.getCoord());
					}

					if (e != null) {
						if (e.getPart() == PARTS.TAIL && e.getSnake() != sn[i]){
							a.doAction(sn[i], e.getSnake());
							al.add(new ActionList(a, sn[i], e.getSnake()));
						}
					}
				} else {
					a.doAction(sn[i]);
					al.add(new ActionList(a, sn[i]));
				}
			}
		} catch (MapNotExistException e) {
			e.printStackTrace();
		}
		return al;
	}

	private Common() {

	}
}
