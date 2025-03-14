package broadGUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
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
	public static PanelSchedule PSRES;
	private static JTextField idText, pwText, pw2Text, nameText;

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
		String[] tvNames = { "KBS", "MBC", "SBS" };

		// 요일 선택
		String[] days = { "일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일" };
		dayBox = new JComboBox<>(days);

		// 방송사 선택
		tvBox = new JComboBox<>(tvNames);
		tvBox.addActionListener(e -> display());
		dayBox.addActionListener(e -> display());

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
		mainPanel.add(CreatesignuptPanel(), "signup");

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
		if (tvIndex < 0 || dayIndex < 0)
			return;

		String selectedTv = tvBox.getItemAt(tvIndex);
		String selectedDay = dayBox.getItemAt(dayIndex);
		// 여기서 선택한 방송사와 요일의 index를 db로 보내야
		// 여기서 db에서 해당하는 list를 받아옴.

		tvProgramMap = new HashMap<>();
		tvProgramMap.put("KBS", List.of("뉴스9", "1박 2일", "개그콘서트"));
		tvProgramMap.put("MBC", List.of("무한도전", "라디오스타", "나 혼자 산다"));
		tvProgramMap.put("SBS", List.of("런닝맨", "집사부일체", "그것이 알고 싶다"));

		List<String> programs = tvProgramMap.getOrDefault(selectedTv, new ArrayList<>()); // 일단은 이렇게 했는데 db에서 방송사, 요일에
																							// 해당하는 문자열 or tv객체들 가져와서 넣게
		// 요일별 데이터 추가 필요
		// 방송사 기본값 : kbs 요일 기본값 : now()

		scheduleListModel.clear();
		scheduleListModel.addElement("방송사: " + selectedTv + " (" + selectedDay + ")");
		scheduleListModel.addElement("================================");

		if (programs.isEmpty()) {
			scheduleListModel.addElement("등록된 프로그램이 없습니다.");
		} else {
			for (String program : programs) {
				scheduleListModel.addElement(program);
			}
		}
	}

	public static void switchPanel(String panelName) {
		cardLayout.show(mainPanel, panelName);
	}

	private static void addComponent(JPanel panel, Component comp, int x, int y, GridBagConstraints gbc) {
		gbc.gridx = x;
		gbc.gridy = y;
		panel.add(comp, gbc);
	}

	private static JPanel panelAdmin() {
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

	public static PanelUser PURES;

	private static JPanel panelAdminUSERBoard() {
		JPanel adminUSERBoardPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

		JButton USUPButton = new JButton("유저 권한 변경"); // 권한이 사용자인 유저들만 띄워서 권한을 관리자로 변경하는 버튼
		JButton USVWButton = new JButton("사용자 조회"); // 모든 id들을 띄우는 버튼
		JButton backButton = new JButton("처음으로");

		addComponent(adminUSERBoardPanel, USUPButton, 0, 0, gbc);
		addComponent(adminUSERBoardPanel, USVWButton, 0, 1, gbc);
		addComponent(adminUSERBoardPanel, backButton, 0, 2, gbc);

		// mainPanel.add(panelAdminTVBoard(), "adminTVboard");
		// mainPanel.add(panelAdminUSERBoard(), "adminUSERboard");

		USUPButton.addActionListener(e -> {
			JOptionPane.showMessageDialog(adminUSERBoardPanel, "유저 권한 변경");
			// switchPanel("adminTVboard");

		});
		USVWButton.addActionListener(e -> {
			JOptionPane.showMessageDialog(adminUSERBoardPanel, "사용자 조회");
			if (PURES != null) {
				PURES.dispose();
				PURES = null;
			}
			PURES = new PanelUser();

		});
		backButton.addActionListener(e -> {
			switchPanel("admin");
		});

		return adminUSERBoardPanel;
	}

	private static JPanel panelAdminTVBoard() {
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

	private static JPanel panelAdminBBMG() {
		JPanel BBMG = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

		// 등록된 프로그램을 모두 가져옴
		String[] pros = { "뉴스9", "1박 2일", "개그콘서트" };
		JComboBox<String> prBox = new JComboBox<>(pros);
		prBox.addActionListener(e -> {
		});

		// 등록된 방송사를 모두 가져옴
		String[] tvs = { "kbs", "mbc", "sbs" };
		JComboBox<String> tvBox = new JComboBox<>(tvs);
		tvBox.addActionListener(e -> {
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

		JButton showSchedulePanel = new JButton("편성표 조회");

		JButton tvProAddBtn = new JButton("프로그램 등록");
		JButton tvProUpdBtn = new JButton("프로그램 수정");
		JButton tvProDelBtn = new JButton("프로그램 삭제");

		JButton backButton = new JButton("뒤로가기");

		addComponent(BBMG, showSchedulePanel, 0, 1, gbc);
		addComponent(BBMG, prBox, 0, 2, gbc);
		addComponent(BBMG, tvBox, 0, 3, gbc);
		addComponent(BBMG, dayBox, 0, 4, gbc);
		addComponent(BBMG, sTimeBox, 0, 5, gbc);
		addComponent(BBMG, sMinBox, 0, 6, gbc);
		addComponent(BBMG, fTimeBox, 0, 7, gbc);
		addComponent(BBMG, fMinBox, 0, 8, gbc);
		addComponent(BBMG, tvProAddBtn, 0, 9, gbc);
		addComponent(BBMG, tvProUpdBtn, 0, 10, gbc);
		addComponent(BBMG, tvProDelBtn, 0, 11, gbc);
		addComponent(BBMG, backButton, 0, 12, gbc);

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

			int PRIndex = prBox.getSelectedIndex();
			int TVIndex = tvBox.getSelectedIndex();
			int STIndex = sTimeBox.getSelectedIndex();
			int SMIndex = sMinBox.getSelectedIndex();
			int FTIndex = fTimeBox.getSelectedIndex();
			int FMIndex = fMinBox.getSelectedIndex();

			if (PRIndex < 0 || TVIndex < 0 || STIndex < 0 || FTIndex < 0) {
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
			String selectedST = sTimeBox.getItemAt(STIndex);
			String selectedSM = sMinBox.getItemAt(SMIndex);
			String selectedFT = fTimeBox.getItemAt(FTIndex);
			String selectedFM = fMinBox.getItemAt(FMIndex);

			if (SMIndex <= 0 || FMIndex <= 0) {
				selectedSM = "0";
				selectedFM = "0";
			}

			// selectedPR, selectedTV, selectedST, selectedSM, selectedFT, selectedFM 이용해서
			// 편성표 등록

			// 보내는데 성공하면?
			JOptionPane.showMessageDialog(BBMG, selectedPR + "등록.");
			// 보내는데 실패하면?
			// JOptionPane.showMessageDialog(BBMG, selectedPR + "등록 실패.");

		});

		/////////////////////////////////////// 수정
		/////////////////////////////////////// 버튼///////////////////////////////////////////

		final int[] res = { 0 };

		tvProUpdBtn.addActionListener(e -> {

			int PRIndex = prBox.getSelectedIndex();
			int TVIndex = tvBox.getSelectedIndex();
			int STIndex = sTimeBox.getSelectedIndex();
			int SMIndex = sMinBox.getSelectedIndex();
			int FTIndex = fTimeBox.getSelectedIndex();
			int FMIndex = fMinBox.getSelectedIndex();

			if (STIndex <= 0 || FTIndex <= 0) {
				JOptionPane.showMessageDialog(BBMG,
						((STIndex == 0) ? "시작시간 " : "") + ((FTIndex == 0) ? "끝시간 " : "") + "선택해주세요");
				return;
			}

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
				JOptionPane.showMessageDialog(BBMG, selectedPR + "등록.");
				res[0] = 0;
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

			int PRIndex = prBox.getSelectedIndex();
			int TVIndex = tvBox.getSelectedIndex();
			int STIndex = sTimeBox.getSelectedIndex();
			int SMIndex = sMinBox.getSelectedIndex();
			int FTIndex = fTimeBox.getSelectedIndex();
			int FMIndex = fMinBox.getSelectedIndex();

			if (PRIndex < 0 || TVIndex < 0 || STIndex < 0 || FTIndex < 0) {
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
			String selectedST = sTimeBox.getItemAt(STIndex);
			String selectedSM = sMinBox.getItemAt(SMIndex);
			String selectedFT = fTimeBox.getItemAt(FTIndex);
			String selectedFM = fMinBox.getItemAt(FMIndex);

			if (SMIndex <= 0 || FMIndex <= 0) {
				selectedSM = "0";
				selectedFM = "0";
			}

			// selectedPR, selectedTV, selectedST, selectedSM, selectedFT, selectedFM 이용해서
			// 해당하는 편성표 삭제

			// 보내는데 성공하면?
			JOptionPane.showMessageDialog(BBMG, selectedPR + "삭제.");
			// 보내는데 실패하면?
			// JOptionPane.showMessageDialog(BBMG, selectedPR + "삭제 실패.");

		});

		backButton.addActionListener(e -> {
			switchPanel("adminTVboard");

		});

		return BBMG;
	}

	private static JPanel panelAdminGRMG() {

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

	private static void GRDEL(JTextField grName) {
		String grNameText = grName.getText().trim();
		int result = JOptionPane.showConfirmDialog(mainPanel, String.format("%s 삭제 하시겠습니까?", grNameText), "확인",
				JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.NO_OPTION) {
			return;
		}
		// grNameText 인 프로그램 삭제 ->
		// 결과 받아올수 있으면 int result = JOptionPane.showConfirmDialog(mainPanel,
		// String.format("%s 삭제되었습니다.", grNameText), "확인", JOptionPane.YES_NO_OPTION);

	}

	private static void GRADDUP(JTextField grName, JTextField grName2) {

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
		}

		// grNameText 장르 등록
		return;
	}

	private static JPanel panelAdminTVMG() {

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

	private static void TVDEL(JTextField tVName) {
		String tvNameText = tVName.getText().trim();
		int result = JOptionPane.showConfirmDialog(mainPanel, String.format("%s 삭제 하시겠습니까?", tvNameText), "확인",
				JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.NO_OPTION) {
			return;
		}
		// tvNameText 인 프로그램 삭제 ->
		// 결과 받아올수 있으면 int result = JOptionPane.showConfirmDialog(mainPanel,
		// String.format("%s 삭제되었습니다.", tvNameText), "확인", JOptionPane.YES_NO_OPTION);

	}

	private static void TVADDUP(JTextField tVName, JTextField tVName2) {

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
			// tvNameText 방송사 이름을 tvName2Text로 변경
		}

		// tvNameText 방송사 등록
		return;
	}

	private static JPanel panelAdminPRMG() {
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
		// mainPanel.add(panelPRUPD(), "PRUPD");
		// mainPanel.add(panelPRDEL(), "PRDEL");
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

	private static JPanel panelPRGR() {
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

	private static JPanel panelPRADD() {
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
		JButton btnDelete = new JButton("프로그램 삭제");
		JButton backButton = new JButton("뒤로가기");



		
		btnUpdate.addActionListener(e -> {
		
		
			
		int ageIndex = ageBox.getSelectedIndex();				
		String selectedAge = ageBox.getItemAt(ageIndex);		//일단 연령대 string으로 보내겠씁니다...
		
		PRADDUP(PRName, PRName2, selectedAge);
		
		}); // 여기서 만든 id를 db로 보냅니다.
		btnDelete.addActionListener(e -> PRDEL(PRName));
		backButton.addActionListener(e -> switchPanel("PRMG"));

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

	private static void PRDEL(JTextField pRName) {
		String pRNameText = pRName.getText().trim();
		int result = JOptionPane.showConfirmDialog(mainPanel, String.format("%s 삭제 하시겠습니까?", pRNameText), "확인",
				JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.NO_OPTION) {
			return;
		}
		// pRNameText 인 프로그램 삭제 ->
		// 결과 받아올수 있으면 int result = JOptionPane.showConfirmDialog(mainPanel,
		// String.format("%s 삭제되었습니다.", pRNameText), "확인", JOptionPane.YES_NO_OPTION);

	}

	private static void PRADDUP(JTextField pRName, JTextField pRName2, String selectedAge) {

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
				// pRNameText 의 연령대를 pRAge2Text로 변경
				return;
			}
			if (selectedAge.isBlank()) {
				// pRNameText 의 이름을 pRName2Text로 변경
				return;
			}
			// pRNameText 의 이름을 pRName2Text로 연령대를 pRAge2Text로 변경
			return;
		}
		if (selectedAge.isBlank()) {
			// pRNameText 만 가지고 프로그램 등록
			return;
		}
		//// pRNameText 와 pRAgeText 가지고 프로그램 등록
		return;

	}

	private static JPanel panelUser() {
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
			switchPanel("bookmark");
		});
		logoutButton.addActionListener(e -> {
			logOut(mainPanel);
		});

		return userPanel;

	}

	private static JPanel panelUserData() {
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
			JOptionPane.showMessageDialog(UserDataPanel, "내 정보 조회");
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

	private static DefaultListModel<String> listModel = new DefaultListModel<>();
	private static JList<String> list = new JList<>(listModel);
	private static JComboBox<String> bmBox; // 삭제용 콤보박스

	private static JPanel panelBookmark() {
		JPanel bookMarkPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

		DefaultListModel<String> listModel = new DefaultListModel<>();
		// 여기서 for문으로 listModel.addElement(스트링); 해서 즐겨찾기 등록
		listModel.add(0, "무한도전");
		listModel.add(1, "1박2일");
		JScrollPane scrollPane = new JScrollPane(list);

		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.weighty = 10;
		bookMarkPanel.add(scrollPane, gbc);

		// 삭제용 combox(전체 프로그램 가져와서 넣어줘야함)
		String[] pros = { "뉴스9", "1박 2일", "개그콘서트" };
		JComboBox<String> prBox = new JComboBox<>(pros);
		JButton bookmarkUpadateButton = new JButton("즐겨찾기 추가");

		// 삭제용 combox(이미 있는애들만)
		String[] bmNames = new String[listModel.getSize()];
		for (int i = 0; i < listModel.getSize(); i++) {
			bmNames[i] = listModel.getElementAt(i); // 얘는 위에서 가져온 listModel에서 가져옵니다
		}
		bmBox = new JComboBox<>(getBookmarkedArray());

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
				refreshBookmarkPanel();
				JOptionPane.showMessageDialog(bookMarkPanel, selectedProgram + " 즐겨찾기에 추가됨.");
			} else {
				JOptionPane.showMessageDialog(bookMarkPanel, "이미 즐겨찾기에 존재하는 프로그램입니다.");
			}
		});

		bookmarkdeleteButton.addActionListener(e -> {
			String selectedProgram = (String) bmBox.getSelectedItem();
			if (selectedProgram != null) {
				listModel.removeElement(selectedProgram);
				refreshBookmarkPanel();
				JOptionPane.showMessageDialog(bookMarkPanel, selectedProgram + " 즐겨찾기에서 삭제됨.");
			}
		});
		backButton.addActionListener(e -> {
			switchPanel("user");
		});

		return bookMarkPanel;

	}

	private static String[] getBookmarkedArray() {
		List<String> bookmarks = new ArrayList<>();
		for (int i = 0; i < listModel.getSize(); i++) {
			bookmarks.add(listModel.getElementAt(i));
		}
		return bookmarks.toArray(new String[0]);
	}

	private static void refreshBookmarkPanel() {
		bmBox.setModel(new DefaultComboBoxModel<>(getBookmarkedArray())); // 콤보박스 새로고침
		list.setModel(listModel); // 리스트 새로고침
		switchPanel("bookmark"); // 패널 새로고침
	}

	private static JPanel CreatesignuptPanel() {
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

		btnRegister.addActionListener(e -> registerId()); // 여기서 만든 id를 db로 보냅니다.
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

	private static void registerId() {

		try {
			String newId = idText.getText().trim();
			String newPw = pwText.getText().trim();
			String newPw2 = pw2Text.getText().trim();
			String newName = nameText.getText().trim();

			if (newId.isBlank() || newPw.isBlank() || newPw2.isBlank() || newName.isBlank()) {
				JOptionPane.showMessageDialog(mainPanel, "빈칸은 있으면 안됩니다.");
				return;
			}

			int result = JOptionPane.showConfirmDialog(mainPanel, "회원가입 하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.NO_OPTION)
				return;

			// 여기서 string들 보내고
			// if (/*id가 이미 존재하면*/){ JOptionPane.showMessageDialog(mainPanel, "이미 존재하는
			// 아이디."); return;}

			idText.setText("");
			pwText.setText("");
			pw2Text.setText("");
			nameText.setText("");

			JOptionPane.showMessageDialog(mainPanel, newName + "님 환영합니다.");
			switchPanel("home");
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(mainPanel, exception.getMessage());
		}
	}

	private static Component panelDetailUpdate() {
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

		btnUpdate.addActionListener(e -> registerIdUpdate(idText2, pwText2, pw2Text2, nameText2)); // 여기서 만든 id를 db로
																									// 보냅니다.
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

	private static void registerIdUpdate(JTextField idText2, JTextField pwText2, JTextField pw2Text2,
			JTextField nameText2) {

		try {
			String newId = idText2.getText().trim();
			String newPw = pwText2.getText().trim();
			String newPw2 = pw2Text2.getText().trim();
			String newName = nameText2.getText().trim();

			if (newId.isBlank() || newPw.isBlank() || newPw2.isBlank() || newName.isBlank()) {
				JOptionPane.showMessageDialog(mainPanel, "빈칸은 있으면 안됩니다.");
				return;
			}

			int result = JOptionPane.showConfirmDialog(mainPanel, "정보 수정 하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.NO_OPTION)
				return;

			// 여기서 string들 보내고
			// if (/*바꾸는 id가 이미 존재하면 -> unique 속성 때문에 추가 안됨*/){
			// JOptionPane.showMessageDialog(mainPanel, "이미 존재하는 아이디."); return;}

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

	public static void main(String[] args) {
		new FrameMain();
	}
}
