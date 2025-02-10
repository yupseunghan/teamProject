package broadGUI;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class UserMenu extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton viewScheduleButton, logoutButton;

	
	public UserMenu(MainFrame mainFrame, List<TvProgram> comList, List<String> companys) {
		  setTitle("user 메뉴");
	        setSize(400, 300);
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setLocationRelativeTo(mainFrame);


	        JPanel panel = new JPanel();
	        panel.setLayout(new GridLayout(2, 1));

	        viewScheduleButton = new JButton("편성표 조회");
	        logoutButton = new JButton("로그아웃");

	        panel.add(viewScheduleButton);
	        panel.add(logoutButton);

	        add(panel);

	        
	        viewScheduleButton.addActionListener(e -> new SchedulePanel(this,comList));
	        logoutButton.addActionListener(e -> {
	            JOptionPane.showMessageDialog(this, "로그아웃되었습니다.");
	            dispose();
	        });

	        setVisible(true);
	    }
	}