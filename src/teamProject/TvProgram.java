package teamProject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TvProgram {
   Tv tv;
   List<Program> programs = new ArrayList<Program>();
   public String getTv() {
      return tv.name();
   }
   
   public void showTv(String menu) {
      if(tv.equals(Tv.valueOf(menu))) {
         for(Program p:programs) {
            System.out.println (p.toString());
         }
      }
   }
   
   public TvProgram(Tv valueOf, Program p) {
      this.tv = valueOf;
      this.programs.add(p);
   }
   
   public boolean update(Program newP) {
     boolean res = false;
     if(programs.contains(newP)) {
    	 int index = programs.indexOf(newP);
    	 programs.set(index, newP);
    	 return !res;
     }
     return res;
   }
   
   public boolean delete(String time) {
      boolean res = false;
      for(Program p : programs) {
         if(p.getProgramTime().equals(time)) {
            programs.remove(p);
            return !res;
         }
      }
      return res;
   }

	public void insert(Program p) {
		if(programs.contains(p)) {
			System.out.println("시간대 중복");
			return;
		}
		programs.add(p);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TvProgram other = (TvProgram) obj;
		return tv == other.tv;
	}
}