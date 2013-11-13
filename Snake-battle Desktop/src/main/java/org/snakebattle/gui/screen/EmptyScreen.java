package org.snakebattle.gui.screen;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import org.snakebattle.logic.BattleMap;

public class EmptyScreen implements IScreen{
	public static EmptyScreen instance = null;
	public EmptyScreen() {
		instance = this;
	}
	@Override
	public void draw(Point point) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void draw(Point point, Color color) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void draw(Rectangle r) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void draw(Rectangle r, Color color) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void draw(Point p, String text) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void draw(Point p, String text, Color color) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void repaint() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void clear(Point p, int width, int height) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void clear(Rectangle r) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setScreen(String name) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setScreen(BattleMap battleMap) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean canExit() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean canDraw() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

}