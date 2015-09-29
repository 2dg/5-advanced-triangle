import javax.swing.*;
import java.awt.*;

public class AdvancedTriangle extends JFrame {
	public AdvancedTriangle() {
		add(new NewPanel());
	}

	public static void main(String[] args) {
		AdvancedTriangle frame = new AdvancedTriangle();
		frame.setTitle("Sun");
		frame.getContentPane().setPreferredSize(new Dimension(1000, 1000));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

class NewPanel extends JPanel {
	double maxX, maxY, minMaxXY, xCenter, yCenter;

	static float Q = .05f;
	static float P = 1 - .05f;
	static int SIDES = 7;
	static double ANGLE_INCREMENT = Math.PI * 2 / SIDES;

	void initgr() {
		maxX = getWidth() - 1;
		maxY = getHeight() - 1;
		minMaxXY = Math.min(maxX, maxY);
		xCenter = maxX / 2;
		yCenter = maxY / 2;
	}

	Polygon nextPolygon(Polygon p) {
		int[] pxpoints = p.xpoints;
		int[] pypoints = p.ypoints;
		int[] xpoints = new int[SIDES];
		int[] ypoints = new int[SIDES];

		for (int i = 0; i < SIDES; i++) {
			xpoints[i] = (int)(pxpoints[i] * P + pxpoints[(i + 2) % SIDES] * Q);
			ypoints[i] = (int)(pypoints[i] * P + pypoints[(i + 2) % SIDES] * Q);
		}

		return new Polygon(xpoints, ypoints, SIDES);
	}

	Polygon basePolygon() {
		int[] xpoints = new int[SIDES];
		int[] ypoints = new int[SIDES];

		for (int i = 0; i < SIDES; i++) {
			double angle = i * ANGLE_INCREMENT;
			xpoints[i] = (int)(maxX / 2 * Math.cos(angle));
			ypoints[i] = (int)(maxY / 2 * Math.sin(angle));
		}

		return new Polygon(xpoints, ypoints, SIDES);
	}

	Polygon scalePolygon(Polygon p, double k) {
		int[] pxpoints = p.xpoints;
		int[] pypoints = p.ypoints;
		int[] xpoints = new int[SIDES];
		int[] ypoints = new int[SIDES];

		for (int i = 0; i < SIDES; i++) {
			xpoints[i] = (int)(pxpoints[i] * k);
			ypoints[i] = (int)(pypoints[i] * k);
		}

		return new Polygon(xpoints, ypoints, SIDES);
	}

	@Override
	protected void paintComponent(Graphics gg) {
		super.paintComponent(gg);
		initgr();
		Graphics2D g = (Graphics2D)(gg.create());
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.translate(maxX / 2, maxY / 2);

		g.setColor(Color.BLACK);
		g.fill(g.getClipBounds());
		g.setStroke(new BasicStroke(5.f));

		Polygon p = basePolygon();

		for (int i = 0; i < 360; i++) {
			g.setColor(Color.getHSBColor(i / 360.f, .7f, .8f));
			Polygon draw = scalePolygon(p, Math.sin((double)(i)));
			g.drawPolygon(draw);
			p = nextPolygon(p);
		}
	}
}