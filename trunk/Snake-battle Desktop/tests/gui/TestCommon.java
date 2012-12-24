package gui;

import static org.junit.Assert.*;
import logic.Map;
import gui.Common.MapAlreadyExistException;
import gui.Common.MapNotExistException;

import org.junit.Test;

public class TestCommon {

	@Test
	public void testSelectMapNull() throws MapNotExistException {
		try {
			Common.selectMap("");
			assert false;
		} catch (MapNotExistException e) {
			assert true;
		}
	}

	@Test
	public void testSelectMap() throws MapNotExistException,
			MapAlreadyExistException {
		try {
			Map map = new Map("name");
			Common.registerMap(map);
			Map map2 = Common.selectMap("name");
			assert map.equals(map2);
		} catch (MapNotExistException | MapAlreadyExistException e) {
			assert false;
		}
	}

	@Test
	public void testReRegisterMap() {
		try {
			Map map = new Map("name");
			Common.registerMap(map);
			Common.registerMap(map);
			assert false;
		} catch (MapAlreadyExistException e) {
			assert true;
		}
	}

	@Test
	public void testRegisterMap() {
		try {
			Map map = new Map("name");
			Map map2 = Common.registerMap(map);
			assert map.equals(map2);
		} catch (MapAlreadyExistException e) {
			assert false;
		}
	}

	@Test
	public void testRemoveMapMap() {
		try {
			Map map = new Map("name");
			Common.registerMap(map);
			Common.removeMap(map);
			Map map2 = Common.selectMap(map.getName());
			assert map.equals(map2);
		} catch (MapAlreadyExistException | MapNotExistException e) {
			assert true;
		}
	}

	@Test
	public void testRemoveMapString() {
		try {
			Map map = new Map("name");
			Common.registerMap(map);
			Common.removeMap(map.getName());
			Map map2 = Common.selectMap(map.getName());
			assert map.equals(map2);
		} catch (MapAlreadyExistException | MapNotExistException e) {
			assert true;
		}
	}

	@Test
	public void testDoStep() {
		fail("Not yet implemented");
	}

}
