package t2.dao;

import org.apache.ibatis.annotations.Param;

import t2.model.vo.View;

public interface ViewDAO {

	View selectView(@Param("cp_num")int cp_num, @Param("week")String selectedDay, @Param("state")String selectedState);

	void insertView(@Param("cp_num")int cp_num, @Param("week")String selectedDay,@Param("state") String selectedState);

	
}
