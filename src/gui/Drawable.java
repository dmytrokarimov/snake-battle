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
	public void setCoord(Point p);
	
	/**
	 * �����, � ������� ������ ������� ���������
	 */
	public Point getCoord();
	
	public int getWidth();

	public void setWidth(int width);

	public int getHeight();

	public void setHeight(int height);
}
