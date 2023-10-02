package com.movie;

public class MainApp {
	public static void main(String[] args) {
		
		System.out.println("프로그램을 시작합니다!");
		Menu menu = MainMenu.getInstance();
		
		while(menu != null) {
			menu.print();
			menu = menu.next();//참조주소를 돌려줌
		}
		System.out.println("프로그램이 종료됩니다.");
	}
}
