package t2.dao;

import org.apache.ibatis.annotations.Param;

public interface ProgramAgeDAO {

	int getAgeKey(@Param("age")String pRAgeText);

}
