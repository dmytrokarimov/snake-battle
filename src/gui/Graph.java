package gui;

import java.awt.Point;

/**
 * �������� ����� ��� ������ �������
 * @author Karimov
 */
public abstract class Graph implements Drawable{
	protected Point coord;
	protected int width;
	protected int height;
	
	/**
	 * ���� ����� ������ ���������� ������
	 * @param coord ������� ����� �����
	 * @param width ������
	 * @param height ������
	 */
	public Graph(Point coord, int width, int height){
		if(coord == null) coord = new Point(0,0);
		this.coord = coord;
		this.width = width;
		this.height = height;
	}
	

	
	/**
	 * �������� �� ��, ��������� �� ����� ������ �������
	 * @param p - �����, �� ������� ������ ������
	 * @return <b>true</b> ���� ���� ������ 
	 */
	public boolean pointAt(Point p){
		return (p.x > coord.x && p.x < coord.x + width) && (p.y > coord.y && p.y < coord.y + height);
	} 
	
	/**
	 * ���������� ��� �������� � ��. ��������
	 * @param obj � ����� �������� ��������� ��������
	 * @return ���������� true ���� �������� ���������
	 */
	public abstract boolean onCollision(Graph obj);

	/**
	 * �����, � ������� ������ ������� ���������
	 */
	public Point getCoord() {
		return coord;
	}

	public void setCoord(Point coord) {
		if(coord == null) return;
		Screen.instance.clear(getCoord(), getWidth(), getHeight());
		this.coord = coord;
		draw();
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		Screen.instance.clear(getCoord(), getWidth(), getHeight());
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		Screen.instance.clear(getCoord(), getWidth(), getHeight());
		this.height = height;
	} 
	
	
}
