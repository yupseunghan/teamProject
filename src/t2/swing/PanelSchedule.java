package t2.swing;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

}
