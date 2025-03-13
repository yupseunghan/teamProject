package t2.swing;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import lombok.Data;
import lombok.NoArgsConstructor;
import t2.service.UserManager;
@Data
@NoArgsConstructor
public class PanelLoginMenu extends JFrame {
    /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private JTextField idField, pwField;
   private JButton loginButton, backButton;
   private UserManager userManager = new UserManager();
   public PanelLoginMenu(FrameMain mainFrame) {
	   
       setTitle("로그인");
       setSize(300, 200);
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       setLocationRelativeTo(mainFrame);
     
       JPanel panel = new JPanel();
       panel.setLayout(new GridLayout(3, 2));

       idField = new JTextField();
       loginButton = new JButton("로그인");
       pwField = new JTextField();
       backButton = new JButton("뒤로 가기");

       panel.add(new JLabel("아이디 입력 :"));
       panel.add(idField);
       panel.add(new JLabel("비밀번호 입력 :"));
       panel.add(pwField);
       panel.add(loginButton);
       panel.add(backButton);

       add(panel);

       loginButton.addActionListener(e -> {   //로그인
	       String id = idField.getText();
	       if (id.isEmpty()) {
	           JOptionPane.showMessageDialog(this, "아이디를 입력하세요.");
	           return;
	       }
	       String pw = pwField.getText();
	       if (pw.isEmpty()) {
	           JOptionPane.showMessageDialog(this, "비밀번호를 입력하세요.");
	           return;
	       }
	       
       	   try {
	       	   boolean authority = userManager.getAuthority(id,pw);
	           if (authority) {            // 여기서 id.equals("admin") 대신 넘겨받은 권한이 admin이면 관리자메뉴로
	              //new PanelAdminMenu(mainFrame, comList); // 관리자 메뉴
	              mainFrame.switchPanel("admin");
	              dispose();
	           } else {
	              //new PanelUserMenu(mainFrame,comList); // 사용자 메뉴
	        	  int userNum = userManager.getUserNum(id);
	        	  mainFrame.setUserKey(userNum);
	              mainFrame.switchPanel("user");
	              
	              dispose();
	           }
       	   }catch(NullPointerException e1) {
       		JOptionPane.showMessageDialog(this, "아이디 비밀번호가 틀렸습니다");
       		return;
       	   }
	       	
	       
	       	JOptionPane.showMessageDialog(mainFrame, id + " 로그인");
       });

       backButton.addActionListener(e->dispose());
       
       setVisible(true);
   }
}
