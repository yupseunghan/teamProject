package t2.swing;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
import t2.model.vo.User;
import t2.model.vo.index;
import t2.service.BroadTimeManager;
import t2.service.ChannelManager;
import t2.service.ProgramManager;
import t2.service.UserManager;
import t2.service.indexManager;

@Data
public class FrameMain extends JFrame {
   private static final long serialVersionUID = 1L;

   private static JPanel mainPanel;
   private JButton loginButton, signupButton, exitButton;
   
   private static CardLayout cardLayout;
   private DefaultListModel<String> scheduleListModel;
   private JList<String> scheduleList;
   private JComboBox<String> tvBox, dayBox;
   private HashMap<String, List<String>> tvProgramMap; // 일단은 이렇게 했는데 나중에는 문자열 리스트로 가져올것
   private PanelLoginMenu loginRES;
   private static JTextField idText, pwText, pw2Text, nameText;
   private BroadTimeManager broadTimeManager = new BroadTimeManager();
   private ChannelManager channelManager = new ChannelManager();
   private UserManager userManager = new UserManager();
   private ProgramManager programManager = new ProgramManager();
   private indexManager indexManager = new indexManager();
   private  int userKey;
   private static List<index> bookMarkList;
   private static DefaultListModel<String> listModel = new DefaultListModel<String>();
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
      String[] tvNames = channelManager.getChannelList();

      // 요일 선택
      String[] days = { "일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일" };
      dayBox = new JComboBox<>(days);

      // 방송사 선택
      tvBox = new JComboBox<>(tvNames);
      tvBox.addActionListener(e -> display());

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

      loginButton.addActionListener(e -> logIn());
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
   private static void logOut(JPanel panel) {
      int result = JOptionPane.showConfirmDialog(mainPanel, "로그아웃 하시겠습니까?", "종료 확인", JOptionPane.YES_NO_OPTION);
      if (result == JOptionPane.YES_OPTION) {
         JOptionPane.showMessageDialog(panel, "로그아웃되었습니다.");
         FrameMain.switchPanel("home");
      }         
      
   }
   private void display() {
	   int tvIndex = tvBox.getSelectedIndex();
       int dayIndex = dayBox.getSelectedIndex();
       if (tvIndex < 0 || dayIndex < 0) return;
       String channel = tvBox.getItemAt(tvIndex);
       String week = dayBox.getItemAt(dayIndex);
       List<BroadTime> programs  = broadTimeManager.getBroadTimeList(channel,week);
       // 요일별 데이터 추가 필요

       scheduleListModel.clear();
       scheduleListModel.addElement("방송사: " + channel + " (" + week + ")");
       scheduleListModel.addElement("================================");

       if (programs.isEmpty()) {
           scheduleListModel.addElement("등록된 프로그램이 없습니다.");
           return;
       } 
       for(BroadTime bt : programs) {
       	scheduleListModel.addElement(bt.toString());
       }
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
	      //mainPanel.add(panelAdminTVMG(), "TVMG");
	      //mainPanel.add(panelAdminGRMG(), "GRMG");
	      //mainPanel.add(panelAdminBBMG(), "BBMG");
	      
	      PRMGButton.addActionListener(e -> {
	         JOptionPane.showMessageDialog(adminTVBoardPanel, "프로그램 추가 수정 삭제");
	         switchPanel("PRMG");         
	      });
	      TVMGButton.addActionListener(e -> {
	         JOptionPane.showMessageDialog(adminTVBoardPanel, "방송국 추가 수정 삭제");
	         //switchPanel("TVMG");         
	      });
	      GRMGButton.addActionListener(e -> {
	         JOptionPane.showMessageDialog(adminTVBoardPanel, "장르 추가 수정 삭제");
	         //switchPanel("GRMG");         
	      });
	      BBMGButton.addActionListener(e -> {
	         JOptionPane.showMessageDialog(adminTVBoardPanel, "프로그램과 방송사 선택 후 요일과 시간 입력");
	         // 만약 select로 이미 있는 프로그램,방송사,요일,시간이라면 알림 후 등록x
	         //switchPanel("BBMG");
	         
	      });
	      backButton.addActionListener(e -> {
	         JOptionPane.showMessageDialog(adminTVBoardPanel, "처음으로");
	         switchPanel("admin");
	      });

	      return adminTVBoardPanel;
	   }
   private static JPanel panelAdminBBMG() {

	      return null;
	   }

