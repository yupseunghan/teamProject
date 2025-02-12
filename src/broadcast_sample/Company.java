package broadcast_sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Data;


@Data
public class Company implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String companyName;
	 List<TimeTable> list;
	
	
	
	

	
	public void sort() {
		//시간c순 정렬
		list.sort((o1,o2)->{
			if(o1.getTime() != o2.getTime()) {
				return o1.getTime() - o2.getTime();
			}
			return o1.getMin() - o2.getMin();
		});
	}
	
	
	/*	
	public void printOnly() {
			
		}
		*/
	
	public void printAll(boolean a) {
		
		if(a) {
		int numA = 0;
		int numB = 0;
		int numC = 0;
		int numD = 0;
		for (int i = 0; i < list.size();i++) {
			numA = list.get(i).getTime();
			numC = list.get(i).getMin();
			
			if(numC>numD) {
				System.out.println();
			}else if(numA>numB) {
				System.out.println();
				numC = 0;
			}

			System.out.println(list.get(i).toString());
			numB = numA;
			numD = numC;
		}
		
		}else {
			for (int i = 0; i < list.size();i++) {
				System.out.println((i+1) + " 번 : " + list.get(i).toString());
			}
		}


	}


	public boolean printThis(int time) {
		
		for(int i = 0; i < list.size(); i++) {
		
			if(list.get(i).getTime() == time) {
				
				System.out.print("[" + this.companyName + "]" + this.list.get(i).toStringNoTime());
			}
		}
		return true;
	}
	
	public String printOut(int time) {
		
		String tmp = "";
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getTime() == time) {
				tmp += ("[" + this.companyName + "]" + this.list.get(i).toStringNoTime());
			}
		}
		return tmp;
	}
	
	
	
	public Company(String companyName) {
		this.companyName = companyName;
		list = new ArrayList<TimeTable>();
	}



	
	//회사명만으로 equals
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		return Objects.equals(companyName, other.companyName);
	}



	@Override
	public int hashCode() {
		return Objects.hash(companyName);
	}
	
	


	
	
	
	
}




