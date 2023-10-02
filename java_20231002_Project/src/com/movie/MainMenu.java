package com.movie;

import java.io.IOException;
import java.util.List;

public class MainMenu extends AbstractMenu{

	private static final MainMenu instance = new MainMenu(null);
	private static final String MAIN_MENU_TEXT	= "1. 영화 예매하기\n"
												+ "2. 예매 확인하기\n"
												+ "3. 예매 취소하기\n"
												+ "4. 관리자 메뉴로 이동\n"
												+ "q. 종료\n\n"
												+ "메뉴를 선택하세요 : ";
													
	
	
	public MainMenu(Menu prevMenu) {
		super(MAIN_MENU_TEXT, prevMenu);
	}

	public static MainMenu getInstance() {
		return instance;
	}

	@Override
	public Menu next() {
		
		switch(sc.nextLine()) {
		case "1" :
			reserve();
			return this;
		case "2" :
			checkReservation();
			return this;
		case "3" : 
			cancelReservation();
			return this;
		case "4" : 
			while(!checkAdminPassword()) {
				System.out.println(">> 비밀번호가 틀렸습니다.");
				System.out.println("비밀번호를 다시 입력해주세요");
//				return this;
			}
			AdminMenu adminMenu = AdminMenu.getInstance();
			adminMenu.setPrevMenu(this);//this => MainMenu
			return adminMenu;
		case "q" : return prevMenu;
		default : return this;
		}
	}

	private void reserve() {
		try {
			List<Movie> movies = Movie.findAll();
			for(Movie movie : movies) {
				System.out.printf("%s\n", movie);
				
			}
			System.out.println("예매할 영화를 선택하세요. ");
			String movieIdStr = sc.nextLine();
			Movie m = Movie.findById(movieIdStr);
			
			List<Reservation> reservations = Reservation.findByMovieId(movieIdStr);
			Seats seats = new Seats(reservations);
			seats.show();
			
			System.out.println("좌석을 선택하세요(예: E-9)");
			String seatName = sc.nextLine();
			seats.mark(seatName);
			
			Reservation r = new Reservation(Long.parseLong(movieIdStr), m.getTitle(), seatName);
			r.save();
			System.out.println(">> 예매가 완료되었습니다. ");
			System.out.printf("예매번호 : %d\n", r.getId());
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	private void cancelReservation() {
		System.out.println("예매번호를 입력하세요 : ");
		try {
			Reservation r = Reservation.cencel(sc.nextLine());
			
			if(r != null) System.out.printf(">> [취소 완료] %s의 예매가 취소되었습니다. \n", r);
			else System.out.println("예매내역이 존재하지 않습니다.");
			
		} catch (Exception e) {
			System.out.println(">> 파일 입출력 문제가 생겼습니다. ");
		}
	}

	private void checkReservation() {
		System.out.println("예매번호를 입력하세요. ");
		try {
			Reservation r = Reservation.findById(sc.nextLine());
			
			if(r != null) {
				System.out.printf(">> [확인 완료] %s\n", r.toString());
			}else {
				System.out.println(">> 예매 내역이 없습니다. ");
			}
		} catch (Exception e) {
			System.out.println();
		}
	}

	private boolean checkAdminPassword() {
		System.out.println("관리자 비밀번호를 입력하세요.");
		return "admin".equals(sc.nextLine());
	}

}
