package broadGUI.network;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class PanelAdminManager extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField  companyNameField, programNameField, timeField, explainField;
    private JButton addButton, updateButton, deleteButton, backButton;
    ProgramMannager p = new ProgramMannager();
    
    
    
    
    
    
    
    
    public PanelAdminManager(JFrame mainFrame, List<TvProgram>comList) {  
        setTitle("프로그램 추가 수정 삭제");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(mainFrame);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));


        addButton = new JButton("추가");
        updateButton = new JButton("수정");
        deleteButton = new JButton("삭제");
        backButton = new JButton("뒤로 가기");
        
        

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(backButton);

        add(panel);

        addButton.addActionListener(e -> {
        	new PanelAdminAdd(this,comList);
        });
        updateButton.addActionListener(e -> {
        	new PanelAdminUpdate(this,comList);
        });
        deleteButton.addActionListener(e -> {
        	new PanelAdminDelete(this,comList);
        
    });

        backButton.addActionListener(e -> dispose());

        setVisible(true);
}
    
  



	public boolean certain(String ment) {
        int result = JOptionPane.showConfirmDialog(this, ment , "확인", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            return true;
        }
        return false;
    }


}
