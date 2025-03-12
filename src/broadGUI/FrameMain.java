package broadGUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class FrameMain extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private JPanel mainPanel;
    private JButton loginButton, guestButton, exitButton;
    private static CardLayout cardLayout;
    private DefaultListModel<String> scheduleListModel;
    private JList<String> scheduleList;
    private JComboBox<String> tvBox, dayBox;
    private HashMap<String, List<String>> tvProgramMap;

    public FrameMain() {
        setTitle("방송 편성표 시스템");
        setSize(400, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel, BorderLayout.CENTER);

        // 초기값
        // 여기서 방송사 전체 개수 가져와서 넣어야
        String[] tvNames = {"KBS", "MBC", "SBS"};

        // 요일 선택 
        String[] days = {"일요일","월요일", "화요일","수요일","목요일","금요일","토요일"};
        dayBox = new JComboBox<>(days);

        
        // 방송사 선택
        tvBox = new JComboBox<>(tvNames);
        tvBox.addActionListener(e -> display());

        // 방송편성표 리스트
        scheduleListModel = new DefaultListModel<>();
        scheduleList = new JList<>(scheduleListModel);
        JScrollPane scrollPane = new JScrollPane(scheduleList);

        // 패널들
        JPanel tvPanel = new JPanel(new GridLayout(1,2));
        tvPanel.add(tvBox);
        tvPanel.add(dayBox);

        JPanel printPanel = new JPanel(new BorderLayout());
        printPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        loginButton = new JButton("로그인");
        guestButton = new JButton("로그인 없이 조회");
        exitButton = new JButton("종료");

        bottomPanel.add(loginButton);
        bottomPanel.add(guestButton);
        bottomPanel.add(exitButton);

        // 추가
        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.add(tvPanel, BorderLayout.NORTH);
        homePanel.add(printPanel, BorderLayout.CENTER);
        homePanel.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.add(homePanel, "home");

        exitButton.addActionListener(e -> exitProgram());

        display();
        setVisible(true);
    }

    private void exitProgram() {
        int result = JOptionPane.showConfirmDialog(this, "정말 종료하시겠습니까?", "종료 확인", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void display() {
        int tvIndex = tvBox.getSelectedIndex();
        int dayIndex = dayBox.getSelectedIndex();
        if (tvIndex < 0 || dayIndex < 0) return;
        String selectedTv = tvBox.getItemAt(tvIndex);
        String selectedDay = dayBox.getItemAt(dayIndex);
        //여기서 선택한 방송사의 index를 db로 보내야
        //여기서 db에서 해당하는 list를 받아옴.	
        
        tvProgramMap = new HashMap<>();
        tvProgramMap.put("KBS", List.of("뉴스9", "1박 2일", "개그콘서트"));
        tvProgramMap.put("MBC", List.of("무한도전", "라디오스타", "나 혼자 산다"));
        tvProgramMap.put("SBS", List.of("런닝맨", "집사부일체", "그것이 알고 싶다"));

        List<String> programs = tvProgramMap.getOrDefault(selectedTv, new ArrayList<>()); 
        // 요일별 데이터 추가 필요

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

    public static void main(String[] args) {
        new FrameMain();
    }
}

