package hdj;

import java.util.List;
import java.util.Objects;

public class Broad_Program {

	private BroadcastingCompany broadCom; //방송사
	private String date; //방영날짜
	private String time; //방영시간
	private String name; //프로그램 제목
	private String explan; //프로그램 설명
	
	List<Program> list; //편성표 출력

	public Broad_Program(String date, String time, String name, String explan, List<Program> list) {
		this.date = date;
		this.time = time;
		this.name = name;
		this.explan = explan;
		this.list = list;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Broad_Program other = (Broad_Program) obj;
		return Objects.equals(date, other.date) && Objects.equals(explan, other.explan)
				&& Objects.equals(list, other.list) && Objects.equals(name, other.name)
				&& Objects.equals(time, other.time);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
}
