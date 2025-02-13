package broadGUI.network;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class FrameMainUser extends JFrame implements ConsoleProgram{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel mainPanel;
    private JButton loginButton, guestButton, exitButton;


    public FrameMainUser(List<TvProgram> comList, ObjectOutputStream oos, ObjectInputStream ois) {
        setTitle("방송 편성표 시스템");
        setLayout(new FlowLayout());	
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1));

        loginButton = new JButton("로그인");
        guestButton = new JButton("로그인 없이 조회");
        exitButton = new JButton("종료");

        mainPanel.add(loginButton);
        mainPanel.add(guestButton);
        mainPanel.add(exitButton);

        add(mainPanel);

        // 버튼 이벤트 리스너
        loginButton.addActionListener(e -> {
        	try {
        	new PanelLoginMenu(this,oos,ois);
        	oos.writeInt(1);
				oos.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
        guestButton.addActionListener(e -> {
        	
        	
        	
        	try {
        	new PanelSchedule(this, oos,ois);
        	//new SchedulePanel(this);		
				oos.writeInt(2);
				oos.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
        });
        exitButton.addActionListener(e -> {
        	System.out.println("종료");
        	exitProgram(comList);
        	
        });

        setVisible(true);
	}


	private void exitProgram(List<TvProgram> comList) {
        int result = JOptionPane.showConfirmDialog(this, "정말 종료하시겠습니까?", "종료 확인", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
        	save(fileName("TvList"),comList);	
            System.exit(0);
        }
    }
	





}
