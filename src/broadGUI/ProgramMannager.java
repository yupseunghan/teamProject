package broadGUI;

import java.util.ArrayList;
import java.util.List;




public class ProgramMannager implements ConsoleProgram{

	
	//private static List<User> userList;				//계정 리스트
	private static List<Company> comList;		//전체 편성표
	private static List<String> companys;			//방송사 목록

	public void run() {
		
		try {


		//userList = (ArrayList<User>)load(fileName("userList"));				
		comList = (ArrayList<Company>)load(fileName("comList"));				
		companys = (ArrayList<String>)load(fileName("companys"));		

		//불러오기
		//조회, 수정 가능
		//userList = (userList == null) ? new ArrayList<>() : userList;
		comList = (comList == null) ? new ArrayList<>() : comList;
		companys = (companys == null) ? new ArrayList<>() : companys;


		//불러오기 실패
		/*
		if(userList.isEmpty() || userList.size()==0) {
			userList.add(new User("admin", true));
			userList.add(new User("smpl", false));
			System.out.println("임의의 유저를 추가했습니다.");
		}
		*/
		
		if(comList.isEmpty() || comList.size()==0) {
			Company kbs = new Company("KBS");
			kbs.getList().add(new TimeTable(1,0,"kbs제목1","내용1"));
			kbs.getList().add(new TimeTable(3,0,"kbs제목2","내용2"));
			kbs.getList().add(new TimeTable(3,30,"kbs제목3","내용3"));
			comList.add(kbs);
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
		

		
		
		//아이디 입력
		List<User> userList = new ArrayList<User>(); 
		
		MainFrame main = new MainFrame(comList,userList, companys);
		
		
		
		
		//save(fileName("userList"),userList);				
		save(fileName("comList"),comList);				
		save(fileName("companys"),companys);
		
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("가동 오류 발생");
			//save(fileName("userList"),userList);				
			save(fileName("comList"),comList);				
			save(fileName("companys"),companys);	
			
		}
		
	}
	

}
