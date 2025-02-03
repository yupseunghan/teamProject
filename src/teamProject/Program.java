package teamProject;

import java.nio.file.attribute.UserPrincipal;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Program {
	static int count=0;
	private String Day, Time, Name, Expain;

	public String printAll() {
<<<<<<< Updated upstream
		return programName+" / "+programTime;
=======
		return Time + " / " + Name;
>>>>>>> Stashed changes
	}

	public boolean checkDay(Program searP) {
		if(programDay.equals(searP.getProgramDay())) {
			return true;
<<<<<<< Updated upstream
		}
		return false;
	}
	
=======
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


>>>>>>> Stashed changes
}
