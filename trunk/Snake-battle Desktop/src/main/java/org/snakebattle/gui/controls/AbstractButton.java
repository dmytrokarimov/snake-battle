package org.snakebattle.gui.controls;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import org.snakebattle.gui.Graph;
import org.snakebattle.gui.events.mouse.MouseClickListener;
import org.snakebattle.gui.screen.Screen;

public abstract class AbstractButton extends Graph implements MouseClickListener{

	private static final long serialVersionUID = -2242655556025883180L;

	private String text;
	private Point textCoord;
	
	public AbstractButton(String text, Point coord, int width, int height) {
		super(coord, width, height);
		this.text = text;
		//TODO проверку на координаты, нормальный центр и т.д.
		textCoord = new Point((int) (coord.x + Math.round(width / 2.3)), (int) (coord.y + Math.round(height / 2.3)));
	}
	
	/**
	 * Вызывается, если нажатие произошло по кнопке 
	 * @param x
	 * @param y
	 */
	public abstract void onMouseClick(int x, int y);
	
	@Override
	public void onMouseClick(Point p) {
		if(p.x > coord.x && p.x < coord.x+width) {
			if (p.y > coord.y && p.y < coord.y+height){
				onMouseClick(p.x, p.y);
			}
		}
	}

	@Override
	public void draw() {
		Screen sc = Screen.instance;
		sc.draw(new Rectangle(coord, new Dimension(width, height)), Color.BLACK);
		textCoord.translate(1, 1);
		sc.draw(textCoord, text, Color.GRAY);
		textCoord.translate(-1, -1);
		sc.draw(textCoord, text, Color.BLACK);
	}

	@Override
	public boolean onCollision(Graph obj) {
		return false;
	}

}
