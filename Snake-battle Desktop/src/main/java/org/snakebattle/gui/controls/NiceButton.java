package org.snakebattle.gui.controls;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import org.snakebattle.gui.screen.Screen;

public abstract class NiceButton extends AbstractButton {

	private static final long serialVersionUID = 48358762054321043L;
	private Dimension arc = new Dimension((int)Math.sqrt(width), (int)Math.sqrt(height));
	
	public NiceButton(String text, Point coord, int width, int height) {
		super(text, coord, Math.max(width, text.length() * 6 + 10), Math.max(height, 8 + 12));
	}

	@Override
	public void draw() {
		Screen sc = Screen.instance;
		
		// turn on anti-alias mode
       // Graphics2D antiAlias = (Graphics2D)g;
       // antiAlias.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // draw white rectangle
		sc.setColor(Color.WHITE);
		sc.fillRoundRect(coord.x, coord.y, getWidth() - 1, getHeight() - 1, arc.width, arc.height);

        // draw black border
        if(mouseEntered)
        {
            sc.setColor(Color.YELLOW);
        }
        else
        {
            sc.setColor(Color.BLACK);
        }
        sc.drawRoundRect(coord.x, coord.y, getWidth() - 1, getHeight() - 1, arc.width, arc.height);

        // draw inside light border
        sc.setColor(Color.decode("#c0c0c0"));
        sc.drawRoundRect(coord.x + 1, coord.y + 1, getWidth() - 3, getHeight() - 3, arc.width, arc.height);

        sc.setColor(Color.WHITE);
        int textLength = text.length() * 6;
        sc.draw(new Point(coord.x + (width - textLength) / 2, coord.y + (height - 8) / 2 + 8), text, Color.BLACK);
	}
}
