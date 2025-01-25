package hdj;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

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
		// TODO Auto-generated method stub
		
	}

}
