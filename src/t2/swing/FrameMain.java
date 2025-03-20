package t2.swing;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import lombok.Data;
import t2.model.vo.BroadTime;
import t2.model.vo.ChannelPro;
import t2.model.vo.User;
import t2.model.vo.View;
import t2.model.vo.index;
import t2.service.BroadTimeManager;
import t2.service.ChannelManager;
import t2.service.ChannelProManager;
import t2.service.GenreManager;
import t2.service.ProgramAgeManager;
import t2.service.ProgramGenreManager;
import t2.service.ProgramManager;
import t2.service.UserManager;
import t2.service.ViewManager;
import t2.service.indexManager;

@Data
public class FrameMain extends JFrame {
   private static final long serialVersionUID = 1L;

   private static JPanel mainPanel;
   private JButton loginButton, signupButton, exitButton;
   private static CardLayout cardLayout;
   private DefaultListModel<String> scheduleListModel;
   private JList<String> scheduleList;
   private JComboBox<String> tvBox = new JComboBox<String>();
   private JComboBox<String> dayBox = new JComboBox<String>();
   private HashMap<String, List<String>> tvProgramMap; // 일단은 이렇게 했는데 나중에는 문자열 리스트로 가져올것
   private PanelLoginMenu loginRES;
   public PanelSchedule PSRES;
   private static JTextField idText, pwText, pw2Text, nameText;
   private BroadTimeManager broadTimeManager = new BroadTimeManager();
   private ProgramGenreManager programGenreManager = new ProgramGenreManager();
   private ChannelManager channelManager = new ChannelManager();
   private ChannelProManager channelProManager = new ChannelProManager();
   private UserManager userManager = new UserManager();
   private ProgramManager programManager = new ProgramManager();
   private indexManager indexManager = new indexManager();
   private ProgramAgeManager programAgeManager = new ProgramAgeManager();
   private GenreManager genreManager = new GenreManager();
   private ViewManager viewManager = new ViewManager();
   private String[] pros = {""};
   private String[] prNames= {""};
   private String[] grNames= {""};
   private String[] tvNames= {""};
   private String[] tvs = {" "};
   private String[] pross = { " " };
   private JComboBox<String> prsBox = new JComboBox<>(pross);
   private JComboBox<String> tvsBox = new JComboBox<>(tvs);
   private int userKey;
   private  List<index> bookMarkList;
   private List<BroadTime> programs; 
   private  DefaultListModel<String> listModel = new DefaultListModel<String>();
   private String[] bmNames= {""};
   private  JComboBox<String> bmBox = new JComboBox<String>();
   private JComboBox<String> prBox = new JComboBox<>(pros);
   private JComboBox<String> prBox2 = new JComboBox<>(prNames);
   private JComboBox<String> grBox = new JComboBox<>(grNames);
   private List<String> bmListStr = new ArrayList<String>();
   public FrameMain() {
      setTitle("방송 편성표 시스템");
      setSize(400, 500);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLayout(new BorderLayout());
      
      cardLayout = new CardLayout();
      mainPanel = new JPanel(cardLayout);
      add(mainPanel);
    
      // 초기값
      // 여기서 방송사 전체 개수 가져와서 넣어야
      tvNames = channelManager.getChannelList();

      // 요일 선택
      String[] days = { "일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일" };
      dayBox = new JComboBox<>(days);
      dayBox.addActionListener(e -> display());
      // 방송사 선택
      tvBox = new JComboBox<>(tvNames);
      tvBox.addActionListener(e -> {
    	  display();
    	  });

      // 방송편성표 리스트
      scheduleListModel = new DefaultListModel<>();
      scheduleList = new JList<>(scheduleListModel);
      JScrollPane scrollPane = new JScrollPane(scheduleList);

      // 패널들
      JPanel choicePanel = new JPanel(new GridLayout(1, 2));
      choicePanel.add(tvBox);
      choicePanel.add(dayBox);

      JPanel printPanel = new JPanel(new BorderLayout());
      printPanel.add(scrollPane, BorderLayout.CENTER);

      JPanel logInPanel = new JPanel();
      loginButton = new JButton("로그인");
      signupButton = new JButton("회원가입");
      exitButton = new JButton("종료");

      logInPanel.add(loginButton);
      logInPanel.add(signupButton);
      logInPanel.add(exitButton);

      // 추가
      JPanel homePanel = new JPanel(new BorderLayout());
      homePanel.add(choicePanel, BorderLayout.NORTH);
      homePanel.add(printPanel, BorderLayout.CENTER);
      homePanel.add(logInPanel, BorderLayout.SOUTH);

      loginButton.addActionListener(e -> { logIn();});
      signupButton.addActionListener(e -> switchPanel("signup"));
      exitButton.addActionListener(e -> exitProgram());

      mainPanel.add(homePanel, "home");
      mainPanel.add(panelAdmin(), "admin");
      mainPanel.add(panelUser(), "user");
      mainPanel.add(CreatesignuptPanel(),"signup");

      
      display();
      setVisible(true);
   }

   private void logIn() {
      if (loginRES != null) {
         loginRES.dispose();
         loginRES = null;
      }
      loginRES = new PanelLoginMenu(this);
   }

   private void exitProgram() {
      int result = JOptionPane.showConfirmDialog(this, "정말 종료하시겠습니까?", "종료 확인", JOptionPane.YES_NO_OPTION);
      if (result == JOptionPane.YES_OPTION) {
         System.exit(0);
      }
   }
   private  void logOut(JPanel panel) {
      int result = JOptionPane.showConfirmDialog(mainPanel, "로그아웃 하시겠습니까?", "종료 확인", JOptionPane.YES_NO_OPTION);
      if (result == JOptionPane.YES_OPTION) {
         JOptionPane.showMessageDialog(panel, "로그아웃되었습니다.");
         tvNames = channelManager.getChannelList();
         tvBox.removeAllItems();
         for(String i : tvNames) {
        	 tvBox.addItem(i);
         }
         
         display();
         
         FrameMain.switchPanel("home");
      }         
      
   }
   private void display() {
	   
	   int tvIndex = tvBox.getSelectedIndex();
       int dayIndex = dayBox.getSelectedIndex();
       if (tvIndex < 0 || dayIndex < 0) return;
       String channel = tvBox.getItemAt(tvIndex);
       String week = dayBox.getItemAt(dayIndex);
       programs = broadTimeManager.getBroadTimeList(channel,week);
       // 요일별 데이터 추가 필요
       
       reflash(scheduleListModel);
       scheduleListModel.addElement("방송사: " + channel + " (" + week + ")");
       scheduleListModel.addElement("================================");
       
       printList(programs);
   }

   private void printList(List<BroadTime> programs) {
	   if (programs.isEmpty()) {
           scheduleListModel.addElement("등록된 프로그램이 없습니다.");
           return;
       } 
       for(BroadTime bt : programs) {
       	scheduleListModel.addElement(bt.toString());
       }
       return;
}

public static void switchPanel(String panelName) {
      cardLayout.show(mainPanel, panelName);
   }

   private  void addComponent(JPanel panel, Component comp, int x, int y, GridBagConstraints gbc) {
      gbc.gridx = x;
      gbc.gridy = y;
      panel.add(comp, gbc);
   }

