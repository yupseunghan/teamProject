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
      int choice;
      do {
         System.out.println("==========="+menu+"채널===========");
         Programs(menu);//처음 실행시 빈 문자열 출력 여부 해결해야함
         System.out.println("============================");
         System.out.println("1.등록  2.수정  3.삭제  4.돌아가기");
         System.out.print("메뉴 선택 : ");
         choice=sc.nextInt();
         switch(choice) {
         case 1:programInsert(menu);
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
      }while(choice != 4);
   }
   private void programDelete(String menu) {
     
     boolean res=false;
     if(list.isEmpty()) {
         System.out.println("리스트가 비어있어요..");
         return;
      }
      List<TvProgram> old = new ArrayList<TvProgram>();
      for(TvProgram tp:list) {
         if(tp.getTv().equals(menu)) {
            old.add(tp);
         }
      }
      if(old.isEmpty()) {
         System.out.println(menu+"채널이 비어있습니다");
         return;
      }
      System.out.print("삭제 할 시간대를 고르시오: ");
      sc.nextLine();
      String time =sc.nextLine();
      for(TvProgram p:old) {
          res=p.delete(time);
          if(res) {
             System.out.println("삭제 완료!");
             break;
          }   
       }
       if(!res)System.out.println("시간대가 없어요..");
      
}
   
private void programUpdate(String menu) {
      boolean res=false;
      if(list.isEmpty()) {
         System.out.println("리스트가 비어있어요..");
         return;
      }
      List<TvProgram> old = new ArrayList<TvProgram>();
      for(TvProgram tp:list) {
         if(tp.getTv().equals(menu)) {
            old.add(tp);
         }
      }
      if(old.isEmpty()) {
         System.out.println(menu+"채널이 비어있습니다");
         return;
      }
      System.out.print("수정 할 시간대를 고르시오: ");
      sc.nextLine();
      String time =sc.nextLine();
      Program newP=newProgram(time);
      for(TvProgram p:old) {
         res=p.update(newP);
         if(res) {
            System.out.println("수정 완료!");
            break;
         }   
      }
      if(!res)System.out.println("시간대가 없어요..");
      
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
      TvProgram tp=new TvProgram(Tv.valueOf(menu),p);
      list.add(tp);
   }
   
   private Program program() {
      System.out.print("시간:");
      sc.nextLine();
      String time =sc.nextLine();
      System.out.print("이름:");
      String name =sc.nextLine();
      System.out.print("설명:");
      String explan =sc.nextLine();
      return new Program(time, name, explan);
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
