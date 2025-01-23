package teamProject;

import java.nio.file.attribute.UserPrincipal;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Program {
	static int count=0;
	private String programDay,programName,programExpain,programTime;

	public String printAll() {
		return programName+" / "+programTime;
	}

	public boolean checkDay(Program searP) {
		if(programDay.equals(searP.getProgramDay())) {
			return true;
		}
		return false;
	}
	
}
