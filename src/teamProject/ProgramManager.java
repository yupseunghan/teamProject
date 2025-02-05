package teamProject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProgramManager {
	List<TvProgram> list = new ArrayList<>();
	Scanner sc = new Scanner(System.in);
   
	public void run() {
      String menu;
      do {
         printMenu();
         menu= sc.nextLine().trim().toUpperCase();
         switch(menu){
         case "SBS","KBS","MBC":
            view(menu);
            break;
         case "EXIT":
            System.out.println("종료합니다");
            break;
         default:
            System.out.println("잘못된 입력");
         }
      }while(!menu.equals("EXIT"));
      
   }
   private void view(String menu) {
	  int choice = -1;
	    do {
	     try {
	         System.out.println("==========="+menu+"채널===========");
	         Programs(menu);
	         System.out.println("============================");
	         System.out.println("1.등록  2.수정  3.삭제  4.돌아가기");
	         System.out.print("메뉴 선택 : ");
	         choice=sc.nextInt();
	         switch(choice) {
	         case 1:
	        	programInsert(menu);
	         	sort();
	            break;
	         case 2:
	            programUpdate(menu);
	            break;
	         case 3:
	            programDelete(menu);
	            break;
	         case 4:
	            sc.nextLine();
	            return;
	         default:
	            System.out.println("정수를 입력해야지");
	         }
     }catch(Exception e) {
    	 System.out.println("올바른 메뉴선택이 아닙니다.");
    	 sc.nextLine();
     }
	 }while(choice != 4);
   }
   
   private void sort() {
     try {
	   list.sort((tp1, tp2) -> {
           // 각 TvProgram 객체 내의 첫 번째 프로그램의 시간을 기준으로 정렬
           return Integer.compare(Integer.parseInt(tp1.getPrograms().get(0).getProgramTime()), 
                                  Integer.parseInt(tp2.getPrograms().get(0).getProgramTime()));
       });
       // 각 TvProgram 내부의 프로그램도 시간순으로 정렬
       for (TvProgram tp : list) {
           tp.getPrograms().sort((p1, p2) -> Integer.compare(Integer.parseInt(p1.getProgramTime()), 
                                                               Integer.parseInt(p2.getProgramTime())));
       }
     }catch(Exception e) {
    	 e.printStackTrace();
     }
   }
   private void programDelete(String menu) {
        boolean res=false;
        if(list.isEmpty()) {
            System.out.println("리스트가 비어있어요..");
            return;
         }
         TvProgram tp=null;
         for(TvProgram tmp:list) {
            if(tmp.getTv().equals(menu)) {
               tp=tmp;
               break;
            }
         }
         if(!list.contains(tp)) {
         	System.out.println(menu+"채널이 비어있습니다");
          	return;
          }
         System.out.print("삭제 할 시간대를 고르시오: ");
         sc.nextLine();
         String time =sc.nextLine();
         res=tp.delete(time);
         if(!res)System.out.println("시간대가 없어요..");
         else System.out.println("삭제 완료!");
   }
   
	private void programUpdate(String menu) {
		boolean res=false;
        if(list.isEmpty()) {
            System.out.println("리스트가 비어있어요..");
            return;
         }
         TvProgram tp=null;
         for(TvProgram tmp:list) {
            if(tmp.getTv().equals(menu)) {
               tp=tmp;
               break;
            }
         }
         if(!list.contains(tp)) {
        	System.out.println(menu+"채널이 비어있습니다");
         	return;
         }
         System.out.print("수정 할 시간대를 고르시오: ");
         sc.nextLine();
         String time =sc.nextLine();
         Program p = newProgram(time);
         res=tp.update(p);
         if(!res)System.out.println("시간대가 없어요..");
         else System.out.println("수정 완료!");
      }
   
  private Program newProgram(String time) {
     System.out.print("이름:");
     String name =sc.nextLine();
     System.out.print("설명:");
     String explan =sc.nextLine();
     return new Program(time, name, explan);
  }
   
  private void programInsert(String menu) {
  		Program p = program();
  		if(p==null)
  			return;
  		TvProgram tp=new TvProgram(Tv.valueOf(menu), p);
  		int index = list.indexOf(tp);
  		if(index < 0) {
  			list.add(tp);
  			return;
  		}
  		list.get(index).insert(p);
     }
      
  private Program program() {
     try {
    	 System.out.print("시간:");
         sc.nextLine();
         String time =sc.nextLine();
         System.out.print("이름:");
         String name =sc.nextLine();
         System.out.print("설명:");
         String explan =sc.nextLine();
         Integer.parseInt(time);
         return new Program(time, name, explan);
     }catch(NumberFormatException e) {
    	 System.out.println("시간대를 숫자로 입력하세요.");
    	 return null;
     }
  }
  
  private void Programs(String menu) {
     for(TvProgram tp:list) {
        tp.showTv(menu);
     }
  }
  
  private void printMenu() {
     System.out.println("===========TV 프로그램 편성표===========");
     System.out.println("SBS              KBS             MBC");
     System.out.println("종료 (EXIT)");
     System.out.print("선택: ");
      }
      
  }