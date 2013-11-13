package org.snakebattle.logic;

import java.awt.Point;

import org.snakebattle.gui.engine.snake.Element;
import org.snakebattle.gui.engine.snake.Element.PARTS;

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
			/**
			 * 
			 */
			private static final long serialVersionUID = 5186996567560235054L;

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
			public ACTION_TYPE getType() {
				return ACTION_TYPE.UP;
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
			/**
			 * 
			 */
			private static final long serialVersionUID = 2693849212560051141L;

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
			public ACTION_TYPE getType() {
				return ACTION_TYPE.DOWN;
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
			/**
			 * 
			 */
			private static final long serialVersionUID = 8329109675822870643L;

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
			public ACTION_TYPE getType() {
				return ACTION_TYPE.LEFT;
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
			/**
			 * 
			 */
			private static final long serialVersionUID = -4971390959079220777L;

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
			public ACTION_TYPE getType() {
				return ACTION_TYPE.RIGHT;
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
			/**
			 * 
			 */
			private static final long serialVersionUID = 5108913997649018894L;

			@Override
			public void doAction(Snake... snake) {				
				/* Рост размеров 0-ой змейки */
				// Увеличение количества элементов змейки
				Element el = new Element(PARTS.BODY, null,
								  snake[0].getElements().get(1).getWidth(),
								  snake[0].getElements().get(1).getHeight(),
								  snake[0]);
				// Явно задаёт необходимые координаты для нового элемента змейки
				el.setCoord(new Point(snake[0].getElements().get(0).getCoord().x,
								  	  snake[0].getElements().get(0).getCoord().y));

				// Задание нового положения головы = хвост поедаемой змейки
				snake[0].getElements().get(0).setCoord(
						new Point(snake[1].getElements().get(snake[1].getElements().size() - 1).getCoord().x,
								  snake[1].getElements().get(snake[1].getElements().size() - 1).getCoord().y));

				/* Сокращение размеров 1-ой змейки */
				// Изменение координат хвоста
				snake[1].getElements().get(snake[1].getElements().size() - 1).setCoord(
						new Point(snake[1].getElements().get(snake[1].getElements().size() - 2).getCoord().x,
								  snake[1].getElements().get(snake[1].getElements().size() - 2).getCoord().y));

				// Уменьшение количества элементов змейки 1
				snake[1].getElements().remove(snake[1].getElements().size() - 2);
				// Если остался всего 1 элемент - доъесть полностью
				if (snake[1].getElements().size() == 1)
					snake[1].getElements().remove(0);
				
				// Добавляет новый элемент в змейку 0
				snake[0].getElements().add(1, el);
				}

			@Override
			public ACTION_TYPE getType() {
				return ACTION_TYPE.EAT_TAIL;
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
			/**
			 * 
			 */
			private static final long serialVersionUID = -3369284277823385855L;

			@Override
			public void doAction(Snake... snake) {
			}

			@Override
			public ACTION_TYPE getType() {
				return ACTION_TYPE.IN_DEAD_LOCK;
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
			/**
			 * 
			 */
			private static final long serialVersionUID = -4216255007609930437L;

			@Override
			public void doAction(Snake... snake) {
			}

			@Override
			public ACTION_TYPE getType() {
				return ACTION_TYPE.LEAVE_BATTLE;
			}
		};
	}
	
	/**
	 * Возвращает действие по введённой команде
	 * @param command
	 * @return Action
	 */
	public static Action getValue(String command){
		switch (command) {
		case "getUp":
			return getUp();
		case "getDown":
			return getDown();
		case "getLeft":
			return getLeft();
		case "getRight":
			return getRight();
		case "getEatTail":
			return getEatTail();
		case "getInDeadLock":
			return getInDeadlock();
		case "getLeaveBattle":
			return getLeaveBattle();
		default: return getInDeadlock();
		}
	}
}