package broadcast_sample;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;




public class BroadcastSchedule_Main {
	
	private User userTmp = new User("tmp",false);
	
	private static Scanner scan = new Scanner(System.in);
	private static final String EXIT = "EXIT";
	
	//private static List<Word> wordList = new ArrayList<Word>();
	private static List<User> userList;				//계정 리스트
	private static List<Company> comList;		//전체 편성표
	private static List<String> companys;			//방송사 목록
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {

		userList = (ArrayList<User>)load(fileName("userList"));				
		comList = (ArrayList<Company>)load(fileName("comList"));				
		companys = (ArrayList<String>)load(fileName("companys"));				
		
		
		
		//방송 편성표
		
		//조회, 수정 가능
		userList = (userList == null) ? new ArrayList<>() : userList;
		comList = (comList == null) ? new ArrayList<>() : comList;
		companys = (companys == null) ? new ArrayList<>() : companys;

		/*
		if(userList == null) {
		    userList = new ArrayList<User>();
		}
		if(comList == null) {
		    comList = new ArrayList<Company>();
		}
		if(companys == null) {
		    companys = new ArrayList<String>();
		}*/
		
		
		if(userList.isEmpty() || userList.size()==0) {
			userList.add(new User("admin", true));
			userList.add(new User("smpl", false));
			System.out.println("임의의 유저를 추가했습니다.");
		}
		
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
		
		
		String menu = "";
		do {
			printMenu("로그인", "로그인 없이 편성표 조회", "회원가입", "세이브 후 종료");
			menu = scan.next();
			scan.nextLine();
			if(menu.equals(EXIT))break;
			runMenu(menu);
			
		}while(!menu.equals("4"));
		System.out.println("종료합니다.");

		save(fileName("userList"),userList);				
		save(fileName("comList"),comList);				
		save(fileName("companys"),companys);	
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("가동 오류 발생");
			save(fileName("userList"),userList);				
			save(fileName("comList"),comList);				
			save(fileName("companys"),companys);	
			
		}

	}
	private static void printMenu(String ...menus) {
		System.out.println("-----------------");

		for(int i = 0; i < menus.length; i++) {
			System.out.println((i + 1) + ". " + menus[i]);
		}

		System.out.println("-----------------");
		System.out.print("메뉴 입력 : ");
	}
	
	
	
	private static void runMenu(String menu) {

		switch (menu) {
		case "1" :
			login();
			return;

		case "2" : 
			table();
			return;
			
		case "3" : 
			signUp();
			return;
		case "4" :
			return;

		default :
			System.out.println("잘못된 입력입니다.");
		}
	}
	
	
	
	
	private static void signUp() {
		
		System.out.print("아이디 입력 : ");
		String id = scan.nextLine();
		
		User tmpUser = new User(id);
		
		int index = userList.indexOf(tmpUser);
		
		if(index<0) {
			System.out.println();
			userList.add(tmpUser);
			//index = userList.indexOf(tmpUser);
			System.out.println("회원 가입 성공");
			return;
		}
		
		System.out.println("이미 존재하는 아이디입니다.");
		
		
	}
	private static void table() {
		String menu = "";
		do {
		printMenu("전체 편성표", "방송사별 조회", "이전 메뉴");
		menu = scan.nextLine();
		if(menu.equals(EXIT))break;
		runTableMenu(menu);
		
	}while(!menu.equals("3"));
	}
	
	
	
	private static void runTableMenu(String menu) {
		switch (menu) {
		case "1" :
			dateTable(false);
			return;

		case "2" : 
			companyTable(false);
			return;
			
		case "3" : 
			return;

		default :
			System.out.println("잘못된 입력입니다.");
		}
		
	}
	
	

	private static void companyTable(boolean b) {

		//로그인된 상태면 추가 메뉴 생각중
		System.out.println("방송사별 조회");

		try {
			viewTable(true);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("숫자로 입력해 주세요");
			scan.nextLine();
		}


	}
	/**특정 방송사의 편성표를 열람합니다.
	 * true 혹은 false를 입력받으며 false 입력시 index번호와 함께 출력됩니다.
	 * 
	 * @param numCk
	 * @return 방송사의 list 번지도 받습니다. 없으면 -1
	 */
	private static int viewTable(boolean numCk) {

		for(int i = 0; i < comList.size(); i++) {
			System.out.println((i+1) + ". " + comList.get(i).getCompanyName());
		}
		
		
		System.out.print("열람할 방송사의 번호를 입력(숫자) : ");
		int inputIndex = scan.nextInt() - 1;
		scan.nextLine();

		if(inputIndex<0||inputIndex>=comList.size()) {
			System.out.println("해당하는 방송사가 존재하지 않습니다.");
			return -1;
		}
		if(comList.get(inputIndex).getList().isEmpty()||comList.get(inputIndex).getList()==null) {
			System.out.println("등록된 프로그램이 없습니다.");
			return -1;
		}

		String company = comList.get(inputIndex).getCompanyName();

		System.out.println(company + " 방송통신표");
		System.out.println("--------------------");
		comList.get(inputIndex).printAll(numCk);

		return inputIndex;

	}
	
	
	
	private static void dateTable(boolean b) {

		System.out.println("일괄 조회");

		for(int j = 1; j <= 24; j++) {
			
			System.out.print(new DecimalFormat("00").format(j) + " 시 : ");

			for(int i = 0; i < comList.size(); i++) {

				comList.get(i).printThis(j);

			}

			System.out.println();
			
		}



		
		
		
	}
	
	
	private static void login() {
		System.out.print("아이디 입력 : ");
		String id = scan.nextLine();
		
		User tmpUser = new User(id);
		
		int index = userList.indexOf(tmpUser);
		
		if(index<0) {
			//userList.add(tmpUser);
			System.out.println("존재하지 않는 아이디입니다.");
			//index = userList.indexOf(tmpUser);
			return;
		}

		runMenu(userList.get(index),userList.get(index).isAth());

	}
	private static void runMenu(User user, boolean ath) {

		//ath가 true일 땐 관리자용 수정 메뉴까지 같이 출력되게

		//일단 관리자이든 아니든 관리자메뉴로 만들어둠(나중에 변경)

		String menu = "";
		do {
			printMenu("전체 편성표", "방송사별 조회", "프로그램 추가", "방송사 추가", "프로그램 삭제", "방송사 삭제", "프로그램 수정", "방송사이름 수정", "편성표 출력(텍스트로)", "이전 메뉴");
			menu = scan.nextLine();
			if(menu.equals(EXIT))break;
			runLoginedTableMenu(menu, user);

			for(Company tmp : comList) {
				tmp.sort();
			}


		}while(!menu.equals("10"));


	}


	private static void runLoginedTableMenu(String menu, User user) {




		switch (menu) {
		case "1" :
			dateTable(false);
			return;

		case "2" : 
			companyTable(false);
			return;

		case "3" : 
			addTable();
			return;

		case "4" : 
			addCompany();
			return;

		case "5" : 
			try {
				delTable();
			}catch (Exception e) {
				System.out.println("삭제 실패");
				// TODO: handle exception
			}	
			return;

		case "6" : 
			try {
				delCom();
			}catch (Exception e) {
				System.out.println("삭제 실패");
				// TODO: handle exception
			}	
			return;

		case "7" : 
			try {
				updTable();
			}catch (Exception e) {
				System.out.println("수정 실패");
				// TODO: handle exception
			}	
			return;

		case "8" : 
			try {
				updCom();
			}catch (Exception e) {
				System.out.println("수정 실패");
				// TODO: handle exception
			}	
			return;

		case "9" : 
			printSchedule();
			return;

		case "10" : 

			return;

		default :
			System.out.println("잘못된 입력입니다.");

		}


	}
	private static void printSchedule() {
		System.out.println("편성표 출력");
		System.out.print("만들 파일의 이름 : ");
		String str = scan.nextLine();


		try(FileWriter fw = new FileWriter(fileName(str))){

			fw.write("TV 편성표");
			fw.write("\n");
			
			for(int j = 1; j <= 24; j++) {
				fw.write(new DecimalFormat("00").format(j) + " 시 : ");
				String writer = ""; 
				for(int i = 0; i < comList.size(); i++) {
					writer += comList.get(i).printOut(j);
				}
				fw.write(writer);
				fw.write("\n");
			}
			fw.flush();


		}catch (Exception e) {
			System.out.println("출력 오류");
		}

		System.out.println(fileName(str) + " : 파일 생성에 성공하였습니다.");

	}




