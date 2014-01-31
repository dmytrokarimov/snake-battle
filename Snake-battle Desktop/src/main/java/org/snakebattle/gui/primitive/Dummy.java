package org.snakebattle.gui.primitive;

import java.awt.Point;

import org.snakebattle.gui.Graph;

public class Dummy extends Graph {

  private static final long serialVersionUID = -7893647042849858027L;

  public Dummy(Point coord, int width, int height) {
    super(coord, width, height);
  }

  @Override
  public void draw() {

  }

  @Override
  public boolean onCollision(Graph obj) {
    return false;
  }

}