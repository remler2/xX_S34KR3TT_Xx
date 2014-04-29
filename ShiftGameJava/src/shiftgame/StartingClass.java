package shiftgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.net.URL;

public class StartingClass extends Applet implements Runnable, KeyListener {

	private URL base;
	private Image background, image;
	private Graphics second;
	private static Background bg;
	Slider slider = new Slider(2);
	Slider slider2 = new Slider();
	private int selectedNumber = slider.array.length / 2;
	private Color selectedColor;
	private boolean gameOver = false;
	private int score = 0;
	private int levelCount = 0;
	private int level = 1;
	private boolean scoreChange = false;
	private BufferedImage bf = new BufferedImage(480,800, BufferedImage.TYPE_INT_RGB);
	
	@Override
	public void init() {
		setSize(480, 800);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("ShiftGame");
		// TODO: handle exception
		try {
			base = getDocumentBase();
		} catch (Exception e) {
		}
		background = getImage(base, "data/bg1.png");

	}

	public void start() {
		bg = new Background(0, 0);
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(background, bg.getBgX(), bg.getBgY(), this);
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 25)); 
		g.drawString("Score:   " + score, 345, 785);
	}

	public void paintScore(Graphics g) {
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		g.drawString("Score:   " + score, 345, 785);
	}

	public void update(Graphics g) {
		if (!gameOver) {
			if (image == null) {
				image = createImage(this.getWidth(), this.getHeight());
				second = image.getGraphics();
			}

			second.setColor(getBackground());
			second.fillRect(0, 0, this.getWidth(), this.getHeight());
			second.setColor(getForeground());
			paint(second);
			
			g.setColor(Color.BLACK);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 25)); 
			g.drawString("Score:   " + score, 345, 785);

			g.drawImage(image, 0, 0, this);
			for (int i = 0; i < Barrier.tempTypes.length; i++) {
				if (i == selectedNumber) {
					g.setColor(Barrier.tempTypes[i]);
					g.fillRect(160, 640, 160, 160);
				} else if (i > selectedNumber) {
					g.setColor(Barrier.tempTypes[i]);
					g.fillRect(240 + (i - selectedNumber) * 80, 680, 80, 80);
				} else {
					g.setColor(Barrier.tempTypes[i]);
					g.fillRect(i * 80, 680, 80, 80);
				}
			}
			selectedColor = Barrier.tempTypes[selectedNumber];

			for (int i = 0; i < slider2.barrier.length; i++) {
				g.setColor(slider2.barrier[i].baColor);
				g.fillRect(0, slider2.barrier[i].baY, 480, 40);
			}

			for (int i = 0; i < slider2.barrier.length; i++) {

				slider2.barrier[i].baY += 3 * Math.sqrt(level);

				if (slider2.barrier[i].baY >= 600) {
					if (selectedColor == slider2.barrier[i].baColor) {
						slider2.reset(i);
						this.paintScore(g);
						scoreChange = true;

						score += 10;
						levelCount++;
						if (levelCount > 5) {
							levelCount = 0;
							level++;
						}

					} else {
						gameOver = true;
					}
				}
			}
		} else {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 480, 800);
			g.setColor(Color.RED);
			g.setFont(new Font("TimesRoman", Font.BOLD, 25));
			g.drawString("GAME OVER", 150, 400);
			g.drawString("Score:   " + score, 150, 450);
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			slider.swipeLeft();
			break;

		case KeyEvent.VK_RIGHT:
			slider.swipeRight();
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		while (true) {
			bg.update();
			repaint();
			try {				
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}

	}
}
