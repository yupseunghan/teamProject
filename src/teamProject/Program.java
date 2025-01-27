package teamProject;


import lombok.Data;

@Data
public class Program {

	private Tv tv;
	private String programName,programTime,programExpaln;
	public void print() {
		System.out.println("==========="+tv+" 채널===========");
		System.out.println(programTime+"시 "+" | "+programName+" : "+programExpaln);
	}
}
