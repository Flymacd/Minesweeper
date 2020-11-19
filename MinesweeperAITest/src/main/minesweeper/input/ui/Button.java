package main.minesweeper.input.ui;

public class Button {
	
	private int x, y, width, height;
	private boolean hover, select;
	private String string;
	
	public Button(int x, int y, int width, int height, boolean hover, boolean select, String string) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.hover = hover;
		this.select = select;
		this.string = string;
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

	public boolean isHover() {
		return hover;
	}

	public void setHover(boolean hover) {
		this.hover = hover;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}
	
	public boolean checkIfSelected(int mx, int my) {
		if (mouseOver(mx, my, x, y, width, height)) {
			if (!select) {
				return true;
			}
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

}
