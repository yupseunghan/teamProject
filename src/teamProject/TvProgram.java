package Tv2;

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
   List<Program> programs=new ArrayList<Program>();
   public String getTv() {
      return tv.name();
   }
   public void showTv(String menu) {
	   if(programs == null) {
		   System.out.println("채널이 비어있습니다");
		   return;
	   }
		  
	   if(tv.equals(Tv.valueOf(menu))) {
         for(Program p:programs) {
            System.out.println (p.toString());
         }
      }
   }
   public TvProgram(Tv valueOf, Program p) {
      this.tv=valueOf;
      this.programs.add(p);
   }
   public boolean update(Program newP) {
     boolean res=false;
      for(Program p : programs) {
         if(p.getProgramTime().equals(newP.getProgramTime())) {
            int index = programs.indexOf(p);
            programs.set(index, newP);
            return !res;
         }
      }
      return res;
   }
   public int delete(String time) {
	   int index=-1;
	   for(Program p : programs) {
		   if(p.getProgramTime().equals(time)) {
			   return programs.indexOf(p);
		   }
	   }
	   return index;
   }
	public void insert(Program p) {
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