package logic;

import java.awt.Point;

import javax.swing.JOptionPane;

import gui.Element;
import gui.Element.PARTS;

/**
 * ��������� ������� ������������/�������� ������
 * @author RED Developer
 */
public class ActionFactory {
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
			public TYPE getType(Action action) {
				return TYPE.UP;
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
			public TYPE getType(Action action) {
				return TYPE.DOWN;
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
			public TYPE getType(Action action) {
				return TYPE.LEFT;
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
			public TYPE getType(Action action) {
				return TYPE.RIGHT;
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
				Element el = new Element(PARTS.BODY, 
						new Point(snake[0].getElements().get(0).getCoord().x,
								  snake[0].getElements().get(0).getCoord().y),
								  snake[0].getElements().get(1).getWidth(),
								  snake[0].getElements().get(1).getHeight(),
								  snake[0]);
				snake[0].getElements().add(1, el);
				// ������� ������ ��������� ������ = ����� ��������� ������
				snake[0].getElements().get(0).setCoord(
						new Point(snake[1].getElements().get(snake[1].getElements().size() - 1).getCoord().x,
								  snake[1].getElements().get(snake[1].getElements().size() - 1).getCoord().y));

				/* ���������� �������� 1-�� ������ */
				// ��������� ��������� ������
				snake[1].getElements().get(snake[1].getElements().size() - 1).setCoord(
						new Point(snake[1].getElements().get(snake[1].getElements().size() - 2).getCoord().x,
								  snake[1].getElements().get(snake[1].getElements().size() - 2).getCoord().y));
				// ���������� ���������� ��������� ������
				snake[1].getElements().remove(snake[1].getElements().size() - 2);
				}

			@Override
			public TYPE getType(Action action) {
				return TYPE.EAT_TAIL;
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
			public TYPE getType(Action action) {
				return TYPE.IN_DEAD_LOCK;
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
				JOptionPane.showMessageDialog(null, "�� ������� ���! ��� ���� �������� ���!!!");
			}

			@Override
			public TYPE getType(Action action) {
				return TYPE.LEAVE_BATTLE;
			}
		};
	}
}