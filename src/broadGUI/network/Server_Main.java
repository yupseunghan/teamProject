package broadGUI.network;

import java.net.ServerSocket;
import java.net.Socket;

public class Server_Main {

	public static void main(String[] args) {

		
		String ip = "127.0.0.1";



			connect();
			

		
		
		




	}


	private static void connect() {
		int port = 3008;
		 
		ServerSocket serverSocket;
		try{
			serverSocket = new ServerSocket(port);
		}catch (Exception e) {
			System.out.println("[서버소켓 생성 실패]");
			e.printStackTrace();
			return;
		}
		// 연결을 기다리다 연결이 성공하면 소캣 객체 생성
		try(Socket socket = serverSocket.accept()) {
			
			// ChatClient 객체 생성
			ProgramMannager pm = new ProgramMannager();	
			//객체를 실행해서 채팅
			pm.serverRun();
			
		} catch (Exception e) {
			//e.printStackTrace();
		}finally {
			if(serverSocket != null && ! serverSocket.isClosed()) {
				try {
					serverSocket.close();
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		
	}



}
