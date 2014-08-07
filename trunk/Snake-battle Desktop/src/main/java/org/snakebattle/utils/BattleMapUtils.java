package org.snakebattle.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.snakebattle.logic.Action;
import org.snakebattle.logic.BattleMap;
import org.snakebattle.logic.Player;
import org.snakebattle.logic.Snake;

/**
 * Class for work with map and can do actions and get result in record
 * 
 * @author Karimov
 * 
 */
public class BattleMapUtils {
	//public static BattleMap std_map = new BattleMap("Battle_map");
	private static List<BattleMap> registeredMaps = new ArrayList<BattleMap>();

	/**
	 * ¬ключает (может быть активна только одна карта) карту с именем name
	 * 
	 * @param name
	 *            им€ карты (карта должна быть уже добавлена)
	 * @return карту с именем name
	 * @throws MapNotExistException
	 *             если карты нет в списке зарегестрированных
	 */
	public synchronized static BattleMap selectMap(String name) throws MapNotExistException {
		BattleMap std_map = null;
		for (int i = 0; i < registeredMaps.size(); i++)
			if (registeredMaps.get(i).getName().equals(name)) {
				std_map = registeredMaps.get(i);
				break;
			}
		if (std_map == null)
			throw new MapNotExistException("BattleMap " + name + " no exist");
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
	 * @param battleMap
	 *            map
	 * @throws MapAlreadyExistException
	 *             if map exist this exception will be throwen
	 */
	public synchronized static BattleMap registerMap(BattleMap battleMap) throws MapAlreadyExistException {
		for (int i = 0; i < registeredMaps.size(); i++)
			if (registeredMaps.get(i).getName().equals(battleMap.getName())) {
				throw new MapAlreadyExistException("BattleMap " + battleMap.getName()
						+ " already exist");
			}
		registeredMaps.add(battleMap);
		return battleMap;
	}

	/**
	 * Remvoe map in internal map archive
	 * 
	 * @param battleMap
	 *            registered map
	 * @throws MapNotExistException
	 *             if map not exist this exception will be throwen
	 */
	public synchronized  static void removeMap(BattleMap battleMap) throws MapNotExistException {
		removeMap(battleMap.getName());
	}

	/**
	 * Register map in internal map archive
	 * 
	 * @param name
	 *            registered map
	 * @throws MapNotExistException
	 *             if map exist this exception will be throwen
	 */
	public synchronized static void removeMap(String name) throws MapNotExistException {
		boolean deleted = false;
		for (int i = 0; i < registeredMaps.size(); i++)
			if (registeredMaps.get(i).getName().equals(name)) {
				registeredMaps.remove(i);
				deleted = true;
			}
		if (!deleted)
			throw new MapNotExistException("BattleMap " + name + " no exist");
	}

	public static class ActionList implements Serializable{
		private static final long serialVersionUID = -2908059247897364803L;
		public final Snake[] param;
		public final Action action;
		public ActionList(Action action, Snake... snakes){
			this.action = action;
			param = snakes;
		}
	}

	/**
	 * This method use map
	 * Calc next step and return step result
	 */
	public synchronized static List<ActionList> doStep(String mapName, Player... sn) {
		List<ActionList> al = new ArrayList<ActionList>(); 
		try {
			BattleMap m = BattleMapUtils.selectMap(mapName);
			for (int i = 0; i < sn.length; i++) {
				if (sn[i].isPlayerCanTurn())
					continue;
				ActionList a = sn[i].getMind().getAction(m);
				if (a == null)
					continue;
				a.action.doAction(a.param);
				al.add(a);
			}
		} catch (MapNotExistException e) {
			e.printStackTrace();
		}
		return al;
	}
	
	/*public static void doStep(List<ActionList> actions, String mapName, Snake... snakes) { 
		try {
			BattleMap m = BattleMapUtils.selectMap(mapName);
			for (int i = 0; i < snakes.length; i++) {
				actions.doAction(actions.param);
			}
		} catch (MapNotExistException e) {
			e.printStackTrace();
		}
	}*/

	
	/**
	 * ѕозвол€ет сгенерировать случайное им€ дл€ карты
	 * @param mapName - ќсновна€ часть имени карты. ѕо умолчанию "battle-"
	 * @return
	 */
	public static String generateName(String mapName) {
		String name;
		Random r = new Random(9999999999L);
		if (mapName == null || mapName == "") mapName = "battle-";
		name = mapName + r.nextLong();
		return name;
	}

	private BattleMapUtils() {

	}
}
