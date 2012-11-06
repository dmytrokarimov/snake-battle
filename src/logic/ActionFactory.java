package logic;

import java.awt.Point;
import gui.Element;
import gui.Element.PARTS;

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
				/*
				// Передвижение змейки на 1 клетку вверх
				for(int i = snake[0].getElements().size() - 1; i > 0; i--)
					// Координата текущего элемента змейки равна координате предыдущего
					snake[0].getElements().get(i).setCoord(snake[0].getElements().get(i - 1).getCoord());
				
				// Меняем координату головы змеи
				snake[0].getElements().get(0).setCoord(
						new Point(snake[0].getElements().get(0).getCoord().x,
								  snake[0].getElements().get(0).getCoord().y - snake[0].getElements().get(0).getHeight()));
				*/
				/* Альтернативный вариант */
				// Изменение координат хвоста
				snake[0].getElements().get(snake[0].getElements().size() - 1).setCoord(
						new Point(snake[0].getElements().get(snake[0].getElements().size() - 2).getCoord().x,
								  snake[0].getElements().get(snake[0].getElements().size() - 2).getCoord().y));
				// Удаление предхвостового элемента
				snake[0].getElements().remove(snake[0].getElements().size() - 2);
				
				// Создание нового элемента
				Element el = new Element(PARTS.BODY, 
						new Point(snake[0].getElements().get(0).getCoord().x,
								  snake[0].getElements().get(0).getCoord().y),
								  snake[0].getElements().get(1).getWidth(),
								  snake[0].getElements().get(1).getHeight(),
								  snake[0]);
				// Добавление нового послеголовного элемента
				snake[0].getElements().add(1, el);
				// Изменение координт головы
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
				/*
				// Передвижение змейки на 1 клетку вниз
				for(int i = snake[0].getElements().size() - 1; i > 0; i--)
					// Координата текущего элемента змейки равна координате предыдущего
					snake[0].getElements().get(i).setCoord(snake[0].getElements().get(i - 1).getCoord());
				
				// Меняем координату головы змеи
				snake[0].getElements().get(0).setCoord(
						new Point(snake[0].getElements().get(0).getCoord().x,
								  snake[0].getElements().get(0).getCoord().y + snake[0].getElements().get(0).getHeight()));
				*/
				
				/* Альтернативный вариант */
				// Изменение координат хвоста
				snake[0].getElements().get(snake[0].getElements().size() - 1).setCoord(
						new Point(snake[0].getElements().get(snake[0].getElements().size() - 2).getCoord().x,
								  snake[0].getElements().get(snake[0].getElements().size() - 2).getCoord().y));
				// Удаление предхвостового элемента
				snake[0].getElements().remove(snake[0].getElements().size() - 2);
				
				// Создание нового элемента
				Element el = new Element(PARTS.BODY, 
						new Point(snake[0].getElements().get(0).getCoord().x,
								  snake[0].getElements().get(0).getCoord().y),
								  snake[0].getElements().get(1).getWidth(),
								  snake[0].getElements().get(1).getHeight(),
								  snake[0]);
				// Добавление нового послеголовного элемента
				snake[0].getElements().add(1, el);
				// Изменение координт головы
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
				/*
				// Передвижение змейки на 1 клетку влево
				for(int i = snake[0].getElements().size() - 1; i > 0; i--)
					// Координата текущего элемента змейки равна координате предыдущего
					snake[0].getElements().get(i).setCoord(snake[0].getElements().get(i - 1).getCoord());
				
				// Меняем координату головы змеи
				snake[0].getElements().get(0).setCoord(
						new Point(snake[0].getElements().get(0).getCoord().x - snake[0].getElements().get(0).getWidth(),
								  snake[0].getElements().get(0).getCoord().y));
				*/
				
				/* Альтернативный вариант */
				// Изменение координат хвоста
				snake[0].getElements().get(snake[0].getElements().size() - 1).setCoord(
						new Point(snake[0].getElements().get(snake[0].getElements().size() - 2).getCoord().x,
								  snake[0].getElements().get(snake[0].getElements().size() - 2).getCoord().y));
				// Удаление предхвостового элемента
				snake[0].getElements().remove(snake[0].getElements().size() - 2);
				
				// Создание нового элемента
				Element el = new Element(PARTS.BODY, 
						new Point(snake[0].getElements().get(0).getCoord().x,
								  snake[0].getElements().get(0).getCoord().y),
								  snake[0].getElements().get(1).getWidth(),
								  snake[0].getElements().get(1).getHeight(),
								  snake[0]);
				// Добавление нового послеголовного элемента
				snake[0].getElements().add(1, el);
				// Изменение координт головы
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
				/*
				// Передвижение змейки на 1 клетку вправо
				for(int i = snake[0].getElements().size() - 1; i > 0; i--)
					// Координата текущего элемента змейки равна координате предыдущего
					snake[0].getElements().get(i).setCoord(snake[0].getElements().get(i - 1).getCoord());
				
				// Меняем координату головы змеи
				snake[0].getElements().get(0).setCoord(
						new Point(snake[0].getElements().get(0).getCoord().x + snake[0].getElements().get(0).getWidth(),
								  snake[0].getElements().get(0).getCoord().y));
				*/
				
				/* Альтернативный вариант */
				// Изменение координат хвоста
				snake[0].getElements().get(snake[0].getElements().size() - 1).setCoord(
						new Point(snake[0].getElements().get(snake[0].getElements().size() - 2).getCoord().x,
								  snake[0].getElements().get(snake[0].getElements().size() - 2).getCoord().y));
				// Удаление предхвостового элемента
				snake[0].getElements().remove(snake[0].getElements().size() - 2);
				
				// Создание нового элемента
				Element el = new Element(PARTS.BODY, 
						new Point(snake[0].getElements().get(0).getCoord().x,
								  snake[0].getElements().get(0).getCoord().y),
								  snake[0].getElements().get(1).getWidth(),
								  snake[0].getElements().get(1).getHeight(),
								  snake[0]);
				// Добавление нового послеголовного элемента
				snake[0].getElements().add(1, el);
				// Изменение координт головы
				snake[0].getElements().get(0).setCoord(
						new Point(snake[0].getElements().get(0).getCoord().x + snake[0].getElements().get(0).getWidth(),
								  snake[0].getElements().get(0).getCoord().y));
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
	
	/**
	 * Описывает действие змейки - простой (ход невозможен) 
	 * @return
	 * Action
	 */
	public static Action getInDeadlock() {
		return new Action() {
			@Override
			public void doAction(Snake... snake) {
			}
		};
	}
}