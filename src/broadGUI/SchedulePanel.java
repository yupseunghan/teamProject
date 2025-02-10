package broadGUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SchedulePanel extends JFrame {
    private JComboBox<String> tvBox;
    private DefaultListModel<String> scheduleListModel;
    private JList<String> scheduleList;
    private List<TvProgram> tvPrograms;

    public SchedulePanel(JFrame frame, List<TvProgram> tvList) {
        setTitle("편성표 조회");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(frame);

        this.tvPrograms = (tvList != null) ? tvList : List.of(); // Null 방지

        initializeUI();
        display();

        setVisible(true);
    }

    /** UI 초기화 */
    private void initializeUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        if (tvPrograms.isEmpty()) {
            JOptionPane.showMessageDialog(this, "저장된 방송사가 없습니다.");
            dispose();
            return;
        }

        // 방송사 선택 콤보박스
        String[] tvNames = new String[tvPrograms.size()];
        for (int i = 0; i < tvPrograms.size(); i++) {
            tvNames[i] = tvPrograms.get(i).getTv();
        }
        tvBox = new JComboBox<>(tvNames);

        tvBox.addActionListener(e -> display());

        // 편성표 리스트
        scheduleListModel = new DefaultListModel<>();
        scheduleList = new JList<>(scheduleListModel);
        JScrollPane scrollPane = new JScrollPane(scheduleList);

        // 뒤로 가기 버튼 (dispose()로 창 닫기)
        JButton btnBack = new JButton("뒤로 가기");
        btnBack.addActionListener(e -> dispose());

        // UI 배치
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(tvBox, gbc);

        gbc.gridy = 1;
        gbc.weighty = 10;
        add(scrollPane, gbc);

        gbc.gridy = 2;
        gbc.weighty = 1;
        add(btnBack, gbc);
    }

    /** 선택된 방송사의 편성표를 업데이트하여 출력 */
    private void display() {
        scheduleListModel.clear();
        int index = tvBox.getSelectedIndex();
        if (index < 0) return;

        TvProgram selectedTv = tvPrograms.get(index);
        scheduleListModel.addElement("방송사: " + selectedTv.getTv());
        scheduleListModel.addElement("================================");

        if (selectedTv.getPrograms().isEmpty()) {
            scheduleListModel.addElement("등록된 프로그램이 없습니다.");
        } else {
            selectedTv.getPrograms().forEach(program -> scheduleListModel.addElement(program.toString()));
        }
    }
}
