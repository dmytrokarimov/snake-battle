package gui;

import java.awt.Point;

import logic.Snake;

public class Element extends Graph {

	private ElementFactory.PARTS part;
	private Snake snake;

	protected Element(ElementFactory.PARTS part, Point coord, int width, int height, Snake snake) {
		super(coord, width, height);
		this.part = part;
		this.snake = snake;
	}

	public void draw() {
		switch (part) {
		case BODY:
			break;
		case HEAD:
			break;
		case TAIL:
			break;
		}
	}

	void onCollision(Graph obj) {
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