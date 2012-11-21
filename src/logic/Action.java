package logic;

import java.io.Serializable;

/**
 *  Класс описывает интерфейс для выполнения какого-либо действия
 * @author RED Developer
 */
public abstract class Action implements Serializable{
	/**
	 * Перечисление типов действий, которые могут происходить со змейкой
	 * @author RED Developer
	 */
	public enum ACTION_TYPE {
		UP, DOWN, LEFT, RIGHT, EAT_TAIL, LEAVE_BATTLE, IN_DEAD_LOCK;
	}
	/**
	 *  Метод вызывается при каком-либо действии
	 * @param snake
	 */
	public abstract void doAction(Snake... snake);
	
	/**
	 * Возвращает тип действия, вызванного для змейки 
	 * @return TYPE
	 */
	public abstract ACTION_TYPE getType();
}