private static void updCom() {
	if(companys==null||companys.size()==0||comList==null||comList.size()==0) {
		System.out.println("등록된 방송사가 없습니다.");
		return;
	}

	System.out.println("방송사 이름 수정");
	System.out.println("수정할 방송사를 입력");
	for(int i = 0; i < comList.size(); i++) {
		System.out.println((i+1) + ". " + comList.get(i).getCompanyName());
	}
	System.out.println("--------------------");
	System.out.print("수정할 방송사의 번호 입력 : ");
	int inputNum = scan.nextInt()-1;
	scan.nextLine();
	System.out.println("--------------------");
	try {
			comList.get(inputNum).getCompanyName();
			System.out.print("수정할 이름 입력 : ");
			String name = scan.nextLine();
			if(comList.contains(new Company(name))) {
				System.out.println("이미 존재하는 이름입니다.");
				return;
			}
			comList.get(inputNum).setCompanyName(name);
			System.out.println("--------------------");
			companys.add(inputNum, name);
			System.out.print(comList.get(inputNum).getCompanyName() + " 으로 ");

		}catch (Exception e) {
			System.out.println("수정 실패");
			return;
		}
		System.out.println("수정되었습니다.");


	}


	private static void updTable() {
		System.out.println("프로그램 수정");
		System.out.println("수정할 프로그램 입력");

		int index = 0;
		try { 

			index = viewTable(false);

		}catch (Exception e) {
			System.out.println("해당하는 숫자 입력");
			scan.nextLine();
			return;

		}

		System.out.print("수정할 번호 입력 : ");
		int input = scan.nextInt() - 1;
		scan.nextLine();

		if(input<0||input>=comList.get(index).getList().size()) {
			System.out.println("해당하는 번호가 없습니다.");
			return;
		}

		try {
			System.out.println(comList.get(index).getList().get(input).toString());
			System.out.println();
			int time,min;
			while(true) {
				System.out.print("시작 시각 입력(1~24까지) : ");
				time = scan.nextInt();
				System.out.println("분 입력(0~60까지) : ");
				min = scan.nextInt();
				if(time>0 && time<=24 && min>=0 && min<=60)break;
				System.out.println("형식에 맞게 입력해주세요.");
			}
			System.out.println("시간 입력받았습니다.");
		scan.nextLine();
		System.out.print("프로그램명 입력 : ");
		String name = scan.nextLine();
		System.out.println("주석 입력 : ");
		String type = scan.nextLine();
		
		comList.get(index).getList().add(input, new TimeTable(time, min, name, type));
			System.out.println("수정에 성공했습니다.");
			return;
		
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("수정 실패");
			return;
		}
		
		
		
	}
		
	
	
	private static void delCom() {
		if(companys==null||companys.size()==0||comList==null||comList.size()==0) {
			System.out.println("등록된 방송사가 없습니다.");
			return;
		}
		
		System.out.println("방송사 삭제");
		System.out.println("삭제할 방송사를 입력");
		for(int i = 0; i < comList.size(); i++) {
			System.out.println((i+1) + ". " + comList.get(i).getCompanyName());
		}
		System.out.println("--------------------");
		System.out.print("삭제할 방송사의 번호 입력 : ");
		int inputNum = scan.nextInt()-1;
		scan.nextLine();
		System.out.println("--------------------");
		try {
		comList.remove(inputNum).printAll(true);
		System.out.println("--------------------");
		System.out.println(companys.remove(inputNum));
		}catch (Exception e) {
			System.out.println("삭제 실패");
			return;
		}
		System.out.println("삭제되었습니다.");
		
	}
	private static void delTable() {
		System.out.println("프로그램 삭제");
		System.out.println("삭제할 프로그램 입력");
		
		int index = 0;
		try { 
			
			index = viewTable(false);
			
		}catch (Exception e) {
			System.out.println("해당하는 숫자 입력");
			scan.nextLine();
			return;
			
		}
		if(index<0)return;
		
		System.out.print("삭제할 번호 입력 : ");
		int input = scan.nextInt() - 1;
		scan.nextLine();
		
		if(input<0||input>=comList.get(index).getList().size()) {
			System.out.println("해당하는 번호가 없습니다.");
			return;
		}
		
		try {
		System.out.println(comList.get(index).getList().remove(input).toString());
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("삭제 실패");
			return;
		}
		System.out.println("삭제되었습니다.");
		
		
		
	}
	private static void addTable() {
		if(companys==null||companys.size()==0||comList==null||comList.size()==0) {
			System.out.println("등록된 방송사가 없습니다.");
			return;
		}
		// TODO Auto-generated method stub
		System.out.println("프로그램 추가");
		System.out.println("추가할 프로그램 입력");
		
		System.out.print("방송사를 입력 : ");
		String inputCom = scan.nextLine();
		int index = comList.indexOf(new Company(inputCom));
		if(index<0) {
			System.out.println("해당하는 방송사가 없습니다.");
			return;
		}
		System.out.println(comList.get(comList.indexOf(new Company(inputCom))).getCompanyName() + "에 프로그램을 추가합니다.");

		int time,min;
		while(true) {

			try {
				System.out.print("시작 시각 입력(1~24까지) : ");
				time = scan.nextInt();
				System.out.print("분 입력(0~60까지) : ");
				//min = Integer.parseInt(scan.nextLine());
				min = scan.nextInt();

				if(time < 1 || time > 24 || min < 0 || min >= 60) {
					throw new RuntimeException("시간 또는 분 입력값이 잘못되었습니다.");
				}
			} catch (NumberFormatException e) {
				System.out.println("숫자로 입력해주세요.");
				continue;
			} catch (RuntimeException e) {
				System.out.println(e.getMessage());
				continue;
			}
			scan.nextLine();
			break;
		}
		System.out.println("시간 입력받았습니다.");
		System.out.print("프로그램명 입력 : ");
		String name = scan.nextLine();
		System.out.print("주석 입력 : ");
		String type = scan.nextLine();

		if(comList.get(index).getList().add(new TimeTable(time, min, name, type))) {
			System.out.println("등록에 성공했습니다.");
			return;
		}
		System.out.println("등록 실패");
		return;
		
	}
	private static void addCompany() {
		System.out.println("방송사 추가");
		System.out.print("추가할 방송사를 입력 : ");
		String inputCom = scan.nextLine();
		
		if(comList.indexOf(new Company(inputCom))>0) {
			System.out.println("이미 등록된 방송사입니다.");
			return;
		}
		
		if(comList.add(new Company(inputCom))) {
		companys.add(inputCom);
		System.out.println("등록했습니다.");
		return;
		}
		System.out.println("등록 실패");
		return;
		
	}
	
	 /////////////////////////////////////////////////////////////////////세이브///////////////////////////////////////    
    private static String fileName(String input) {
  	  
  	  return "src/broadcast_sample/" + input +".txt";
    }
    
    

  	private static Object load(String fileName) {		

  		try(FileInputStream fis = new FileInputStream(fileName);
  			ObjectInputStream ois = new ObjectInputStream(fis)){
  		
  			System.out.println("-----------------");
			System.out.println("불러왔습니다.");
			System.out.println("-----------------");
			
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

  	
  	private static void save(String fileName, Object obj) {
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



}
