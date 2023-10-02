package com.movie;

import java.util.List;

public class AdminMenu extends AbstractMenu{
	private static final AdminMenu instance = new AdminMenu(null);
	private static final String MAIN_MENU_TEXT	= "1. 영화 등록하기\n"
												+ "2. 영화 목록보기\n"
												+ "3. 영화 삭제하기\n"
												+ "4. 메인 메뉴로 이동\n"
												+ "b. 종료\n\n"
												+ "메뉴를 선택하세요 : ";

	private AdminMenu(Menu prevMenu) {
		super(MAIN_MENU_TEXT, prevMenu);
	}

	public static AdminMenu getInstance() {
		return instance;
	}

	@Override
	public Menu next() {
		switch(sc.nextLine()) {
		case "1" : 
			createMovie();
			return this;
		case "2" :
			printAllmovie();
			return this;
		case "3" : 
			deleteMovies();
			return this;
		case "b" : return prevMenu;
		default : return this;
		}
	}

	private void deleteMovies() {
		System.out.println("영화 리스트 : ");
		printAllmovie();
		System.out.println("삭제할 영화를 선택하세요 : ");
		
		try {
			Movie.delete(sc.nextLine());
			System.out.println(">> 삭제 되었습니다. ");
		} catch (Exception e) {
			System.out.println(">> 삭제 실패하였습니다. ");
			e.printStackTrace();
		}
	}

	private void createMovie() {
		System.out.println("제목 : ");
		String title = sc.nextLine();
		
		System.out.println("장르 : ");
		String genre = sc.nextLine();
		
		Movie movie = new Movie(title, genre);
		try {
			movie.save();
			System.out.println(">> 등록 되었습니다.");
		} catch (Exception e) {
			System.out.println(">> 등록 실패하였습니다.");
		}
	}

	private void printAllmovie() {
		try {
			List<Movie> movies = Movie.findAll();
			for(Movie movie : movies)
				System.out.printf("%s\n", movie.toString());
		} catch (Exception e) {
			System.out.println("데이터 접근에 실패했습니다.");
}
	}
	
	

}
