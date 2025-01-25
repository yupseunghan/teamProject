package hdj;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
public class Server {

	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	List<Broad_Program> list;

	public Server(Socket socket, List<Broad_Program> list) {
		this.socket = socket;
		this.list = list;
		if(socket == null) {
			return;
		}
			try {
				ois = new ObjectInputStream(socket.getInputStream());
				oos = new ObjectOutputStream(socket.getOutputStream());
				
			}catch(Exception e) {
			
		}
	}

	public void run() {
		if(socket == null || ois == null || oos == null) {
			return;
		}
		Thread t = new Thread(()->{
			
			int menu;
			try {
				do {
					menu = ois.readInt();
					runMenu(menu, ois, oos);
				}while(menu != 3);
			}catch(IOException e) {
				e.printStackTrace();
			}
		});
		t.start();
		
	}

	private void runMenu(int menu, ObjectInputStream ois, ObjectOutputStream oos) {
		switch(menu) {
		case 1:
			insertPro(ois, oos); //프로그램 추가
			break;
		case 2:
			deletePro(ois, oos); //프로그램 삭제
			break;
		case 3: 
			
			break;
		default :
			
		}
		
	}

	private void insertPro(ObjectInputStream ois, ObjectOutputStream oos) {
		
		try {
			Broad_Program pro = (Broad_Program)ois.readObject();
			
			boolean res = true;
			
			if(list.contains(pro)) {
				res = false;
			}
			else {
				list.add(pro);
			}
			oos.writeBoolean(res);
			oos.flush();
			//sort();    //정렬이 필요할 경우
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	/*private void sort() {
		list.sort((o1, o2)->{
			if(o1.getDate() != o2.getDate()) {
				return o1.getDate() - o2.getDate();
			}
			if(o1.getTime() != o2.getTime()) {
				return o1.getTime() - o2.getTime();
			}
			return o1.getNum() - o2.getNum();
		});
		System.out.println(list);
	}*/

	private void deletePro(ObjectInputStream ois, ObjectOutputStream oos) {
		
		try {
			Broad_Program pro = (Broad_Program)ois.readObject();
			
			boolean res = list.remove(pro);
			oos.writeBoolean(res);
			oos.flush();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
