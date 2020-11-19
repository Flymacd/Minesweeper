package main.minesweeper.gameobjects.tiles;

public class Tile {

	private int num, x, y, width, height;
	private boolean bomb, reveal = false, flag = false, hover = false;
	
	public Tile(int num, int x, int y, int width, int height, boolean bomb) {
		this.num = num;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.bomb = bomb;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
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

	public boolean isBomb() {
		return bomb;
	}

	public void setBomb(boolean bomb) {
		this.bomb = bomb;
	}

	public boolean isReveal() {
		return reveal;
	}

	public void setReveal(boolean reveal) {
		this.reveal = reveal;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public boolean isHover() {
		return hover;
	}

	public void setHover(boolean hover) {
		this.hover = hover;
	}
	
	public boolean checkIfSelected(int mx, int my) {
		if (mouseOver(mx, my, x, y, width, height)) {
			return true;
		}
		return false;
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if(mx > x && mx < x + width) {
			if(my > y && my < y + height) {
				return true;
			} else return false;
		} else return false;
	}
	
	public String toString() {
		return "Tile Number: " + getNum();
	}
}
