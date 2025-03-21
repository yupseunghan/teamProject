package t2.swing;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PanelUser extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private DefaultListModel<String> userListModel;
	private JList<String> userList;
	private JComboBox<String> userBox;
	public static PanelUser PURES;

	public PanelUser() {
		setTitle("편성표 조회");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		initializeUI();
		

		
		//display();

		setVisible(true);
	}

	private void initializeUI() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
	
	
		// 초기값
		// 여기서 유저들 가져와서 key 순으로 넣어야
		String[] userNames = { "aaa", "qwer", "aew", "user1", "user2", "user3" };	
		String[] userAth = { "관리자", "사용자", "관리자" };		//얘네 둘은 그냥 보기좋으라고 정리해놓은거. 삭제하면 됩니다.
		
		List<String> users = new ArrayList<String>();
		users.add("aaa");
		users.add("qwer");
		users.add("aew");
		users.add("user1");
		users.add("user2");
		users.add("user3");
		List<String> aths = new ArrayList<String>();
		aths.add("관리자");
		aths.add("사용자");
		aths.add("사용자");
		aths.add("사용자");
		aths.add("사용자");
		aths.add("사용자");
		
		
		
		// 유저명 리스트
		userListModel = new DefaultListModel<>();
		userList = new JList<>(userListModel);
		JScrollPane scrollPane = new JScrollPane(userList);

		userListModel.clear();
		userListModel.addElement("유저명");
		userListModel.addElement("================================");

		if (users.isEmpty()) {
			userListModel.addElement("등록된 프로그램이 없습니다.");
		} else {
			for (String user : users) {
				int index = users.indexOf(user);
				userListModel.addElement((user + " : " + (String)aths.get(index)));
			}
		}
	
		

		// 유저 선택 
		userBox = new JComboBox<>(userNames);
		JButton userDeleteButton = new JButton("사용자 삭제"); 
		JButton userAthButton = new JButton("사용자 권한 변경"); 
		
		// 패널
		JPanel printPanel = new JPanel(new BorderLayout());
		printPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel choicePanel = new JPanel(new GridLayout(1, 2));
		choicePanel.add(userBox);
		choicePanel.add(userDeleteButton);
		choicePanel.add(userAthButton);
		

		JPanel homePanel = new JPanel(new BorderLayout());
		homePanel.add(printPanel, BorderLayout.CENTER);
		homePanel.add(choicePanel, BorderLayout.SOUTH);

		setContentPane(homePanel);


		userDeleteButton.addActionListener(e -> {
			// 유저 삭제 근데 삭제하면 key는...?
			int userIndex = userBox.getSelectedIndex();
			String selectedUser = userBox.getItemAt(userIndex);
			if(aths.get(userIndex).equals("관리자")) {
			JOptionPane.showMessageDialog(this, ("관리자 계정은 삭제 불가합니다."));
			return;
			
			}
			JOptionPane.showMessageDialog(this, (selectedUser + "을 삭제"));
			
			users.remove(userIndex);
			aths.remove(userIndex);
			
			userListModel.clear();
			userListModel.addElement("유저명");
			userListModel.addElement("================================");

			if (users.isEmpty()) {
				userListModel.addElement("등록된 프로그램이 없습니다.");
			} else {
				for (String user : users) {
					int index = users.indexOf(user);
					userListModel.addElement((user + " : " + (String)aths.get(index)));
				}
			}
			
		});
		userAthButton.addActionListener(e -> {
			// 유저 권한 변경 
			// 선택한 유저를 가져와서 권한이 관리자이면 사용자로 사용자면 관리자로

			int userIndex = userBox.getSelectedIndex();
			String selectedUser = userBox.getItemAt(userIndex);
			JOptionPane.showMessageDialog(this, (selectedUser + "의 권한을 변경"));
			
			if(aths.get(userIndex).equals("관리자")) {
				aths.set(userIndex,"사용자");
				//return;
			}else {
			aths.set(userIndex,"관리자");
			//return;
			}

			userListModel.clear();
			userListModel.addElement("유저명");
			userListModel.addElement("================================");

			if (users.isEmpty()) {
				userListModel.addElement("등록된 프로그램이 없습니다.");
			} else {
				for (String user : users) {
					int index = users.indexOf(user);
					userListModel.addElement((user + " : " + (String)aths.get(index)));
				}
			}
		});
		
}


private void display() {
	

}
}



