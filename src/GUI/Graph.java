package GUI;

import java.awt.Point;

/**
 * �������� ����� ��� ������ �������
 * @author Karimov
 */
public abstract class Graph {
	/**
	 *  ����� �������� �� �����
	 * @return 
	 */
	abstract void draw();
	
	/**
	 * �������� �� ��, ��������� �� ����� ������ �������
	 * @param p - �����, �� ������� ������ ������
	 * @return <b>true</b> ���� ���� ������ 
	 */
	boolean pointAt(Point p){
		return false;
	} 
	
	/**
	 * ���������� ��� �������� � ��. ��������
	 * @param obj � ����� �������� ��������� ��������
	 */
	abstract void onCollision(Graph obj); 
	
	/**
	 * �����, � ������� ������ ������� ���������
	 * @return
	 */
	Point getCoord(){
		return null;
	}
	
	void setCoord(Point p){
		
	}
}
