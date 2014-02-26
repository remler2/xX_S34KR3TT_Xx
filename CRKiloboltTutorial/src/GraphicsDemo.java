import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;


public class GraphicsDemo extends JFrame{

	public GraphicsDemo() {
		setTitle("GraphicsDemo with Kilobolt");
		setSize(800,480);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void paint(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 800, 480);
		g.setColor(Color.BLUE);
		g.drawRect(60, 200, 100, 250);
		g.setColor(Color.BLACK);
		g.drawString("My name is James", 200, 400);
	}
	public static void main(String args[]) {
		GraphicsDemo demo = new GraphicsDemo();
	}
}
