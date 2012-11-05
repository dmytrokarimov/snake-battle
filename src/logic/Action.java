package logic;

/**
 *  Класс описывает интерфейс для выполнения какого-либо действия
 * @author RED Developer
 */
public abstract class Action extends ActionFactory {
	/**
	 *  Метод вызывается при каком-либо действии
	 * @param snake
	 */
	abstract void doAction(Snake... snake);
}