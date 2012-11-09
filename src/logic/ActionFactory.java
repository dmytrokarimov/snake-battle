package logic;

import java.awt.Point;

import javax.swing.JOptionPane;

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
				// Передвижение змейки на 1 клетку вверх
				for(int i = snake[0].getElements().size() - 1; i > 0; i--)
					// Координата текущего элемента змейки равна координате предыдущего
					snake[0].getElements().get(i).setCoord(snake[0].getElements().get(i - 1).getCoord());
				
				// Меняем координату головы змеи
				snake[0].getElements().get(0).setCoord(
						new Point(snake[0].getElements().get(0).getCoord().x,
								  snake[0].getElements().get(0).getCoord().y - snake[0].getElements().get(0).getHeight()));
			}

			@Override
			public TYPE getType(Action action) {
				return TYPE.UP;
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

			@Override
			public TYPE getType(Action action) {
				return TYPE.DOWN;
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

			@Override
			public TYPE getType(Action action) {
				return TYPE.LEFT;
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
								  snake[0].getElements().get(0).getCoord().y));
			}

			@Override
			public TYPE getType(Action action) {
				return TYPE.RIGHT;
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

			@Override
			public TYPE getType(Action action) {
				return TYPE.EAT_TAIL;
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

			@Override
			public TYPE getType(Action action) {
				return TYPE.IN_DEAD_LOCK;
			}
		};
	}
	
	/**
	 * Описывает действие змейки - побег с поля боя
	 * @return
	 * Action
	 */
	public static Action getLeaveBattle() {
		return new Action() {
			@Override
			public void doAction(Snake... snake) {
				JOptionPane.showMessageDialog(null, "Вы сбежали боя! Бог змей покарает Вас!!!");
			}

			@Override
			public TYPE getType(Action action) {
				return TYPE.LEAVE_BATTLE;
			}
		};
	}
}