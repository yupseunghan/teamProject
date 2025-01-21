package teamProject;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Program {
	static int count=0;
	private String programDay,programName,programExpain,programTime;

	public String printAll() {
		return programTime+"/ "+programName;
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
		return Objects.equals(programDay, other.programDay) && Objects.equals(programTime, other.programTime);
	}


	
	
	
}
