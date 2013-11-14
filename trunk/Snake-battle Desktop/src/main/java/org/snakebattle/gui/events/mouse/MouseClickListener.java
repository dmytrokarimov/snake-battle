package org.snakebattle.gui.events.mouse;

import java.awt.Point;

import org.snakebattle.gui.events.EventListener;

public interface MouseClickListener extends EventListener{
	/**
	 * Передается координата, куда реально было нажатие, т.е. координата на всем экране
	 * @param p
	 */
	void onMouseClick(Point p);
}
