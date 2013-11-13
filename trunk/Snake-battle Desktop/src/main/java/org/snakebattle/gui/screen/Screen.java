package org.snakebattle.gui.screen;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import org.snakebattle.logic.Map;

public class Screen implements IScreen {
	public static Screen instance = null;
	private static IScreen realScreen = null;

	public static final int SELECTION_TOOL = 0;
	public static final int DRAW_TOOL = 1;
	public static final int TEXT_TOOL = 2;

	public static boolean GRAPHICS_ON = false;

	public Screen() {
		if (instance == null) {
			instance = this;
			new SwingScreen();
			realScreen = SwingScreen.instance;
		}
	}

	public Screen(IScreen screen) {
		if (instance == null) {
			instance = this;
			realScreen = screen;
		}
	}

	public void draw(Point point) {
		realScreen.draw(point);
	}

	public void draw(Point point, Color color) {
		realScreen.draw(point, color);
	}

	public void draw(Rectangle r) {
		realScreen.draw(r);
	}

	public void draw(Rectangle r, Color color) {
		realScreen.draw(r, color);
	}

	public void draw(Point p, String text) {
		realScreen.draw(p, text);
	}

	public void draw(Point p, String text, Color color) {
		realScreen.draw(p, text, color);
	}

	public void repaint() {
		realScreen.repaint();
	}

	public void clear(Point p, int width, int height) {
		realScreen.clear(p, width, height);
	}

	public void clear(Rectangle r) {
		realScreen.clear(r);
	}

	public void setScreen(String name) {
		realScreen.setScreen(name);
	}

	public void setScreen(Map map) {
		realScreen.setScreen(map);
	}

	public boolean canExit() {
		return realScreen.canExit();
	}

	public boolean canDraw() {
		return realScreen.canDraw();
	}

	public int getWidth() {
		return realScreen.getWidth();
	}

	public int getHeight() {
		return realScreen.getHeight();
	}

}