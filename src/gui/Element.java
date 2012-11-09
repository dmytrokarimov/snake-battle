package gui;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import logic.Snake;

public class Element extends Graph {
	public enum PARTS {
		BODY, HEAD, TAIL, THIS_IS_BAD_IDEA;
	}
	
	private PARTS part;
	private Snake snake;

	/**
	 * Возвращает тип части тела
	 */
	public PARTS getPart() {
		return part;
	}

	public Snake getSnake() {
		return snake;
	}

	public Element(PARTS part, Point coord, int width,
			int height, Snake snake) {
		super(coord, width, height);
		this.part = part;
		this.snake = snake;
	}

	public void draw() {
		Screen sc = Screen.instance;
		switch (part) {
		case BODY:
			sc.draw(new Rectangle(getCoord().x, getCoord().y, width, height),
					Color.GRAY);
			break;
		case HEAD:
			sc.draw(new Rectangle(getCoord().x, getCoord().y, width, height),
					Color.red);
			break;
		case TAIL:
			sc.draw(new Rectangle(getCoord().x, getCoord().y, width, height),
					Color.DARK_GRAY);
			break;
		}
	}


	public boolean onCollision(Graph obj) {
		return true;
	}

	public String getName() {
		String name = "";
		switch (part) {
		case BODY:
			name = "body";
			break;
		case HEAD:
			name = "head";
			break;
		case TAIL:
			name = "tail";
			break;
		}
		return name;
	}

	public String getDescription() {
		String description = "";
		switch (part) {
		case BODY:
			description = "";
			break;
		case HEAD:
			description = "";
			break;
		case TAIL:
			description = "";
			break;
		}
		return description;
	}

	public Snake getOwner() {
		return snake;
	}

	public String toString() {
		return getName() + ": " + getDescription();
	}
}