package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class CompositeGObject extends GObject {

	private List<GObject> gObjects;

	public CompositeGObject() {
		super(0, 0, 0, 0);
		gObjects = new ArrayList<GObject>();
	}

	public void add(GObject gObject) {
		// add a gObject
		gObjects.add(gObject);
	}

	public void remove(GObject gObject) {
		// remove a gObject
		gObjects.remove(gObject);
	}

	@Override
	public void move(int dx, int dy) {
		this.x += dx;
		this.y += dy;
		// set new coordinate

		for (GObject gObject : gObjects) {
			gObject.move(dx, dy);
		}
		// for loop to move object in coordinate plane
	}

	public void recalculateRegion() {
		GObject refGObject = gObjects.get(0);
		// declare min&max value
		int x = refGObject.x;
		int max_x = refGObject.x + refGObject.width;
		int y = refGObject.y;
		int max_y = refGObject.y + refGObject.height;
		// loop through value and set condition
		for (GObject gObject : gObjects) {
			if (gObject.x < x)
				x = gObject.x;

			if (gObject.x + gObject.width > max_x)
				max_x = gObject.x + gObject.width;

			if (gObject.y < y)
				y = gObject.y;

			if (gObject.y + gObject.height > max_y)
				max_y = gObject.y + gObject.height;
		}
		// recalculate the region
		this.x = x;
		this.width = max_x - x;
		this.y = y;
		this.height = max_y - y;
	}

	@Override
	public void paintObject(Graphics g) {
		// paint graphic object
		for (GObject gObject : gObjects) {
			gObject.paintObject(g);
		}
	}

	@Override
	public void paintLabel(Graphics g) {
		// paint object label
		for (GObject gObject : gObjects) {
			gObject.paintLabel(g);
		}
	}

}