	   private static JPanel panelAdminGRMG() {
	      // TODO Auto-generated method stub
	      return null;
	   }

	   private JPanel panelAdminTVMG() {
	      JPanel TVMGPanel = new JPanel(new GridBagLayout());
	      GridBagConstraints gbc = new GridBagConstraints();
	      gbc.fill = GridBagConstraints.BOTH;
	      gbc.weightx = 1.0;
	      gbc.weighty = 1.0;

	      JButton prADD = new JButton("프로그램 추가");
	      JButton TVMGButton = new JButton("프로그램 수정");
	      JButton GRMGButton = new JButton("프로그램 삭제");
	      JButton BBMGButton = new JButton("프로그램 장르 등록");
	      JButton backButton = new JButton("처음으로");
	      

	      addComponent(TVMGPanel, prADD, 0, 0, gbc);
	      addComponent(TVMGPanel, TVMGButton, 0, 1, gbc);
	      addComponent(TVMGPanel, GRMGButton, 0, 2, gbc);
	      addComponent(TVMGPanel, BBMGButton, 0, 3, gbc);
	      addComponent(TVMGPanel, backButton, 0, 4, gbc);
	      
	      mainPanel.add(panelPRADD(), "PRADD");
	      //mainPanel.add(panelPRUPD(), "PRUPD");
	      //mainPanel.add(panelPRDEL(), "PRDEL");
	      //mainPanel.add(panelPRGR(), "PRGR");
	      
	      prADD.addActionListener(e -> {
	         JOptionPane.showMessageDialog(TVMGPanel, "프로그램 추가");
	         //switchPanel("PRADD");         
	      });
	      TVMGButton.addActionListener(e -> {
	         JOptionPane.showMessageDialog(TVMGPanel, "프로그램 수정");
	         //switchPanel("PRUPD");         
	      });
	      GRMGButton.addActionListener(e -> {
	         JOptionPane.showMessageDialog(TVMGPanel, "프로그램 삭제");
	         //switchPanel("PRDEL");         
	      });
	      BBMGButton.addActionListener(e -> {
	         JOptionPane.showMessageDialog(TVMGPanel, "프로그램 장르 등록");
	         //switchPanel("PRGR");
	         
	      });
	      backButton.addActionListener(e -> {
	         JOptionPane.showMessageDialog(TVMGPanel, "처음으로");
	         switchPanel("admin");
	      });

	      return TVMGPanel;
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
	      //mainPanel.add(panelPRUPD(), "PRUPD");
	      //mainPanel.add(panelPRDEL(), "PRDEL");
	      mainPanel.add(panelPRGR(), "PRGR");
	      
	      prADD.addActionListener(e -> {
	         JOptionPane.showMessageDialog(PRMGPanel, "프로그램 추가 수정 삭제");
	         switchPanel("PRADD");         
	      });

	      prGR.addActionListener(e -> {
	         JOptionPane.showMessageDialog(PRMGPanel, "프로그램 장르 등록");
	         switchPanel("PRGR");
	         
	      });
	      backButton.addActionListener(e -> {
	         JOptionPane.showMessageDialog(PRMGPanel, "뒤로가기");
	         switchPanel("adminTVboard");
	      });

	      return PRMGPanel;
	   }

