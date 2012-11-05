package logic;

/**
 *  ����� ��������� ��������� ��� ���������� ������-���� ��������
 * @author RED Developer
 */
public abstract class Action extends ActionFactory {
	/**
	 *  ����� ���������� ��� �����-���� ��������
	 * @param snake
	 */
	abstract void doAction(Snake... snake);
}