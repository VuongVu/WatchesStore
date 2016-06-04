package com.wstore.beans;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Bean {
	private int x;
	private int y;
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
	
	public void submit(){
		System.out.println("x: " + x);
	    System.out.println("y: " + y);
	}
}
