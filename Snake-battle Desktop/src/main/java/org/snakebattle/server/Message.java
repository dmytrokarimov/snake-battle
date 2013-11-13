package org.snakebattle.server;

import java.io.Serializable;
import java.util.List;

import org.snakebattle.logic.BattleMap;
import org.snakebattle.logic.Snake;
import org.snakebattle.utils.BattleMapUtils.ActionList;

/**
 * ���������, ������� ��������� �� ������� ��������
 * @author RED Developer
 */
public class Message implements Serializable{
	private static final long serialVersionUID = -1806470678368730150L;
	// �����, �� ������� ����������� �����
	private BattleMap battleMap;
	// ����, ������� ��������� ������� � �����
	private Snake[] snakes;
	// ������ ��������, ������� ��������� ������ � �����
	private List<ActionList> al;
	
	public Message(BattleMap battleMap, Snake[] snakes, List<ActionList> al){
		this.battleMap = battleMap;
		this.snakes = snakes;
		this.al = al;
	}
	
	public BattleMap getMap() {
		return battleMap;
	}
	/*public void setMap(BattleMap battleMap) {
		this.map = battleMap;
	}*/
	public Snake[] getSnakes() {
		return snakes;
	}
	/*public void setSnakes(Snake[] snakes) {
		this.snakes = snakes;
	}*/
	public List<ActionList> getAl() {
		return al;
	}
	/*public void setAl(List<ActionList> al) {
		this.al = al;
	}*/

}
