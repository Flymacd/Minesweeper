package main.minesweeper.aiplayer.aihandler;

import main.minesweeper.Game;
import main.minesweeper.aiplayer.AIBrain;

public class AIHandler {
	
	public AIBrain steve;
	private Game game;
	
	public AIHandler(Game game) {
		this.game = game;
		steve = new AIBrain(game.getWidth() / 2, game.getHeight() / 2, 25, 25, game.b.tiles, game);
	}
	
	public void tick() {
//		steve.think();
	}
	
}
