package teamProject;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TvProgram {
	Tv tv;
	List<Program> programs;
	public void insert(Program p) {
		programs.add(p);
	}
}
