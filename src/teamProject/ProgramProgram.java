package teamProject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProgramProgram {
	Scanner sc =new Scanner(System.in);
	List<TvProgram> list = new ArrayList<>();
	public void run() {
		list.add(new TvProgram(Tv.KBS, new ArrayList<Program>()));
		list.add(new TvProgram(Tv.MBC, new ArrayList<Program>()));
		list.add(new TvProgram(Tv.SBS, new ArrayList<Program>()));
		runMenu();
	}

	private void runMenu() {
		String menu;
		do {
			printMenu();
			menu = sc.nextLine().trim().toUpperCase();
			switch(menu) {
			case "SBS":
				
				view(menu);
				break;
			case "KBS":
				
				view(menu);
				break;
			case "MBC":
				
				view(menu);
				break;
			case "EXIT":
				System.out.println("종료합니다");
				break;
			default:
				System.out.println("잘못된 입력");
			}
		}while(!menu.equals("EXIT"));
	}

	private void view(String menu) {
		List<TvProgram> tmpList = new ArrayList<TvProgram>();
		for(TvProgram tp:list) {
			if(tp.getTv().equals(Tv.valueOf(menu))) {
				tmpList.add(tp);
			}
		}
		runProgram(menu,tmpList);
	}

	private void runProgram(String menu, List<TvProgram> tmpList) {
		int me;
		do {
			menuProgram(menu,tmpList);
			me=sc.nextInt();
			switch(me) {
			case 1:insertProgram(menu);break;
			}
		}while(me != 4);
	}

	private void insertProgram(String menu) {
		System.out.print("시간:");
		sc.nextLine();
		String time =sc.nextLine();
		System.out.print("이름:");
		String name =sc.nextLine();
		System.out.print("설명:");
		String explan =sc.nextLine();
		Program p = new Program(time, name, explan);
		
		TvProgram tp = getTvProgram(menu);
		if (tp != null) {
            tp.insert(p);
            System.out.println("프로그램이 성공적으로 등록되었습니다.");
        } else {
            System.out.println("해당 채널을 찾을 수 없습니다.");
        }
	}

	private TvProgram getTvProgram(String menu) {
		for (TvProgram tp : list) {
            if (tp.getTv().equals(Tv.valueOf(menu))) {
                return tp; // 해당 메뉴의 TvProgram 반환
            }
        }
        return null;
	}

	private void menuProgram(String menu,List<TvProgram> tmpList) {
		System.out.println("==========="+menu+"채널===========");
		printProgram(tmpList);//처음 실행시 빈 문자열 출력 여부 해결해야함
		System.out.println("============================");
		System.out.println("1.등록  2.수정  3.삭제  4.돌아가기");
		System.out.print("메뉴 선택 : ");
	}

	private void printProgram(List<TvProgram> tmpList) {
		int count =1;
		for(int i=0;i<tmpList.size();i++) {
			//SBS채널에 줄넘김이 안 됨 1. 다음 2. 가 안 나옴
			//System.out.println(count+++". "+tmpList.get(i).toString());
		}
		
	}

	private void printMenu() {
		System.out.println("===========TV 프로그램 편성표===========");
		System.out.println("SBS              KBS             MBC");
		System.out.println("종료 (EXIT)");
		System.out.print("선택: ");
	}
}
