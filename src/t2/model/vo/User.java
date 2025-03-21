package t2.model.vo;
import lombok.Data;

@Data
public class User {
	private int us_key;
	private String us_id,us_pw,us_name,us_authority;
	@Override
	public String toString() {
		return "아이디: "+us_id+"\n비밀번호: "+us_pw+"\n이름: "+us_name;
	}
	
}
