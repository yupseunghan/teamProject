package teamProject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ProgramProgram implements ConsoleProgram{
	Scanner scan = new Scanner(System.in);
	ArrayList<Program> list = new ArrayList<Program>();
	
	@Override
	public void run() {
		runMenu();
	}

	@Override
	public void printMenu() {
		System.out.println("=============주간 TV 프로그램=============");
		System.out.println("1.프로그램 추가              2.프로그램 수정");
		System.out.println("3.프로그램 삭제              4.프로그램 조회");
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
	public void runMenu() {
		char menu;
		do {
			printMenu();
			
			menu = scan.next().charAt(0);
			scan.nextLine();
			
			switch(menu) {
			case'1':
				insert(); 
				break;
			case'2':
				update(); 
				break;
			case'3':
				delete(); 
				break;
			case'4':
				search(); 
				break;
			case'5':
				System.out.println("종료합니다"); 
				break;
			default:
				System.out.println("잘못된 메뉴선택"); 
			}
		}while(menu !='5');
	}

	private void update() {
		List<Program> p =search();
		System.out.print("수정시킬 번호: ");
		int index = scan.nextInt()-1;
		Program oldP=p.get(index);
		System.out.println("수정할 내용 입력하세요");
		scan.nextLine();
		Program newP = inputProgram();
		int newindex = list.indexOf(oldP);
		list.set(newindex, newP);
	}

	private void delete() {
		List<Program> p =search();
		System.out.print("삭제시킬 번호: ");
		int index = scan.nextInt()-1;
		Program delP=p.get(index);
		list.remove(delP);
		System.out.println(p.get(index).printAll()+"삭제 성공");
	}

	private List<Program> search() {
		//번호를 입력
		System.out.print("날짜 : ");
		String day = scan.nextLine();
<<<<<<< Updated upstream
		Program searP = new Program(day, "", "", "");
		List<Program> p = new ArrayList<>();
		int index=0;
		for(Program pro : list) {
			if(pro.checkDay(searP)) {
				p.add(pro);
=======
		System.out.print("시간 : ");
		String time = scan.nextLine();
		
		Program tmpP = new Program(day, "", "", time);
		List<Program> p = new ArrayList<Program>();
		int index = 0;
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).equals(tmpP)) {
				p.add(list.get(i));
>>>>>>> Stashed changes
				index++;
				System.out.println(index+". "+pro.printAll());
			}
		}
		return p;
	}

	private void insert() {
		Program p = inputProgram();
		list.add(p);
		System.out.println("프로그램을 등록 했습니다.");
	}

	private Program inputProgram() {
		System.out.print("날짜 입력: ");
		String programDay = scan.nextLine();
		
		System.out.print("프로그램명 : ");
		String programName = scan.nextLine();
		
		System.out.print("설명 : ");
		String programExpain = scan.nextLine();
		
		System.out.print("프로그램 시간: ");
		String programTime = scan.nextLine();
		
		return new Program(programDay,programName,programExpain,programTime);
	}
	

}
