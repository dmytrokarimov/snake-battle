package gui;

import java.awt.Point;

import logic.Map;

public class Text extends Graph implements Drawable{
	/**
	 * Time To Die - how many tics can text be alive
	 */
	private int TTD = 0;
	private String text = "";
	
	/**
	 * Create new Text graphical object
	 * @param TTD Time To Die - how many tics can text be alive
	 * @param map reference to map, who contain this object
	 */
	public Text(Point coord, String text, int TTD, Map map) {
		super(coord, 0, 0);
		this.text = text;
		this.TTD = TTD;
	}
	
	public void draw() {
		Screen.instance.draw(getCoord(), text);
		TTD--;
	}

	public boolean pointAt(Point p) {
		return false;
	}

	public boolean onCollision(Graph obj) {
		return false;
	}

}
