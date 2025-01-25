package hdj;

import java.net.Socket;
import java.util.Scanner;

public class Broad_Client {

	/* 방송사 별 방영 프로그램 편성표
	 * - 방송사 출력(KBS SBS MBC)
	 * 
	 * - 방송사 선택 후
	 *  - 선택 방송사 프로그램 편성표 출력
	 *  - 
	 *   - 프로그램 등록
	 *   - 프로그램 삭제
	 *   - 이전(방송사 선택으로)
	 * */
	
	static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		String ip = "127.0.0.1";
		int port = 5005;
		
		try(Socket socket = new Socket(ip, port)) {
			System.out.println("[서버와 연결되었습니다.]");
			Client_Broad program = new Client_Broad(socket);
			program.run();
		
		}catch(Exception e) {
			System.out.println("[서버와 연결되지 않아 프로그램을 종료합니다.]");
			e.printStackTrace();
		
		}
	}

}
