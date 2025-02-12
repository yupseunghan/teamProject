package broadGUI;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;







public class ProgramMannager implements ConsoleProgram{

	
	//private static List<User> userList;				//계정 리스트
	private static List<TvProgram> list;		//전체 편성표
	
	public boolean programUpdate(String company, String programName, String time, String explain) {
	      	try {
	      		boolean res=false;
		        if(list.isEmpty()) {
		            return res;
		         }
		        
		         TvProgram tp=null;
		         for(TvProgram tmp:list) {
		            if(tmp.getTv().equals(company)) {
		               tp=tmp;
		               break;
		            }
		         }
		         
		         if(!list.contains(tp)) {
		            return res;
		         }
		         
		         Program p = new Program(time,programName,explain);
		         System.out.println(p);
		         res=tp.update(p);
		         return res;
	      	}catch(Exception e) {
	      		return true;
	      	}
	         
	      }

	public boolean programInsert(String company, String programName, String time, String explain) {
        try {
        	Program p = new Program(time,programName,explain);
            if(p==null)
               return false;
            TvProgram tp=new TvProgram(Tv.valueOf(company), p);
            int index = list.indexOf(tp);
            if(index < 0) {
               list.add(tp);
               return false;
            }
            list.get(index).insert(p);
            return true;
        }catch(Exception e) {
        	return false;
        }
     }
	
	public void run() {
		
		try {


		//userList = (ArrayList<User>)load(fileName("userList"));				
		list = (ArrayList<TvProgram>)load(fileName("TvList"));				

		//불러오기
		//조회, 수정 가능
		//userList = (userList == null) ? new ArrayList<>() : userList;
		list = (list == null) ? new ArrayList<>() : list;



		//불러오기 실패
		/*
		if(userList.isEmpty() || userList.size()==0) {
			userList.add(new User("admin", true));
			userList.add(new User("smpl", false));
			System.out.println("임의의 유저를 추가했습니다.");
		}
		*/
		/*
		if(list.isEmpty() || list.size()==0) {
			TvProgram kbs = new TvProgram("KBS");
			TvProgram("KBS",new Program("1","kbs제목1","내용1")));
			kbs.getPrograms().add(new Program("3","kbs제목2","내용2"));
			kbs.getPrograms().add(new Program("4","kbs제목3","내용3"));
			list.add(kbs);
			companys.add("KBS");
			
			Company sbs = new Company("SBS");
			sbs.getList().add(new TimeTable(1,30,"sbs제목1","sbs내용1"));
			sbs.getList().add(new TimeTable(5,40,"sbs제목2","sbs내용2"));
			comList.add(sbs);
			companys.add("SBS");
			
			Company mbc = new Company("MBC");
			mbc.getList().add(new TimeTable(1,0,"mbc제목1","mbc내용1"));
			comList.add(mbc);
			companys.add("MBC"); 
			
			System.out.println("빈 시간표를 임의로 작성했습니다.");
		}
		*/

		
		
		//아이디 입력
		
		
		FrameMain main = new FrameMain(list);
		
		
		
		
		//save(fileName("userList"),userList);				
						
		
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("가동 오류 발생");
			 save(fileName("TvList"),list);	
			//save(fileName("userList"),userList);							
			
		}
		
	}

	public boolean programDelete(String company, String time) {
    	
		try {
		if(list.isEmpty()) {
    		System.out.println("리스트가 비어있어요..");
    		return false;
    	}

    	TvProgram tp=null;

    	for(TvProgram tmp:list) {
    		if(tmp.getTv().equals(company)) {
    			tp=tmp;
    			break;
    		}
    	}

    	if(!list.contains(tp)) {
    		System.out.println(company + "채널이 비어있습니다");
    		return false;
    	}

    	return tp.delete(time);
	
	
	}catch (Exception e) {
		e.printStackTrace();
		return false;
	}
	
	}

	
	public String programName(String company, String time) {
    	
		try {
		if(list.isEmpty()) {
    		System.out.println("리스트가 비어있어요..");
    		return "기존 프로그램 없음 ";
    	}

    	TvProgram tp=null;

    	for(TvProgram tmp:list) {
    		if(tmp.getTv().equals(company)) {
    			tp=tmp;
    			break;
    		}
    	}

    	if(!list.contains(tp)) {
    		System.out.println(company + "채널이 비어있습니다 ");
    		return "기존 프로그램 없음 ";
    	}

    	return (tp.getPrograms().contains(new Program(time,"",""))) ? tp.getPrograms().get(tp.getPrograms().indexOf(new Program(time,"",""))).getProgramName() + " " : "기존 프로그램 없음 ";
	
	
	}catch (Exception e) {
		e.printStackTrace();
		return "기존 프로그램 없음 ";
	}
	
	}

	
	 public void sort() {
	     try {
	      list.sort((tp1, tp2) -> {
	           return Integer.compare(Integer.parseInt(tp1.getPrograms().get(0).getProgramTime()), Integer.parseInt(tp2.getPrograms().get(0).getProgramTime()));
	       });
	       for (TvProgram tp : list) {
	           tp.getPrograms().sort((p1, p2) -> Integer.compare(Integer.parseInt(p1.getProgramTime()), Integer.parseInt(p2.getProgramTime())));
	       }
	     }catch(Exception e) {
	        e.printStackTrace();
	     }
	   }
	 
	 
	
}
