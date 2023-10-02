package com.movie;

import java.util.Scanner;

abstract public class AbstractMenu implements Menu{
	
	protected String menuText;//기본문구
	protected Menu prevMenu;//이전메뉴
	protected static final Scanner sc = new Scanner(System.in);
	
	public AbstractMenu(String menuText, Menu prevMenu) {
		this.menuText = menuText;
		this.prevMenu = prevMenu;
	}

	@Override
	public void print() {
		System.out.println("\n" + menuText);
	}

	public void setPrevMenu(Menu preMenu) {
		this.prevMenu = preMenu;
	}
	
}
