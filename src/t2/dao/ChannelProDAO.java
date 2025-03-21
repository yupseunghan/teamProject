package t2.dao;

import org.apache.ibatis.annotations.Param;

import t2.model.vo.Channel;
import t2.model.vo.ChannelPro;

public interface ChannelProDAO {

	ChannelPro selectCp(@Param("pg")String selectedPR, @Param("ch")String selectedTV);

	void insertCp(@Param("pg")String selectedPR, @Param("ch")String selectedTV);

	

}
