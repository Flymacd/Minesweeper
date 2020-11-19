package main.minesweeper.aiplayer;

public class FitnessScore {

	public float fs = 0.00f;
	public int flagsPlaced = 0, runId = 0, tilesRevealed;
	public boolean win = false;
	public double prob[][] = new double[35][18];
	public int num[][] = new int[35][18];
	public Rating rating;
	
	public FitnessScore(int runId, float fs, boolean win, int flagsPlaced, Rating rating, double prob[][], int num[][], int tilesRevealed) {
		this.fs = fs;
		this.win = win;
		this.flagsPlaced = 	flagsPlaced;
		this.rating = rating;
		this.runId = runId;
		this.prob = prob;
		this.num = num;
		this.tilesRevealed = tilesRevealed;
	}
	
	public String toString() {
		return "Run " + runId + ": \nFitness Score: " + fs + " | Win: " + win + " | Flags Placed: " + flagsPlaced + " | Tiles Revealed: " + tilesRevealed + " | Rating: " + rating; 
	}
	
}
