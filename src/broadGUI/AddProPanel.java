package broadGUI;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddProPanel extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField  companyNameField, programNameField, timeField, minField, explainField;
    private JButton addButton, updateButton, deleteButton, backButton;

    public AddProPanel(JFrame frame) {  //기본생성자(테스트용)
        setTitle("프로그램 추가 수정 삭제");
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
        
        panel.add(new JLabel("시작 분 (mm) :"));
        minField = new JTextField();
        panel.add(minField);
        
        panel.add(new JLabel("장르 :"));
        explainField = new JTextField();
        panel.add(explainField);

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
        	
        JOptionPane.showMessageDialog(this, "프로그램 추가");
        });
        updateButton.addActionListener(e -> {
        JOptionPane.showMessageDialog(this, "프로그램 수정");
        });
        deleteButton.addActionListener(e -> {
        	
        JOptionPane.showMessageDialog(this, "프로그램 삭제");
        });
        
        backButton.addActionListener(e -> dispose());

        setVisible(true);
    }
    
    public AddProPanel(MainFrame mainFrame, List<TvProgram>comList, List<String> companys) {  
        setTitle("프로그램 추가 수정 삭제");
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
        
        panel.add(new JLabel("시작 분 (mm) :"));
        minField = new JTextField();
        panel.add(minField);
        
        panel.add(new JLabel("장르 :"));
        explainField = new JTextField();
        panel.add(explainField);

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
        	
        JOptionPane.showMessageDialog(this, "프로그램 추가");
        //TimeTable tmp = new TimeTable(timeField.getText(),minField.getName(), programNameField.getText(), explainField.getName());
        //comList.
        //방송사 안 겹칠 경우 추가하면 됨
        });
        updateButton.addActionListener(e -> {
        JOptionPane.showMessageDialog(this, "프로그램 수정");
        //생각중
        });
        deleteButton.addActionListener(e -> {
        	
        JOptionPane.showMessageDialog(this, "프로그램 삭제");
        //생각중
        });
        
        backButton.addActionListener(e -> dispose());

        setVisible(true);
    }
}
