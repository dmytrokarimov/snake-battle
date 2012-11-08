package server;

import gui.Element;
import gui.Element.PARTS;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import logic.Action;
import logic.Snake;

/**
 * Класс для инициализации битвы и расчёта её исхода
 * @author RED Developer
 */
public class Battle {
	// Центр экрана !!!!!Размеры экрана (необходимо как-то получать)!!!!!!
	private int centerX = (int) 400;
	private int centerY = (int) 300;
	
	// В битве принимают участие до snakeLimit змеек
	private final byte snakeLimit = 4;
	// Количество змеек в заявке
	private byte snakeCount = 0;
	// Начальная длина каждой змейки
	private final byte snakeLength = 20;
	// Начальная координата 0-й змейки (относительно центра)
	private Point snakeStart = new Point(0, -100);
	// Размер ячейки змеек
	private final Point snakeSize = new Point (10, 10);
	// Множтели поправок для координат змеиных голов
	private int headX = -1, headY = -1;
	// Множтели поправок для координат змеиных элементов тела (и хвоста)
	private int bodyX = 0, bodyY = 0;
	
	/**
	 * Проводит нормализацию координаты относительно центра экрана
	 * @param x
	 * @param y
	 * @return
	 */
	private Point normal(int x, int y)
	{
		// Координаты центра экрана
		return new Point(x + centerX, y + centerY);
	}
	/**
	 * Проводит нормализацию координаты относительно центра экрана
	 * @param coord
	 * @param screen
	 * @return Point
	 */
	private Point normal(Point coord)
	{
		// Координаты центра экрана
		return new Point(coord.x + centerX, coord.y + centerY);
	}
	/**
	 * Осуществляет поворот координаты на 90 градусов
	 * @param coord
	 * @return
	 */
	private Point coordCorrect(Point coord)
	{
		if (coord.x != 0) headX *= -1;
		if (coord.y != 0) headY *= -1;
		Point p = new Point (coord.x + headX * 10 * snakeSize.x, coord.y + headY * 10 * snakeSize.y);
		
		return p;
	}
	
	/**
	 * Инициализирует змейки
	 * @param snake
	 * 				- змейки, заявленные на бой
	 */
	public void init(Snake[] snake){
		// Количество змеек, заявленных на бой 
		byte snakes = (byte) snake.length;
		// Реальное количество змеек в заявке
		for (byte i = 0; i < snakes; i++)
			if (snake[i] != null) snakeCount++;
		// Счётчик змеек
		byte iSnake = 0;
		// Для создания элементов змеек
		List<Element> el = new ArrayList<Element>();;
		
		// Инициализация змеек
		while (iSnake < snakeLimit && iSnake < snakes)
		{
			snakeStart = coordCorrect(snakeStart);
			// Если змей 2 - поставить друг напротив друга 
			if (snakeCount == 2) snakeStart = coordCorrect(snakeStart);
			
			if (snake[iSnake] != null) {
				// Новая голова для змейки iSnake. "head? * snakeSize.?" - поправочный коэффициент
				el.add(new Element(PARTS.HEAD, normal(snakeStart.x,
						snakeStart.y), snakeSize.x, snakeSize.y, snake[iSnake]));

				// В какую сторону отстраивать тело
				if (snakeStart.x < 0 && snakeStart.y == 0) {
					// Влево
					bodyX = -1;
					bodyY = 0;
				} else if (snakeStart.x > 0 && snakeStart.y == 0) {
					// Вправо
					bodyX = 1;
					bodyY = 0;
				} else if (snakeStart.x == 0 && snakeStart.y < 0) {
					// Вверх
					bodyX = 0;
					bodyY = -1;
				} else if (snakeStart.x == 0 && snakeStart.y > 0) {
					// Вниз
					bodyX = 0;
					bodyY = 1;
				}
				// Строим тело и хвост
				for (byte i = 1; i < snakeLength - 1; i++) {
					el.add(new Element(PARTS.BODY, new Point(
							el.get(i - 1).getCoord().x + snakeSize.x * bodyX, 
							el.get(i - 1).getCoord().y + snakeSize.y * bodyY), 
							snakeSize.x, snakeSize.y, snake[iSnake]));
				}
				el.add(new Element(PARTS.TAIL, new Point(
						el.get(snakeLength - 2).getCoord().x + snakeSize.x * bodyX, 
						el.get(snakeLength - 2).getCoord().y + snakeSize.y * bodyY), 
						snakeSize.x, snakeSize.y, snake[iSnake]));

				// Добавление элементов в змейку
				snake[iSnake].setElements(el);
				// Для новой змейки будет новый список элементов
				el.clear();
			}
			// Следующая змея
			iSnake++;
		}
	}
	
	/**
	 * Инициализирует начало битвы и записывает ёё результаты
	 * @param snake
	 * @return List of Actions
	 */
	protected List<Action> Start(Snake[] snake){
		
		
		return new ArrayList<Action>();
	}
	// =====================================================================
	// ===========================Для тестирования==========================
	// =====================================================================
	/*
	public static void main(String[] args){
		Battle b = new Battle();
		
		Snake snake0 = new Snake();
		Snake snake1 = new Snake();
		Snake snake2 = new Snake();
		Snake snake3 = new Snake();
		
		b.init(new Snake[]{snake0, snake1, snake2, snake3});
		System.out.println(snake0.getElements().get(0).getCoord().x + "; " + snake0.getElements().get(0).getCoord().y);
		System.out.println(snake1.getElements().get(0).getCoord().x + "; " + snake1.getElements().get(0).getCoord().y);
		if (snake2 != null) System.out.println(snake2.getElements().get(0).getCoord().x + "; " + snake2.getElements().get(0).getCoord().y);
		System.out.println(snake3.getElements().get(0).getCoord().x + "; " + snake3.getElements().get(0).getCoord().y);
	}*/
}