   private  JPanel panelAdmin() {
      JPanel adminPanel = new JPanel(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.BOTH;
      gbc.weightx = 1.0;
      gbc.weighty = 1.0;

      JButton scheduleButton = new JButton("편성표 관리");
      JButton userButton = new JButton("사용자 관리");
      JButton logoutButton = new JButton("로그아웃");

      addComponent(adminPanel, scheduleButton, 0, 0, gbc);
      addComponent(adminPanel, userButton, 0, 1, gbc);
      addComponent(adminPanel, logoutButton, 0, 2, gbc);
      
      mainPanel.add(panelAdminTVBoard(), "adminTVboard");
      mainPanel.add(panelAdminUSERBoard(), "adminUSERboard");
      
      scheduleButton.addActionListener(e -> {
         JOptionPane.showMessageDialog(adminPanel, "편성표 관리");
         switchPanel("adminTVboard");
      });
      userButton.addActionListener(e -> {
         JOptionPane.showMessageDialog(adminPanel, "사용자 관리");
         switchPanel("adminUSERboard");
      });
      logoutButton.addActionListener(e -> {
    	  
         logOut(mainPanel);
      });

      return adminPanel;

   }
   private  JPanel panelAdminUSERBoard() {
	      JPanel adminUSERBoardPanel = new JPanel(new GridBagLayout());
	      GridBagConstraints gbc = new GridBagConstraints();
	      gbc.fill = GridBagConstraints.BOTH;
	      gbc.weightx = 1.0;
	      gbc.weighty = 1.0;

	      JButton USUPButton = new JButton("유저 권한 변경");   // 권한이 사용자인 유저들만 띄워서 권한을 관리자로 변경하는 버튼
	      JButton USVWButton = new JButton("사용자 조회");   // 모든 id들을 띄우는 버튼
	      JButton backButton = new JButton("처음으로");
	      
	      
	      addComponent(adminUSERBoardPanel, USUPButton, 0, 0, gbc);
	      addComponent(adminUSERBoardPanel, USVWButton, 0, 1, gbc);
	      addComponent(adminUSERBoardPanel, backButton, 0, 2, gbc);
	      
	      //mainPanel.add(panelAdminTVBoard(), "adminTVboard");
	      //mainPanel.add(panelAdminUSERBoard(), "adminUSERboard");
	      
	      USUPButton.addActionListener(e -> {
	         JOptionPane.showMessageDialog(adminUSERBoardPanel, "유저 권한 변경");
	         //switchPanel("adminTVboard");
	         
	      });
	      USVWButton.addActionListener(e -> {
	         JOptionPane.showMessageDialog(adminUSERBoardPanel, "사용자 조회");
	         //switchPanel("adminUSERboard");
	         
	      });
	      backButton.addActionListener(e -> {
	         switchPanel("admin");
	      });

	      return adminUSERBoardPanel;
	   }
   private JPanel panelAdminTVBoard() {
	      JPanel adminTVBoardPanel = new JPanel(new GridBagLayout());
	      GridBagConstraints gbc = new GridBagConstraints();
	      gbc.fill = GridBagConstraints.BOTH;
	      gbc.weightx = 1.0;
	      gbc.weighty = 1.0;

	      JButton PRMGButton = new JButton("프로그램 관리");
	      JButton TVMGButton = new JButton("방송국 관리");
	      JButton GRMGButton = new JButton("장르 관리");
	      JButton BBMGButton = new JButton("편성표 등록");
	      JButton backButton = new JButton("처음으로");
	      

	      addComponent(adminTVBoardPanel, PRMGButton, 0, 0, gbc);
	      addComponent(adminTVBoardPanel, TVMGButton, 0, 1, gbc);
	      addComponent(adminTVBoardPanel, GRMGButton, 0, 2, gbc);
	      addComponent(adminTVBoardPanel, BBMGButton, 0, 3, gbc);
	      addComponent(adminTVBoardPanel, backButton, 0, 4, gbc);
	      
	      mainPanel.add(panelAdminPRMG(), "PRMG");
	      mainPanel.add(panelAdminTVMG(), "TVMG");
	      mainPanel.add(panelAdminGRMG(), "GRMG");
	      mainPanel.add(panelAdminBBMG(), "BBMG");
	      
	      PRMGButton.addActionListener(e -> {
	         JOptionPane.showMessageDialog(adminTVBoardPanel, "프로그램 추가 수정 삭제");
	         switchPanel("PRMG");         
	      });
	      TVMGButton.addActionListener(e -> {
	         JOptionPane.showMessageDialog(adminTVBoardPanel, "방송국 추가 수정 삭제");
	         switchPanel("TVMG");         
	      });
	      GRMGButton.addActionListener(e -> {
	         JOptionPane.showMessageDialog(adminTVBoardPanel, "장르 추가 수정 삭제");
	         switchPanel("GRMG");         
	      });
	      BBMGButton.addActionListener(e -> {
	    	  tvs = channelManager.getChannelList();
	          tvsBox.removeAllItems();
	          for(String i : tvs) {
	         	 tvsBox.addItem(i);
	          }
	          setProgramList();
	         JOptionPane.showMessageDialog(adminTVBoardPanel, "프로그램과 방송사 선택 후 요일과 시간 입력");
	         // 만약 select로 이미 있는 프로그램,방송사,요일,시간이라면 알림 후 등록x
	         switchPanel("BBMG");
	         
	      });
	      backButton.addActionListener(e -> {
	         JOptionPane.showMessageDialog(adminTVBoardPanel, "처음으로");
	         switchPanel("admin");
	      });

	      return adminTVBoardPanel;
	   }
   private  JPanel panelAdminBBMG() {
	      JPanel BBMG = new JPanel(new GridBagLayout());
	      GridBagConstraints gbc = new GridBagConstraints();
	      gbc.fill = GridBagConstraints.BOTH;
	      gbc.weightx = 1.0;
	      gbc.weighty = 1.0;

	      // 등록된 프로그램을 모두 가져옴
	      
	      prsBox.addActionListener(e -> {
	    	  
	      });

	      // 등록된 방송사를 모두 가져옴
	      
	      tvsBox.addActionListener(e -> {
	    	  
	      });

	      String[] days = { "일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일" };
	      JComboBox<String> dayBox = new JComboBox<>(days);
	      dayBox.addActionListener(e -> {
	      });

	      String[] hours = new String[26];
	      hours[0] = "시작 시간";
	      for (int i = 1; i <= 24; i++) {
	         hours[i] = String.format("%02d", i - 1);
	      }

	      // 0~59분 배열 생성 (0번째는 "시작 분" 추가)
	      String[] minutes = new String[61];
	      minutes[0] = "시작 분";
	      for (int i = 1; i < 60; i++) {
	         minutes[i] = String.format("%02d", i - 1);
	      }

	      // JComboBox 생성 및 0번째 옵션 추가
	      JComboBox<String> sTimeBox = new JComboBox<>(hours);
	      sTimeBox.addActionListener(e -> {
	      });

	      JComboBox<String> sMinBox = new JComboBox<>(minutes);
	      sMinBox.addActionListener(e -> {
	      });

	      // 0번째 옵션을 "끝 시간", "끝 분"으로 변경한 새로운 배열 생성
	      String[] hoursWithEnd = hours.clone();
	      hoursWithEnd[0] = "끝 시간";

	      String[] minutesWithEnd = minutes.clone();
	      minutesWithEnd[0] = "끝 분";

	      JComboBox<String> fTimeBox = new JComboBox<>(hoursWithEnd);
	      fTimeBox.addActionListener(e -> {
	      });

	      JComboBox<String> fMinBox = new JComboBox<>(minutesWithEnd);
	      fMinBox.addActionListener(e -> {
	      });

	      // 방영상태 박스. 이건 이대로 ok
	      String[] states = { "방영상태를 선택해주세요.", "생방송", "재방송", "휴방" };
	      JComboBox<String> stateBox = new JComboBox<>(states);
	      stateBox.addActionListener(e -> {
	      });
	      
	      JButton showSchedulePanel = new JButton("편성표 조회");

	      JButton tvProAddBtn = new JButton("프로그램 등록");
	      JButton tvProUpdBtn = new JButton("프로그램 수정");
	      JButton tvProDelBtn = new JButton("프로그램 삭제");

	      JButton backButton = new JButton("뒤로가기");
	      JTextField textField = new JTextField();
	      textField.setColumns(20);
	      addComponent(BBMG, textField, 0, 1, gbc);
	      addComponent(BBMG, showSchedulePanel, 0, 0, gbc);
	      addComponent(BBMG, prsBox, 0, 2, gbc);
	      addComponent(BBMG, tvsBox, 0, 3, gbc);
	      addComponent(BBMG, dayBox, 0, 4, gbc);
	      addComponent(BBMG, sTimeBox, 0, 5, gbc);
	      addComponent(BBMG, sMinBox, 0, 6, gbc);
	      addComponent(BBMG, fTimeBox, 0, 7, gbc);
	      addComponent(BBMG, fMinBox, 0, 8, gbc);
	      addComponent(BBMG, stateBox, 0, 9, gbc);
	      addComponent(BBMG, tvProAddBtn, 0, 10, gbc);
	      addComponent(BBMG, tvProUpdBtn, 0, 11, gbc);
	      addComponent(BBMG, tvProDelBtn, 0, 12, gbc);
	      addComponent(BBMG, backButton, 0, 13, gbc);
	      textField.addFocusListener(new FocusListener() {
	    	    @Override
	    	    public void focusGained(FocusEvent e) {
	    	        // 포커스를 받으면 텍스트를 지운다.
	    	    	if (textField.getText().equals("날짜를 입력하세요")) {
	    	            textField.setText("");
	    	        }
	    	    }

	    	    @Override
	    	    public void focusLost(FocusEvent e) {
	    	        // 포커스를 잃으면 텍스트를 원래대로 돌린다.
	    	        if (textField.getText().isEmpty()) {
	    	            textField.setText("날짜를 입력하세요");
	    	        }
	    	    }
	    	});
	      showSchedulePanel.addActionListener(e -> {
	         if (PSRES != null) {
	            PSRES.dispose();
	            PSRES = null;
	         }
	         PSRES = new PanelSchedule();
	      });
	      /////////////////////////////////////// 추가
	      /////////////////////////////////////// 버튼///////////////////////////////////////////

	      // int PRIndex, TVIndex, STIndex, SMIndex, FTIndex, FMIndex;

	      tvProAddBtn.addActionListener(e -> {
	         // JOptionPane.showMessageDialog(BBMG, "프로그램 등록");
	    	 int PRIndex = prsBox.getSelectedIndex();
		     int TVIndex = tvsBox.getSelectedIndex();
		     int DayIndex = dayBox.getSelectedIndex();
	         int STIndex = sTimeBox.getSelectedIndex();
	         int SMIndex = sMinBox.getSelectedIndex();
	         int FTIndex = fTimeBox.getSelectedIndex();
	         int FMIndex = fMinBox.getSelectedIndex();
	         int stateIndex = stateBox.getSelectedIndex();
	         if (PRIndex < 0 || TVIndex < 0 || STIndex < 0 || FTIndex < 0 ||stateIndex <= 0) {
	            JOptionPane.showMessageDialog(BBMG, "선택해주세요.");
	            return;
	         }
	         if (STIndex == 0 || FTIndex == 0) {
	            JOptionPane.showMessageDialog(BBMG,
	                  ((STIndex == 0) ? "시작시간 " : "") + ((FTIndex == 0) ? "끝시간 " : "") + "선택해주세요");
	            return;
	         }

	         
	         String selectedPR = prBox.getItemAt(PRIndex);
	         String selectedTV = tvBox.getItemAt(TVIndex);
	         String selectedDay = dayBox.getItemAt(DayIndex);
	         String selectedST = sTimeBox.getItemAt(STIndex);
	         String selectedSM = sMinBox.getItemAt(SMIndex);
	         String selectedFT = fTimeBox.getItemAt(FTIndex);
	         String selectedFM = fMinBox.getItemAt(FMIndex);
	         
	         String selectedState = null;
	         switch(stateIndex) {
	            case 1 : selectedState = "생방송"; break;
	            case 2 : selectedState = "재방송"; break;
	            case 3 : selectedState = "휴방"; break;
	            default : break;
	            };

	         if (SMIndex <= 0 || FMIndex <= 0) {
	            selectedSM = "0";
	            selectedFM = "0";
	         }
	         ChannelPro cp = channelProManager.selectCp(selectedPR,selectedTV);
	         String day =textField.getText();
    		 String startTime= selectedST+":"+selectedSM+":00";
    		 String endTime = selectedFT+":"+selectedFM+":00";
	         if( cp != null) {
	        	 //채널프로에서 이미 있어
	        	 System.out.println("채널프로에 있대");
	        	 View view =viewManager.select(cp.getCp_num(),selectedDay,selectedState);
	        	 if(view != null) {
	        		 //뷰에도 있데
	        		 System.out.println("뷰에도 있대");
	        		 ;
	        		 BroadTime bt = broadTimeManager.select(view.getVw_key(),day,startTime);
	        		 if(bt != null) {
	        			 //이미 편성표에 있데
	        			 JOptionPane.showMessageDialog(mainPanel, "이미 편성표에 등록 되어 있습니다");
	        			 return;
	        		 }//편성표에만 없대 등록해
	        		 broadTimeManager.insertBt(view.getVw_key(),day,startTime,endTime );
	        		 JOptionPane.showMessageDialog(mainPanel, "편성표에 등록 되었습니다");
	        		 return;
	        	 }//뷰에 없대 뷰 부터 다 등록해
	        	 View vieW = viewManager.insert(cp.getCp_num(),selectedState,selectedDay);
		         broadTimeManager.insertBt(vieW.getVw_key(),day,startTime,endTime );
		         JOptionPane.showMessageDialog(mainPanel, "편성표에 등록 되었습니다");
	        	 return;
	         }//채널프로에 없대 다 등록해
	         ChannelPro cP =channelProManager.insertCp(selectedPR,selectedTV);
	         View vieW = viewManager.insert(cP.getCp_num(),selectedState,selectedDay);
	         broadTimeManager.insertBt(vieW.getVw_key(),day,startTime,endTime );
	         JOptionPane.showMessageDialog(mainPanel, "편성표에 등록 되었습니다");

	      });

	      /////////////////////////////////////// 수정
	      /////////////////////////////////////// 버튼///////////////////////////////////////////

	      final int[] res = { 0 };

	      tvProUpdBtn.addActionListener(e -> {

	         int PRIndex = prsBox.getSelectedIndex();
	         int TVIndex = tvsBox.getSelectedIndex();
	         int STIndex = sTimeBox.getSelectedIndex();
	         int SMIndex = sMinBox.getSelectedIndex();
	         int FTIndex = fTimeBox.getSelectedIndex();
	         int FMIndex = fMinBox.getSelectedIndex();
	         int stateIndex = stateBox.getSelectedIndex();

	         if (STIndex <= 0 || FTIndex <= 0) {
	            JOptionPane.showMessageDialog(BBMG,
	                  ((STIndex == 0) ? "시작시간 " : "") + ((FTIndex == 0) ? "끝시간 " : "") + "선택해주세요");
	            return;
	         }
	         
	         String selectedState = null;
	         switch(stateIndex) {
	         case 1 : selectedState = "생방송"; break;
	         case 2 : selectedState = "재방송"; break;
	         case 3 : selectedState = "휴방"; break;
	         default : break;
	         };

	         String selectedPR = prBox.getItemAt(PRIndex);
	         String selectedTV = tvBox.getItemAt(TVIndex);
	         String selectedST = sTimeBox.getItemAt(STIndex);
	         String selectedSM = sMinBox.getItemAt(SMIndex);
	         String selectedFT = fTimeBox.getItemAt(FTIndex);
	         String selectedFM = fMinBox.getItemAt(FMIndex);

	         if (SMIndex <= 0 || FMIndex <= 0) {
	            selectedSM = "0";
	            selectedFM = "0";
	         }

	         // 수정버튼 처음 눌렀을 때
	         if (res[0] == 0) {

	            // selectedPR, selectedTV, selectedST 을 이용해서 해당 방송에 해당 시간이 있는지 찾음. 없으면 안내문구 후
	            // return;

	            // 있으면
	            if (true) {
	               JOptionPane.showMessageDialog(BBMG, selectedPR + "수정할 내용을 입력해주세요.");

	               res[0] = 1;
	               System.out.println(selectedPR+selectedTV+selectedST+selectedSM+selectedFT+selectedFM+selectedState);
	               return;
	            }
	            // 없으면
	            /*
	             * if(true) { JOptionPane.showMessageDialog(BBMG, selectedPR + "은 존재하지 않습니다.");
	             * 
	             * return; }
	             */
	         }

	         // 수정버튼 두번째로 눌렀을 때
	         // selectedPR, selectedTV, selectedST, selectedSM, selectedFT, selectedFM 이용해서
	         // 편성표 수정

	         // 보내는데 성공하면?
	         if (true) {
	            
	            if(stateIndex==0) {/*방영상태가 선택 안됐으니 방송명, 방송사명, 시간만 update*/}
	            else {/*방송명, 방송사명, 시간, 방영상태 update*/}
	            
	            JOptionPane.showMessageDialog(BBMG, selectedPR + "등록.");
	            res[0] = 0;
	            System.out.println(selectedPR+selectedTV+selectedST+selectedSM+selectedFT+selectedFM+selectedState);
	            return;
	         }
	         // 보내는데 실패하면?
	         /*
	          * if(true) { JOptionPane.showMessageDialog(BBMG, selectedPR + "등록 실패."); res[0]
	          * = 0; return; }
	          */
	         
	         
	      });

	      /////////////////////////////////////// 삭제
	      /////////////////////////////////////// 버튼///////////////////////////////////////////

	      tvProDelBtn.addActionListener(e -> {
	         // JOptionPane.showMessageDialog(BBMG, "프로그램 등록");
	    	  
		         
	         int PRIndex = prsBox.getSelectedIndex();
	         int TVIndex = tvsBox.getSelectedIndex();
	         int DayIndex = dayBox.getSelectedIndex();
	         int STIndex = sTimeBox.getSelectedIndex();
	         int SMIndex = sMinBox.getSelectedIndex();
	         int FTIndex = fTimeBox.getSelectedIndex();
	         int FMIndex = fMinBox.getSelectedIndex();
	         int stateIndex = stateBox.getSelectedIndex();

	         if (PRIndex < 0 || TVIndex < 0 || STIndex < 0 || FTIndex < 0||stateIndex <= 0) {
	            JOptionPane.showMessageDialog(BBMG, "선택해주세요.");
	            return;
	         }
	         if (STIndex == 0 || FTIndex == 0) {
	            JOptionPane.showMessageDialog(BBMG,
	                  ((STIndex == 0) ? "시작시간 " : "") + ((FTIndex == 0) ? "끝시간 " : "") + "선택해주세요");
	            return;
	         }

	         String selectedPR = prBox.getItemAt(PRIndex);
	         String selectedTV = tvBox.getItemAt(TVIndex);
	         String selectedDay = dayBox.getItemAt(DayIndex);
	         String selectedST = sTimeBox.getItemAt(STIndex);
	         String selectedSM = sMinBox.getItemAt(SMIndex);
	         String selectedFT = fTimeBox.getItemAt(FTIndex);
	         String selectedFM = fMinBox.getItemAt(FMIndex);
	         String selectedState = null;
	         switch(stateIndex) {
	            case 1 : selectedState = "생방송"; break;
	            case 2 : selectedState = "재방송"; break;
	            case 3 : selectedState = "휴방"; break;
	            default : break;
            };
	         if (SMIndex <= 0 || FMIndex <= 0) {
	            selectedSM = "0";
	            selectedFM = "0";
	         }
	       //채널객채 찾아서 키로 뷰 찾고 뷰객채에 뷰키로 브로드객체 찾아서 null이 아니면!!!! 삭제
	         ChannelPro cp = channelProManager.selectCp(selectedPR,selectedTV);
	         String day =textField.getText();
    		 String startTime= selectedST+":"+selectedSM+":00";
    		 String endTime = selectedFT+":"+selectedFM+":00";
	         if( cp != null) {
	        	 //채널프로에서 이미 있어
	        	 System.out.println("채널프로에 있대");
	        	 View view =viewManager.select(cp.getCp_num(),selectedDay,selectedState);
	        	 if(view != null) {
	        		 //뷰에도 있데
	        		 System.out.println("뷰에도 있대");
	        		 ;
	        		 BroadTime bt = broadTimeManager.select(view.getVw_key(),day,startTime);
	        		 if(bt != null) {
	        			 //딜리트
	        			 broadTimeManager.delete(bt.getBt_num());
	        			 JOptionPane.showMessageDialog(BBMG, selectedPR + "삭제.");
	        			 return;
	        		 }//편성표에만 없대 등록해
	        		 JOptionPane.showMessageDialog(mainPanel, "편성표에 등록 되어 있지 않습니다");
	        		 return;
	        	 }//뷰에 없대 뷰 부터 다 등록해
	        	 JOptionPane.showMessageDialog(mainPanel, "편성표에 등록 되어 있지 않습니다");
        		 return;
	         }
	         JOptionPane.showMessageDialog(mainPanel, "편성표에 등록 되어 있지 않습니다");
    		 return; 
	      });

	      backButton.addActionListener(e -> {
	         switchPanel("adminTVboard");

	      });

	      return BBMG;
	   }

	   private  JPanel panelAdminGRMG() {
		   JPanel GRMG = new JPanel(new GridBagLayout());
		      GridBagConstraints gbc = new GridBagConstraints();
		      gbc.fill = GridBagConstraints.BOTH;
		      gbc.weightx = 1.0;
		      gbc.weighty = 1.0;

		      JTextField grName = new JTextField();
		      JTextField grName2 = new JTextField();

		      JButton btnUpdate = new JButton("장르 등록/수정");
		      JButton btnDelete = new JButton("장르 삭제");
		      JButton backButton = new JButton("뒤로가기");

		      btnUpdate.addActionListener(e -> GRADDUP(grName, grName2)); // 여기서 만든 id를 db로 보냅니다.
		      btnDelete.addActionListener(e -> GRDEL(grName));
		      backButton.addActionListener(e -> switchPanel("adminTVboard"));

		      addComponent(GRMG, new JLabel("장르명"), 0, 0, gbc);
		      addComponent(GRMG, grName, 1, 0, gbc);
		      addComponent(GRMG, new JLabel("장르 수정시"), 0, 1, gbc);
		      addComponent(GRMG, grName2, 1, 1, gbc);

		      gbc.gridx = 0;
		      gbc.gridy = 2;
		      gbc.gridwidth = 1;
		      GRMG.add(btnUpdate, gbc);
		      gbc.gridx = 1;
		      GRMG.add(btnDelete, gbc);
		      gbc.gridx = 0;
		      gbc.gridy = 3;
		      gbc.gridwidth = 2;
		      GRMG.add(backButton, gbc);

		      return GRMG;
	   }
	   private  void GRDEL(JTextField grName) {
		      String grNameText = grName.getText().trim();
		      int result = JOptionPane.showConfirmDialog(mainPanel, String.format("%s 삭제 하시겠습니까?", grNameText), "확인",
		            JOptionPane.YES_NO_OPTION);
		      if (result == JOptionPane.NO_OPTION) {
		         return;
		      }
		      // grNameText 인 프로그램 삭제 ->
		      // 결과 받아올수 있으면 int result = JOptionPane.showConfirmDialog(mainPanel,
		      // String.format("%s 삭제되었습니다.", grNameText), "확인", JOptionPane.YES_NO_OPTION);
		      if(!genreManager.checkGenre(grNameText)) {
		        	 JOptionPane.showMessageDialog(mainPanel, "등록된 장르가 아닙니다");
		        	 return;
		         }
		      genreManager.delGenre(grNameText);
		      JOptionPane.showMessageDialog(mainPanel, "장르 삭제 완료");
	        	 return;
		   }
	   
	   private  void GRADDUP(JTextField grName, JTextField grName2) {

		      String grNameText = grName.getText().trim();
		      String grName2Text = grName2.getText().trim();

		      if (grNameText.isBlank()) {
		         JOptionPane.showMessageDialog(mainPanel, "장르명은 입력해주세요.");
		         return;
		      }

		      int result = JOptionPane.showConfirmDialog(mainPanel, "새로운 장르명 등록 하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
		      
		      if (result == JOptionPane.NO_OPTION) {
		         int result2 = JOptionPane.showConfirmDialog(mainPanel, "기존 장르명 수정 하시겠습니까?", "확인",
		               JOptionPane.YES_NO_OPTION);
		         if (result2 == JOptionPane.NO_OPTION)
		            return;
		         if (grName2Text.isBlank()) {
		            JOptionPane.showMessageDialog(mainPanel, "수정할 장르명이 공란일 경우 수정이 불가합니다.");
		            return;
		         }
		         // grNameText 장르 이름을 grName2Text로 변경
		         if(!genreManager.checkGenre(grNameText)) {
		        	 JOptionPane.showMessageDialog(mainPanel, "등록된 장르가 아닙니다");
		        	 return;
		         }
		         if(!genreManager.updateGnere(grNameText,grName2Text)) {
		        	 JOptionPane.showMessageDialog(mainPanel, "수정할려는 장르가 이미 존재합니다");
		        	 return;
		         }
		         JOptionPane.showMessageDialog(mainPanel, "수정 완료");
		         return;
		      }

		      // grNameText 장르 등록
		      if(!genreManager.insertGenre(grNameText)) {
		    	  JOptionPane.showMessageDialog(mainPanel, "이미 등록된 장르");
		    	  return;
		      }
		      JOptionPane.showMessageDialog(mainPanel, "등록 완료");
		      return;
		   }
	   
	   private JPanel panelAdminTVMG() {
		   JPanel TVMG = new JPanel(new GridBagLayout());
		      GridBagConstraints gbc = new GridBagConstraints();
		      gbc.fill = GridBagConstraints.BOTH;
		      gbc.weightx = 1.0;
		      gbc.weighty = 1.0;

		      JTextField tVName = new JTextField();
		      JTextField tVName2 = new JTextField();

		      JButton btnUpdate = new JButton("방송사 등록/수정");
		      JButton btnDelete = new JButton("방송사 삭제");
		      JButton backButton = new JButton("뒤로가기");

		      btnUpdate.addActionListener(e -> TVADDUP(tVName, tVName2)); // 여기서 만든 id를 db로 보냅니다.
		      btnDelete.addActionListener(e -> TVDEL(tVName));
		      backButton.addActionListener(e -> switchPanel("adminTVboard"));

		      addComponent(TVMG, new JLabel("방송사명"), 0, 0, gbc);
		      addComponent(TVMG, tVName, 1, 0, gbc);
		      addComponent(TVMG, new JLabel("방송사 수정시"), 0, 1, gbc);
		      addComponent(TVMG, tVName2, 1, 1, gbc);

		      gbc.gridx = 0;
		      gbc.gridy = 2;
		      gbc.gridwidth = 1;
		      TVMG.add(btnUpdate, gbc);
		      gbc.gridx = 1;
		      TVMG.add(btnDelete, gbc);
		      gbc.gridx = 0;
		      gbc.gridy = 3;
		      gbc.gridwidth = 2;
		      TVMG.add(backButton, gbc);

		      return TVMG;
	   }
	   private  void TVDEL(JTextField tVName) {
		      String tvNameText = tVName.getText().trim();
		      int result = JOptionPane.showConfirmDialog(mainPanel, String.format("%s 삭제 하시겠습니까?", tvNameText), "확인",
		            JOptionPane.YES_NO_OPTION);
		      if (result == JOptionPane.NO_OPTION) {
		         return;
		      }
		      
		      if(!channelManager.selectChannel(tvNameText)) {
		    	  JOptionPane.showMessageDialog(mainPanel, "존재하지 않는 방송사입니다.");
		    	  return;
		      }
		   // tvNameText 인 프로그램 삭제 ->
		      channelManager.delChannel(tvNameText);
		      JOptionPane.showMessageDialog(mainPanel, "삭제 완료");
		      return;
		   }
	   private void TVADDUP(JTextField tVName, JTextField tVName2) {

		      String tVNameText = tVName.getText().trim();
		      String tVName2Text = tVName2.getText().trim();

		      if (tVNameText.isBlank()) {
		         JOptionPane.showMessageDialog(mainPanel, "방송사 이름은 입력해주세요.");
		         return;
		      }
		      
		      int result = JOptionPane.showConfirmDialog(mainPanel, "새로운 방송사 등록 하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
		      if (result == JOptionPane.NO_OPTION) {
		         int result2 = JOptionPane.showConfirmDialog(mainPanel, "기존 방송사 수정 하시겠습니까?", "확인",
		               JOptionPane.YES_NO_OPTION);
		         if (result2 == JOptionPane.NO_OPTION)
		            return;
		         if (tVName2Text.isBlank()) {
		            JOptionPane.showMessageDialog(mainPanel, "수정할 방송사명이 공란일 경우 수정이 불가합니다.");
		            return;
		         }
		         
		         if(!channelManager.selectChannel(tVNameText)) {
			    	  JOptionPane.showMessageDialog(mainPanel, "수정하려는 방송사가 없습니다.");
			    	  return;
			      }
		         if(channelManager.selectChannel(tVName2Text)) {
			    	  JOptionPane.showMessageDialog(mainPanel, "수정하려는 방송사가 이미 등록되어 있습니다");
			    	  return;
			      }
		      // tvNameText 방송사 이름을 tvName2Text로 변경
		         channelManager.updateChannel(tVNameText,tVName2Text);
		         JOptionPane.showMessageDialog(mainPanel, "수정 완료");
		         return;
		      }

		     
		      if(channelManager.selectChannel(tVNameText)) {
		    	  JOptionPane.showMessageDialog(mainPanel, "이미 등록되어 있는 방송사입니다.");
		    	  return;
		      }
		      channelManager.insertChannel(tVNameText);
		      JOptionPane.showMessageDialog(mainPanel, "방송사 등록 완료");
		      return;
		   }
	   private JPanel panelAdminPRMG() {
	      JPanel PRMGPanel = new JPanel(new GridBagLayout());
	      GridBagConstraints gbc = new GridBagConstraints();
	      gbc.fill = GridBagConstraints.BOTH;
	      gbc.weightx = 1.0;
	      gbc.weighty = 1.0;

	      JButton prADD = new JButton("프로그램 추가 수정 삭제");

	      JButton prGR = new JButton("프로그램 장르 등록");
	      JButton backButton = new JButton("뒤로가기");
	      

	      addComponent(PRMGPanel, prADD, 0, 0, gbc);

	      addComponent(PRMGPanel, prGR, 0, 3, gbc);
	      addComponent(PRMGPanel, backButton, 0, 4, gbc);
	      
	      mainPanel.add(panelPRADD(), "PRADD");

	      mainPanel.add(panelPRGR(), "PRGR");
	      
	      prADD.addActionListener(e -> {
	         JOptionPane.showMessageDialog(PRMGPanel, "프로그램 추가 수정 삭제");
	         switchPanel("PRADD");         
	      });

	      prGR.addActionListener(e -> {
	         JOptionPane.showMessageDialog(PRMGPanel, "프로그램 장르 등록");
	         setProgramList();
	         setGenreList();
	         switchPanel("PRGR");
	         
	      });
	      backButton.addActionListener(e -> {
	         JOptionPane.showMessageDialog(PRMGPanel, "뒤로가기");
	         switchPanel("adminTVboard");
	      });

	      return PRMGPanel;
	   }

	   private void setGenreList() {
		   grNames =  genreManager.getGenreList();
		   grBox.removeAllItems();
		   for(String i: grNames) {
			   grBox.addItem(i);
		   }
	}

	private Component panelPRGR() {
	       JPanel PRGRPanel = new JPanel(new GridBagLayout());
	       GridBagConstraints gbc = new GridBagConstraints();
	       gbc.fill = GridBagConstraints.BOTH;
	       gbc.weightx = 1.0;
	       gbc.weighty = 1.0;

	       JButton btnRefresh = new JButton("새로고침");

	       // 여기로 모든 방송사들 가져와주면 됩니다.
	       
	       

	       JButton btnAdd = new JButton("장르 등록");
	       JButton btnDelete = new JButton("장르 삭제");
	       JButton backButton = new JButton("뒤로가기");
	       
	       btnAdd.addActionListener(e ->{
	    	   int ageIndex = prBox2.getSelectedIndex();
			   String prName = prBox2.getItemAt(ageIndex);
			   
			   int ageIndex2 = grBox.getSelectedIndex();            
			   String grName = grBox.getItemAt(ageIndex2);
			   if(programGenreManager.selectPrGr(prName,grName)) {
				   JOptionPane.showMessageDialog(mainPanel, "프로그램에 이미 장르가 등록되어 있습니다");
				   return;
			   }
			   programGenreManager.insertPrGr(prName,grName);
			   JOptionPane.showMessageDialog(mainPanel, "등록 성공");
			  
	       });
	       btnDelete.addActionListener(e -> {
	    	   int ageIndex = prBox2.getSelectedIndex();
			   String prName = prBox2.getItemAt(ageIndex);
			   
			   int ageIndex2 = grBox.getSelectedIndex();            
			   String grName = grBox.getItemAt(ageIndex2);
			   if(!programGenreManager.selectDelPrGr(prName,grName)) {
				   JOptionPane.showMessageDialog(mainPanel, "프로그램에 등록되어있지 않은 장르입니다.");
				   return;
			   }
			   programGenreManager.delPrGr(prName,grName);
			   JOptionPane.showMessageDialog(mainPanel, "삭제 완료");
			   return;
			   
	       });
	       backButton.addActionListener(e -> switchPanel("PRMG"));
	       
	       gbc.gridx = 0;
	       gbc.gridy = 0;
	       gbc.gridwidth = 2;
	       PRGRPanel.add(btnRefresh, gbc);

	       gbc.gridy = 1;
	       gbc.gridwidth = 1;
	       PRGRPanel.add(new JLabel("방송 프로그램"), gbc);
	       
	       gbc.gridx = 1;
	       PRGRPanel.add(prBox2, gbc);

	       gbc.gridx = 0;
	       gbc.gridy = 2;
	       PRGRPanel.add(new JLabel("장르 선택"), gbc);

	       gbc.gridx = 1;
	       PRGRPanel.add(grBox, gbc);

	       gbc.gridx = 0;
	       gbc.gridy = 3;
	       PRGRPanel.add(btnAdd, gbc);

	       gbc.gridx = 1;
	       PRGRPanel.add(btnDelete, gbc);

	       gbc.gridx = 0;
	       gbc.gridy = 4;
	       gbc.gridwidth = 2;
	       PRGRPanel.add(backButton, gbc);

	       return PRGRPanel;
	   }

	   private Component panelPRADD() {
		   JPanel PRADDPanel = new JPanel(new GridBagLayout());
		      GridBagConstraints gbc = new GridBagConstraints();
		      gbc.fill = GridBagConstraints.BOTH;
		      gbc.weightx = 1.0;
		      gbc.weighty = 1.0;

		      JTextField PRName = new JTextField();
		      JTextField PRName2 = new JTextField();
		      
		      // 등록된 연령대 모두 가져옴
		      String[] ages = { "모든 연령 시청가"
		            , "7세 이상 시청가"
		            , "12세 이상 시청가"
		            , "15세 이상 시청가"
		            , "19세 이상 시청가" };
		      JComboBox<String> ageBox = new JComboBox<>(ages);
		      ageBox.addActionListener(e -> {});

		      JButton btnUpdate = new JButton("프로그램 등록/수정");
		      JButton btnDelete = new JButton("방영 종료");
		      JButton backButton = new JButton("뒤로가기");

		      btnUpdate.addActionListener(e -> {
    
		      int ageIndex = ageBox.getSelectedIndex();            
		      String selectedAge = ageBox.getItemAt(ageIndex);      //일단 연령대 string으로 보내겠씁니다...
		      
		      PRADDUP(PRName, PRName2, selectedAge);
		     
		      }); // 여기서 만든 id를 db로 보냅니다.
		      btnDelete.addActionListener(e -> PRDEL(PRName));
		      backButton.addActionListener(e -> {
		    	  setProgramList();
		    	  switchPanel("PRMG");
		      });

		      addComponent(PRADDPanel, new JLabel("프로그램명"), 0, 0, gbc);
		      addComponent(PRADDPanel, PRName, 1, 0, gbc);
		      addComponent(PRADDPanel, new JLabel("프로그램명 수정시"), 0, 1, gbc);
		      addComponent(PRADDPanel, PRName2, 1, 1, gbc);
		      addComponent(PRADDPanel, new JLabel("프로그램 연령대"), 0, 2, gbc);
		      addComponent(PRADDPanel, ageBox, 1, 2, gbc);


		      gbc.gridx = 0;
		      gbc.gridy = 3;
		      gbc.gridwidth = 1;
		      PRADDPanel.add(btnUpdate, gbc);
		      gbc.gridx = 1;
		      PRADDPanel.add(btnDelete, gbc);
		      gbc.gridx = 0;
		      gbc.gridy = 4;
		      gbc.gridwidth = 2;
		      PRADDPanel.add(backButton, gbc);

		      return PRADDPanel;
	   }
	   private  void PRDEL(JTextField pRName) {
		      String pRNameText = pRName.getText().trim();
		      int result = JOptionPane.showConfirmDialog(mainPanel, String.format("%s 방영을 종료 하시겠습니까?", pRNameText), "확인",
		            JOptionPane.YES_NO_OPTION);
		      if (result == JOptionPane.NO_OPTION) {
		         return;
		      }
		      if(!programManager.searchName(pRNameText)) {
		    	  JOptionPane.showMessageDialog(mainPanel, "프로그램명이 존재하지 않습니다");
		    	  return;
		      }
		      programManager.delProgram(pRNameText);
		      JOptionPane.showMessageDialog(mainPanel, "프로그램 방영이 종료 되었습니다");
		      return;
		      // pRNameText 인 프로그램 삭제 ->
		      // 결과 받아올수 있으면 int result = JOptionPane.showConfirmDialog(mainPanel,
		      // String.format("%s 삭제되었습니다.", pRNameText), "확인", JOptionPane.YES_NO_OPTION);

		   }
	   
	      private  void PRADDUP(JTextField pRName, JTextField pRName2, String selectedAge) {

	          String pRNameText = pRName.getText().trim();
	          String pRName2Text = pRName2.getText().trim();
	          // 연령대 selectedAge로
	          //String pRAgeText = pRAge.getText().trim();
	          //String pRAge2Text = pRAge2.getText().trim();

	          if (pRNameText.isBlank()) {
	             JOptionPane.showMessageDialog(mainPanel, "프로그램 이름은 입력해주세요.");
	             return;
	          }
	          int result = JOptionPane.showConfirmDialog(mainPanel, "새로운 프로그램을 등록 하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
	          if (result == JOptionPane.NO_OPTION) {
	             int result2 = JOptionPane.showConfirmDialog(mainPanel, "기존 프로그램을 수정 하시겠습니까?", "확인",
	                   JOptionPane.YES_NO_OPTION);
	             if (result2 == JOptionPane.NO_OPTION)
	                return;
	             if (pRName2Text.isBlank()) {
	                if (selectedAge.isBlank()) {
	                   JOptionPane.showMessageDialog(mainPanel, "이름과 연령대 둘 다 선택하지 않았을 경우 수정이 불가합니다.");
	                   return;
	                }
	                if(programManager.searchName(pRNameText)) {
			        	  
			        	  programManager.updateProgram(pRNameText,selectedAge);
			        	  JOptionPane.showMessageDialog(mainPanel, "수정완료!");
			        	  //업데이트 되었습니다!
			        	  return;
			          }
	                JOptionPane.showMessageDialog(mainPanel, "등록되지 않은 프로그램명입니다");
	                return;
	             }
	             
	             if(!programManager.searchName(pRNameText)) {
	            	 JOptionPane.showMessageDialog(mainPanel, "프로그램명이 존재하지 않습니다.");
		        	  return;
	             }
	             if(programManager.searchName(pRName2Text)) {
	            	 JOptionPane.showMessageDialog(mainPanel, "수정할려는 프로그램명이 이미 등록되어 있습니다");
	            	 return;
	             }
	          // pRNameText 의 이름을 pRName2Text로 연령대를 pRAge2Text로 변경
	             programManager.updateProgramAll(pRNameText,pRName2Text,selectedAge);
	             //업데이트 성공 !
	             JOptionPane.showMessageDialog(mainPanel, "수정완료!");
	             return;
	          }
	          //// pRNameText 와 pRAgeText 가지고 프로그램 등록
	          if(programManager.searchName(pRNameText)) {
	        	  JOptionPane.showMessageDialog(mainPanel, "이미 등록된 프로그램입니다");
	        	  return;
	          }
	          int ageKey = programAgeManager.getAgeKey(selectedAge);
		      programManager.insertProgram(pRNameText,ageKey);
		      //등록되셨습니다!
	          return;
	       }
	   
   private  JPanel panelUser() {
	  JPanel userPanel = new JPanel(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.BOTH;
      gbc.weightx = 1.0;
      gbc.weighty = 1.0;
      JButton userDataButton = new JButton("내 정보");
      JButton bookmarkButton = new JButton("즐겨찾기");
      JButton logoutButton = new JButton("로그아웃");
      
      addComponent(userPanel, userDataButton, 0, 0, gbc);
      addComponent(userPanel, bookmarkButton, 0, 1, gbc);
      addComponent(userPanel, logoutButton, 0, 2, gbc);

      
      mainPanel.add(panelUserData(), "userData");
      JPanel bookmarkPanel = panelBookmark(); 
      mainPanel.add( bookmarkPanel, "bookmark");
      
      
      
      userDataButton.addActionListener(e -> {
         JOptionPane.showMessageDialog(userPanel, "내 정보");
         // new PanelAdminManager(frameMain)
         switchPanel("userData");
      });
      bookmarkButton.addActionListener(e -> {
    	  listModel.clear();
    	  
    	  
         JOptionPane.showMessageDialog(userPanel, "즐겨찾기");
         setProgramList();
        setBookmarkList(userKey);
   	
         switchPanel("bookmark");
      });
      logoutButton.addActionListener(e -> {
    	  reflash(listModel);
    	  
         logOut(mainPanel);
      });
      
      return userPanel;
      
   }
   


private void reflash(DefaultListModel<String> listModel2) {
	listModel2.clear();	
}

private  JPanel panelUserData() {
	      JPanel UserDataPanel = new JPanel(new GridBagLayout());
	      GridBagConstraints gbc = new GridBagConstraints();
	      gbc.fill = GridBagConstraints.BOTH;
	      gbc.weightx = 1.0;
	      gbc.weighty = 1.0;
	      
	      JButton detailButton = new JButton("내 정보 조회");
	      JButton detailUpdateButton = new JButton("내 정보 수정");
	      JButton backButton = new JButton("처음으로");
	      
	      addComponent(UserDataPanel, detailButton, 0, 0, gbc);
	      addComponent(UserDataPanel, detailUpdateButton, 0, 1, gbc);
	      addComponent(UserDataPanel, backButton, 0, 2, gbc);
	      
	      mainPanel.add(panelDetailUpdate(), "detailUpdate");
	      
	      detailButton.addActionListener(e -> {
	    	  User user = userManager.getUser(userKey);
	          JOptionPane.showMessageDialog(UserDataPanel, "내 정보\n"+user.toString());
	         // 그냥 아이디 비번 이름 가져오면 됩니다 귀찮은데 그냥 콘솔에 sysout으로 띄울까요
	      });
	      detailUpdateButton.addActionListener(e -> {
	         JOptionPane.showMessageDialog(UserDataPanel, "내 정보 수정");
	         switchPanel("detailUpdate");
	      });
	      backButton.addActionListener(e -> {
	         switchPanel("user");
	      });
	      
	      return UserDataPanel;
	   }
   
   private JPanel panelBookmark() {
	   JPanel bookMarkPanel = new JPanel(new GridBagLayout());
	      GridBagConstraints gbc = new GridBagConstraints();
	      gbc.fill = GridBagConstraints.BOTH;
	      gbc.weightx = 1.0;
	      gbc.weighty = 1.0;
	      
	      
	        
	      JList<String> list= new JList<>(listModel);
	      JScrollPane scrollPane = new JScrollPane(list);
		gbc.gridx = 0; gbc.gridy = 10; gbc.weighty = 10;
		bookMarkPanel.add(scrollPane, gbc);
		  
		// 삭제용 combox(전체 프로그램 가져와서 넣어줘야함)
		      
	    JButton bookmarkUpadateButton = new JButton("즐겨찾기 추가");
       
	      // 삭제용 combox(이미 있는애들만)
	      

	      JButton bookmarkdeleteButton = new JButton("즐겨찾기 삭제");
	       
	      JButton backButton = new JButton("뒤로가기");
	      
	    
	      
	     
	      addComponent(bookMarkPanel, prBox, 0, 11, gbc);
	      addComponent(bookMarkPanel, bookmarkUpadateButton, 0, 12, gbc);
	      addComponent(bookMarkPanel, bmBox, 0, 13, gbc);
	      addComponent(bookMarkPanel, bookmarkdeleteButton, 0, 14, gbc);
	      addComponent(bookMarkPanel, backButton, 0, 15, gbc);
	      
	      bookmarkUpadateButton.addActionListener(e -> {
	    	  String selectedProgram = (String) prBox.getSelectedItem();
	          if (!listModel.contains(selectedProgram)) {
	             listModel.addElement(selectedProgram);
	             reflash(listModel);
	             insertBookMark(userKey,selectedProgram);
	             setBookmarkList(userKey);
	             JOptionPane.showMessageDialog(bookMarkPanel, selectedProgram + " 즐겨찾기에 추가됨.");
	          } else {
	             JOptionPane.showMessageDialog(bookMarkPanel, "이미 즐겨찾기에 존재하는 프로그램입니다.");
	          }
	      });
	      
	      bookmarkdeleteButton.addActionListener(e -> {
	    	  String selectedProgram = (String) bmBox.getSelectedItem();
	          if (selectedProgram != null) {
	             listModel.removeElement(selectedProgram);
	             reflash(listModel);
	             delBookMark(userKey,selectedProgram);
	             setBookmarkList(userKey);
	             
	             JOptionPane.showMessageDialog(bookMarkPanel, selectedProgram + " 즐겨찾기에서 삭제됨.");
	          }
	      });
	      backButton.addActionListener(e -> {
	         switchPanel("user");
	      });
	      return bookMarkPanel;
	   }
private void setProgramList() {
	   
		pros = programManager.getProgramList();
		pross = programManager.getProgramList();
		prNames =programManager.getProgramList();  
	      // 여기서 for문으로 listModel.addElement(스트링); 해서 즐겨찾기 등록 	      
	   prBox.removeAllItems();
	   prsBox.removeAllItems();
	   prBox2.removeAllItems();
	   for(String i : pros) {
	    	prBox.addItem(i);
	   }
	   for(String i : prNames) {
	    	prBox2.addItem(i);
	   }
	   for(String i : pross) {
	    	prsBox.addItem(i);
	   }
   }
   private void insertBookMark(int userKey, String selectedProgram) {
	   	int pgKey = programManager.getPgKey(selectedProgram);
	   	indexManager.insertBookMark(userKey,pgKey);
	
}

private void delBookMark(int userKey, String selectedProgram) {
	   	indexManager.delBookMark(userKey,selectedProgram);
}

private void setBookmarkList(int userKey) {
	   
	   List<index> bookMarkList = indexManager.getBookMarkList(userKey);                 
	      // 여기서 for문으로 listModel.addElement(스트링); 해서 즐겨찾기 등록 	      
	   bmBox.removeAllItems();
	   for(index i : bookMarkList) {
	    	listModel.addElement(i.getPg_name());
	    	bmBox.addItem(i.getPg_name());
	   }
   }
   
   
    private  JPanel CreatesignuptPanel() {
        JPanel signuptPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        idText = new JTextField();
        pwText = new JTextField();
        pw2Text = new JTextField();
        nameText = new JTextField();
        JButton btnRegister = new JButton("회원 가입");
        JButton backButton = new JButton("뒤로가기");

        btnRegister.addActionListener(e -> registerId());   // 여기서 만든 id를 db로 보냅니다.
        backButton.addActionListener(e -> switchPanel("home"));
        
        addComponent(signuptPanel, new JLabel("아이디"), 0, 0, gbc);
        addComponent(signuptPanel, idText, 1, 0, gbc);
        addComponent(signuptPanel, new JLabel("비밀번호"), 0, 1, gbc);
        addComponent(signuptPanel, pwText, 1, 1, gbc);
        addComponent(signuptPanel, new JLabel("비번확인"), 0, 2, gbc);
        addComponent(signuptPanel, pw2Text, 1, 2, gbc);
        addComponent(signuptPanel, new JLabel("유저이름"), 0, 3, gbc);
        addComponent(signuptPanel, nameText, 1, 3, gbc);
        
        

        gbc.gridx = 0; 
        gbc.gridy = 4; 
        gbc.gridwidth = 2; 
        signuptPanel.add(btnRegister, gbc);
        gbc.gridy = 5; 
        signuptPanel.add(backButton, gbc);
        
        return signuptPanel;
    }
    
    private  void registerId() {
        try {

           String newId = idText.getText().trim();
           String newPw = pwText.getText().trim();
           String newPw2 = pw2Text.getText().trim();
           String newName = nameText.getText().trim();
           
           if(newId.isBlank()||newPw.isBlank()||newPw2.isBlank()||newName.isBlank()) {
              JOptionPane.showMessageDialog(mainPanel, "빈칸은 있으면 안됩니다.");   
              return;
           }
           
           // 여기서 string들 보내고
			if(userManager.idRepeat(newId)) {
				   JOptionPane.showMessageDialog(mainPanel, "이미 존재하는 아이디.");
				   return;
			   }
			if(!newPw.equals(newPw2)) {
				JOptionPane.showMessageDialog(mainPanel, "비밀번호 일치하지 않습니다.");
				return;
			}
			userManager.insertUser(newId,newPw,newName);
            // if (/*id가 이미 존재하면*/){ JOptionPane.showMessageDialog(mainPanel, "이미 존재하는 아이디.");   return;}
           
           idText.setText("");
           pwText.setText("");
           pw2Text.setText("");
           nameText.setText("");
            
            
            JOptionPane.showMessageDialog(mainPanel, newName + "회원가입 성공");
            switchPanel("home");
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(mainPanel, exception.getMessage());
        }
    }
    private  Component panelDetailUpdate() {
        JPanel detailUpdatePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JTextField idText2 = new JTextField();
        JTextField pwText2 = new JTextField();
        JTextField pw2Text2 = new JTextField();
        JTextField nameText2 = new JTextField();
        JButton btnUpdate = new JButton("정보 수정");
        JButton backButton = new JButton("뒤로가기");

        btnUpdate.addActionListener(e -> registerIdUpdate(idText2,pwText2,pw2Text2,nameText2));   // 여기서 만든 id를 db로 보냅니다.
        backButton.addActionListener(e -> switchPanel("userData"));
        
        addComponent(detailUpdatePanel, new JLabel("아이디"), 0, 0, gbc);
        addComponent(detailUpdatePanel, idText2, 1, 0, gbc);
        addComponent(detailUpdatePanel, new JLabel("비밀번호"), 0, 1, gbc);
        addComponent(detailUpdatePanel, pwText2, 1, 1, gbc);
        addComponent(detailUpdatePanel, new JLabel("비번확인"), 0, 2, gbc);
        addComponent(detailUpdatePanel, pw2Text2, 1, 2, gbc);
        addComponent(detailUpdatePanel, new JLabel("유저이름"), 0, 3, gbc);
        addComponent(detailUpdatePanel, nameText2, 1, 3, gbc);

        gbc.gridx = 0; 
        gbc.gridy = 4; 
        gbc.gridwidth = 2; 
        detailUpdatePanel.add(btnUpdate, gbc);
        gbc.gridy = 5; 
        detailUpdatePanel.add(backButton, gbc);
        
        return detailUpdatePanel;
   }
    private void registerIdUpdate(JTextField idText2, JTextField pwText2, JTextField pw2Text2, JTextField nameText2) {
        
        try {
           String newId = idText2.getText().trim();
           String newPw = pwText2.getText().trim();
           String newPw2 = pw2Text2.getText().trim();
           String newName = nameText2.getText().trim();
           
           if(newId.isBlank()||newPw.isBlank()||newPw2.isBlank()||newName.isBlank()) {
              JOptionPane.showMessageDialog(mainPanel, "빈칸은 있으면 안됩니다.");   
              return;
           }
           

          int result = JOptionPane.showConfirmDialog(mainPanel, "정보 수정 하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
          if (result == JOptionPane.NO_OPTION) return;   
          if(!newPw.equals(newPw2)) {
        	  JOptionPane.showMessageDialog(mainPanel, "비밀번호가 일치하지 않습니다..");
        	  return;
          }
          if(userManager.idRepeat(newId)) {
			   JOptionPane.showMessageDialog(mainPanel, "이미 존재하는 아이디.");
			   return;
		   }
          userManager.updateUser(newId,newPw,newName,userKey);
          
           
           idText.setText("");
           pwText.setText("");
           pw2Text.setText("");
            nameText.setText("");
            
            
            JOptionPane.showMessageDialog(mainPanel, newName + "님 변경되었습니다.");
            switchPanel("userData");
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(mainPanel, exception.getMessage());
        }
    }
    

}
