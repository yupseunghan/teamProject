package teamProject;

import java.util.ArrayList;
import java.util.Scanner;

public class BroadcastBuild {
	
	
	
	
	static Scanner sc =new Scanner(System.in);
	static ArrayList<Program> program = new ArrayList<Program>();
	
	public void run() {
	char menu = 0;
			
	do {
		printmenu(); 
		
		menu=sc.next().charAt(0);
		sc.nextLine();
		
		runmenu(menu);
		
	}while(menu!='5');
	}

	private void runmenu(char menu) {
		switch(menu) {
		case'1':System.out.println("추가하는 메서드");break;
		case'2':System.out.println("수정하는 메서드");break;
		case'3':System.out.println("삭제하는 메서드");break;
		case'4':System.out.println("조회하는 메서드");break;
		case'5':System.out.println("종료합니다");break;
		default:System.out.println("잘못된 메뉴선택");break;
		}
		
	}

	private void printmenu() {
		System.out.println("주간 TV편성 프로그램");
		System.out.println("--------------------");
		System.out.println("1.프로그램 추가\n2.프로그램 수정\n3.프로그램 삭제\n4.프로그램 조회\n5.프로그램 종"
				+ "료");
		System.out.println("--------------------");
		System.out.print("메뉴 선택: ");
	}
}
