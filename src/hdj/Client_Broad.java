package hdj;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client_Broad {

	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Scanner scan;
	
	public Client_Broad(Socket socket) {
		this.socket = socket;
		this.scan = new Scanner(System.in);
		
		if(socket == null) {
			return;
		}
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
		}catch(Exception e) {
			//e.printStackTrace();
		}
	}
	
	private void choice() {
		
		Broad_Program broad = choiceBroad();
		System.out.println("[해당 방송사 프로그램 편성표를 출력 중 입니다.]");
		
		Broad_Program broad2 = null;
		
		try {
			oos.writeObject(broad);
			oos.flush();
			
			broad2 = (Broad_Program)ois.readObject();
			
		}catch(Exception e) {
			System.out.println("예외 발생");
		}
		if(broad2 == null) {
			System.out.println("[해당 방송사의 정보가 없습니다.]");
			return;
		}
		System.out.println("[해당 방송사 프로그램 편성표를 출력합니다.]");
	}

	public void run() {
		if(socket == null || ois == null || oos == null) {
			System.out.println("[서버 연결이 실패했습니다.]");
			return;
		}
		int menu = 0;
		
		do {
			try {
				printMenu();
				
				menu = scan.nextInt();
				scan.nextLine();
				
				runMenu(menu);
			}catch(InputMismatchException e) {
				System.out.println("[메뉴 입력은 숫자입니다.]");
				scan.nextLine();
			}catch(Exception e) {
				
			}
		}while(menu != 3);
	}

	private void printMenu() {
		System.out.print("============================\r" + 
						 "1. 방송 프로그램 추가\r" +
						 "2. 방송 프로그램 삭제\r" +
						 "3. 방송사 선택(이전 페이지)\r" +
						 "============================\r" +
						 "메뉴 선택 : ");
		
	}

	private void runMenu(int menu) {
		switch(menu){
		case 1:
			insertPro(); //프로그램 추가
			break;
		case 2:
			deletePro(); //프로그램 삭제
			break;
		case 3:
						 //방송사 선택으로
			break;
		default:
			
		}
		
	}
	
	private void insertPro() {
		
		
	}

	private void deletePro() {
		
		
	}

	private Broad_Program choiceBroad() {
		String comName;
		do {
			BroadcastingCompany.printBroad();
			System.out.print("방송사 : ");
			comName = scan.nextLine();
			System.out.println(!BroadcastingCompany.check(comName));
		}while(!BroadcastingCompany.check(comName));
		
		return null;
		//choiceBroad로 방송사를 출력 후 해당 방송사 편성표 메뉴가 출력되도록 작업
		//new Broad_Program(comName, comName, comName, comName, null);
	}
	
}








