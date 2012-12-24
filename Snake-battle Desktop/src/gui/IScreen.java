package gui;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JMenuBar;
import gui.Point;
public interface IScreen {

	/**
	 * ������ ����� �� ������ ������ ��-���������
	 */
	public void draw(Point point);

	/**
	 * ������ ����� �� ������
	 */
	public void draw(Point point, Color color) ;

	/**
	 * ������ ������������� �� ������ ������ ��-���������
	 */
	public void draw(Rectangle r) ;

	/**
	 * ������ �������������
	 */
	public void draw(Rectangle r, Color color) ;

	/**
	 * ������� ����� �� ����� ������ ��-���������
	 */
	public void draw(Point p, String text) ;

	/**
	 * ������� ����� �� �����
	 */
	public void draw(Point p, String text, Color color) ;

	/**
	 * ������� image �� ����� � ����� p
	 */
	public void draw(Point p, BufferedImage image) ;

	public void repaint() ;

	public void setImage(BufferedImage image) ;

	/** Clears the entire image area by painting it with the current color. */
	public void clear(BufferedImage bi) ;

	public void clear(Point p, int width, int height) ;

	public void clear(Rectangle r) ;

	public void setScreen(String name) ;

	public void setScreen(logic.Map map) ;

	public JComponent getGui() ;

	public JMenuBar getMenuBar(boolean webstart) ;

	public boolean canExit() ;

	public boolean canDraw() ;

	/**
	 * This method calc screen size
	 */
	public int getWidth() ;

	/**
	 * This method calc screen size
	 */
	public int getHeight() ;
}