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
