package org.snakebattle.gui.screen;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang3.ClassUtils;
import org.snakebattle.gui.events.EventListener;
import org.snakebattle.gui.events.mouse.MouseClickListener;

public class SwingScreen implements IScreen {
	public static SwingScreen instance = null;
	/** Image used to make changes. */
	private BufferedImage canvasImage;
	private BufferedImage bufferImage;
	/** The main GUI that might be added to a frame or applet. */
	private JPanel gui;
	/**
	 * The color to use when calling clear, text or other drawing functionality.
	 */
	private Color color = Color.WHITE;
	/** General user messages. */
	private JLabel output = new JLabel("You DooDoodle!");

	private BufferedImage colorSample = new BufferedImage(16, 16,
			BufferedImage.TYPE_INT_RGB);
	private JLabel imageLabel;

	private Stroke stroke = new BasicStroke(6, BasicStroke.CAP_ROUND,
			BasicStroke.JOIN_ROUND, 1.7f);
	private RenderingHints renderingHints;

	private boolean ready = false;

	private JFrame frame = null;
	public boolean repaintOnEveryDraw = false;

	private Map<Class<?>, Set<EventListener>> eventListeners = new HashMap<>();
	private MouseListener mouseListener = new MouseListener() {

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			Set<EventListener> listeners = eventListeners
					.get(MouseClickListener.class);
			if (listeners != null) {
				for (EventListener eventListener : listeners) {
					((MouseClickListener) eventListener).onMouseClick(e.getPoint());
				}
			}
		}
	};

	/**
	 * Создает объект экрана и отображает его на экране Внимание! Это хоть и
	 * singleton, но предыдущие окна не закрываются
	 */
	public SwingScreen() {
		instance = this;
		Runnable r = new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					// use default
				}
				if (!Screen.GRAPHICS_ON)
					return;
				frame = new JFrame("DooDoodle!");
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setLocationByPlatform(true);

				// Добавлено после 33-й ревизии
				frame.setContentPane(instance.getGui());
				frame.setJMenuBar(instance.getMenuBar(false));

				frame.pack();
				frame.setMinimumSize(frame.getSize());
				frame.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(r);
	}

	/**
	 * Рисует точку на экране цветом по-умолчанию
	 */
	public void draw(Point point) {
		if (!Screen.GRAPHICS_ON)
			return;
		draw(point, color);
	}

	/**
	 * Рисует точку на экране
	 */
	public void draw(Point point, Color color) {
		if (!Screen.GRAPHICS_ON)
			return;
		Graphics2D g;
		if (repaintOnEveryDraw)
			g = this.canvasImage.createGraphics();
		else
			g = this.bufferImage.createGraphics();
		g.setRenderingHints(renderingHints);
		g.setColor(new java.awt.Color(color.getRGB()));
		g.setStroke(stroke);
		int n = 0;
		g.drawLine(point.x, point.y, point.x + n, point.y + n);
		g.dispose();
		if (repaintOnEveryDraw)
			this.imageLabel.repaint();
	}

	/**
	 * Рисует прямоугольник на экране цветом по-умолчанию
	 */
	public void draw(Rectangle r) {
		if (!Screen.GRAPHICS_ON)
			return;
		draw(r, color);
	}

	/**
	 * Рисует прямоугольник
	 */
	public void draw(Rectangle r, Color color) {
		if (!Screen.GRAPHICS_ON)
			return;
		Graphics2D g;
		if (repaintOnEveryDraw)
			g = this.canvasImage.createGraphics();
		else
			g = this.bufferImage.createGraphics();
		g.setRenderingHints(renderingHints);
		g.setColor(new java.awt.Color(color.getRGB()));
		g.setStroke(stroke);
		g.drawRect(r.x, r.y, r.width, r.height);
		g.dispose();
		if (repaintOnEveryDraw)
			this.imageLabel.repaint();
	}

	/**
	 * Выводит текст на экран цветом по-умолчанию
	 */
	public void draw(Point p, String text) {
		if (!Screen.GRAPHICS_ON)
			return;
		draw(p, text, color);
	}

	/**
	 * Выводит текст на экран
	 */
	public void draw(Point p, String text, Color color) {
		if (!Screen.GRAPHICS_ON)
			return;
		Graphics2D g;
		if (repaintOnEveryDraw)
			g = this.canvasImage.createGraphics();
		else
			g = this.bufferImage.createGraphics();
		g.setRenderingHints(renderingHints);
		g.setColor(new java.awt.Color(color.getRGB()));
		g.setStroke(stroke);
		g.drawString(text, p.x, p.y);
		g.dispose();
		if (repaintOnEveryDraw)
			this.imageLabel.repaint();
	}

	/**
	 * Выводит image на экран в точке p
	 */
	public void draw(Point p, BufferedImage image) {
		if (!Screen.GRAPHICS_ON)
			return;
		// BufferedImage img = ImageIO.read(imageSrc);
		Graphics2D g;
		if (repaintOnEveryDraw)
			g = this.canvasImage.createGraphics();
		else
			g = this.bufferImage.createGraphics();
		g.setRenderingHints(renderingHints);
		g.setStroke(stroke);
		float[] scales = { 1f, 1f, 1f, 0.5f };
		float[] offsets = new float[4];
		RescaleOp rop = new RescaleOp(scales, offsets, null);
		g.drawImage(image, rop, p.x, p.y);
		g.dispose();
		if (repaintOnEveryDraw)
			this.imageLabel.repaint();
	}

	public void repaint() {
		if (!Screen.GRAPHICS_ON)
			return;
		if (repaintOnEveryDraw)
			this.imageLabel.repaint();
		else {
			imageLabel.setIcon(new ImageIcon(bufferImage));
			this.imageLabel.repaint();
			canvasImage = bufferImage;
			bufferImage = new BufferedImage(canvasImage.getWidth(),
					canvasImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
			;
			clear(bufferImage);
		}
	}

	public void setImage(BufferedImage image) {
		if (!Screen.GRAPHICS_ON)
			return;
		int w = image.getWidth();
		int h = image.getHeight();
		canvasImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		bufferImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = this.canvasImage.createGraphics();
		g.setRenderingHints(renderingHints);
		g.drawImage(image, 0, 0, gui);
		g.dispose();

		g = bufferImage.createGraphics();
		// g.setRenderingHints(renderingHints);
		g.drawImage(image, 0, 0, gui);
		g.dispose();

		// new Rectangle(0, 0, w, h);
		if (this.imageLabel != null) {
			imageLabel.setIcon(new ImageIcon(canvasImage));
			this.imageLabel.repaint();
		}
		if (gui != null) {
			gui.invalidate();
		}
	}

	/** Clears the entire image area by painting it with the current color. */
	public void clear(BufferedImage bi) {
		if (!Screen.GRAPHICS_ON)
			return;
		Graphics2D g = bi.createGraphics();
		g.setRenderingHints(renderingHints);
		g.setColor(new java.awt.Color(color.getRGB()));
		g.fillRect(0, 0, bi.getWidth(), bi.getHeight());

		g.dispose();
		if (repaintOnEveryDraw)
			imageLabel.repaint();
	}

	public void clear(Point p, int width, int height) {
		if (!Screen.GRAPHICS_ON)
			return;
		Graphics2D g;
		if (repaintOnEveryDraw)
			g = this.canvasImage.createGraphics();
		else
			g = this.bufferImage.createGraphics();
		// Graphics2D g = this.canvasImage.createGraphics();
		g.setRenderingHints(renderingHints);
		g.setColor(new java.awt.Color(color.getRGB()));
		g.fillRect(p.x - 1, p.y - 1, width + 3, height + 3);
		g.dispose();
		if (repaintOnEveryDraw)
			this.imageLabel.repaint();
	}

	public void clear(Rectangle r) {
		if (!Screen.GRAPHICS_ON)
			return;
		clear(new Point(r.x, r.y), r.width, r.height);
	}

	public void setScreen(String name) {

	}

	public void setScreen(org.snakebattle.logic.BattleMap battleMap) {

	}

	public JComponent getGui() {
		if (gui == null) {
			ready = false;
			java.util.Map<Key, Object> hintsMap = new HashMap<RenderingHints.Key, Object>();
			hintsMap.put(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			hintsMap.put(RenderingHints.KEY_DITHERING,
					RenderingHints.VALUE_DITHER_ENABLE);
			hintsMap.put(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			renderingHints = new RenderingHints(hintsMap);

			setImage(new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB));
			gui = new JPanel(new BorderLayout(4, 4));
			gui.setBorder(new EmptyBorder(5, 3, 5, 3));

			JPanel imageView = new JPanel(new GridBagLayout());
			imageView.setPreferredSize(new Dimension(820, 620));
			imageLabel = new JLabel(new ImageIcon(canvasImage));

			imageView.addMouseListener(mouseListener);

			JScrollPane imageScroll = new JScrollPane(imageView);
			imageView.add(imageLabel);
			gui.add(imageScroll, BorderLayout.CENTER);

			JToolBar tools = new JToolBar(JToolBar.VERTICAL);
			tools.setFloatable(false);

			JLabel sn1 = new JLabel("Peter");
			JLabel sn2 = new JLabel("Rembo");
			JLabel sn3 = new JLabel("Seal");

			tools.add(new JLabel("Snakes:         "));
			tools.add(sn1);
			tools.add(sn2);
			tools.add(sn3);

			gui.add(tools, BorderLayout.LINE_END);
			gui.add(output, BorderLayout.PAGE_END);
			clear(colorSample);
			clear(canvasImage);
			clear(bufferImage);
			ready = true;
		}

		return gui;
	}

	public JMenuBar getMenuBar(boolean webstart) {
		JMenuBar mb = new JMenuBar();
		mb.add(this.getFileMenu(webstart));
		return mb;
	}

	private JMenu getFileMenu(boolean webstart) {
		JMenu file = new JMenu("File");
		file.setMnemonic('f');

		JMenuItem newImageItem = new JMenuItem("New");
		newImageItem.setMnemonic('n');
		ActionListener newImage = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				BufferedImage bi = new BufferedImage(360, 300,
						BufferedImage.TYPE_INT_ARGB);
				clear(bi);
				setImage(bi);
			}
		};
		newImageItem.addActionListener(newImage);
		file.add(newImageItem);

		if (canExit()) {
			ActionListener exit = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					ready = false;
					frame.dispose();
					// System.exit(0);
				}
			};
			JMenuItem exitItem = new JMenuItem("Exit");
			exitItem.setMnemonic('x');
			file.addSeparator();
			exitItem.addActionListener(exit);
			file.add(exitItem);
		}

		return file;
	}

	public boolean canExit() {
		boolean canExit = false;
		SecurityManager sm = System.getSecurityManager();
		if (sm == null) {
			canExit = true;
		} else {
			try {
				sm.checkExit(0);
				canExit = true;
			} catch (Exception stayFalse) {
			}
		}

		return canExit;
	}

	public boolean canDraw() {
		return ready && Screen.GRAPHICS_ON;
	}

	/**
	 * This method calc screen size
	 */
	public int getWidth() {
		// TODO нужно каким-то образом вычислять это значение!
		return 800;
	}

	/**
	 * This method calc screen size
	 */
	public int getHeight() {
		// TODO нужно каким-то образом вычислять это значение!
		return 600;
	}

	@Override
	public void subscribe(EventListener eventListener) {
		for (Class<?> listenerInterface : ClassUtils
				.getAllInterfaces(eventListener.getClass())) {
			if (EventListener.class.isAssignableFrom(listenerInterface)) {
				Set<EventListener> listeners = eventListeners
						.get(listenerInterface);
				if (listeners == null) {
					listeners = new HashSet<>();
					eventListeners.put(listenerInterface, listeners);
				}

				listeners.add(eventListener);
			}
		}
	}

	@Override
	public void unSubscribe(EventListener eventListener) {
		for (Class<?> listenerInterface : ClassUtils
				.getAllInterfaces(eventListener.getClass())) {
			if (EventListener.class.isAssignableFrom(listenerInterface)) {
				Set<EventListener> listeners = eventListeners
						.get(listenerInterface);
				if (listeners != null) {
					listeners.remove(eventListener);
				}
			}
		}
	}
}