package crestyledesign.ctdo.spokepov;

import java.awt.Color;
import java.io.Serializable;

public class DotDataEntry implements Serializable {
	private static final long serialVersionUID = -9215006078538794415L;
	private Color color = Color.black;
	
	public DotDataEntry() {
		this.color = Color.black;	// black is the only real color ;-)
	}
	
	public DotDataEntry(Color c) {
		this.color = c;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
}
