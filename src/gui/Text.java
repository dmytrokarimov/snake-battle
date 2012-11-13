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
		if (!graph_on)
			Screen.instance.draw(getCoord(), text);
		if (TTD <= 0)
			graph_on = false;
		TTD--;
	}

	public boolean pointAt(Point p) {
		return false;
	}

	public boolean onCollision(Graph obj) {
		return false;
	}

	
	
	public int getTTD() {
		return TTD;
	}

	public void setTTD(int tTD) {
		graph_on = tTD > 0; 
		TTD = tTD;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + TTD;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Text)) {
			return false;
		}
		Text other = (Text) obj;
		if (TTD != other.TTD) {
			return false;
		}
		if (text == null) {
			if (other.text != null) {
				return false;
			}
		} else if (!text.equals(other.text)) {
			return false;
		}
		return true;
	}

	
}
