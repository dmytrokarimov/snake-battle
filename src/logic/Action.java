package logic;

/**
 *  Класс описывает интерфейс для выполнения какого-либо действия
 * @author RED Developer
 */
public abstract class Action extends ActionFactory {
	/**
	 * Перечисление типов действий, которые могут происходить со змейкой
	 * @author RED Developer
	 */
	public enum TYPE {
		UP, DOWN, LEFT, RIGHT, EAT_TAIL, LEAVE_BATTLE, IN_DEAD_LOCK;
	}
	/**
	 *  Метод вызывается при каком-либо действии
	 * @param snake
	 */
	public abstract void doAction(Snake... snake);
	
	/**
	 * Возвращает тип действия, вызванного для змейки 
	 * @param action
	 * @return TYPE
	 */
	public abstract TYPE getType(Action action);
}