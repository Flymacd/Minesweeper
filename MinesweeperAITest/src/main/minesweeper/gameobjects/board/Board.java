package main.minesweeper.gameobjects.board;

import main.minesweeper.gameobjects.tiles.Tile;

public class Board {
	
	public int width = 35, height = 18;
	public int mines = 0, flags = 0;
	private final int TILE_SIZE = 40;
	public Tile[][] tiles = new Tile[width][height];
	
	public Board() {
		// Generation
		int bombs = 100;
		int bir = 0;
		boolean canbomb = true;
		for (int i = 0; i < tiles.length; i++) {
			for (int y = 0; y < tiles[i].length; y++) {
				int r = (int) (Math.random() * 12);
				boolean bomb = false;
				int x = 5 + (i * TILE_SIZE);
				int y1 = 85 + (y * TILE_SIZE);
				if (r < 2) {
					if (bombs > 0 && canbomb) {
						bomb = true;
						bombs--;
						bir++;
						mines++;
						if (bir >= 5) {
							canbomb = false;
							bir = 0;
						}
					}
				}
				
				if (!canbomb) {
					bir++;
					if (bir >= 5) {
						canbomb = true;
						bir = 0;
					}
				}
				tiles[i][y] = new Tile(0, x, y1, TILE_SIZE, TILE_SIZE, bomb);
				if (tiles[i][y].isBomb()) {
					tiles[i][y].setNum(-1);
				}
			}
		}
		
		flags = mines;
		
		// Get Number For Nearby Mines
		for (int i = 0; i < tiles.length; i++) {
			for (int y = 0; y < tiles[i].length; y++) {
				for (int ii = i - 1; ii <= i + 1; ii++) {
					for (int yy = y - 1; yy <= y + 1; yy++) {
						if (ii >= 0 && ii < width) {
							if (yy >= 0 && yy < height) {
								if (tiles[ii][yy].isBomb()) {
									tiles[i][y].setNum(tiles[i][y].getNum() + 1);
								}
							}
						}
					}
				}
			}
		}
	}

}
