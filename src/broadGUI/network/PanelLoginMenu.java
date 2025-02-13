package broadGUI.network;

import java.awt.GridLayout;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelLoginMenu extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField idField;
    private JButton loginButton, signUpButton, backButton;

    public PanelLoginMenu(JFrame frame) {		//기본생성자(테스트용)
        setTitle("로그인");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(frame);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        idField = new JTextField();
        loginButton = new JButton("로그인");
        //signUpButton = new JButton("회원가입");
        backButton = new JButton("뒤로 가기");

        panel.add(new JLabel("아이디 입력 :"));
        panel.add(idField);
        panel.add(loginButton);
        //panel.add(signUpButton);
        panel.add(backButton);

        add(panel);

        loginButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "로그인"));
        //signUpButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "회원가입"));
        backButton.addActionListener(e->dispose());
        setVisible(true);
    }

	public PanelLoginMenu(FrameMain mainFrame, List<TvProgram>comList) {
	       setTitle("로그인");
	        setSize(300, 200);
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	        JPanel panel = new JPanel();
	        panel.setLayout(new GridLayout(4, 1));

	        idField = new JTextField();
	        loginButton = new JButton("로그인");
	        //signUpButton = new JButton("회원가입");
	        backButton = new JButton("뒤로 가기");

	        panel.add(new JLabel("아이디 입력 :"));
	        panel.add(idField);
	        panel.add(loginButton);
	       // panel.add(signUpButton);
	        panel.add(backButton);

	        add(panel);

	        loginButton.addActionListener(e -> {		//로그인
	            String id = idField.getText().trim();
	            if (id.isEmpty()) {
	                JOptionPane.showMessageDialog(this, "아이디를 입력하세요.");
	                return;
	            }
	            /*
	           int index = userList.indexOf(new User(id));
	            if (index>=0) {
	                dispose();
				*/
	            JOptionPane.showMessageDialog(this, id + " 로그인");
	                if (id.equals("admin")) {
	                	new PanelAdminMenu(mainFrame, comList); // 관리자 메뉴
	                } else {
	                	new PanelUserMenu(mainFrame,comList); // 사용자 메뉴
	                }
	                /*
	            } else {
	            	JOptionPane.showMessageDialog(this, "존재하지 않는 아이디.");
	            }*/
	        });

	        /*
	        signUpButton.addActionListener(e -> {
	        	String id = idField.getText().trim();
	        	if (id.isEmpty()) {
	        		JOptionPane.showMessageDialog(this, "아이디를 입력하세요.");
	        		return;
	        	}

	        	if (!userList.contains(new User(id))) {
	        		JOptionPane.showMessageDialog(this, "회원가입 성공.");
	        		userList.add(new User(id));
	        	} else {
	        		JOptionPane.showMessageDialog(this, "중복된 아이디.");
	        	}
	        });
		*/

	        backButton.addActionListener(e->dispose());
	        
	        setVisible(true);
	}

	public PanelLoginMenu(FrameMainUser frameMainUser, List<TvProgram> comList) {
		 setTitle("로그인");
	        setSize(300, 200);
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	        JPanel panel = new JPanel();
	        panel.setLayout(new GridLayout(4, 1));

	        idField = new JTextField();
	        loginButton = new JButton("로그인");
	        backButton = new JButton("뒤로 가기");

	        panel.add(new JLabel("아이디 입력 :"));
	        panel.add(idField);
	        panel.add(loginButton);
	        panel.add(backButton);

	        add(panel);

	        loginButton.addActionListener(e -> {		
	            String id = idField.getText().trim();
	            if (id.isEmpty()) {
	                JOptionPane.showMessageDialog(this, "아이디를 입력하세요.");
	                return;
	            }
	      
	            JOptionPane.showMessageDialog(this, id + " 로그인");
	                if (id.equals("admin")) {
	                	new PanelAdminMenu(this, comList); // 관리자 메뉴
	                } else {
	                	new PanelUserMenu(this,comList); // 사용자 메뉴
	                }
	            
	        });

	       

	        backButton.addActionListener(e->dispose());
	        
	        setVisible(true);
	}

	public PanelLoginMenu(FrameMainUser frameMainUser, ObjectOutputStream oos, ObjectInputStream ois) {
		 setTitle("로그인");
	        setSize(300, 200);
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	        JPanel panel = new JPanel();
	        panel.setLayout(new GridLayout(4, 1));

	        idField = new JTextField();
	        loginButton = new JButton("로그인");
	        backButton = new JButton("뒤로 가기");

	        panel.add(new JLabel("아이디 입력 :"));
	        panel.add(idField);
	        panel.add(loginButton);
	        panel.add(backButton);

	        add(panel);

	        loginButton.addActionListener(e -> {		
	            String id = idField.getText().trim();
	            if (id.isEmpty()) {
	                JOptionPane.showMessageDialog(this, "아이디를 입력하세요.");
	                return;
	            }
	      
	            JOptionPane.showMessageDialog(this, id + " 로그인");
	                if (id.equals("admin")) {
	                	new PanelAdminMenu(this, oos, ois); // 관리자 메뉴
	                } else {
	                	new PanelUserMenu(this,oos, ois); // 사용자 메뉴
	                }
	            
	        });

	       

	        backButton.addActionListener(e->dispose());
	        
	        setVisible(true);
	}


}
