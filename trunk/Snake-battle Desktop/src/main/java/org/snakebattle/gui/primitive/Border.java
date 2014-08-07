package org.snakebattle.gui.primitive;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import org.snakebattle.gui.Graph;
import org.snakebattle.gui.screen.Screen;

public class Border extends Graph {

	private static final long serialVersionUID = 7857423245685146566L;

	public Border(Point coord, int width, int height) {
		super(coord, width, height);
	}

	@Override
	public void draw() {
		if (!graph_on)
			return;
		Screen sc = Screen.instance;
		sc.draw(new Rectangle(getCoord().x, getCoord().y, width, height),
				Color.CYAN);
	}

	@Override
	public boolean onCollision(Graph obj) {
		return false;
	}

}
