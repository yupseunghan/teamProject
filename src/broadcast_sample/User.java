package broadcast_sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private boolean ath;
	public List<String> searchList;
	
	
	
	
	
	public User(String id) {
		super();
		this.id = id;
	}
	
	
	public User(String id, boolean a) {
		super();
		this.id = id;
		this.ath = a;
		
		if(searchList == null || searchList.isEmpty()) {
			searchList	= new ArrayList<String>();
		}
	}
	 
	

	public void myList() {
		if(searchList == null || searchList.isEmpty()) {
			System.out.println("검색 기록이 없습니다.");
			return;
		}
		System.out.println("내 검색 목록");
		for(String str : searchList) {
			System.out.print(str+ " ");
		}
		System.out.println();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}



	
	
}
