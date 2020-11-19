package main.minesweeper.aiplayer;

import java.util.ArrayList;

import main.minesweeper.Game;
import main.minesweeper.gameobjects.tiles.Tile;

public class AIBrain {
	
	private int x, y, width, height, moves = 0, tilesRevealed = 0, runId = 0, flagsPlaced = 0;
	private ArrayList<FitnessScore> fitnessOvertime = new ArrayList<FitnessScore>();
	private String tp = "";
	
	private double[][] prob = new double[35][18];
	private int[][] num = new int[35][18];
	private Game game;
	private float fs = 0.0f;
	private double bombprob = 65.0;
	private long tick;
	private boolean canReveal = false;
	
	
	int placei = (int) (Math.random() * 35);
	int placem = (int) (Math.random() * 18);
	
	private Tile[][] tiles;
	
	public AIBrain(int x, int y, int width, int height, Tile[][] tiles, Game game) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.tiles = tiles;
		this.game = game;
		tick = System.currentTimeMillis();
		for (int i = 0; i < prob.length; i++) {
			for (int m = 0; m < prob[i].length; m++) {
				prob[i][m] = 0.0;
				num[i][m] = -1;
			}
		}
	}
	
	public void think() {

		if (canReveal) {
			int i = 0;
			int m = 0;
				i = (int) (Math.random() * 35);
				m = (int) (Math.random() * 18);
			Tile t = tiles[i][m];
//			if (!t.isReveal() /*|| t.isBomb()*/) {
//				game.ig.reveal(i, m);
//				tp += "Revealing Square With: " + prob[i][m] + "Of Being A Mine.\n";
//				tick = System.currentTimeMillis();
//				canReveal = false;
//				moves++;
//			}
			if (!t.isReveal()) {
				if (prob[i][m] >= bombprob) {
					t.setFlag(true);
					flagsPlaced++;
					if (t.isBomb()) {
						fs += 5.0f;
					} else if (!t.isBomb()) {
						fs -= 5.0f;
					}
				} else if (prob[i][m] <= 25.0){
					if (t.isFlag()) {
						t.setFlag(false);
						flagsPlaced--;
					}
					game.ig.reveal(i, m);
				}
				tick = System.currentTimeMillis();
				canReveal = false;
				moves++;
			}
		}
		
		int tilesR = 0;
		for (int i = 0; i < tiles.length; i++) {
			for (int m = 0; m < tiles[i].length; m++) {
				if (tiles[i][m].isReveal()) {
					if (!tiles[i][m].isBomb()) {
						tilesR++;
					}
					if (num[i][m] != tiles[i][m].getNum()) {
						num[i][m] = tiles[i][m].getNum();
					}
//					if (num[i][m] != 0) {
//						System.out.println(i + " | " + m + " | " + num[i][m]);
//					}
				}
			}
		}
		tilesRevealed = tilesR;
		System.out.println(game.winstate);
		if (game.gameover) {
			boolean win = true;
			if (game.winstate == 0) {
				System.out.println(game.winstate);
				win = false;
			}
			
			for (int i = 0; i < tilesRevealed; i++) {
				fs += 0.05f;
			}
			
			if (win) {
				fs += 50.0f;
			} else {
				fs -= 50.0f;
			}
			
			Rating rating = Rating.bad;
			
			if (fs >= 50.0f) {
				rating = Rating.good;
			}
			
			FitnessScore fitScore = new FitnessScore(runId, fs, win, flagsPlaced, rating, prob, num, tilesRevealed);
			
			fitnessOvertime.add(fitScore);
			runId++;
			fs = 0.0f;
			flagsPlaced = 0;
			for (int i = 0; i < prob.length; i++) {
				for (int m = 0; m < prob[i].length; m++) {
					prob[i][m] = 0.0;
					num[i][m] = -1;
				}
			}
			tilesRevealed = 0;
			canReveal = false;
			tick = System.currentTimeMillis();
			System.out.println(runId);
			if (runId == 9) {
				for (int n = 0; n < fitnessOvertime.size(); n++) {
					System.out.println(fitnessOvertime.get(n));
				}
				return;
			} else {
				if (!win) {
					game.ig.reset();
				}
			}
		}
		
		if (System.currentTimeMillis() - tick >= 25) {
			canReveal = true;
		}
		
		if (!tp.equals("")) {
			System.out.println(tp);
		}
		tp = "";
	}
	
	private void thinkOne(Tile t, int i, int m) {
		if (t.isReveal() || t.isFlag()) {
			for (int iii = 0; iii < tiles.length; iii++) {
				for (int mmm = 0; mmm < tiles[iii].length; mmm++) {
					Tile t2 = tiles[iii][mmm];
					if (t2.isReveal() && t2.getNum() > 0) {
						for (int ii = max(iii - 1, 0); ii < tiles.length && ii < iii + 1; ii++) {
							for (int mm = max(mmm - 1, 0); mm < tiles[ii].length && mm < mmm + 1; mm++) {
								Tile t3 = tiles[ii][mm];
								if (!t3.isReveal()) {
									if (ii < 35 && ii > 0 && mm < 18 && mm > 0) {
										i = ii;
										m = mm;
									}
								}
							}
						}
					}
				}
			}
			t = tiles[i][m];
		}
		if (!t.isReveal()) {
			if (prob[i][m] <= 25) {
				if (t.isFlag()) {
					t.setFlag(false);
					game.b.flags++;
				}
				game.ig.reveal(i, m);
				tp += "Revealing Square With: " + prob[i][m] + "Of Being A Mine.\n";
				tick = System.currentTimeMillis();
				canReveal = false;
				moves++;
			} else if (prob[i][m] >= 65) {
				if (game.b.flags > 0) {
					game.b.flags--;
					tp += "Flagging Square With: " + prob[i][m] + "Of Being A Mine.\n";
					t.setFlag(true);
					tick = System.currentTimeMillis();
					canReveal = true;
					moves++;
				}
			}
		}
	
		// Calculate Probability
		int sides = 0;
		for (int ii = 0; i < tiles.length; ii++) {
			for (int mm = 0; m < tiles[ii].length; mm++) {
				Tile t3 = tiles[ii][mm];
				if (!t3.isReveal()) {
					for (int iii = max(i - 1, 0); iii < tiles.length && iii <= ii + 1; iii++) {
						for (int mmm = max(m - 1, 0); mmm < tiles[iii].length && mmm <= mm + 1; mmm++) {
							Tile t2 = tiles[iii][mmm];
							if (t2.isReveal()) {
								if (t2.getNum() > 0) {
									if (t2.getNum() == 1) {
										prob[ii][mm] += 5.0;
										if (sides > 2) {
											prob[ii][mm] += 10.0;
										}
									} else if (t2.getNum() == 2) {
										prob[ii][mm] += 7.5;
										if (sides > 2) {
											prob[ii][mm] += 15.0;
										}
									} else if (t2.getNum() >= 3) {
										prob[ii][mm] += 10.0;
										if (sides > 2) {
											prob[ii][mm] += 20.0;
										}
									}
									sides++;
									if (sides > 4) {
										prob[ii][mm] = 100.0;
									}
								}
							} else if (!t2.isReveal()) {
								prob[ii][mm] += 0.01;
							}
						}
					}
				}
			}
		}
	}

	private int max(int i, int j) {
		if (i > j) {
			return i;
		}
		return j;
	}

	private int min(int i, int j) {
		if (i < j) {
			return i;
		}
		return j;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getTp() {
		return tp;
	}

	public void setTp(String tp) {
		this.tp = tp;
	}

	public double[][] getProb() {
		return prob;
	}

	public void setProb(double[][] prob) {
		this.prob = prob;
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}
}
