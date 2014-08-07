package org.snakebattle.logic;

import java.io.Serializable;


/**
 *  ����� ��������� ��������� ��� ���������� ������-���� ��������
 * @author RED Developer
 */
public abstract class Action implements Serializable{
	private static final long serialVersionUID = 8503241747509513740L;

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