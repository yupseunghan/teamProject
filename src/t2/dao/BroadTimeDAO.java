package t2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import t2.model.vo.BroadTime;

public interface BroadTimeDAO {

	List<BroadTime> getBroadTimeList(@Param("ch") String channel, @Param("we")String week);

	BroadTime selectBt(@Param("vw_key")int vw_key, @Param("bt_date")String day, @Param("bt_st")String selectedST);

	void insertBt(@Param("vw_key")int vw_key, @Param("bt_date")String day, @Param("bt_st")String startTime,@Param("bt_end") String endTime);

	void deleteBt(@Param("bt_num")int bt_num);

}
