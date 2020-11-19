package main.minesweeper.opt;

import java.awt.Graphics;
import java.util.ArrayList;

import main.minesweeper.Game;
import main.minesweeper.enums.State;
import main.minesweeper.input.ui.Button;

public class Options {
	
	private Game game;
	
	private Button apply, back;
	public ArrayList<Button> buttons = new ArrayList<Button>();
	
	public Options(Game game) {
		this.game = game;
		createButtons();
	}

	private void createButtons() {
		for (int i = 0; i < 2; i++) {
			Button b = apply;
			String string = "Apply";
			if (i == 1) {
				b = back;
				string = "Back";
			}
			b = new Button(game.getWidth() / 2 - ((game.getWidth() / 7) / 2), game.getHeight() / 2 + (game.getHeight() / 4) - ((i - 3) * -game.getHeight() / 10), game.getWidth() / 7, game.getHeight() / 11, false, false, string);
			buttons.add(b);
			if (i == 0) {
				apply = b;
			} else if (i == 1) {
				back = b;
			}
		}
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		game.dg.drawTitle("Options", g, 150);
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
					System.out.println("lol");
					game.m.resetMouseClick();
				} else if (i == 1) {
					game.state = State.mm;
					game.m.resetMouseClick();
				}
			}
		}
	}

}
