package broadGUI;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class PanelAdminDelete extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField  companyNameField, programNameField, timeField, explainField;
    private JButton addButton, updateButton, deleteButton, backButton;
    ProgramMannager p = new ProgramMannager();
    
    
    
    
    
    
    
    
    public PanelAdminDelete(JFrame mainFrame, List<TvProgram>comList) {  
        setTitle("프로그램 삭제");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));

        panel.add(new JLabel("방송사 :"));
        companyNameField = new JTextField();
        panel.add(companyNameField);
        
        

        panel.add(new JLabel("방송 시간 (HH) :"));
        timeField = new JTextField();
        panel.add(timeField);
         



        deleteButton = new JButton("삭제");
        backButton = new JButton("뒤로 가기");
        
        


        panel.add(deleteButton);
        panel.add(backButton);

        add(panel);


        deleteButton.addActionListener(e -> {
        	delProgram(this,comList);
        
    });

        backButton.addActionListener(e -> dispose());

        setVisible(true);
    }
    
	private void delProgram(PanelAdminDelete delProgram, List<TvProgram> comList) {
		boolean res =false;
    	String company = companyNameField.getText();
    	
    	String time = timeField.getText();
    	

    	if(!certain(p.programName(company, time) + "삭제하시겠습니까?")) res = false;
    	else res=p.programDelete(company, time);
    	if(!res)JOptionPane.showMessageDialog(this, "프로그램 삭제 실패");
    	else JOptionPane.showMessageDialog(this, "프로그램 삭제");
    	p.sort();
	}
	public boolean certain(String ment) {
        int result = JOptionPane.showConfirmDialog(this, ment , "확인", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            return true;
        }
        return false;
    }
}
    
    