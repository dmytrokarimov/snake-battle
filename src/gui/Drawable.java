package gui;

import java.awt.Point;

/**
 * ������� ��������� ��� ����������� ���������
 * @author Karimov
 *
 */
public interface Drawable {
	/**
	 *  ����� �������� �� �����
	 * @return 
	 */
	public abstract void draw();
	
	/**
	 * �������� �� ��, ��������� �� ����� ������ �������
	 * @param p - �����, �� ������� ������ ������
	 * @return <b>true</b> ���� ���� ������ 
	 */
	public boolean pointAt(Point p);
	
	/**
	 * �����, � ������� ������ ������� ���������
	 */
	public Point getCoord();
}
