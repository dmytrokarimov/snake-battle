package gui;

import java.util.*;

import logic.Map;

public class Common {
	public static Map std_map;
	private static List<Map> registeredMaps = new ArrayList<Map>();

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
	
	public static class MapAlreadyExistException extends Exception{
		public MapAlreadyExistException(String string) {
			super(string);
		}

		private static final long serialVersionUID = 4780051374649338074L;
	}
	
	public static class MapNotExistException extends Exception{
		public MapNotExistException(String string) {
			super(string);
		}

		private static final long serialVersionUID = 4780051374649338074L;
	}
	
	/**
	 * Register map in internal map archive
	 * @param map map
	 * @throws MapAlreadyExistException if map exist this exception will be throwen
	 */
	public static Map registerMap(Map map) throws MapAlreadyExistException {
		for (int i = 0; i < registeredMaps.size(); i++)
			if (registeredMaps.get(i).getName().equals(map.getName())) {
				throw new MapAlreadyExistException("Map " + map.getName() + " already exist");
			}
		registeredMaps.add(map);
		return map;
	}

	/**
	 * Remvoe map in internal map archive
	 * @param map registered map
	 * @throws MapNotExistException if map not exist this exception will be throwen
	 */
	public static void removeMap(Map map) throws MapNotExistException {
		removeMap(map.getName());
	}
	
	/**
	 * Register map in internal map archive
	 * @param name registered map
	 * @throws MapNotExistException if map exist this exception will be throwen
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
	private Common() {

	}
}
