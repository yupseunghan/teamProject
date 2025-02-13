package broadGUI.network;

import java.awt.GridLayout;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PanelUserMenu extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton viewScheduleButton, logoutButton;

	
	public PanelUserMenu(FrameMain mainFrame, List<TvProgram> comList) {
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

	        
	        viewScheduleButton.addActionListener(e -> new PanelSchedule(this,comList));
	        logoutButton.addActionListener(e -> {
	            JOptionPane.showMessageDialog(this, "로그아웃되었습니다.");
	            dispose();
	        });

	        setVisible(true);
	    }


	public PanelUserMenu(PanelLoginMenu panelLoginMenu, List<TvProgram> comList) {
		// TODO Auto-generated constructor stub
	}


	public PanelUserMenu(PanelLoginMenu panelLoginMenu, ObjectOutputStream oos, ObjectInputStream ois) {
		// TODO Auto-generated constructor stub
	}
	}