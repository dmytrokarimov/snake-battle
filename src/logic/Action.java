package logic;

// ����� ��������� ��������� ��� ���������� ������-���� ��������
public abstract class Action{ //extends ActionFactory {
	// ����� ���������� ��� �����-���� ��������
	abstract boolean doAction(Map map, Snake... snake);
}