	   private Component panelPRGR() {
	       JPanel PRGRPanel = new JPanel(new GridBagLayout());
	       GridBagConstraints gbc = new GridBagConstraints();
	       gbc.fill = GridBagConstraints.BOTH;
	       gbc.weightx = 1.0;
	       gbc.weighty = 1.0;

	       JButton btnRefresh = new JButton("새로고침");

	       String[] prNames = { "무한도전", "1박2일", "아침마당" };// 여기로 모든 방송사들 가져와주면 됩니다.
	       JComboBox<String> prBox = new JComboBox<>(prNames);

	       String[] grNames = { "예능", "시사", "교양" }; // 여기로 모든 장르들 가져와주면 됩니다.
	       JComboBox<String> grBox = new JComboBox<>(grNames);

	       JButton btnAdd = new JButton("장르 등록");
	       JButton btnDelete = new JButton("장르 삭제");
	       JButton backButton = new JButton("뒤로가기");

	       backButton.addActionListener(e -> switchPanel("PRMG"));

	       gbc.gridx = 0;
	       gbc.gridy = 0;
	       gbc.gridwidth = 2;
	       PRGRPanel.add(btnRefresh, gbc);

	       gbc.gridy = 1;
	       gbc.gridwidth = 1;
	       PRGRPanel.add(new JLabel("방송 프로그램"), gbc);
	       
	       gbc.gridx = 1;
	       PRGRPanel.add(prBox, gbc);

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
	        JTextField PRAge = new JTextField();
	        JTextField PRAge2 = new JTextField();
	        JButton btnUpdate = new JButton("프로그램 등록/수정");
	        JButton btnDelete = new JButton("프로그램 삭제");
	      JButton backButton = new JButton("뒤로가기");

	      //btnUpdate.addActionListener(e -> PRADDUP(PRName,PRName2,PRAge,PRAge2));   // 여기서 만든 id를 db로 보냅니다.
	      backButton.addActionListener(e -> switchPanel("PRMG"));
	        
	        addComponent(PRADDPanel, new JLabel("프로그램명"), 0, 0, gbc);
	        addComponent(PRADDPanel, PRName, 1, 0, gbc);
	        addComponent(PRADDPanel, new JLabel("프로그램명 수정시"), 0, 1, gbc);
	        addComponent(PRADDPanel, PRName2, 1, 1, gbc);
	        addComponent(PRADDPanel, new JLabel("프로그램 연령대"), 0, 2, gbc);
	        addComponent(PRADDPanel, PRAge, 1, 2, gbc);
	        addComponent(PRADDPanel, new JLabel("연령대 수정시"), 0, 3, gbc);
	        addComponent(PRADDPanel, PRAge2, 1, 3, gbc);

	        gbc.gridx = 0; 
	        gbc.gridy = 4; 
	        gbc.gridwidth = 1; 
	        PRADDPanel.add(btnUpdate, gbc);
	        gbc.gridx = 1; 
	        PRADDPanel.add(btnDelete, gbc);
	        gbc.gridx = 0; 
	        gbc.gridy = 5; 
	        gbc.gridwidth = 2; 
	        PRADDPanel.add(backButton, gbc);
	        
	        return PRADDPanel;
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
      mainPanel.add(panelBookmark(), "bookmark");
      
      
      
      userDataButton.addActionListener(e -> {
         JOptionPane.showMessageDialog(userPanel, "내 정보");
         // new PanelAdminManager(frameMain)
         switchPanel("userData");
      });
      bookmarkButton.addActionListener(e -> {
         JOptionPane.showMessageDialog(userPanel, "즐겨찾기");
         // new PanelSchedule(frameMain);
         bookMarkList = indexManager.getBookMarkList(userKey);
         System.out.println(bookMarkList);        
	      // 여기서 for문으로 listModel.addElement(스트링); 해서 즐겨찾기 등록 	      
	    System.out.println(userKey);
    	for(index i : bookMarkList) {
	    	  listModel.addElement(i.getPg_name());
	      }
    	
         switchPanel("bookmark");
      });
      logoutButton.addActionListener(e -> {
         logOut(mainPanel);
      });
      
      return userPanel;
      
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
	      
	      
	        
	      JList<String> list = new JList<>(listModel);
	      JScrollPane scrollPane = new JScrollPane(list);
	        gbc.gridx = 0; gbc.gridy = 10; gbc.weighty = 10;
	        bookMarkPanel.add(scrollPane, gbc);
	      
	        // 삭제용 combox(전체 프로그램 가져와서 넣어줘야함)
	        String[] pros = programManager.getProgramList();
	        JComboBox<String> prBox = new JComboBox<>(pros);      
	        JButton bookmarkUpadateButton = new JButton("즐겨찾기 추가");
       
	      // 삭제용 combox(이미 있는애들만)
	      String[] bmNames = new String[listModel.getSize()];
	      for (int i = 0; i < listModel.getSize(); i++) {
	          bmNames[i] = listModel.getElementAt(i);                  //얘는 위에서 가져온 listModel에서 가져옵니다
	      }
	       JComboBox<String> bmBox = new JComboBox<>(bmNames);

	      JButton bookmarkdeleteButton = new JButton("즐겨찾기 삭제");
	       
	      JButton backButton = new JButton("뒤로가기");
	      
	      addComponent(bookMarkPanel, prBox, 0, 11, gbc);
	      addComponent(bookMarkPanel, bookmarkUpadateButton, 0, 12, gbc);
	      addComponent(bookMarkPanel, bmBox, 0, 13, gbc);
	      addComponent(bookMarkPanel, bookmarkdeleteButton, 0, 14, gbc);
	      addComponent(bookMarkPanel, backButton, 0, 15, gbc);
	      
	      bookmarkUpadateButton.addActionListener(e -> {
	         JOptionPane.showMessageDialog(bookMarkPanel, "즐겨찾기에 추가");
	         panelBookmarkAdd();
	      });
	      
	      bookmarkdeleteButton.addActionListener(e -> {
	         JOptionPane.showMessageDialog(bookMarkPanel, "즐겨찾기 삭제하는 메소드");
	         panelBookmarkDel();
	      });
	      backButton.addActionListener(e -> {
	         switchPanel("user");
	      });
	      return bookMarkPanel;
	   }
   
   
   private  JPanel panelBookmarkUpdate() {
	  JPanel bookMarkUpdatePanel = new JPanel(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.BOTH;
      gbc.weightx = 1.0;
      gbc.weighty = 1.0;
      
      JButton bookmarkAddButton = new JButton("즐겨찾기 추가");
      JButton bookmarkModButton = new JButton("즐겨찾기 수정");
      JButton bookmarkdeleteButton = new JButton("즐겨찾기 삭제");
      JButton backButton = new JButton("처음으로");
      
      addComponent(bookMarkUpdatePanel, bookmarkAddButton, 0, 0, gbc);
      addComponent(bookMarkUpdatePanel, bookmarkModButton, 0, 1, gbc);
      addComponent(bookMarkUpdatePanel, bookmarkdeleteButton, 0, 2, gbc);
      addComponent(bookMarkUpdatePanel, backButton, 0, 3, gbc);
   
      bookmarkAddButton.addActionListener(e -> {
         JOptionPane.showMessageDialog(bookMarkUpdatePanel, "즐겨찾기 추가하는 메소드");
         panelBookmarkAdd();
      });
      bookmarkModButton.addActionListener(e -> {
         JOptionPane.showMessageDialog(bookMarkUpdatePanel, "즐겨찾기 수정하는 메소드");
         panelBookmarkMod();
      });
      bookmarkdeleteButton.addActionListener(e -> {
         JOptionPane.showMessageDialog(bookMarkUpdatePanel, "즐겨찾기 삭제하는 메소드");
         panelBookmarkDel();
      });
      backButton.addActionListener(e -> {
         switchPanel("user");
      });
      
      return bookMarkUpdatePanel;
      
   }
   private static void panelBookmarkDel() {
	      System.out.println("즐겨찾기 추가하는 메소드");
   }

   private static void panelBookmarkMod() {
      System.out.println("즐겨찾기 수정하는 메소드");
      
   }

   private static void panelBookmarkAdd() {
      System.out.println("즐겨찾기 삭제하는 메소드");
      
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
