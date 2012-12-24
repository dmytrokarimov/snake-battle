package gui;

import java.awt.Color;
import java.io.Serializable;

import logic.Snake;

public class Element extends Graph {
	private static final long serialVersionUID = -6533857847490344497L;

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
		if (!graph_on)
			return;
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
		default:
			break;
		}
	}


	public boolean onCollision(Graph obj) {
		return false;
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
		default:
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
		default:
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((part == null) ? 0 : part.hashCode());
		//result = prime * result + ((snake == null) ? 0 : snake.hashCode());
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
		if (!(obj instanceof Element)) {
			return false;
		}
		Element other = (Element) obj;
		if (part != other.part) {
			return false;
		}
		//if (snake == null) {
		//	if (other.snake != null) {
		//		return false;
		//	}
		//} else if (!snake.equals(other.snake)) {
		//	return false;
		//}
		return super.equals(obj);
	}
}