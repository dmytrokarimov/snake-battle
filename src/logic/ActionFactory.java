package logic;

import java.awt.Point;
import gui.Element;
import gui.Element.PARTS;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Реализует функции передвижения/действия змейки
 * @author RED Developer
 */
public class ActionFactory {
	/**
	 * Описывает движение змейки вверх 
	 * @return
	 * Action
	 */
	public static Action getUp() {
		return new Action() {
			@Override
			public void doAction(Snake... snake) {
				// Передвижение змейки на 1 клетку вверх
				for(int i = snake[0].getElements().size() - 1; i > 0; i--)
					// Координата текущего элемента змейки равна координате предыдущего
					snake[0].getElements().get(i).setCoord(snake[0].getElements().get(i - 1).getCoord());
				
				// Меняем координату головы змеи
				snake[0].getElements().get(0).setCoord(
						new Point(snake[0].getElements().get(0).getCoord().x,
								  snake[0].getElements().get(0).getCoord().y - snake[0].getElements().get(0).getHeight()));
			}
		};
	}

	/**
	 * Описывает движение змейки вниз 
	 * @return
	 * Action
	 */
	public static Action getDown() {
		return new Action() {
			@Override
			public void doAction(Snake... snake) {
				// Передвижение змейки на 1 клетку вниз
				for(int i = snake[0].getElements().size() - 1; i > 0; i--)
					// Координата текущего элемента змейки равна координате предыдущего
					snake[0].getElements().get(i).setCoord(snake[0].getElements().get(i - 1).getCoord());
				
				// Меняем координату головы змеи
				snake[0].getElements().get(0).setCoord(
						new Point(snake[0].getElements().get(0).getCoord().x,
								  snake[0].getElements().get(0).getCoord().y + snake[0].getElements().get(0).getHeight()));
			}
		};
	}

	/**
	 * Описывает движение змейки влево 
	 * @return
	 * Action
	 */
	public static Action getLeft() {
		return new Action() {
			@Override
			public void doAction(Snake... snake) {
				// Передвижение змейки на 1 клетку влево
				for(int i = snake[0].getElements().size() - 1; i > 0; i--)
					// Координата текущего элемента змейки равна координате предыдущего
					snake[0].getElements().get(i).setCoord(snake[0].getElements().get(i - 1).getCoord());
				
				// Меняем координату головы змеи
				snake[0].getElements().get(0).setCoord(
						new Point(snake[0].getElements().get(0).getCoord().x - snake[0].getElements().get(0).getWidth(),
								  snake[0].getElements().get(0).getCoord().y));
			}
		};
	}

	/**
	 * Описывает движение змейки вправо 
	 * @return
	 * Action
	 */
	public static Action getRight() {
		return new Action() {
			@Override
			public void doAction(Snake... snake) {				
				// Передвижение змейки на 1 клетку вправо
				for(int i = snake[0].getElements().size() - 1; i > 0; i--)
					// Координата текущего элемента змейки равна координате предыдущего
					snake[0].getElements().get(i).setCoord(snake[0].getElements().get(i - 1).getCoord());
				
				// Меняем координату головы змеи
				snake[0].getElements().get(0).setCoord(
						new Point(snake[0].getElements().get(0).getCoord().x + snake[0].getElements().get(0).getWidth(),
								  snake[0].getElements().get(0).getCoord().y));;
			}
		};
	}

	/**
	 * Описывает действие змейки - поедание змейкой snake[0] хвоста змейки snake[1] 
	 * @return
	 * Action
	 */
	public static Action getEatTail() {
		return new Action() {
			@Override
			public void doAction(Snake... snake) {				
				/* Рост размеров 0-ой змейки */
				// Увеличение количества элементов змейки
				Element el = new Element(PARTS.BODY, 
						new Point(snake[0].getElements().get(0).getCoord().x,
								  snake[0].getElements().get(0).getCoord().y),
								  snake[0].getElements().get(1).getWidth(),
								  snake[0].getElements().get(1).getHeight(),
								  snake[0]);
				snake[0].getElements().add(1, el);
				// Задание нового положения головы = хвост поедаемой змейки
				snake[0].getElements().get(0).setCoord(
						new Point(snake[1].getElements().get(snake[1].getElements().size() - 1).getCoord().x,
								  snake[1].getElements().get(snake[1].getElements().size() - 1).getCoord().y));

				/* Сокращение размеров 1-ой змейки */
				// Изменение координат хвоста
				snake[1].getElements().get(snake[1].getElements().size() - 1).setCoord(
						new Point(snake[1].getElements().get(snake[1].getElements().size() - 2).getCoord().x,
								  snake[1].getElements().get(snake[1].getElements().size() - 2).getCoord().y));
				// Уменьшение количества элементов змейки
				snake[1].getElements().remove(snake[1].getElements().size() - 2);
				}
		};
	}
	// Тестовый вариант... Выбор действия надо перенести в class Mind
	/**
	 * Описывает действие змейки - простой (ход невозможен) 
	 * @return
	 * Action
	 */
	public static Action getInDeadlock() {
		// Генерация случайного события
		Random r = new Random();
		
		// Выбор события
		switch (r.nextInt(4)){
			case 0: return getUp();
			case 1: return getDown();
			case 2: return getLeft();
			case 3: return getRight();
			default: return getInDeadlock();
		}
	}
}