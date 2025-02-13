package broadGUI.network;

import javax.swing.*;
import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class PanelAdminMenu extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton scheduleButton, logoutButton;
	private JButton viewScheduleButton;

    public PanelAdminMenu(FrameMain mainFrame, List<TvProgram> comList) {
	
        setTitle("admin 메뉴");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(mainFrame);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        scheduleButton = new JButton("편성표 관리");
       // manageUsersButton = new JButton("사용자 관리");
        viewScheduleButton = new JButton("편성표 조회");
        logoutButton = new JButton("로그아웃");

        panel.add(scheduleButton);
       // panel.add(manageUsersButton);
        panel.add(viewScheduleButton);
        panel.add(logoutButton);

        add(panel);

        scheduleButton.addActionListener(e -> new PanelAdminManager(this,comList));
        //manageUsersButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "user 관리"));
        viewScheduleButton.addActionListener(e -> new PanelSchedule(this,comList));
        logoutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "로그아웃되었습니다.");
            dispose();
        });

        setVisible(true);
    }

	public PanelAdminMenu(PanelLoginMenu panelLoginMenu, List<TvProgram> comList) {
		// TODO Auto-generated constructor stub
	}

	public PanelAdminMenu(PanelLoginMenu panelLoginMenu, ObjectOutputStream oos, ObjectInputStream ois) {
		// TODO Auto-generated constructor stub
	}

	
}