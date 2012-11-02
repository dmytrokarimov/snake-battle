package logic;


import java.awt.Point;
import gui.*;

public class Snake {
	private Point p;
	private Element[] elements;
	private Mind mind;

	public Point getCoord() {
		return p;
	}

	public void setCoord(Point Coord) {
		this.p = Coord;
	}

	public Element[] getElements() {
		return elements;
	}

	public void setElements(Element[] elements) {
		this.elements = elements;
	}

	public void setMind(Mind mind) {
		this.mind = mind;
	}
	public Mind getMind() {
		return mind;
	}
}