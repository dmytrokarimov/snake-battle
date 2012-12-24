package com.example.android.tictactoe;

import gui.IScreen;
import gui.Point;
import gui.Rectangle;
import gui.Screen;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.*;

import logic.Map;

public class AndroidScreen implements IScreen{
	public static AndroidScreen instance = null;
	/**
	 * Создает объект экрана и отображает его на экране Внимание! Это хоть и
	 * singleton, но предыдущие окна не закрываются
	 */
	public AndroidScreen() {
		instance = this;
	}
	@Override
	public boolean canDraw() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean canExit() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void clear(BufferedImage arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void clear(Rectangle arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void clear(Point arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void draw(Point arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void draw(Rectangle arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void draw(Point arg0, Color arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void draw(Rectangle arg0, Color arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void draw(Point arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void draw(Point arg0, BufferedImage arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void draw(Point arg0, String arg1, Color arg2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public JComponent getGui() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public JMenuBar getMenuBar(boolean arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void repaint() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setImage(BufferedImage arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setScreen(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setScreen(Map arg0) {
		// TODO Auto-generated method stub
		
	}
}