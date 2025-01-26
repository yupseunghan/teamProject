package teamProject;

import java.util.Scanner;

public class ProgramProgram {
	Scanner sc =new Scanner(System.in);
	TvProgram tvp = new TvProgram();
	public void run() {
		runMenu();
	}

	private void runMenu() {
		String menu;
		do {
			printMenu();
			menu = sc.nextLine().trim().toUpperCase();
			switch(menu) {
			case "SBS":
				System.out.println("SBS 채널로 이동합니다");
				tvp.printView(menu);
				break;
			case "KBS":
				System.out.println("KBS 채널로 이동합니다");
				tvp.printView(menu);
				break;
			case "MBC":
				System.out.println("MBC 채널로 이동합니다");
				tvp.printView(menu);
				break;
			case "EXIT":
				System.out.println("종료합니다");
				break;
			default:
				System.out.println("잘못된 입력");
			}
		}while(!menu.equals("EXIT"));
	}

	private void printMenu() {
		System.out.println("===========TV 프로그램 편성표===========");
		System.out.println("SBS              KBS             MBC");
		System.out.println("종료 (EXIT)");
		System.out.print("선택: ");
	}
}
