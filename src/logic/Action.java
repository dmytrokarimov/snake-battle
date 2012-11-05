package logic;

// Класс описывает интерфейс для выполнения какого-либо действия
public abstract class Action{ //extends ActionFactory {
	// Метод вызывается при каком-либо действии
	abstract boolean doAction(Map map, Snake... snake);
}