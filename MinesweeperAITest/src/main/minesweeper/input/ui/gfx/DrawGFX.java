package main.minesweeper.input.ui.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import main.minesweeper.Game;
import main.minesweeper.gameobjects.board.Board;
import main.minesweeper.gameobjects.tiles.Tile;
import main.minesweeper.input.ui.Button;



public class DrawGFX {
	
	private Game game;
	
	public DrawGFX(Game game) {
		this.game = game;
	}
	
	public void drawString(String s, int x, int y, Color color, Graphics g) {
		int size = 2;
		
		g.setColor(Color.black);
		g.drawString(s, x - g.getFontMetrics().stringWidth(s) / 2 + size, y - g.getFontMetrics().getHeight() / 3 + size);
		
		g.setColor(color);
		g.drawString(s, x - g.getFontMetrics().stringWidth(s) / 2, y - g.getFontMetrics().getHeight() / 3);
	}
	
	public void drawButton(Button b, Graphics g) {
		if (b.isHover()) {
			g.setColor(Color.GREEN);
		} else {
			g.setColor(Color.BLACK);
		}
		
		g.drawRect(b.getX() - 1, b.getY() - 1, b.getWidth() + 2, b.getHeight() + 2);
		
		g.drawRect(b.getX(), b.getY(), b.getWidth(), b.getHeight());
		
		if (b.getString() != null) {
			//drawCenteredStringShadow(b, g);
				if (b.isHover()) {
					g.setColor(Color.GREEN);
				} else {
					g.setColor(Color.WHITE);
				}
			
			drawCenteredString(b, g);
		}
	}
	
	public void drawTitle(String s, Graphics g, int y) {
		// TEXT SCALING
		Font fntT = new Font(game.fnt.getFontName(), 1, 50);
		g.setFont(fntT);
		FontMetrics fm = g.getFontMetrics();
	    int x = (Game.WIDTH - fm.stringWidth(s)) / 2;
	    g.setColor(Color.BLACK);
	    g.drawString(s, x + 2, y);
	    g.setColor(Color.LIGHT_GRAY);
	    g.drawString(s, x, y + 2);
	    g.setFont(game.fnt);
	}
	
	public void drawBoard(Board b, Graphics g) {
		for (int i = 0; i < b.tiles.length; i++) {
			for (int y = 0; y < b.tiles[i].length; y++) {
				if (b.tiles[i][y].isHover()) {
					g.setColor(Color.green);
				} else {
					g.setColor(Color.black);
				}
				Tile t = b.tiles[i][y];
				g.drawRect(t.getX(), t.getY(), t.getWidth() - 1, t.getHeight() - 1);
				
				if (t.isReveal()) {
					// Revealed
					if (t.isBomb()) {
						g.setColor(Color.WHITE);
						g.fillRect(t.getX() + 1, t.getY() + 1, t.getWidth() - 2, t.getHeight() - 2);
						g.setColor(Color.BLACK);
						g.fillOval(t.getX() + 5, t.getY() + 5, t.getWidth() - 10, t.getHeight() - 10);
					} else {
						g.setColor(Color.WHITE);
						g.fillRect(t.getX() + 1, t.getY() + 1, t.getWidth() - 2, t.getHeight() - 2);
						g.setColor(Color.BLACK);
						if (t.getNum() > 0) {
							drawCenteredString(t, t.getNum() + "", g);
						}
					}
				} else {
					g.setColor(Color.DARK_GRAY);
					g.fillRect(t.getX() + 1, t.getY() + 1, t.getWidth() - 2, t.getHeight() - 2);
					if (t.isFlag()) {
						g.setColor(Color.black);
						g.fillRect(t.getX() + 7 + t.getWidth() / 3, t.getY() + 7, t.getWidth() / 5, t.getHeight() / 2);
						g.fillRect(t.getX() + 12, t.getY() + 7 + t.getHeight() / 2, t.getWidth() / 2, t.getHeight() / 5);
						g.setColor(Color.red);
						g.fillRect(t.getX() + 7, t.getY() + 7, t.getWidth() / 3, t.getHeight() / 3);
					}
				}
			}
		}
	}
	
	public void drawCenteredString(Button b, Graphics g) {
		FontMetrics fm = g.getFontMetrics();
	    int x = b.getX() + (b.getWidth() - fm.stringWidth(b.getString())) / 2 - 1;
	    int y = b.getY() + (fm.getAscent() + (b.getHeight() - (fm.getAscent() + fm.getDescent())) / 2) - 1;
	    g.drawString(b.getString(), x, y);
	}
	
	public void drawCenteredString(Tile b, String string, Graphics g) {
		FontMetrics fm = g.getFontMetrics();
	    int x = b.getX() + (b.getWidth() - fm.stringWidth(string)) / 2 - 1;
	    int y = b.getY() + (fm.getAscent() + (b.getHeight() - (fm.getAscent() + fm.getDescent())) / 2) - 1;
	    g.drawString(string, x, y);
	}
	
	public void drawCenteredStringShadow(Button b, Graphics g) {
		g.setColor(Color.black);
		FontMetrics fm = g.getFontMetrics();
	    int x = b.getX() + (b.getWidth() - fm.stringWidth(b.getString())) / 2 - 1;
	    int y = b.getY() + (fm.getAscent() + (b.getHeight() - (fm.getAscent() + fm.getDescent())) / 2) - 1;
	    int size = 2;
		if (Game.WIDTH > 900 && Game.HEIGHT > 700) {
			size = 2;
		} else {
			size = 1;
		} 
	    g.drawString(b.getString(), x + size, y + size);
	}
	
	public Color getColor(int r, int g, int b) {
		return new Color(r, g, b);
	}
}