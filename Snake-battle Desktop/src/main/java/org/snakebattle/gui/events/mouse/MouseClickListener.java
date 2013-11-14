package org.snakebattle.gui.events.mouse;

import java.awt.Point;

import org.snakebattle.gui.events.EventListener;

public interface MouseClickListener extends EventListener{
	/**
	 * ���������� ����������, ���� ������� ���� �������, �.�. ���������� �� ���� ������
	 * @param p
	 */
	void onMouseClick(Point p);
}
