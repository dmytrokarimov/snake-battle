package GUI;

import java.awt.Point;

/**
 * �������� ����� ��� ������ �������
 * @author Karimov
 */
public abstract class Graph {
	private Point coord;
	private int width;
	private int height;
	
	/**
	 * ���� ����� ������ ���������� ������
	 * @param coord ������� ����� �����
	 * @param width ������
	 * @param height ������
	 */
	public Graph(Point coord, int width, int height){
		this.coord = coord;
		this.width = width;
		this.height = height;
	}
	
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
	public boolean pointAt(Point p){
		return (p.x >= coord.x && p.x <= coord.x + width) && (p.y >= coord.y && p.y <= coord.y + height);
	} 
	
	/**
	 * ���������� ��� �������� � ��. ��������
	 * @param obj � ����� �������� ��������� ��������
	 */
	abstract void onCollision(Graph obj);

	/**
	 * �����, � ������� ������ ������� ���������
	 */
	public Point getCoord() {
		return coord;
	}

	public void setCoord(Point coord) {
		this.coord = coord;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	} 
	
	
}
