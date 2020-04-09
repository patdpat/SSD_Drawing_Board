package objects;

import java.awt.Color;
import java.awt.Graphics;

public class Oval extends GObject {

	private Color color;

	public Oval(int x, int y, int width, int height, Color color) {
		super(x, y, width, height);
		this.color = color;
	}

	@Override
	public void paintObject(Graphics g) {
		// paint oval
		g.fillOval(this.x, this.y, this.width, this.height);
		g.setColor(this.color);
	}

	@Override
	public void paintLabel(Graphics g) {
		// paint oval label
		String label = "Oval";
		g.drawString(label, this.x + (this.width / 4), this.y + this.height + 16);
	}
}