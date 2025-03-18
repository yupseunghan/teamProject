package t2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import t2.model.vo.BroadTime;

public interface BroadTimeDAO {

	List<BroadTime> getBroadTimeList(@Param("ch") String channel, @Param("we")String week);

}
