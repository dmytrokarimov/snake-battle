package gui;

import java.awt.Point;

import logic.Snake;

public class ElementFactory {
	public enum PARTS {
		BODY, HEAD, TAIL;
	}

	public static Element getElement(PARTS part, Point coord, int width, int height, Snake snake){
		
			return new Element(part, coord, width, height, snake);
		}
}
