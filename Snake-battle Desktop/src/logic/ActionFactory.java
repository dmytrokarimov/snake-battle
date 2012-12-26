package logic;

import gui.Element;
import gui.Element.PARTS;
import gui.Point;

/**
 * ��������� ������� ������������/�������� ������
 * @author RED Developer
 */
public class ActionFactory {
	private static final long serialVersionUID = 1L;

	/**
	 * ��������� �������� ������ ����� 
	 * @return
	 * Action
	 */
	public static Action getUp() {
		return new Action() {
			@Override
			public void doAction(Snake... snake) {
				// ������������ ������ �� 1 ������ �����
				for(int i = snake[0].getElements().size() - 1; i > 0; i--)
					// ���������� �������� �������� ������ ����� ���������� �����������
					snake[0].getElements().get(i).setCoord(snake[0].getElements().get(i - 1).getCoord());
				
				// ������ ���������� ������ ����
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
	 * ��������� �������� ������ ���� 
	 * @return
	 * Action
	 */
	public static Action getDown() {
		return new Action() {
			@Override
			public void doAction(Snake... snake) {
				// ������������ ������ �� 1 ������ ����
				for(int i = snake[0].getElements().size() - 1; i > 0; i--)
					// ���������� �������� �������� ������ ����� ���������� �����������
					snake[0].getElements().get(i).setCoord(snake[0].getElements().get(i - 1).getCoord());
				
				// ������ ���������� ������ ����
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
	 * ��������� �������� ������ ����� 
	 * @return
	 * Action
	 */
	public static Action getLeft() {
		return new Action() {
			@Override
			public void doAction(Snake... snake) {
				// ������������ ������ �� 1 ������ �����
				for(int i = snake[0].getElements().size() - 1; i > 0; i--)
					// ���������� �������� �������� ������ ����� ���������� �����������
					snake[0].getElements().get(i).setCoord(snake[0].getElements().get(i - 1).getCoord());
				
				// ������ ���������� ������ ����
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
	 * ��������� �������� ������ ������ 
	 * @return
	 * Action
	 */
	public static Action getRight() {
		return new Action() {
			@Override
			public void doAction(Snake... snake) {
				// ������������ ������ �� 1 ������ ������
				for(int i = snake[0].getElements().size() - 1; i > 0; i--)
					// ���������� �������� �������� ������ ����� ���������� �����������
					snake[0].getElements().get(i).setCoord(snake[0].getElements().get(i - 1).getCoord());
				
				// ������ ���������� ������ ����
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
	 * ��������� �������� ������ - �������� ������� snake[0] ������ ������ snake[1] 
	 * @return
	 * Action
	 */
	public static Action getEatTail() {
		return new Action() {
			@Override
			public void doAction(Snake... snake) {				
				/* ���� �������� 0-�� ������ */
				// ���������� ���������� ��������� ������
				Element el = new Element(PARTS.BODY, null,
								  snake[0].getElements().get(1).getWidth(),
								  snake[0].getElements().get(1).getHeight(),
								  snake[0]);
				// ���� ����� ����������� ���������� ��� ������ �������� ������
				el.setCoord(new Point(snake[0].getElements().get(0).getCoord().x,
								  	  snake[0].getElements().get(0).getCoord().y));

				// ������� ������ ��������� ������ = ����� ��������� ������
				snake[0].getElements().get(0).setCoord(
						new Point(snake[1].getElements().get(snake[1].getElements().size() - 1).getCoord().x,
								  snake[1].getElements().get(snake[1].getElements().size() - 1).getCoord().y));

				/* ���������� �������� 1-�� ������ */
				// ��������� ��������� ������
				snake[1].getElements().get(snake[1].getElements().size() - 1).setCoord(
						new Point(snake[1].getElements().get(snake[1].getElements().size() - 2).getCoord().x,
								  snake[1].getElements().get(snake[1].getElements().size() - 2).getCoord().y));

				// ���������� ���������� ��������� ������ 1
				snake[1].getElements().remove(snake[1].getElements().size() - 2);
				// ���� ������� ����� 1 ������� - ������� ���������
				if (snake[1].getElements().size() == 1)
					snake[1].getElements().remove(0);
				
				// ��������� ����� ������� � ������ 0
				snake[0].getElements().add(1, el);
				}

			@Override
			public ACTION_TYPE getType() {
				return ACTION_TYPE.EAT_TAIL;
			}
		};
	}
	
	/**
	 * ��������� �������� ������ - ������� (��� ����������) 
	 * @return
	 * Action
	 */
	public static Action getInDeadlock() {
		return new Action() {
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
	 * ��������� �������� ������ - ����� � ���� ���
	 * @return
	 * Action
	 */
	public static Action getLeaveBattle() {
		return new Action() {
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
	 * ���������� �������� �� �������� �������
	 * @param command
	 * @return Action
	 */
	public Object getValue(String command){
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