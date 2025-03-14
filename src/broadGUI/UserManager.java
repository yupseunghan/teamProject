package broadGUI;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class UserManager {

	
	
	
	
	private  void PRADDUP(JTextField pRName, JTextField pRName2, String selectedAge) {

        String pRNameText = pRName.getText().trim();
        String pRName2Text = pRName2.getText().trim();
        // 연령대 selectedAge로
        //String pRAgeText = pRAge.getText().trim();
        //String pRAge2Text = pRAge2.getText().trim();

        if (pRNameText.isBlank()) {
           JOptionPane.showMessageDialog(mainPanel, "프로그램 이름은 입력해주세요.");
           return;
        }
        int result = JOptionPane.showConfirmDialog(mainPanel, "새로운 프로그램을 등록 하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.NO_OPTION) {
           int result2 = JOptionPane.showConfirmDialog(mainPanel, "기존 프로그램을 수정 하시겠습니까?", "확인",
                 JOptionPane.YES_NO_OPTION);
           if (result2 == JOptionPane.NO_OPTION)
              return;
           if (pRName2Text.isBlank()) {
              if (selectedAge.isBlank()) {
                 JOptionPane.showMessageDialog(mainPanel, "이름과 연령대 둘 다 선택하지 않았을 경우 수정이 불가합니다.");
                 return;
              }
              if(programManager.searchName(pRNameText)) {
                 programManager.updateProgram(pRNameText,selectedAge);
                 //업데이트 되었습니다!
                 return;
                 
              }
              //프로그램이 없습니다 등록해주세요
              return;
           }
           
           // pRNameText 의 이름을 pRName2Text로 연령대를 pRAge2Text로 변경
           //업데이트 성공 !
           return;
        }
        //// pRNameText 와 pRAgeText 가지고 프로그램 등록
        if(programManager.searchName(pRNameText)) {
           //이미 등록된 프로그램입니다
           return;
        }
        int ageKey = programAgeManager.getAgeKey(selectedAge);
       programManager.insertProgram(pRNameText,ageKey);
       //등록되셨습니다!
        return;
     }
}
