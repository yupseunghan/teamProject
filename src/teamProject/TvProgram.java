package teamProject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TvProgram {
	Scanner sc = new Scanner(System.in);
	ProgramProgram program = new ProgramProgram();
	List<Program> tv = new ArrayList<>();
	public void printView(String menu) {
		for(Program p : tv) {
			if(p.getTv().equals(menu)) {
				p.print();
			}
		}
		runMenu();
	}
	private void runMenu() {
		printMenu();
		int menu;
		do {
			menu=sc.nextInt();
			switch(menu) {
			case 1:
				insertProgram();
				break;
			case 4:
				back();
				break;
			}
		}while(menu!=4);
	}
	private void insertProgram() {
		System.out.println("추가");
	}
	private void back() {
		program.run();
	}
	private void printMenu() {
		System.out.println("==================================");
		System.out.println("1.추가하기 2.삭제하기 3.수정하기 4.돌아가기");
		System.out.print("선택: ");
	}
	
	
}
