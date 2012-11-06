package logic;

import java.awt.Point;
import gui.Element;
import gui.Element.PARTS;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
								  snake[0].getElements().get(0).getCoord().y));;
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
		};
	}
	// �������� �������... ����� �������� ���� ��������� � class Mind
	/**
	 * ��������� �������� ������ - ������� (��� ����������) 
	 * @return
	 * Action
	 */
	public static Action getInDeadlock() {
		// ��������� ���������� �������
		Random r = new Random();
		
		// ����� �������
		switch (r.nextInt(4)){
			case 0: return getUp();
			case 1: return getDown();
			case 2: return getLeft();
			case 3: return getRight();
			default: return getInDeadlock();
		}
	}
}