package main.minesweeper.input;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import main.minesweeper.Game;
import main.minesweeper.enums.State;



public class Mouse extends MouseAdapter implements MouseWheelListener {
	  
	private Game game;
  
	// Declaring
	public int mouseX = 0, mouseY = 0;
	public boolean canClick = false, canScroll = false;
	
	public long tick = 0;
	
	
  
	public Mouse(Game game) {
		this.game = game;
		game.addMouseListener(this);
		game.addMouseWheelListener(this);
		tick = System.currentTimeMillis();
	}
  
	public void tick() {
		// Mouse Cursor
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		Point c = game.getLocationOnScreen();
		mouseX = (int) b.getX() - (int) c.getX();
		mouseY = (int) b.getY() - (int) c.getY();
		// Mouse Cursor End
	
		if (!canClick) {
			if (System.currentTimeMillis() - tick >= 125) {
				canClick = true;
			}
		} else if (!canScroll) {
			if (System.currentTimeMillis() - tick >= 125) {
				canScroll = true;
			}
		}
		
		if (game.state == State.mm) {
			game.mm.handleMouseTick(mouseX, mouseY);
		} else if (game.state == State.ng) {
			game.ng.handleMouseTick(mouseX, mouseY);
		} else if (game.state == State.opt) {
			game.opt.handleMouseTick(mouseX, mouseY);
		} else if (game.state == State.game) {
			game.ig.handleMouseTick(mouseX, mouseY);
		}
		
	}
  
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if (canClick) {
			if (game.state == State.mm) {
				game.mm.handleMouseClick(mx, my);
			} else if (game.state == State.ng) {
				game.ng.handleMouseClick(mx, my);
			} else if (game.state == State.opt) {
				game.opt.handleMouseClick(mx, my);
			} else if (game.state == State.game) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					game.ig.handleRightClick(mx, my);
				} else if (e.getButton() == MouseEvent.BUTTON1) {
					game.ig.handleMouseClick(mx, my);
				}
			}
		}
		
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO: Mouse Scrolling
//		if (game.state == State.serverlist || game.state == State.loadgame) {
//			try {
//				game.h.uih.mouseWheel(e, e.getX(), e.getY());
//			} catch (Exception exc) {
//				game.db.printError(exc.toString());
//			}
//		}
	}
	

	public void mouseReleased(MouseEvent e) {

	}
	
	public void resetMouseClick() {
		canClick = false;
		tick = System.currentTimeMillis();
	}
	
	
	public boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if(mx > x && mx < x + width) {
			if(my > y && my < y + height) {
				return true;
			} else return false;
		} else return false;
	}
}
