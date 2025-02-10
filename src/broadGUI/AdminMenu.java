package broadGUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminMenu extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton scheduleButton, manageUsersButton, logoutButton;
	private JButton viewScheduleButton;

    public AdminMenu(MainFrame mainFrame, List<Company> comList, List<String> companys, List<User> userList) {
	
        setTitle("admin 메뉴");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        scheduleButton = new JButton("편성표 관리");
        manageUsersButton = new JButton("사용자 관리");
        viewScheduleButton = new JButton("편성표 조회");
        logoutButton = new JButton("로그아웃");

        panel.add(scheduleButton);
        panel.add(manageUsersButton);
        panel.add(viewScheduleButton);
        panel.add(logoutButton);

        add(panel);

        scheduleButton.addActionListener(e -> new AddProPanel(this));
        manageUsersButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "user 관리"));
        viewScheduleButton.addActionListener(e -> new SchedulePanel(this,comList));
        logoutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "로그아웃되었습니다.");
            dispose();
        });

        setVisible(true);
    }

	
}