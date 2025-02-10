package broadGUI;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface ConsoleProgram {

	void run();

	default void printMenu() {}	//추상메소드가 아닌 디폴트로 만들면 구현 안해도됨

	default void runMenu(int menu) {}

	default Object load(String fileName) {
		try(FileInputStream fis = new FileInputStream(fileName);
				ObjectInputStream ois = new ObjectInputStream(fis)){

			return ois.readObject();

		}catch(NullPointerException e) {
  			System.out.println("-------------------");
  			System.out.println(fileName + "불러올 파일이 없습니다.");
  			System.out.println("-------------------");
  		} catch (Exception e) {
  			System.out.println("-------------------");
  			System.out.println(fileName + "불러오기 실패");
  			System.out.println("-------------------");
  		}
		return null;
	}

	default void save(String fileName, Object obj) {
		try(FileOutputStream fos = new FileOutputStream(fileName);
				ObjectOutputStream oos = new ObjectOutputStream(fos)){

			oos.writeObject(obj);

		} catch (Exception e) {
  			System.out.println("-----------------");
  			System.out.println(fileName + "저장하기 실패");
  			System.out.println("-----------------");
  			return;
  		}
  		System.out.println("-----------------");
			System.out.println("저장되었습니다.");
			System.out.println("-----------------");
	}
	
  	default String fileName(String input) {
    	  
    	  return "src/broadcast_sample/swingGUI/" + input +".txt";
      }
	
}

