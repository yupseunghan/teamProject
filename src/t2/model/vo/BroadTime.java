package t2.model.vo;

import lombok.Data;

@Data
public class BroadTime {
	private int bt_num, bt_vw_key;
	private String bt_date, bt_startTime,bt_endTime;
	private String ch_name;
	private String we_name;
	private String pg_name;
	private String pr_gr_name;
	private String pa_age;
	private String vw_state;
	
	@Override
	public String toString() {
		return ch_name+" "+bt_date+" "+we_name+" "+pg_name+" "+pr_gr_name+" "+pa_age+" "+vw_state+" "
	+bt_startTime+" ~ "+bt_endTime;
	}
	
	
}
