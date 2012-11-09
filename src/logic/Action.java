package logic;

/**
 *  ����� ��������� ��������� ��� ���������� ������-���� ��������
 * @author RED Developer
 */
public abstract class Action extends ActionFactory {
	/**
	 * ������������ ����� ��������, ������� ����� ����������� �� �������
	 * @author RED Developer
	 */
	public enum ACTION_TYPE {
		UP, DOWN, LEFT, RIGHT, EAT_TAIL, LEAVE_BATTLE, IN_DEAD_LOCK;
	}
	/**
	 *  ����� ���������� ��� �����-���� ��������
	 * @param snake
	 */
	public abstract void doAction(Snake... snake);
	
	/**
	 * ���������� ��� ��������, ���������� ��� ������ 
	 * @return TYPE
	 */
	public abstract ACTION_TYPE getType();
}