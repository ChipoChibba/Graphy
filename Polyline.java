import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * A multi-segment Shape, with straight lines connecting "joint" points -- (x1,y1) to (x2,y2) to (x3,y3) ...
 * @author Chipo Chibbamulilo
 * @author Chikwanda Chisha
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2016
 * @author CBK, updated Fall 2016
 */
public class Polyline implements Shape {
	// TODO: YOUR CODE HERE
	public ArrayList<Point> points;
	int x1,y1;
	private Color color;

	Polyline(int x1, int y1, Color color){
		this.x1 = x1;
//		this.x2 = x1;
		this.y1 = y1;
//		this.y2 = y1;
		this.color = color;
		//new additions
		points=new ArrayList<>();
		points.add(new Point(x1, y1));
	}

	@Override
	public void moveBy(int dx, int dy) {
		for (Point point : points) {
			point.x += dx;
			point.y += dy;
		}
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public boolean contains(int x, int y) {
		for (int i = 0; i < points.size()-1; i++){
			if(Segment.pointToSegmentDistance(x,y,points.get(i).x, points.get(i).y, points.get(i+1).x, points.get(i+1).y) <= 4){
				return true;
			}
		}
		return false;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		for (int i = 0; i < points.size()-1; i++){
			g.drawLine(points.get(i).x, points.get(i).y, points.get(i+1).x, points.get(i+1).y);
		}
	}

	@Override
	public String toString() {
		StringBuilder stringPoints = new StringBuilder();
		for (Point point : points){
			stringPoints.append(String.valueOf(point.x)).append(" ");
			stringPoints.append(String.valueOf(point.y)).append(" ");
		}
		return "freehand "+ stringPoints + " "+ color.getRGB();
	}
}
