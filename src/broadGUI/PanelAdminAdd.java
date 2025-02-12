package broadGUI;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelAdminAdd extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JTextField  companyNameField, programNameField, timeField, explainField;
    private JButton addButton, updateButton, deleteButton, backButton;
    ProgramMannager p = new ProgramMannager();
    
    public PanelAdminAdd(JFrame frame, List<TvProgram>comList) { 
	
	  setTitle("프로그램 추가");
      setSize(400, 300);
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

      JPanel panel = new JPanel();
      panel.setLayout(new GridLayout(7, 2));

      panel.add(new JLabel("방송사 :"));
      companyNameField = new JTextField();
      panel.add(companyNameField);
      
      panel.add(new JLabel("프로그램명 :"));
      programNameField = new JTextField();
      panel.add(programNameField);

      panel.add(new JLabel("방송 시간 (HH) :"));
      timeField = new JTextField();
      panel.add(timeField);
       
      panel.add(new JLabel("장르 :"));
      explainField = new JTextField();
      panel.add(explainField);

      addButton = new JButton("추가");
      backButton = new JButton("뒤로 가기");
      
      
      panel.add(addButton);
      panel.add(backButton);

      add(panel);
      
      addButton.addActionListener(e -> {
      	addProgram(this,comList);
      });
      
      backButton.addActionListener(e -> dispose());

      setVisible(true);
    }

	private void addProgram(PanelAdminAdd addProgram, List<TvProgram> comList) {
		
		
		boolean res =false;
    	String company = companyNameField.getText();
    	String programName = programNameField.getText();
    	String time = timeField.getText();
    	String explain = explainField.getText();
    	if(!certain("추가하시겠습니까?")) res = false;
    	else res=p.programInsert(company,programName,time,explain);
    	if(res)JOptionPane.showMessageDialog(this, "프로그램 추가");
    	else JOptionPane.showMessageDialog(this, "프로그램 추가 실패");
    	
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
