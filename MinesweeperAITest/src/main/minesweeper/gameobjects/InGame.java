package main.minesweeper.gameobjects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import main.minesweeper.Game;
import main.minesweeper.aiplayer.aihandler.AIHandler;
import main.minesweeper.enums.State;
import main.minesweeper.gameobjects.board.Board;
import main.minesweeper.gameobjects.tiles.Tile;
import main.minesweeper.input.ui.Button;

public class InGame {
	
	private Game game;
	private boolean canPlay = true;
	
	private Button reset, exit;
	public ArrayList<Button> buttons = new ArrayList<Button>();
	
	public InGame(Game game) {
		this.game = game;
		createButtons();
	}

	private void createButtons() {
		for (int i = 0; i < 2; i++) {
			Button b = reset;
			String string = "Reset";
			if (i == 1) {
				b = exit;
				string = "Exit";
			}
			b = new Button(game.getWidth() / 2 - ((game.getWidth() / 3)) + ((i) * game.getWidth() / 2), 5, game.getWidth() / 7, game.getHeight() / 11, false, false, string);
			buttons.add(b);
			if (i == 0) {
				reset = b;
			} else if (i == 1) {
				exit = b;
			}
		}
	}
	
	public void tick() {
		if (game.gameover) {
			if (game.winstate == -1) {
				for (int i = 0; i < game.b.tiles.length; i++) {
					for (int y = 0; y < game.b.tiles[i].length; y++) {
						if (game.b.tiles[i][y].isBomb()) {
							game.b.tiles[i][y].setReveal(true);
							return;
						}
					}
				}
			}
		} else if (!game.gameover) {
			game.aih.tick();
			checkIfAllRevealed();
		}
	}
	
	private void checkIfAllRevealed() {
		boolean shouldEnd = true;
		for (int i = 0; i < game.b.tiles.length; i++) {
			for (int y = 0; y < game.b.tiles[i].length; y++) {
				Tile t = game.b.tiles[i][y];
				if (!t.isBomb()) {
					if (!t.isReveal()) {
						shouldEnd = false;
					}
				}
			}
		}
		
		if (shouldEnd) {
			game.gameover = true;
			if (game.winstate != -1) {
				game.winstate = 1;
			}
			canPlay = false;
		}
	}

	public void render(Graphics g) {
		for (Button b : buttons) {
			game.dg.drawButton(b, g);
		}
		game.dg.drawString("Mines: " + game.b.mines, Game.WIDTH / 2 - 105, 60, Color.white, g);
		game.dg.drawString("Flags: " + game.b.flags, Game.WIDTH / 2 + 65, 60, Color.white, g);
		if (game.gameover) {
			String string = "You WON!";
			Color color = Color.green;
			if (game.winstate == -1) {
				string = "You LOST!";
				color = Color.red;
			}
			game.dg.drawString(string, Game.WIDTH / 2 + Game.WIDTH / 2 - 115, 60, color, g);
		}
	}
	
	public void handleMouseTick(int mx, int my) {
		for (Button b : buttons) {
			b.setHover(b.checkIfSelected(mx, my));
		}
		
		Board b = game.b;
		
		if (canPlay) {
			for (int i = 0; i < b.tiles.length; i++) {
				for (int y = 0; y < b.tiles[i].length; y++) {
					b.tiles[i][y].setHover(b.tiles[i][y].checkIfSelected(mx, my));
				}
			}
		}
	}
	
	public void handleMouseClick(int mx, int my) {
		for (int i = 0; i < buttons.size(); i++) {
			Button b = buttons.get(i);
			if (b.checkIfSelected(mx, my)) {
				if (i == 0) {
					reset();
					game.m.resetMouseClick();
				} else if (i == 1) {
					game.state = State.mm;
					game.m.resetMouseClick();
				}
			}
		}
		
		Board b = game.b;
		if (canPlay) {
			for (int i = 0; i < b.tiles.length; i++) {
				for (int y = 0; y < b.tiles[i].length; y++) {
					if (b.tiles[i][y].checkIfSelected(mx, my)) {
						if (!b.tiles[i][y].isFlag()) {
							reveal(i, y);
						}
					}
				}
			}
		}
	}
	
	public void reveal(int i, int y) {
		Board b = game.b;
		Tile t = b.tiles[i][y];
		if (!t.isReveal()) {
			t.setReveal(true);
			if (t.isBomb()) {
				game.winstate = -1;
				canPlay = false;
				game.gameover = true;
			} else if (t.getNum() == 0) {
				for (int ii = max(i - 1, 0); ii < b.width && ii <= i + 1; ii++) {
					for (int yy = max(y - 1, 0); yy < b.height && yy <= y + 1; yy++) {
						reveal(ii, yy);
					}
				}
			}
		}
	}
	
	private int max(int n, int m) {
		if (n > m) {
			return n;
		}
		return m;
	}

	public void handleRightClick(int mx, int my) {
		Board b = game.b;
		if (canPlay) {
			for (int i = 0; i < b.tiles.length; i++) {
				for (int y = 0; y < b.tiles[i].length; y++) {
					if (b.tiles[i][y].checkIfSelected(mx, my)) {
						Tile t = b.tiles[i][y];
						if (!t.isReveal()) {
							if (!t.isFlag()) {
								if (b.flags > 0) {
									b.tiles[i][y].setFlag(!b.tiles[i][y].isFlag());
									b.flags--;
								}
							} else {
								b.tiles[i][y].setFlag(!b.tiles[i][y].isFlag());
								b.flags++;
							}
						}
					}
				}
			}
		}
	}

	public void reset() {
		game.state = State.loading;
		game.b = new Board();
		game.gameover = false;
		game.winstate = 0;
		canPlay = true;
		game.state = State.game;
	}

}
