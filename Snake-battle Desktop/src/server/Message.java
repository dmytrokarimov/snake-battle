package server;

import java.io.Serializable;
import java.util.List;

import logic.Map;
import logic.Snake;
import gui.Common.ActionList;

/**
 * Сообщение, которое передаётся от сервера клиентам
 * @author RED Developer
 */
public class Message implements Serializable{
	private static final long serialVersionUID = -1806470678368730150L;
	// Карта, на которой проводилась битва
	private Map map;
	// Змеи, которые принимали участие в битве
	private Snake[] snakes;
	// Список действий, которые совершили змейки в битве
	private List<ActionList> al;
	
	public Message(Map map, Snake[] snakes, List<ActionList> al){
		this.map = map;
		this.snakes = snakes;
		this.al = al;
	}
	
	public Map getMap() {
		return map;
	}
	/*public void setMap(Map map) {
		this.map = map;
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
