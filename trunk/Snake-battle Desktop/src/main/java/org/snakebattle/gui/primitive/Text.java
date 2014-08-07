package org.snakebattle.gui.primitive;

import java.awt.Color;
import java.awt.Point;

import org.snakebattle.gui.Drawable;
import org.snakebattle.gui.Graph;
import org.snakebattle.gui.screen.Screen;

public class Text extends Graph implements Drawable{
	private static final long serialVersionUID = -147751962202965335L;
	
	/**
	 * Time To Die - how many tics can text be alive
	 */
	private int TTD = 0;
	private String text = "";
	private Color color;
	
	/**
	 * Create new Text graphical object
	 * @param TTD Time To Die - how many tics can text be alive (if negative - always)
	 * @param battleMap reference to map, who contain this object
	 */
	public Text(Point coord, String text, int TTD) {
		super(coord, 0, 0);
		this.text = text;
		this.TTD = TTD;
		this.color = Color.black;
	}	
	
	/**
	 * Create new Text graphical object
	 * @param TTD Time To Die - how many tics can text be alive (if negative - always)
	 * @param battleMap reference to map, who contain this object
	 */
	public Text(Point coord, String text, int TTD, Color color) {
		this(coord, text, TTD);
		this.color = color;		
	}
	
	public void draw() {
		if (graph_on){
			Screen.instance.setColor(color);
			Screen.instance.draw(getCoord(), text);
		}
		if (TTD < 0){
			return ;
		}
		if (TTD == 0){
			graph_on = false;
			return;
		}
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

	public void setTTD(int TTD) {
		graph_on = TTD > 0; 
		this.TTD = TTD;
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
