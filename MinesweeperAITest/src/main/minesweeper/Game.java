package main.minesweeper;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import main.minesweeper.aiplayer.aihandler.AIHandler;
import main.minesweeper.enums.State;
import main.minesweeper.gameobjects.InGame;
import main.minesweeper.gameobjects.board.Board;
import main.minesweeper.input.Mouse;
import main.minesweeper.input.ui.gfx.DrawGFX;
import main.minesweeper.mm.MainMenu;
import main.minesweeper.ng.NewGame;
import main.minesweeper.opt.Options;



@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable {

	public static int WIDTH = 1400, HEIGHT = 800;
	public static final double VERSION = 0.01;
	public static final String NAME = "Minesweeper " + " | " + "v" + VERSION;
	public static Game game;
	
	
	public JFrame frame;
	
	public int fntSize = 25;
	public Font fnt = new Font("verdana", 1, fntSize);
	
	// Game State
	public State state = State.mm;
	
	// Menus
	public MainMenu mm;
	public NewGame ng;
	public Options opt;
	public InGame ig;
	
	// AI Handler
	public AIHandler aih;
	
	// Board
	public Board b;

	// GFX
	public DrawGFX dg;
	
	// Input
	public Mouse m;
	
	public int winstate = 0;
	public boolean gameover = false;
	
	public int fps = 0;
	
	
	public boolean running = false;
	public Thread thread;
	
	
	public Game() {
		// Initialize
		init();
		
		// for fun
//		int i = 1;
//		for (Resolution r : opt.resos) {
//			db.printLine(i + ": " + r);
//			i++;
//		}
		
		createFrame();
	}
	
	public void createFrame() {
		frame = new JFrame(NAME);
		
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setFocusable(true);
		
		frame.requestFocus();
	}
	
	public void init() {
		mm = new MainMenu(this);
		ng = new NewGame(this);
		opt = new Options(this);
		ig = new InGame(this);
		m = new Mouse(this);
		dg = new DrawGFX(this);
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/60D;
		int ticks = 0;
		int frames = 0;
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		init();
		
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			
			while (delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}
			
			try { 
				Thread.sleep(2);;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (shouldRender) {
				frames++;
				render();
			}
			
			if (System.currentTimeMillis() - lastTimer > 1000) {
				lastTimer = System.currentTimeMillis();
				fps = frames;
				frames = 0;
				ticks = 0;
			}
		}
	}
	
	public void tick() {
		m.tick();
		if (state == State.mm) {
			mm.tick();
		} else if (state == State.ng) {
			ng.tick();
		} else if (state == State.opt) {
			opt.tick();
		} else if (state == State.game) {
			ig.tick();
		}
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		
		Graphics g = bs.getDrawGraphics();
		g.setFont(fnt);
		g.setColor(new Color(27, 33, 76));
		g.fillRect(0, 0, getWidth(), getHeight());
		
		
		if (state == State.mm) {
			mm.render(g);
		} else if (state == State.ng) {
			ng.render(g);
		} else if (state == State.opt) {
			opt.render(g);
		} else if (state == State.game) {
			dg.drawBoard(b, g);
			ig.render(g);
		}
		
//		g.setColor(Color.white);
//		g.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
		
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		new Game().start();
	}

	
	public void startNewGame() {
		b = new Board();
		aih = new AIHandler(this);
		state = State.game;
	}
}
