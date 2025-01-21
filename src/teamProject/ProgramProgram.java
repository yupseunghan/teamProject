package teamProject;

import java.util.ArrayList;
import java.util.Scanner;

public class ProgramProgram implements ConsoleProgram{
	Scanner sc = new Scanner(System.in);
	@Override
	public void run() {
		ArrayList<Program> program = new ArrayList<Program>();
		char menu;
		printMenu();
		menu=sc.next().charAt(0);
		runMenu(menu);
	}

	@Override
	public void printMenu() {
		System.out.println("=============주간 TV 프로그램=============");
		System.out.println("1.프로그램 조회              2.프로그램 추가");
		System.out.println("3.프로그램 수정              4.프로그램 삭제");
		System.out.print("메뉴선택(종료'5'): ");
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void runMenu(char menu) {
		do {
			switch(menu) {
			case'1':insert();
			case'2':System.out.println("수정하는 메서드");
			case'3':System.out.println("삭제하는 메서드");
			case'4':System.out.println("조회하는 메서드");
			case'5':System.out.println("종료합니다"); break;
			default:System.out.println("잘못된 메뉴선택"); 
			}
		}while(menu !='5');
	}

	private void insert() {
		Program p = inputProgram();
	}

	private Program inputProgram() {
		System.out.print("날짜 입력: ");
		String programDay = sc.nextLine();
		return null;
	}

}
