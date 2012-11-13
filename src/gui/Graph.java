package gui;

import java.awt.Point;

/**
 * �������� ����� ��� ������ �������
 * 
 * @author Karimov
 */
public abstract class Graph implements Drawable {
	protected Point coord;
	protected int width;
	protected int height;
	/**
	 * If graph_on = false this object haven't drawing into screen
	 */
	public boolean graph_on = true;

	/**
	 * ���� ����� ������ ���������� ������
	 * 
	 * @param coord
	 *            ������� ����� �����
	 * @param width
	 *            ������
	 * @param height
	 *            ������
	 */
	public Graph(Point coord, int width, int height) {
		if (coord == null)
			coord = new Point(0, 0);
		this.coord = coord;
		this.width = width;
		this.height = height;
	}

	/**
	 * �������� �� ��, ��������� �� ����� ������ �������
	 * 
	 * @param p
	 *            - �����, �� ������� ������ ������
	 * @return <b>true</b> ���� ���� ������
	 */
	public boolean pointAt(Point p) {
		return (p.x > coord.x && p.x < coord.x + width)
				&& (p.y > coord.y && p.y < coord.y + height);
	}

	/**
	 * ���������� ��� �������� � ��. ��������
	 * 
	 * @param obj
	 *            � ����� �������� ��������� ��������
	 * @return ���������� true ���� �������� ���������, false ���� �� ���������,
	 *         � ����� ���� ��������� ������������ � ������ ��������
	 */
	public abstract boolean onCollision(Graph obj);

	/**
	 * �����, � ������� ������ ������� ���������
	 */
	public Point getCoord() {
		return coord;
	}

	public void setCoord(Point coord) {
		if (coord == null)
			return;
		if (graph_on)
			Screen.instance.clear(getCoord(), getWidth(), getHeight());
		this.coord = coord;
		// draw();
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		if (graph_on)
			Screen.instance.clear(getCoord(), getWidth(), getHeight());
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		if (graph_on)
			Screen.instance.clear(getCoord(), getWidth(), getHeight());
		this.height = height;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coord == null) ? 0 : coord.hashCode());
		result = prime * result + height;
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Graph)) {
			return false;
		}
		Graph other = (Graph) obj;
		if (coord == null) {
			if (other.coord != null) {
				return false;
			}
		} else if (!coord.equals(other.coord)) {
			return false;
		}
		if (height != other.height) {
			return false;
		}
		if (width != other.width) {
			return false;
		}
		return true;
	}

}
