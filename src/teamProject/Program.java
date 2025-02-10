package teamProject;


import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Program implements Serializable{

private static final long serialVersionUID = 7084410164360690259L;

private String programTime,programName,programExpaln;

   @Override
   public String toString() {
      return programTime+"시 "+programName+" | 장르: "+programExpaln;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      Program other = (Program) obj;
      return Objects.equals(programTime, other.programTime);
   }
   
}
