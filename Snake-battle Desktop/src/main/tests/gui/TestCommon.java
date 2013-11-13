package gui;

import static org.junit.Assert.*;

import org.junit.Test;
import org.snakebattle.logic.BattleMap;
import org.snakebattle.utils.BattleMapUtils;
import org.snakebattle.utils.BattleMapUtils.MapAlreadyExistException;
import org.snakebattle.utils.BattleMapUtils.MapNotExistException;

public class TestCommon {

	@Test
	public void testSelectMapNull() throws MapNotExistException {
		try {
			BattleMapUtils.selectMap("");
			assert false;
		} catch (MapNotExistException e) {
			assert true;
		}
	}

	@Test
	public void testSelectMap() throws MapNotExistException,
			MapAlreadyExistException {
		try {
			BattleMap battleMap = new BattleMap("name");
			BattleMapUtils.registerMap(battleMap);
			BattleMap map2 = BattleMapUtils.selectMap("name");
			assert battleMap.equals(map2);
		} catch (MapNotExistException | MapAlreadyExistException e) {
			assert false;
		}
	}

	@Test
	public void testReRegisterMap() {
		try {
			BattleMap battleMap = new BattleMap("name");
			BattleMapUtils.registerMap(battleMap);
			BattleMapUtils.registerMap(battleMap);
			assert false;
		} catch (MapAlreadyExistException e) {
			assert true;
		}
	}

	@Test
	public void testRegisterMap() {
		try {
			BattleMap battleMap = new BattleMap("name");
			BattleMap map2 = BattleMapUtils.registerMap(battleMap);
			assert battleMap.equals(map2);
		} catch (MapAlreadyExistException e) {
			assert false;
		}
	}

	@Test
	public void testRemoveMapMap() {
		try {
			BattleMap battleMap = new BattleMap("name");
			BattleMapUtils.registerMap(battleMap);
			BattleMapUtils.removeMap(battleMap);
			BattleMap map2 = BattleMapUtils.selectMap(battleMap.getName());
			assert battleMap.equals(map2);
		} catch (MapAlreadyExistException | MapNotExistException e) {
			assert true;
		}
	}

	@Test
	public void testRemoveMapString() {
		try {
			BattleMap battleMap = new BattleMap("name");
			BattleMapUtils.registerMap(battleMap);
			BattleMapUtils.removeMap(battleMap.getName());
			BattleMap map2 = BattleMapUtils.selectMap(battleMap.getName());
			assert battleMap.equals(map2);
		} catch (MapAlreadyExistException | MapNotExistException e) {
			assert true;
		}
	}

	@Test
	public void testDoStep() {
		fail("Not yet implemented");
	}

}
