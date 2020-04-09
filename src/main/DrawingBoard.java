package main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import java.util.List;
import javax.swing.JPanel;
import objects.*;
import java.util.Collections;


public class DrawingBoard extends JPanel {

	private MouseAdapter mouseAdapter;
	private List<GObject> gObjects;
	private GObject target;

	private int gridSize = 10;

	public DrawingBoard() {
		gObjects = new ArrayList<GObject>();
		mouseAdapter = new MAdapter();
		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);
		setPreferredSize(new Dimension(800, 600));
	}

	public void addGObject(GObject gObject) {
		// add graphic
		gObjects.add(gObject);

		repaint();
	}

	public void groupAll() {
		// group selected object to same group
		CompositeGObject compositeGObject = new CompositeGObject();

		for (GObject gObject : gObjects) {
			compositeGObject.add(gObject);
		}

		gObjects.clear();

		compositeGObject.recalculateRegion();

		gObjects.add(compositeGObject);

		repaint();
	}

	public void deleteSelected() {
		// delete selected object
		gObjects.remove(target);

		repaint();
	}

	public void clear() {
		// clear
		gObjects.clear();

		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		paintBackground(g);
		paintGrids(g);
		paintObjects(g);
	}

	private void paintBackground(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	private void paintGrids(Graphics g) {
		g.setColor(Color.lightGray);
		int gridCountX = getWidth() / gridSize;
		int gridCountY = getHeight() / gridSize;
		for (int i = 0; i < gridCountX; i++) {
			g.drawLine(gridSize * i, 0, gridSize * i, getHeight());
		}
		for (int i = 0; i < gridCountY; i++) {
			g.drawLine(0, gridSize * i, getWidth(), gridSize * i);
		}
	}

	private void paintObjects(Graphics g) {
		for (GObject go : gObjects) {
			go.paint(g);
		}
	}

	class MAdapter extends MouseAdapter {

		// TODO: You need some variables here
		private int x;
		private int y;

		private void deselectAll() {

			// deselect every selected object
			for (GObject gObject : gObjects) {
				gObject.deselected();
			}

			target = null;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			this.x = e.getX();
			this.y = e.getY();

			deselectAll();

			Collections.reverse(gObjects);

			for (GObject gObject : gObjects) {
				if (gObject.pointerHit(x, y)) {
					target = gObject;
					gObject.selected();

					break;
				}
			}

			Collections.reverse(gObjects);

			repaint();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (target != null) {
				int currentX = e.getX();
				int currentY = e.getY();
				int selectedX = this.x;
				int selectedY = this.y;

				int xOffset = currentX - selectedX;
				int yOffset = currentY - selectedY;

				target.move(xOffset, yOffset);

				repaint();

				this.x = e.getX();
				this.y = e.getY();
			}
		}
	}

}