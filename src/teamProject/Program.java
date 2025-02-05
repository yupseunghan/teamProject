package teamProject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Program {


	private String programTime,programName,programExpaln;

	@Override
	public String toString() {
		return programTime+"시 "+programName+" | 장르: "+programExpaln;
	}
	

	}

	public boolean checkDay(Program searP) {
		if(programDay.equals(searP.getProgramDay())) {
			return true;

		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Program other = (Program) obj;
		return Objects.equals(Day, other.Day) && Objects.equals(Expain, other.Expain)
				&& Objects.equals(Name, other.Name) && Objects.equals(Time, other.Time);
	}

	@Override
	public String toString() {
		return Day + " - " + Time + " - " + Name + " - " + Expain;
	}



}
