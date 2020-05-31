import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class FlappyBird implements ActionListener,MouseListener,KeyListener{
	
	
	public static FlappyBird flappybird;
	public Rendrer rendrer;
	public Rectangle bird;
	public final int HEIGHT=800,WIDTH=800;
	public ArrayList<Rectangle> columns;
	public Random rand;
	public int ticks,yMotion,score;
	public boolean gameover,started;
	
	public FlappyBird()
	{
		
		JFrame jframe=new JFrame();
		rendrer=new Rendrer();
		rand=new Random();
		Timer timer=new Timer(20,this);
		jframe.add(rendrer);
		jframe.setSize(HEIGHT, WIDTH);
		jframe.setVisible(true);	
		jframe.setTitle("Flappy Bird");
		jframe.addMouseListener(this);
		jframe.addKeyListener(this);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setResizable(false);
		
		
		timer.start();
		
		bird=new Rectangle((HEIGHT/2)-10,(WIDTH/2)-10,20,20);
		columns=new ArrayList<Rectangle>();
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		
	}
	
	public void addColumn(Boolean start){
		int space=300;
		int width=100;
		int height=50+rand.nextInt(300);
		
		if(start){
		columns.add(new Rectangle(WIDTH+width+columns.size()*300,HEIGHT-height-120,width,height));
		columns.add(new Rectangle(WIDTH+width+(columns.size()-1)*300,0,width,HEIGHT-height-space));
		}else{
			columns.add(new Rectangle(columns.get(columns.size()-1).x+600,HEIGHT-height-120,width,height));
			columns.add(new Rectangle(columns.get(columns.size()-1).x,0,width,HEIGHT-height-space));
		}
	}
	
	public void paintColumn(Graphics g,Rectangle column)
	{
		g.setColor(Color.GREEN.darker());
		g.fillRect(column.x, column.y, column.width, column.height);
		
	}
	
	
	public void repaint(Graphics g){
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, HEIGHT, WIDTH);
		
		g.setColor(Color.ORANGE);
		g.fillRect(0, HEIGHT-120, HEIGHT, 120);
		
		g.setColor(Color.green);
		g.fillRect(0, HEIGHT-120, HEIGHT, 20);
		
		g.setColor(Color.red);
		g.fillRect(bird.x, bird.y, bird.width, bird.height);
		
		for(Rectangle column:columns)
		{
			paintColumn(g,column);
		}
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial",1,80));
		
		if(!started){
			g.drawString("Click to Start!", 75, HEIGHT/2 -50 );
		}

		if(gameover){
			g.drawString("Game Over!", 100, HEIGHT/2 -50 );
		}
		
		if(!gameover && started){
			g.drawString(String.valueOf(score), WIDTH/2-25,100);
		}
	}
	
	

	public static void main(String[] args) {
		flappybird=new FlappyBird();

	}
	
	public void jump(){
		if(gameover){
			
			bird=new Rectangle((HEIGHT/2)-10,(WIDTH/2)-10,20,20);
			columns.clear();
			yMotion=0;
			score=0;
			
			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);
			gameover=false;
		}
		if(!started){
			started=true;
		}
		else if(!gameover){
			if(yMotion>0){
				yMotion=0;
			}
			yMotion-=10;
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		ticks++;	
		int speed=10;
		if(started){
		for(int i=0;i<columns.size();i++){
			Rectangle column=columns.get(i);
			column.x-=speed;
		}
		if(ticks%2==0 && yMotion <15)
		{
			yMotion+=2;
		}
		for(int i=0;i<columns.size();i++){
			Rectangle column=columns.get(i);
			if(column.x+column.width<0)
			{
				columns.remove(column);
				if(column.y==0)
				{
					addColumn(false);
				}
			}
		}
		
		bird.y+=yMotion;
		
		for(Rectangle column:columns ){
			
			if(column.y==0 && bird.x+bird.width/2>column.x+column.width/2-10 && bird.x+bird.width/2<column.x+column.width/2+10 )
			{
				score++;
			}
			
			if(	column.intersects(bird))
			{
				gameover=true;
				if(bird.x <= column.x){
					bird.x=column.x-bird.width;
				}else{
					if(column.y !=0)
					{
						bird.y=column.x-bird.width;
					}else if(bird.y<column.height)
					{
						bird.y=column.height;
					}
				}
				
				
			}
		}
		if(bird.y>HEIGHT-120 || bird.y<0){
			
			gameover=true;
		}
		
		if(bird.y+yMotion >= HEIGHT-120)
		{
			bird.y=HEIGHT-120-bird.height; 
		}
	}
		
		rendrer.repaint();
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		jump();
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
			jump();
		}	
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
