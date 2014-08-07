package org.snakebattle.logic;

import org.snakebattle.utils.BattleMapUtils.ActionList;

public interface Mind extends Cloneable{
	public Object clone() throws CloneNotSupportedException;

	public ActionList getAction(BattleMap m);
}
