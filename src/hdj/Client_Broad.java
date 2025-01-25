package hdj;

import java.io.IOException;
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
	
	private void choice() {  //방송사 선택 화면 출력
		
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

	public void run() {  //방송사 선택 후 메뉴 출력
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
						 //방송사 선택으로(이전 메뉴 구현 중)
			break;
		default:
			
		}
		
	}
	
	private void insertPro() {
		
		System.out.println("추가할 방송 프로그램을 입력하세요.");
		Broad_Program pro = input();
		
		try {
			oos.writeInt(1);
			oos.writeObject(pro);
			oos.flush();
			boolean res = ois.readBoolean();
			if(res) {
				System.out.println("방송 프로그램이 등록됐습니다.");
				return;
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		System.out.println("방송 프로그램을 등록하지 못했습니다.");
	}

	private Broad_Program input() {
		
		Broad_Program pro = inputBase();
		
		return pro;
	}

	private Broad_Program inputBase() {
		
		System.out.print("방영일자 : ");
		String date = scan.nextLine();
		System.out.print("편성시간 : ");
		String time = scan.nextLine();
		System.out.print("프로그램 제목 : ");
		String name = scan.nextLine();
		System.out.print("프로그램 종류 : ");
		String explan = scan.nextLine();
		
		return new Broad_Program(date, time, name, explan, null);
	}

	private void deletePro() {
		
		try {
			Broad_Program pro = inputBase();
			
			oos.writeInt(2);
			oos.writeObject(pro);
			oos.flush();
			
			boolean res = ois.readBoolean();
			if(res) {
				System.out.println("프로그램 정보를 삭제했습니다.");
			}
			else {
				System.out.println("프로그램 정보를 삭제하지 못했습니다.");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
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








