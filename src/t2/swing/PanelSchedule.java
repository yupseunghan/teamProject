package t2.swing;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import t2.model.vo.BroadTime;
import t2.service.BroadTimeManager;
import t2.service.ChannelManager;

public class PanelSchedule extends JFrame {
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private DefaultListModel<String> scheduleListModel;
   private JList<String> scheduleList;
   private JComboBox<String> tvBox, dayBox;
   private HashMap<String, List<String>> tvProgramMap; // 일단은 이렇게 했는데 나중에는 문자열 리스트로 가져올것
   public static PanelSchedule PSRES;
   private String[] tvNames= {""};
   private ChannelManager channelManager = new ChannelManager();
   private List<BroadTime> programs; 
   private BroadTimeManager broadTimeManager = new BroadTimeManager();
   /*
    * public PanelSchedule(JFrame frame, List<TvProgram> tvList) {
    * setTitle("편성표 조회"); setSize(400, 300);
    * setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    * setLocationRelativeTo(frame);
    * 
    * this.tvPrograms = (tvList != null) ? tvList : List.of(); // Null 방지
    * 
    * 
    * initializeUI(); display();
    * 
    * setVisible(true); }
    */
   public PanelSchedule() {
      setTitle("편성표 조회");
      setSize(400, 300);
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      // setLocationRelativeTo(frame);

      initializeUI();
      display();

      setVisible(true);
   }

   private void initializeUI() {
      setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.BOTH;
      gbc.weightx = 1.0;

      // 초기값
      // 여기서 방송사 전체 개수 가져와서 넣어야
      
      tvNames = channelManager.getChannelList();
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

      // 추가
      JPanel homePanel = new JPanel(new BorderLayout());
      homePanel.add(choicePanel, BorderLayout.NORTH);
      homePanel.add(printPanel, BorderLayout.CENTER);

      setContentPane(homePanel);
      display();
   }

   /** 선택된 방송사의 편성표를 업데이트하여 출력 */
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

  
  private void reflash(DefaultListModel<String> listModel2) {
		listModel2.clear();	
	}
}
