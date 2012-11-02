package gui;

import java.awt.*;
import java.awt.RenderingHints.Key;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.*;

import logic.Map;

public class Screen {
	private BufferedImage canvasImage;
	private RenderingHints renderingHints;
	private Stroke stroke = new BasicStroke(3, BasicStroke.CAP_ROUND,
			BasicStroke.JOIN_ROUND, 1.7f);
	private Color color;
	private JLabel imageLabel;
	private JFrame gui;
	
	public void draw(Point point) {
		draw(point, color);
	}

	public void draw(Point point, Color color) {
		Graphics2D g = this.canvasImage.createGraphics();
		g.setRenderingHints(renderingHints);
		g.setColor(this.color);
		g.setStroke(stroke);
		int n = 0;
		g.drawLine(point.x, point.y, point.x + n, point.y + n);
		g.dispose();
		this.imageLabel.repaint();
	}

	public void draw(Rectangle r) {
		draw(r, color);
	}

	public void draw(Rectangle r, Color color) {
		Graphics2D g = this.canvasImage.createGraphics();
		g.setRenderingHints(renderingHints);
		g.setColor(this.color);
		g.setStroke(stroke);
		g.drawRect(r.x, r.y, r.width, r.height);
		g.dispose();
		this.imageLabel.repaint();
	}

	public void draw(Point p, String text) {
		Graphics2D g = this.canvasImage.createGraphics();
		g.setRenderingHints(renderingHints);
		g.setColor(this.color);
		g.setStroke(stroke);
		g.drawString(text, p.x, p.y);
		g.dispose();
		this.imageLabel.repaint();
	}

	public void draw(Point p, BufferedImage image) {
		// BufferedImage img = ImageIO.read(imageSrc);
		Graphics2D g = this.canvasImage.createGraphics();
		g.setRenderingHints(renderingHints);
		g.setStroke(stroke);
		float[] scales = { 1f, 1f, 1f, 0.5f };
		float[] offsets = new float[4];
		RescaleOp rop = new RescaleOp(scales, offsets, null);
		g.drawImage(image, rop, p.x, p.y);
		g.dispose();
		this.imageLabel.repaint();
	}

	public void setImage(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        canvasImage = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = this.canvasImage.createGraphics();
        g.setRenderingHints(renderingHints);
        g.drawImage(image, 0, 0, gui);
        g.dispose();

        new Rectangle(0,0,w,h); 
        if (this.imageLabel!=null) {
            imageLabel.setIcon(new ImageIcon(canvasImage));
            this.imageLabel.repaint();
        }
        if (gui!=null) {
            gui.invalidate();
        }
	}
	
	/** Clears the entire image area by painting it with the current color. */
	public void clear(BufferedImage bi) {
        Graphics2D g = bi.createGraphics();
        g.setRenderingHints(renderingHints);
        g.setColor(color);
        g.fillRect(0, 0, bi.getWidth(), bi.getHeight());

        g.dispose();
        imageLabel.repaint();
	}

	public void setScreen(String name) {

	}

	public void setScreen(Map map) {
		
	}
}