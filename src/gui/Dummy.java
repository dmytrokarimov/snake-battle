package gui;

import java.awt.Point;

public class Dummy extends Graph {

	public Dummy(Point coord, int width, int height) {
		super(coord, width, height);
	}

	@Override
	public void draw() {

	}

	@Override
	public boolean onCollision(Graph obj) {
		return false;
	}

}
