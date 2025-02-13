package broadGUI.network;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

public interface ConsoleProgram {


	
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
    	  
    	  return "src/broadGUI/" + input +".txt";
      }
	
  	
	private static void printSchedule(List<TvProgram> comList) {
		Scanner scan = new Scanner(System.in);
		System.out.println("편성표 출력");
		System.out.print("만들 파일의 이름 : ");
		String str = scan.nextLine();


		try(FileWriter fw = new FileWriter("src/broadGUI/" + str +".txt")){

			fw.write("TV 편성표");
			fw.write("\n");
			
			for(int j = 1; j <= 24; j++) {
				fw.write(new DecimalFormat("00").format(j) + " 시 : ");
				String writer = ""; 
				for(int i = 0; i < comList.size(); i++) {
					writer += comList.get(i).printOut(Integer.toString(j));
				}
				fw.write(writer);
				fw.write("\n");
			}
			fw.flush();


		}catch (Exception e) {
			System.out.println("출력 오류");
		}

		System.out.println("src/broadGUI/" + str +".txt" + " : 파일 생성에 성공하였습니다.");

	}
  	
}

