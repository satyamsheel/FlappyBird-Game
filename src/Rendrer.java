import java.awt.Graphics;

import javax.swing.JPanel;

public class Rendrer extends JPanel{
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		FlappyBird.flappybird.repaint(g);
	}

	 }
