package broadGUI.network;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client_Main {

		// TODO Auto-generated method stub
		private static Scanner scan = new Scanner(System.in);
		
		public static void main(String[] args) {

			int port = 3008;
			String ip = "127.0.0.1";


			int menu;
			
			do {
				printMenu();
				
				menu = scan.nextInt();
				scan.nextLine();
				
				runMenu(menu);
				
			}while(menu != 3);
			
			
			




		}

		private static void printMenu() {
			System.out.println("--------------------");
			System.out.println("1. 접속");
			System.out.println("2. 접속 기록 확인");
			System.out.println("3. 종료");
			System.out.println("--------------------");
			System.out.print("메뉴 선택 : ");

		}

		private static void runMenu(int menu) {
			System.out.println("--------------------");

			switch(menu) {
			case 1 :
				connect();
				break;
			case 2 :
				System.out.println("미구현된 기능입니다,");
				break;
			case 3 :
				break;
			default : 
				System.out.println("[잘못된 입력입니다.]");
			}

		}

		private static void connect() {
			String ip = "127.0.0.1";
			int port = 3008;
			
			try(Socket socket = new Socket(ip, port);) {
				
				ProgramMannager pm = new ProgramMannager();		
				pm.clientRun(ip, port);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}


	}
