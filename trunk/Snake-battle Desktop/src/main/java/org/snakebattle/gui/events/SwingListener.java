package org.snakebattle.gui.events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class SwingListener extends EventProcessor implements MouseListener,
		MouseMotionListener {

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(final MouseEvent e) {
		onEvent(new Event<org.snakebattle.gui.events.mouse.MouseListener>() {
			@Override
			public void onEvent(
					org.snakebattle.gui.events.mouse.MouseListener eventListener) {
				((org.snakebattle.gui.events.mouse.MouseListener) eventListener)
						.onMouseMove(e.getPoint());
			}
		});
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		onEvent(new Event<org.snakebattle.gui.events.mouse.MouseListener>() {
			@Override
			public void onEvent(
					org.snakebattle.gui.events.mouse.MouseListener eventListener) {
				((org.snakebattle.gui.events.mouse.MouseListener) eventListener)
						.onMouseClick(e.getPoint());
			}
		});
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
