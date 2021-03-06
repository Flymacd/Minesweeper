package main.minesweeper.ng;

import java.awt.Graphics;
import java.util.ArrayList;

import main.minesweeper.Game;
import main.minesweeper.enums.State;
import main.minesweeper.input.ui.Button;

public class NewGame {
	
	private Game game;
	
	private Button start, back;
	public ArrayList<Button> buttons = new ArrayList<Button>();
	
	public NewGame(Game game) {
		this.game = game;
		createButtons();
	}

	private void createButtons() {
		for (int i = 0; i < 2; i++) {
			Button b = start;
			String string = "Start";
			if (i == 1) {
				b = back;
				string = "Back";
			}
			b = new Button(game.getWidth() / 2 - ((game.getWidth() / 7) / 2), game.getHeight() / 2 + (game.getHeight() / 4) - ((i - 3) * -game.getHeight() / 10), game.getWidth() / 7, game.getHeight() / 11, false, false, string);
			buttons.add(b);
			if (i == 0) {
				start = b;
			} else if (i == 1) {
				back = b;
			}
		}
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		game.dg.drawTitle("New Game", g, 150);
		for (Button b : buttons) {
			game.dg.drawButton(b, g);
		}
	}
	
	public void handleMouseTick(int mx, int my) {
		for (Button b : buttons) {
			b.setHover(b.checkIfSelected(mx, my));
		}
	}
	
	public void handleMouseClick(int mx, int my) {
		for (int i = 0; i < buttons.size(); i++) {
			Button b = buttons.get(i);
			if (b.checkIfSelected(mx, my)) {
				if (i == 0) {
					game.startNewGame();
					game.m.resetMouseClick();
				} else if (i == 1) {
					game.state = State.mm;
					game.m.resetMouseClick();
				}
			}
		}
	}

}
