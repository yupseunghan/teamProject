package teamProject;

<<<<<<< Updated upstream
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
=======
>>>>>>> Stashed changes
import lombok.Data;

@Data
public class Program {
<<<<<<< Updated upstream
	private Date programDate;
	private String startTime,endTime;
	private String programName,programExpain;
	
	@Override
	public String toString() {
		return "["+getDateStr()+" "+startTime+" ~ "+endTime+"] "+programName+" "+programExpain;
	}
	
	public void setDate(String dateTime) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		this.programDate=format.parse(dateTime);
	}
	
	private String getDateStr() {
		if(programDate == null)
			return null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(programDate);
=======
	private Tv tv;
	private String programName,programTime,programExpaln;
	public void print() {
		System.out.println("==========="+tv+" 채널===========");
		System.out.println(programTime+"시 "+" | "+programName+" : "+programExpaln);
>>>>>>> Stashed changes
	}
}
