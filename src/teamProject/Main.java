package teamProject;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	static Scanner sc =new Scanner(System.in);
	public static void main(String[] args) {
		ArrayList<Program> program = new ArrayList<Program>();
		char menu;
		do {
			System.out.print("주간 TV편성 프로그램\n1.프로그램 추가\n2.프로그램 수정\n3.프로그램 삭제\n4.프로그램 조회\n5.프로그램 종"
			+ "료\n메뉴 선택: ");
			menu=sc.next().charAt(0);
			switch(menu) {
			case'1':System.out.println("추가하는 메서드");
			case'2':System.out.println("수정하는 메서드");
			case'3':System.out.println("삭제하는 메서드");
			case'4':System.out.println("조회하는 메서드");
			case'5':System.out.println("종료합니다");
			default:System.out.println("잘못된 메뉴선택");
			}
		}while(menu=='5');
	}

}
