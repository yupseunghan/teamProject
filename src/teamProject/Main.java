package teamProject;

<<<<<<< Updated upstream
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

=======
>>>>>>> Stashed changes
public class Main {

	static Scanner sc =new Scanner(System.in);
	public static void main(String[] args) {
<<<<<<< Updated upstream
		ArrayList<Program> program = new ArrayList<Program>();
		Program p = new Program(null,"12:30","14:00","무한도전", "꿀잼");
		char menu;
		do {
			System.out.print("주간 TV편성 프로그램\n"
					+ "=====================================\n");
			try {
				
				p.setDate("2020-12-12");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(p);
			System.out.println("=====================================");
			System.out.print("1.프로그램 추가\n2.프로그램 수정\n3.프로그램 삭제\n4.프로그램 조회\n5.프로그램 종료\n메뉴선택: ");
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
=======
		ProgramProgram program = new ProgramProgram();
		program.run();
>>>>>>> Stashed changes
	}

}
