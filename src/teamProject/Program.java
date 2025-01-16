package teamProject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Program {
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
	}
}
